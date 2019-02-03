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
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.RoleInfoMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: RoleInfoDao
 * @Description: TODO
 */
@Repository("tanyaRoleInfoDao")
public class RoleInfoDao {

    @Autowired
    RoleInfoMapper roleInfoMapper;

    @CacheEvict(value = "RoleInfo", allEntries = true)
    public RoleInfo updateRoleInfo(RoleInfo roleInfo) {
        if (roleInfo.getId() == null) {
            roleInfo.setCreateAt(new Date());
            roleInfoMapper.insertSelective(roleInfo);
        } else {
            roleInfo.setUpdateAt(new Date());
            roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
        }
        return roleInfo;
    }

    @CacheEvict(value = "RoleInfo", allEntries = true)
    public Integer updateRoleInfoByExample(RoleInfo roleInfo, RoleInfoExample example) {
        return roleInfoMapper.updateByExampleSelective(roleInfo, example);
    }

    @CacheEvict(value = "RoleInfo", allEntries = true)
    public Integer delRoleInfo(RoleInfo roleInfo) {
        RoleInfoExample example = getRoleInfoExample(roleInfo);
        roleInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return roleInfoMapper.updateByExampleSelective(roleInfo, example);
    }

    @CacheEvict(value = "RoleInfo", allEntries = true)
    public Integer delRoleInfoByExample(RoleInfoExample example) {
        Integer res = 0;
        List<RoleInfo> roleInfoList = getRoleInfoByExample(example);
        for (RoleInfo roleInfo : roleInfoList) {
            roleInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += roleInfoMapper.updateByPrimaryKey(roleInfo);
        }
        return res;
    }

    public Long countRoleInfo(RoleInfo roleInfo) {
        RoleInfoExample example = getRoleInfoExample(roleInfo);
        return roleInfoMapper.countByExample(example);
    }

    public Long countRoleInfoByExample(RoleInfoExample example) {
        return roleInfoMapper.countByExample(example);
    }

    @Cacheable(value = "RoleInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<RoleInfo> getAllRoleInfoList(Byte valid) {
        RoleInfoExample example = new RoleInfoExample();
        RoleInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return roleInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "RoleInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public RoleInfo getRoleInfobyId(Integer id) {
        return roleInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "RoleInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<RoleInfo> getRoleInfoSelective(RoleInfo roleInfo) {
        RoleInfoExample example = getRoleInfoExample(roleInfo);
        List<RoleInfo> res = roleInfoMapper.selectByExample(example);
        return res;
    }

    public List<RoleInfo> getRoleInfoByExample(RoleInfoExample example) {
        List<RoleInfo> res = roleInfoMapper.selectByExample(example);
        return res;
    }

    private RoleInfoExample getRoleInfoExample(RoleInfo roleInfo) {
        RoleInfoExample example = new RoleInfoExample();
        RoleInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(roleInfo);
        ReflectionUtil.getFieldList(roleInfo).forEach((field) -> {
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