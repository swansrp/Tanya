/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository
 * @author: sharuopeng
 * @date: 2019/04/21
 */
package com.srct.service.tanya.common.datalayer.tanya.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.redis.CacheExpire;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.FactoryMerchantMapMapper;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository("tanyaFactoryMerchantMapDao")
public class FactoryMerchantMapDao {

    @Autowired
    FactoryMerchantMapMapper factoryMerchantMapMapper;

    @CacheEvict(value = "FactoryMerchantMap", allEntries = true)
    public FactoryMerchantMap updateFactoryMerchantMap(FactoryMerchantMap factoryMerchantMap) {
        int res = 0;
        if (factoryMerchantMap.getId() == null) {
            factoryMerchantMap.setCreateAt(new Date());
            res = factoryMerchantMapMapper.insertSelective(factoryMerchantMap);
        } else {
            factoryMerchantMap.setUpdateAt(new Date());
            res = factoryMerchantMapMapper.updateByPrimaryKeySelective(factoryMerchantMap);
        }
        if (res == 0) {
            throw new ServiceException("update FactoryMerchantMap error");
        }
        return factoryMerchantMap;
    }

    @CacheEvict(value = "FactoryMerchantMap", allEntries = true)
    public FactoryMerchantMap updateFactoryMerchantMapStrict(FactoryMerchantMap factoryMerchantMap) {
        int res = 0;
        if (factoryMerchantMap.getId() == null) {
            factoryMerchantMap.setCreateAt(new Date());
            res = factoryMerchantMapMapper.insert(factoryMerchantMap);
        } else {
            factoryMerchantMap.setUpdateAt(new Date());
            res = factoryMerchantMapMapper.updateByPrimaryKey(factoryMerchantMap);
        }
        if (res == 0) {
            throw new ServiceException("update FactoryMerchantMap error");
        }
        return factoryMerchantMap;
    }

    @CacheEvict(value = "FactoryMerchantMap", allEntries = true)
    public Integer updateFactoryMerchantMapByExample(FactoryMerchantMap factoryMerchantMap,
            FactoryMerchantMapExample example) {
        return factoryMerchantMapMapper.updateByExampleSelective(factoryMerchantMap, example);
    }

    @CacheEvict(value = "FactoryMerchantMap", allEntries = true)
    public Integer updateFactoryMerchantMapByExampleStrict(FactoryMerchantMap factoryMerchantMap,
            FactoryMerchantMapExample example) {
        return factoryMerchantMapMapper.updateByExample(factoryMerchantMap, example);
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
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return factoryMerchantMapMapper.selectByExample(example);
    }

    @Cacheable(value = "FactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<FactoryMerchantMap> getAllFactoryMerchantMapList(Byte valid, PageInfo<?> pageInfo) {
        FactoryMerchantMapExample example = new FactoryMerchantMapExample();
        FactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<FactoryMerchantMap> res = factoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<FactoryMerchantMap>(res);
    }

    @Cacheable(value = "FactoryMerchantMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public FactoryMerchantMap getFactoryMerchantMapById(Integer id) {
        return factoryMerchantMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "FactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<FactoryMerchantMap> getFactoryMerchantMapSelective(FactoryMerchantMap factoryMerchantMap,
            PageInfo<?> pageInfo) {
        FactoryMerchantMapExample example = getFactoryMerchantMapExample(factoryMerchantMap);
        PageHelper.startPage(pageInfo);
        List<FactoryMerchantMap> res = factoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<FactoryMerchantMap>(res);
    }

    @Cacheable(value = "FactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<FactoryMerchantMap> getFactoryMerchantMapSelective(FactoryMerchantMap factoryMerchantMap) {
        FactoryMerchantMapExample example = getFactoryMerchantMapExample(factoryMerchantMap);
        List<FactoryMerchantMap> res = factoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    public PageInfo<FactoryMerchantMap> getFactoryMerchantMapByExample(FactoryMerchantMapExample example,
            PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<FactoryMerchantMap> res = factoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<FactoryMerchantMap>(res);
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
                    e.printStackTrace();
                }
                if (criteriaMethod == null)
                    throw new ServiceException("");
                try {
                    criteriaMethod.invoke(criteria, valueMap.get(field.getName()));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        return example;
    }
}
