

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
        if (factoryInfo.getId() == null) {
            FactoryInfoExample example = getFactoryInfoExample(factoryInfo);
            factoryInfo.setUpdateAt(new Date());
            int updateNum = factoryInfoMapper.updateByExampleSelective(factoryInfo, example);
            if (updateNum == 0) {
                factoryInfo.setCreateAt(new Date());
                factoryInfoMapper.insertSelective(factoryInfo);
            }
        } else {
            factoryInfo.setUpdateAt(new Date());
            factoryInfoMapper.updateByPrimaryKeySelective(factoryInfo);
        }
        return factoryInfo;
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
    public List<FactoryInfo> getFactoryInfoSelective(FactoryInfo factoryInfo) {
        FactoryInfoExample example = getFactoryInfoExample(factoryInfo);
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