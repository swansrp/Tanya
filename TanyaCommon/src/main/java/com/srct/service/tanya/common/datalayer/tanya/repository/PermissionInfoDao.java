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
import com.srct.service.tanya.common.datalayer.tanya.entity.PermissionInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.PermissionInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.PermissionInfoMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: PermissionInfoDao
 * @Description: TODO
 */
@Repository("tanyaPermissionInfoDao")
public class PermissionInfoDao {

    @Autowired
    PermissionInfoMapper permissionInfoMapper;

    @CacheEvict(value = "PermissionInfo", allEntries = true)
    public PermissionInfo updatePermissionInfo(PermissionInfo permissionInfo) {
        if (permissionInfo.getId() == null) {
            permissionInfo.setCreateAt(new Date());
            permissionInfoMapper.insertSelective(permissionInfo);
        } else {
            permissionInfo.setUpdateAt(new Date());
            permissionInfoMapper.updateByPrimaryKeySelective(permissionInfo);
        }
        return permissionInfo;
    }

    @CacheEvict(value = "PermissionInfo", allEntries = true)
    public Integer updatePermissionInfoByExample(PermissionInfo permissionInfo, PermissionInfoExample example) {
        return permissionInfoMapper.updateByExampleSelective(permissionInfo, example);
    }

    @CacheEvict(value = "PermissionInfo", allEntries = true)
    public Integer delPermissionInfo(PermissionInfo permissionInfo) {
        PermissionInfoExample example = getPermissionInfoExample(permissionInfo);
        permissionInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return permissionInfoMapper.updateByExampleSelective(permissionInfo, example);
    }

    @CacheEvict(value = "PermissionInfo", allEntries = true)
    public Integer delPermissionInfoByExample(PermissionInfoExample example) {
        Integer res = 0;
        List<PermissionInfo> permissionInfoList = getPermissionInfoByExample(example);
        for (PermissionInfo permissionInfo : permissionInfoList) {
            permissionInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += permissionInfoMapper.updateByPrimaryKey(permissionInfo);
        }
        return res;
    }

    public Long countPermissionInfo(PermissionInfo permissionInfo) {
        PermissionInfoExample example = getPermissionInfoExample(permissionInfo);
        return permissionInfoMapper.countByExample(example);
    }

    public Long countPermissionInfoByExample(PermissionInfoExample example) {
        return permissionInfoMapper.countByExample(example);
    }

    @Cacheable(value = "PermissionInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<PermissionInfo> getAllPermissionInfoList(Byte valid) {
        PermissionInfoExample example = new PermissionInfoExample();
        PermissionInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return permissionInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "PermissionInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public PermissionInfo getPermissionInfobyId(Integer id) {
        return permissionInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "PermissionInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<PermissionInfo> getPermissionInfoSelective(PermissionInfo permissionInfo) {
        PermissionInfoExample example = getPermissionInfoExample(permissionInfo);
        List<PermissionInfo> res = permissionInfoMapper.selectByExample(example);
        return res;
    }

    public List<PermissionInfo> getPermissionInfoByExample(PermissionInfoExample example) {
        List<PermissionInfo> res = permissionInfoMapper.selectByExample(example);
        return res;
    }

    private PermissionInfoExample getPermissionInfoExample(PermissionInfo permissionInfo) {
        PermissionInfoExample example = new PermissionInfoExample();
        PermissionInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(permissionInfo);
        ReflectionUtil.getFieldList(permissionInfo).forEach((field) -> {
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