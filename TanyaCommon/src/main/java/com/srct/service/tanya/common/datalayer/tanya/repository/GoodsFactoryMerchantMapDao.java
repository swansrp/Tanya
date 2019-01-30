

/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository 
 * @author: Sharp   
 * @date: 2019/01/30
 */
package com.srct.service.tanya.common.datalayer.tanya.repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.redis.CacheExpire;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.GoodsFactoryMerchantMapMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: GoodsFactoryMerchantMapDao
 * @Description: TODO
 */
@Repository("tanyaGoodsFactoryMerchantMapDao")
public class GoodsFactoryMerchantMapDao {

    @Autowired
    GoodsFactoryMerchantMapMapper goodsFactoryMerchantMapMapper;

    @CacheEvict(value = "GoodsFactoryMerchantMap", allEntries = true)
    public GoodsFactoryMerchantMap updateGoodsFactoryMerchantMap(GoodsFactoryMerchantMap goodsFactoryMerchantMap) {
        if (goodsFactoryMerchantMap.getId() == null) {
            GoodsFactoryMerchantMapExample example = getGoodsFactoryMerchantMapExample(goodsFactoryMerchantMap);
            goodsFactoryMerchantMap.setUpdateAt(new Date());
            int updateNum = goodsFactoryMerchantMapMapper.updateByExampleSelective(goodsFactoryMerchantMap, example);
            if (updateNum == 0) {
                goodsFactoryMerchantMap.setCreateAt(new Date());
                goodsFactoryMerchantMapMapper.insertSelective(goodsFactoryMerchantMap);
            }
        } else {
            goodsFactoryMerchantMap.setUpdateAt(new Date());
            goodsFactoryMerchantMapMapper.updateByPrimaryKeySelective(goodsFactoryMerchantMap);
        }
        return goodsFactoryMerchantMap;
    }

    @Cacheable(value = "GoodsFactoryMerchantMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<GoodsFactoryMerchantMap> getAllGoodsFactoryMerchantMapList(Byte valid) {
        GoodsFactoryMerchantMapExample example = new GoodsFactoryMerchantMapExample();
        GoodsFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return goodsFactoryMerchantMapMapper.selectByExample(example);
    }
    
    @Cacheable(value = "GoodsFactoryMerchantMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public GoodsFactoryMerchantMap getGoodsFactoryMerchantMapbyId(Integer id) {
        return goodsFactoryMerchantMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "GoodsFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapSelective(GoodsFactoryMerchantMap goodsFactoryMerchantMap) {
        GoodsFactoryMerchantMapExample example = getGoodsFactoryMerchantMapExample(goodsFactoryMerchantMap);
        List<GoodsFactoryMerchantMap> res = goodsFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    private GoodsFactoryMerchantMapExample getGoodsFactoryMerchantMapExample(GoodsFactoryMerchantMap goodsFactoryMerchantMap) {
        GoodsFactoryMerchantMapExample example = new GoodsFactoryMerchantMapExample();
        GoodsFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(goodsFactoryMerchantMap);
        ReflectionUtil.getFieldList(goodsFactoryMerchantMap).forEach((field) -> {
            if (valueMap.get(field.getName()) != null) {
                Method criteriaMethod = null;
                try {
                    String criteriaMethodName = "and" + StringUtil.firstUpperCamelCase(field.getName()) + "EqualTo";
                    criteriaMethod = criteria.getClass().getMethod(criteriaMethodName, field.getType());
                } catch (NoSuchMethodException | SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (criteriaMethod == null)
                    throw new ServiceException("");
                try {
                    criteriaMethod.invoke(criteria, valueMap.get(field.getName()));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        return example;
    }
}