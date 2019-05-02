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
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.RoleInfoMapper;
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

@Repository("tanyaRoleInfoDao")
public class RoleInfoDao {

    @Autowired
    RoleInfoMapper roleInfoMapper;

    @CacheEvict(value = "RoleInfo", allEntries = true)
    public RoleInfo updateRoleInfo(RoleInfo roleInfo) {
        int res = 0;
        if (roleInfo.getId() == null) {
            roleInfo.setCreateAt(new Date());
            res = roleInfoMapper.insertSelective(roleInfo);
        } else {
            roleInfo.setUpdateAt(new Date());
            res = roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
        }
        if (res == 0) {
            throw new ServiceException("update RoleInfo error");
        }
        return roleInfo;
    }

    @CacheEvict(value = "RoleInfo", allEntries = true)
    public RoleInfo updateRoleInfoStrict(RoleInfo roleInfo) {
        int res = 0;
        if (roleInfo.getId() == null) {
            roleInfo.setCreateAt(new Date());
            res = roleInfoMapper.insert(roleInfo);
        } else {
            roleInfo.setUpdateAt(new Date());
            res = roleInfoMapper.updateByPrimaryKey(roleInfo);
        }
        if (res == 0) {
            throw new ServiceException("update RoleInfo error");
        }
        return roleInfo;
    }

    @CacheEvict(value = "RoleInfo", allEntries = true)
    public Integer updateRoleInfoByExample(RoleInfo roleInfo, RoleInfoExample example) {
        return roleInfoMapper.updateByExampleSelective(roleInfo, example);
    }

    @CacheEvict(value = "RoleInfo", allEntries = true)
    public Integer updateRoleInfoByExampleStrict(RoleInfo roleInfo, RoleInfoExample example) {
        return roleInfoMapper.updateByExample(roleInfo, example);
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
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return roleInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "RoleInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<RoleInfo> getAllRoleInfoList(Byte valid, PageInfo<?> pageInfo) {
        RoleInfoExample example = new RoleInfoExample();
        RoleInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<RoleInfo> res = roleInfoMapper.selectByExample(example);
        return new PageInfo<RoleInfo>(res);
    }

    @Cacheable(value = "RoleInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public RoleInfo getRoleInfoById(Integer id) {
        return roleInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "RoleInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<RoleInfo> getRoleInfoSelective(RoleInfo roleInfo, PageInfo<?> pageInfo) {
        RoleInfoExample example = getRoleInfoExample(roleInfo);
        PageHelper.startPage(pageInfo);
        List<RoleInfo> res = roleInfoMapper.selectByExample(example);
        return new PageInfo<RoleInfo>(res);
    }

    @Cacheable(value = "RoleInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<RoleInfo> getRoleInfoSelective(RoleInfo roleInfo) {
        RoleInfoExample example = getRoleInfoExample(roleInfo);
        List<RoleInfo> res = roleInfoMapper.selectByExample(example);
        return res;
    }

    public PageInfo<RoleInfo> getRoleInfoByExample(RoleInfoExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<RoleInfo> res = roleInfoMapper.selectByExample(example);
        return new PageInfo<RoleInfo>(res);
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
