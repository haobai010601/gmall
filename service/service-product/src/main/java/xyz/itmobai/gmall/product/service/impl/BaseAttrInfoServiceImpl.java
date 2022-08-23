package xyz.itmobai.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.itmobai.gmall.model.product.BaseAttrInfo;
import xyz.itmobai.gmall.model.product.BaseAttrValue;
import xyz.itmobai.gmall.product.mapper.BaseAttrInfoMapper;
import xyz.itmobai.gmall.product.mapper.BaseAttrValueMapper;
import xyz.itmobai.gmall.product.service.BaseAttrInfoService;

import java.util.ArrayList;
import java.util.List;

/**
* @author Hi
* @description 针对表【base_attr_info(属性表)】的数据库操作Service实现
* @createDate 2022-08-22 22:19:19
*/
@Service
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoMapper, BaseAttrInfo> implements BaseAttrInfoService{

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

//    @Override
//    public List<BaseAttrInfo> getAttrInfoList(Long category1Id, Long category2Id, Long category3Id) {
//        return baseAttrInfoMapper.selectBaseAttrInfoList(category1Id,category2Id,category3Id);
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //判断AttrInfo是应该修改还是新增，并修改标签
        if (baseAttrInfo.getId() == null) {
            baseAttrInfoMapper.insert(baseAttrInfo);
        }else {
            baseAttrInfoMapper.updateById(baseAttrInfo);
        }

        //获取本次修改或保存的AttrInfo的attrValueList
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();

        //获取本次修改的AttrInfo的所有保留的原有AttrValue
        List<Long> vidList = new ArrayList<>();
        attrValueList.forEach((attrValue)->{
            vidList.add(attrValue.getId());
        });

        //先删除所有数据库存在，但本次数据中没有的数据。即要删除的数据
        QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id", baseAttrInfo.getId());
        //如果vidList中数据为0,并且，则代表
        if (vidList.size() > 0){
            wrapper.notIn("id",vidList);
            baseAttrValueMapper.delete(wrapper);
        }else{
            baseAttrValueMapper.delete(wrapper);
        }

        //再新增AttrValue
        if (attrValueList != null && attrValueList.size() > 0){
            attrValueList.forEach((attrValue) ->{
                if (attrValue.getId() == null) {
                    attrValue.setAttrId(baseAttrInfo.getId());
                    baseAttrValueMapper.insert(attrValue);
                }else {
                    baseAttrValueMapper.updateById(attrValue);
                }
            });
        }
    }

    @Override
    public List<BaseAttrInfo> getAttrInfoList(Long category1Id, Long category2Id, Long category3Id) {
        QueryWrapper<BaseAttrInfo> wrapper = new QueryWrapper<>();

        if (category1Id != null && category1Id != 0){
            wrapper.or().eq("category_id",category1Id).eq("category_level",1);
        }
        if (category2Id != null && category2Id != 0){
            wrapper.or().eq("category_id",category2Id).eq("category_level",2);
        }
        if (category3Id != null && category3Id != 0){
            wrapper.or().eq("category_id",category3Id).eq("category_level",3);
        }
        return baseAttrInfoMapper.selectList(wrapper);
    }
}




