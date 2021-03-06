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
import com.srct.service.tanya.common.datalayer.tanya.entity.RolePermissionMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.RolePermissionMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.RolePermissionMapMapper;
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

@Repository("tanyaRolePermissionMapDao")
public class RolePermissionMapDao {

    @Autowired
    RolePermissionMapMapper rolePermissionMapMapper;

    @CacheEvict(value = "RolePermissionMap", allEntries = true)
    public RolePermissionMap updateRolePermissionMap(RolePermissionMap rolePermissionMap) {
        int res = 0;
        if (rolePermissionMap.getId() == null) {
            rolePermissionMap.setCreateAt(new Date());
            res = rolePermissionMapMapper.insertSelective(rolePermissionMap);
        } else {
            rolePermissionMap.setUpdateAt(new Date());
            res = rolePermissionMapMapper.updateByPrimaryKeySelective(rolePermissionMap);
        }
        if (res == 0) {
            throw new ServiceException("update RolePermissionMap error");
        }
        return rolePermissionMap;
    }

    @CacheEvict(value = "RolePermissionMap", allEntries = true)
    public RolePermissionMap updateRolePermissionMapStrict(RolePermissionMap rolePermissionMap) {
        int res = 0;
        if (rolePermissionMap.getId() == null) {
            rolePermissionMap.setCreateAt(new Date());
            res = rolePermissionMapMapper.insert(rolePermissionMap);
        } else {
            rolePermissionMap.setUpdateAt(new Date());
            res = rolePermissionMapMapper.updateByPrimaryKey(rolePermissionMap);
        }
        if (res == 0) {
            throw new ServiceException("update RolePermissionMap error");
        }
        return rolePermissionMap;
    }

    @CacheEvict(value = "RolePermissionMap", allEntries = true)
    public Integer updateRolePermissionMapByExample(RolePermissionMap rolePermissionMap,
            RolePermissionMapExample example) {
        return rolePermissionMapMapper.updateByExampleSelective(rolePermissionMap, example);
    }

    @CacheEvict(value = "RolePermissionMap", allEntries = true)
    public Integer updateRolePermissionMapByExampleStrict(RolePermissionMap rolePermissionMap,
            RolePermissionMapExample example) {
        return rolePermissionMapMapper.updateByExample(rolePermissionMap, example);
    }

    @CacheEvict(value = "RolePermissionMap", allEntries = true)
    public Integer delRolePermissionMap(RolePermissionMap rolePermissionMap) {
        RolePermissionMapExample example = getRolePermissionMapExample(rolePermissionMap);
        rolePermissionMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return rolePermissionMapMapper.updateByExampleSelective(rolePermissionMap, example);
    }

    @CacheEvict(value = "RolePermissionMap", allEntries = true)
    public Integer delRolePermissionMapByExample(RolePermissionMapExample example) {
        Integer res = 0;
        List<RolePermissionMap> rolePermissionMapList = getRolePermissionMapByExample(example);
        for (RolePermissionMap rolePermissionMap : rolePermissionMapList) {
            rolePermissionMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += rolePermissionMapMapper.updateByPrimaryKey(rolePermissionMap);
        }
        return res;
    }

    public Long countRolePermissionMap(RolePermissionMap rolePermissionMap) {
        RolePermissionMapExample example = getRolePermissionMapExample(rolePermissionMap);
        return rolePermissionMapMapper.countByExample(example);
    }

    public Long countRolePermissionMapByExample(RolePermissionMapExample example) {
        return rolePermissionMapMapper.countByExample(example);
    }

    @Cacheable(value = "RolePermissionMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<RolePermissionMap> getAllRolePermissionMapList(Byte valid) {
        RolePermissionMapExample example = new RolePermissionMapExample();
        RolePermissionMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return rolePermissionMapMapper.selectByExample(example);
    }

    @Cacheable(value = "RolePermissionMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<RolePermissionMap> getAllRolePermissionMapList(Byte valid, PageInfo<?> pageInfo) {
        RolePermissionMapExample example = new RolePermissionMapExample();
        RolePermissionMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<RolePermissionMap> res = rolePermissionMapMapper.selectByExample(example);
        return new PageInfo<RolePermissionMap>(res);
    }

    @Cacheable(value = "RolePermissionMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public RolePermissionMap getRolePermissionMapById(Integer id) {
        return rolePermissionMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "RolePermissionMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<RolePermissionMap> getRolePermissionMapSelective(RolePermissionMap rolePermissionMap,
            PageInfo<?> pageInfo) {
        RolePermissionMapExample example = getRolePermissionMapExample(rolePermissionMap);
        PageHelper.startPage(pageInfo);
        List<RolePermissionMap> res = rolePermissionMapMapper.selectByExample(example);
        return new PageInfo<RolePermissionMap>(res);
    }

    @Cacheable(value = "RolePermissionMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<RolePermissionMap> getRolePermissionMapSelective(RolePermissionMap rolePermissionMap) {
        RolePermissionMapExample example = getRolePermissionMapExample(rolePermissionMap);
        List<RolePermissionMap> res = rolePermissionMapMapper.selectByExample(example);
        return res;
    }

    public PageInfo<RolePermissionMap> getRolePermissionMapByExample(RolePermissionMapExample example,
            PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<RolePermissionMap> res = rolePermissionMapMapper.selectByExample(example);
        return new PageInfo<RolePermissionMap>(res);
    }

    public List<RolePermissionMap> getRolePermissionMapByExample(RolePermissionMapExample example) {
        List<RolePermissionMap> res = rolePermissionMapMapper.selectByExample(example);
        return res;
    }

    private RolePermissionMapExample getRolePermissionMapExample(RolePermissionMap rolePermissionMap) {
        RolePermissionMapExample example = new RolePermissionMapExample();
        RolePermissionMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(rolePermissionMap);
        ReflectionUtil.getFields(rolePermissionMap).forEach((field) -> {
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
