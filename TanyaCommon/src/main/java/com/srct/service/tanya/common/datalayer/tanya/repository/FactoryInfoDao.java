/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository 
 * @author: sharuopeng   
 * @date: 2019/02/23
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.redis.CacheExpire;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.FactoryInfoMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: FactoryInfoDao
 * @Description: TODO
 */
@Repository("tanyaFactoryInfoDao")
public class FactoryInfoDao {

    @Autowired
    FactoryInfoMapper factoryInfoMapper;

    @CacheEvict(value = "FactoryInfo", allEntries = true)
    public FactoryInfo updateFactoryInfo(FactoryInfo factoryInfo) {
        int res = 0;
        if (factoryInfo.getId() == null) {
            factoryInfo.setCreateAt(new Date());
            res = factoryInfoMapper.insertSelective(factoryInfo);
        } else {
            factoryInfo.setUpdateAt(new Date());
            res = factoryInfoMapper.updateByPrimaryKeySelective(factoryInfo);
        }
        if(res == 0) {
            throw new ServiceException("update FactoryInfo error");
        }
        return factoryInfo;
    }

    @CacheEvict(value = "FactoryInfo", allEntries = true)
    public FactoryInfo updateFactoryInfoStrict(FactoryInfo factoryInfo) {
        int res = 0;
        if (factoryInfo.getId() == null) {
            factoryInfo.setCreateAt(new Date());
            res = factoryInfoMapper.insert(factoryInfo);
        } else {
            factoryInfo.setUpdateAt(new Date());
            res = factoryInfoMapper.updateByPrimaryKey(factoryInfo);
        }
        if(res == 0) {
            throw new ServiceException("update FactoryInfo error");
        }
        return factoryInfo;
    }

    @CacheEvict(value = "FactoryInfo", allEntries = true)
    public Integer updateFactoryInfoByExample(FactoryInfo factoryInfo, FactoryInfoExample example) {
        return factoryInfoMapper.updateByExampleSelective(factoryInfo, example);
    }

    @CacheEvict(value = "FactoryInfo", allEntries = true)
    public Integer updateFactoryInfoByExampleStrict(FactoryInfo factoryInfo, FactoryInfoExample example) {
        return factoryInfoMapper.updateByExample(factoryInfo, example);
    }

    @CacheEvict(value = "FactoryInfo", allEntries = true)
    public Integer delFactoryInfo(FactoryInfo factoryInfo) {
        FactoryInfoExample example = getFactoryInfoExample(factoryInfo);
        factoryInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return factoryInfoMapper.updateByExampleSelective(factoryInfo, example);
    }

    @CacheEvict(value = "FactoryInfo", allEntries = true)
    public Integer delFactoryInfoByExample(FactoryInfoExample example) {
        Integer res = 0;
        List<FactoryInfo> factoryInfoList = getFactoryInfoByExample(example);
        for (FactoryInfo factoryInfo : factoryInfoList) {
            factoryInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += factoryInfoMapper.updateByPrimaryKey(factoryInfo);
        }
        return res;
    }

    public Long countFactoryInfo(FactoryInfo factoryInfo) {
        FactoryInfoExample example = getFactoryInfoExample(factoryInfo);
        return factoryInfoMapper.countByExample(example);
    }

    public Long countFactoryInfoByExample(FactoryInfoExample example) {
        return factoryInfoMapper.countByExample(example);
    }

    @Cacheable(value = "FactoryInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<FactoryInfo> getAllFactoryInfoList(Byte valid) {
        FactoryInfoExample example = new FactoryInfoExample();
        FactoryInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return factoryInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "FactoryInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public FactoryInfo getFactoryInfobyId(Integer id) {
        return factoryInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "FactoryInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<FactoryInfo> getShopInfoSelective(FactoryInfo factoryInfo, PageInfo<?> pageInfo) {
        FactoryInfoExample example = getFactoryInfoExample(factoryInfo);
        PageHelper.startPage(pageInfo);
        List<FactoryInfo> res = factoryInfoMapper.selectByExample(example);
        pageInfo = new PageInfo<FactoryInfo>(res);
        return res;
    }
    @Cacheable(value = "FactoryInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<FactoryInfo> getFactoryInfoSelective(FactoryInfo factoryInfo) {
        FactoryInfoExample example = getFactoryInfoExample(factoryInfo);
        List<FactoryInfo> res = factoryInfoMapper.selectByExample(example);
        return res;
    }

    public List<FactoryInfo> getFactoryInfoByExample(FactoryInfoExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<FactoryInfo> res = factoryInfoMapper.selectByExample(example);
        pageInfo = new PageInfo<FactoryInfo>(res);
        return res;
    }
    public List<FactoryInfo> getFactoryInfoByExample(FactoryInfoExample example) {
        List<FactoryInfo> res = factoryInfoMapper.selectByExample(example);
        return res;
    }

    private FactoryInfoExample getFactoryInfoExample(FactoryInfo factoryInfo) {
        FactoryInfoExample example = new FactoryInfoExample();
        FactoryInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(factoryInfo);
        ReflectionUtil.getFieldList(factoryInfo).forEach((field) -> {
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