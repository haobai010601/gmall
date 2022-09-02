package xyz.itmobai.gamll.cache.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.itmobai.gamll.cache.annotations.GmallCache;
import xyz.itmobai.gamll.cache.annotations.GmallCacheUpdate;
import xyz.itmobai.gamll.cache.constant.SysRedisConst;
import xyz.itmobai.gamll.cache.service.CacheOpsService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @classname: xyz.itmobai.gamll.cache.aspect.CacheAspact
 * @author: hao_bai
 * @date: 2022/9/1 18:40
 * @version: 1.0
 */
@Aspect
@Component
public class CacheAspect {

    @Autowired
    CacheOpsService cacheOpsService;

    //创建一个表达式解析器，这个是线程安全的
    ExpressionParser parser = new SpelExpressionParser();
    ParserContext context = new TemplateParserContext();
    @Around("@annotation(xyz.itmobai.gamll.cache.annotations.GmallCacheUpdate)")
    public Object aroundCacheUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        String cacheKey = determinCacheKey(joinPoint, GmallCacheUpdate.class);
        try {
            Object proceed = joinPoint.proceed(joinPoint.getArgs());
            return proceed;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            cacheOpsService.delayDeleteCache(cacheKey);
        }
    }


    @Around("@annotation(xyz.itmobai.gamll.cache.annotations.GmallCache)")
    public Object aroundCache(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取缓存键
        String cacheKey = determinCacheKey(joinPoint,GmallCache.class);
        //获取目标方法的返回值类型
        Type returnType = determinReturnType(joinPoint);
        //获取到缓存中的数据
        Object cacheData = cacheOpsService.getCacheData(cacheKey, returnType);
        //如果缓存中是空数据
        if (cacheData == null) {
            //获取Bloom过滤器名称
            String bloomName = determinBloomName(joinPoint);
            //判断是否需要使用布隆过滤
            if (!StringUtils.isEmpty(bloomName)) {
                //获取Bloom过滤器要判断的值
                Object bloomValue = determinBloomValue(joinPoint);
                //如果bloom说不存在则直接返回null
                if (!cacheOpsService.bloomContains(bloomName,bloomValue)){
                    return null;
                }
            }

            //获取本次使用的Redisson分布式锁名
            String lockName = determinLockName(joinPoint);
            //尝试获取锁
            Boolean isLock = cacheOpsService.tryLock(lockName);
            try {
                //如果获取到锁
                if (isLock) {
                    //执行目标方法，获取返回值
                    Object result = joinPoint.proceed(joinPoint.getArgs());
                    //将返回值放入缓存
                    cacheOpsService.savaCacheData(cacheKey,result);
                    //并返回
                    return result;
                //否则
                }else {
                    //程序等待1秒钟
                    TimeUnit.SECONDS.sleep(1);
                    //直接查询缓存中的值并返回
                    return cacheOpsService.getCacheData(cacheKey, returnType);
                }
            } finally {
                //如果获取到锁，则需要解锁
                if (isLock){
                    cacheOpsService.unLock(lockName);
                }
            }
        }
        return cacheData;
    }

    /**
    * 获取目标方法返回值类型
    */
    private Type determinReturnType(ProceedingJoinPoint joinPoint) {
        //获取要调用的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取目标方法返回值类型
        Type returnType = method.getGenericReturnType();
        return returnType;
    }


    /**
    * 获取本次要使用的锁的名称
    */
    private String determinLockName(ProceedingJoinPoint joinPoint) {
        //获取要调用的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取GmallCache注解实例
        GmallCache annotation = method.getDeclaredAnnotation(GmallCache.class);
        //获取锁名称表达式
        String lockNameExpression = annotation.lockName();
        if (StringUtils.isEmpty(lockNameExpression)){
            return SysRedisConst.LOCK_PREFIX + method.getName();
        }
        return evaluationExpression(lockNameExpression, joinPoint, String.class);
    }

    /**
     * 获取本次执行时布隆过滤器要判断的值
     */
    private Object determinBloomValue(ProceedingJoinPoint joinPoint) {
        //获取要调用的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取GmallCache注解实例
        GmallCache annotation = method.getDeclaredAnnotation(GmallCache.class);
        //获取Bloom过滤值表达式
        String bloomValueExpression = annotation.bloomValue();
        Object value = evaluationExpression(bloomValueExpression, joinPoint, Object.class);
        return value;
    }

    /**
     * 获取本次执行要使用的布隆过滤器名称
     */
    private String determinBloomName(ProceedingJoinPoint joinPoint) {
        //获取要调用的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取GmallCache注解实例
        GmallCache annotation = method.getDeclaredAnnotation(GmallCache.class);
        //获取Bloom名称表达式
        String bloomNameExpression = annotation.bloomName();
        return evaluationExpression(bloomNameExpression, joinPoint,String.class);
    }

    /**
    * 获取本次执行要使用的缓存键
    */
    private String determinCacheKey(ProceedingJoinPoint joinPoint) {
        //获取要调用的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取GmallCache注解实例
        GmallCache annotation = method.getDeclaredAnnotation(GmallCache.class);
        //获取缓存名称表达式表达式
        String cacheKeyExpression = annotation.cacheKey();
        return evaluationExpression(cacheKeyExpression, joinPoint,String.class);
    }
    /**
     * 获取本次执行要使用的缓存键
     */
    private String determinCacheKey(ProceedingJoinPoint joinPoint,Class<? extends Annotation> clazz) throws Exception {
        //获取要调用的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取注解实例
        Annotation annotation = method.getDeclaredAnnotation(clazz);
        //获取到注解Class
        Class<? extends Annotation> annotationClass = annotation.getClass();
        Method cacheKey = annotationClass.getDeclaredMethod("cacheKey");
        cacheKey.setAccessible(true);
        //获取缓存名称表达式表达式
        String cacheKeyExpression = (String) cacheKey.invoke(annotation);
        return evaluationExpression(cacheKeyExpression, joinPoint,String.class);
    }

    /**
    * 解析传入的表达式，并返回解析完成后的表达式成品
    */
    private<T> T evaluationExpression(String expression,
                                      ProceedingJoinPoint joinPoint,
                                      Class<T> clazz) {
        //解析表达式
        Expression exp = parser.parseExpression(expression, context);
        //获取一个上下文对象
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        //获取所有参数
        Object[] args = joinPoint.getArgs();
        //绑定到当前的上下文
        evaluationContext.setVariable("params",args);
        //获取最终结果
        T value = exp.getValue(evaluationContext, clazz);
        return value;
    }
}
