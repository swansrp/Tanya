/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository 
 * @author: srct   
 * @date: 2019/02/03
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
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.TraderFactoryMerchantMapMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: TraderFactoryMerchantMapDao
 * @Description: TODO
 */
@Repository("tanyaTraderFactoryMerchantMapDao")
public class TraderFactoryMerchantMapDao {

    @Autowired
    TraderFactoryMerchantMapMapper traderFactoryMerchantMapMapper;

    @CacheEvict(value = "TraderFactoryMerchantMap", allEntries = true)
    public TraderFactoryMerchantMap updateTraderFactoryMerchantMap(TraderFactoryMerchantMap traderFactoryMerchantMap) {
        if (traderFactoryMerchantMap.getId() == null) {
            traderFactoryMerchantMap.setCreateAt(new Date());
            traderFactoryMerchantMapMapper.insertSelective(traderFactoryMerchantMap);
        } else {
            traderFactoryMerchantMap.setUpdateAt(new Date());
            traderFactoryMerchantMapMapper.updateByPrimaryKeySelective(traderFactoryMerchantMap);
        }
        return traderFactoryMerchantMap;
    }

    @CacheEvict(value = "TraderFactoryMerchantMap", allEntries = true)
    public Integer updateTraderFactoryMerchantMapByExample(TraderFactoryMerchantMap traderFactoryMerchantMap, TraderFactoryMerchantMapExample example) {
        return traderFactoryMerchantMapMapper.updateByExampleSelective(traderFactoryMerchantMap, example);
    }

    @CacheEvict(value = "TraderFactoryMerchantMap", allEntries = true)
    public Integer delTraderFactoryMerchantMap(TraderFactoryMerchantMap traderFactoryMerchantMap) {
        TraderFactoryMerchantMapExample example = getTraderFactoryMerchantMapExample(traderFactoryMerchantMap);
        traderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return traderFactoryMerchantMapMapper.updateByExampleSelective(traderFactoryMerchantMap, example);
    }

    @CacheEvict(value = "TraderFactoryMerchantMap", allEntries = true)
    public Integer delTraderFactoryMerchantMapByExample(TraderFactoryMerchantMapExample example) {
        Integer res = 0;
        List<TraderFactoryMerchantMap> traderFactoryMerchantMapList = getTraderFactoryMerchantMapByExample(example);
        for (TraderFactoryMerchantMap traderFactoryMerchantMap : traderFactoryMerchantMapList) {
            traderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += traderFactoryMerchantMapMapper.updateByPrimaryKey(traderFactoryMerchantMap);
        }
        return res;
    }

    public Long countTraderFactoryMerchantMap(TraderFactoryMerchantMap traderFactoryMerchantMap) {
        TraderFactoryMerchantMapExample example = getTraderFactoryMerchantMapExample(traderFactoryMerchantMap);
        return traderFactoryMerchantMapMapper.countByExample(example);
    }

    public Long countTraderFactoryMerchantMapByExample(TraderFactoryMerchantMapExample example) {
        return traderFactoryMerchantMapMapper.countByExample(example);
    }

    @Cacheable(value = "TraderFactoryMerchantMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<TraderFactoryMerchantMap> getAllTraderFactoryMerchantMapList(Byte valid) {
        TraderFactoryMerchantMapExample example = new TraderFactoryMerchantMapExample();
        TraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return traderFactoryMerchantMapMapper.selectByExample(example);
    }

    @Cacheable(value = "TraderFactoryMerchantMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public TraderFactoryMerchantMap getTraderFactoryMerchantMapbyId(Integer id) {
        return traderFactoryMerchantMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "TraderFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<TraderFactoryMerchantMap> getTraderFactoryMerchantMapSelective(TraderFactoryMerchantMap traderFactoryMerchantMap) {
        TraderFactoryMerchantMapExample example = getTraderFactoryMerchantMapExample(traderFactoryMerchantMap);
        List<TraderFactoryMerchantMap> res = traderFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    public List<TraderFactoryMerchantMap> getTraderFactoryMerchantMapByExample(TraderFactoryMerchantMapExample example) {
        List<TraderFactoryMerchantMap> res = traderFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    private TraderFactoryMerchantMapExample getTraderFactoryMerchantMapExample(TraderFactoryMerchantMap traderFactoryMerchantMap) {
        TraderFactoryMerchantMapExample example = new TraderFactoryMerchantMapExample();
        TraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(traderFactoryMerchantMap);
        ReflectionUtil.getFieldList(traderFactoryMerchantMap).forEach((field) -> {
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