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
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.FactoryMerchantMapMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: FactoryMerchantMapDao
 * @Description: TODO
 */
@Repository("tanyaFactoryMerchantMapDao")
public class FactoryMerchantMapDao {

    @Autowired
    FactoryMerchantMapMapper factoryMerchantMapMapper;

    @CacheEvict(value = "FactoryMerchantMap", allEntries = true)
    public FactoryMerchantMap updateFactoryMerchantMap(FactoryMerchantMap factoryMerchantMap) {
        if (factoryMerchantMap.getId() == null) {
            factoryMerchantMap.setCreateAt(new Date());
            factoryMerchantMapMapper.insertSelective(factoryMerchantMap);
        } else {
            factoryMerchantMap.setUpdateAt(new Date());
            factoryMerchantMapMapper.updateByPrimaryKeySelective(factoryMerchantMap);
        }
        return factoryMerchantMap;
    }

    @CacheEvict(value = "FactoryMerchantMap", allEntries = true)
    public Integer updateFactoryMerchantMapByExample(FactoryMerchantMap factoryMerchantMap, FactoryMerchantMapExample example) {
        return factoryMerchantMapMapper.updateByExampleSelective(factoryMerchantMap, example);
    }

    @CacheEvict(value = "FactoryMerchantMap", allEntries = true)
    public Integer delFactoryMerchantMap(FactoryMerchantMap factoryMerchantMap) {
        FactoryMerchantMapExample example = getFactoryMerchantMapExample(factoryMerchantMap);
        factoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return factoryMerchantMapMapper.updateByExampleSelective(factoryMerchantMap, example);
    }

    @CacheEvict(value = "FactoryMerchantMap", allEntries = true)
    public Integer delFactoryMerchantMapByExample(FactoryMerchantMapExample example) {
        Integer res = 0;
        List<FactoryMerchantMap> factoryMerchantMapList = getFactoryMerchantMapByExample(example);
        for (FactoryMerchantMap factoryMerchantMap : factoryMerchantMapList) {
            factoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += factoryMerchantMapMapper.updateByPrimaryKey(factoryMerchantMap);
        }
        return res;
    }

    public Long countFactoryMerchantMap(FactoryMerchantMap factoryMerchantMap) {
        FactoryMerchantMapExample example = getFactoryMerchantMapExample(factoryMerchantMap);
        return factoryMerchantMapMapper.countByExample(example);
    }

    public Long countFactoryMerchantMapByExample(FactoryMerchantMapExample example) {
        return factoryMerchantMapMapper.countByExample(example);
    }

    @Cacheable(value = "FactoryMerchantMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<FactoryMerchantMap> getAllFactoryMerchantMapList(Byte valid) {
        FactoryMerchantMapExample example = new FactoryMerchantMapExample();
        FactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return factoryMerchantMapMapper.selectByExample(example);
    }

    @Cacheable(value = "FactoryMerchantMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public FactoryMerchantMap getFactoryMerchantMapbyId(Integer id) {
        return factoryMerchantMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "FactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<FactoryMerchantMap> getFactoryMerchantMapSelective(FactoryMerchantMap factoryMerchantMap) {
        FactoryMerchantMapExample example = getFactoryMerchantMapExample(factoryMerchantMap);
        List<FactoryMerchantMap> res = factoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    public List<FactoryMerchantMap> getFactoryMerchantMapByExample(FactoryMerchantMapExample example) {
        List<FactoryMerchantMap> res = factoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    private FactoryMerchantMapExample getFactoryMerchantMapExample(FactoryMerchantMap factoryMerchantMap) {
        FactoryMerchantMapExample example = new FactoryMerchantMapExample();
        FactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(factoryMerchantMap);
        ReflectionUtil.getFieldList(factoryMerchantMap).forEach((field) -> {
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