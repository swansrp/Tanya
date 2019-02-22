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
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.UserRoleMapMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: UserRoleMapDao
 * @Description: TODO
 */
@Repository("tanyaUserRoleMapDao")
public class UserRoleMapDao {

    @Autowired
    UserRoleMapMapper userRoleMapMapper;

    @CacheEvict(value = "UserRoleMap", allEntries = true)
    public UserRoleMap updateUserRoleMap(UserRoleMap userRoleMap) {
        int res = 0;
        if (userRoleMap.getId() == null) {
            userRoleMap.setCreateAt(new Date());
            res = userRoleMapMapper.insertSelective(userRoleMap);
        } else {
            userRoleMap.setUpdateAt(new Date());
            res = userRoleMapMapper.updateByPrimaryKeySelective(userRoleMap);
        }
        if(res == 0) {
            throw new ServiceException("update UserRoleMap error");
        }
        return userRoleMap;
    }

    @CacheEvict(value = "UserRoleMap", allEntries = true)
    public UserRoleMap updateUserRoleMapStrict(UserRoleMap userRoleMap) {
        int res = 0;
        if (userRoleMap.getId() == null) {
            userRoleMap.setCreateAt(new Date());
            res = userRoleMapMapper.insert(userRoleMap);
        } else {
            userRoleMap.setUpdateAt(new Date());
            res = userRoleMapMapper.updateByPrimaryKey(userRoleMap);
        }
        if(res == 0) {
            throw new ServiceException("update UserRoleMap error");
        }
        return userRoleMap;
    }

    @CacheEvict(value = "UserRoleMap", allEntries = true)
    public Integer updateUserRoleMapByExample(UserRoleMap userRoleMap, UserRoleMapExample example) {
        return userRoleMapMapper.updateByExampleSelective(userRoleMap, example);
    }

    @CacheEvict(value = "UserRoleMap", allEntries = true)
    public Integer updateUserRoleMapByExampleStrict(UserRoleMap userRoleMap, UserRoleMapExample example) {
        return userRoleMapMapper.updateByExample(userRoleMap, example);
    }

    @CacheEvict(value = "UserRoleMap", allEntries = true)
    public Integer delUserRoleMap(UserRoleMap userRoleMap) {
        UserRoleMapExample example = getUserRoleMapExample(userRoleMap);
        userRoleMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return userRoleMapMapper.updateByExampleSelective(userRoleMap, example);
    }

    @CacheEvict(value = "UserRoleMap", allEntries = true)
    public Integer delUserRoleMapByExample(UserRoleMapExample example) {
        Integer res = 0;
        List<UserRoleMap> userRoleMapList = getUserRoleMapByExample(example);
        for (UserRoleMap userRoleMap : userRoleMapList) {
            userRoleMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += userRoleMapMapper.updateByPrimaryKey(userRoleMap);
        }
        return res;
    }

    public Long countUserRoleMap(UserRoleMap userRoleMap) {
        UserRoleMapExample example = getUserRoleMapExample(userRoleMap);
        return userRoleMapMapper.countByExample(example);
    }

    public Long countUserRoleMapByExample(UserRoleMapExample example) {
        return userRoleMapMapper.countByExample(example);
    }

    @Cacheable(value = "UserRoleMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<UserRoleMap> getAllUserRoleMapList(Byte valid) {
        UserRoleMapExample example = new UserRoleMapExample();
        UserRoleMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return userRoleMapMapper.selectByExample(example);
    }

    @Cacheable(value = "UserRoleMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public UserRoleMap getUserRoleMapbyId(Integer id) {
        return userRoleMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "UserRoleMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<UserRoleMap> getShopInfoSelective(UserRoleMap userRoleMap, PageInfo<?> pageInfo) {
        UserRoleMapExample example = getUserRoleMapExample(userRoleMap);
        PageHelper.startPage(pageInfo);
        List<UserRoleMap> res = userRoleMapMapper.selectByExample(example);
        pageInfo = new PageInfo<UserRoleMap>(res);
        return res;
    }
    @Cacheable(value = "UserRoleMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<UserRoleMap> getUserRoleMapSelective(UserRoleMap userRoleMap) {
        UserRoleMapExample example = getUserRoleMapExample(userRoleMap);
        List<UserRoleMap> res = userRoleMapMapper.selectByExample(example);
        return res;
    }

    public List<UserRoleMap> getUserRoleMapByExample(UserRoleMapExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<UserRoleMap> res = userRoleMapMapper.selectByExample(example);
        pageInfo = new PageInfo<UserRoleMap>(res);
        return res;
    }
    public List<UserRoleMap> getUserRoleMapByExample(UserRoleMapExample example) {
        List<UserRoleMap> res = userRoleMapMapper.selectByExample(example);
        return res;
    }

    private UserRoleMapExample getUserRoleMapExample(UserRoleMap userRoleMap) {
        UserRoleMapExample example = new UserRoleMapExample();
        UserRoleMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(userRoleMap);
        ReflectionUtil.getFieldList(userRoleMap).forEach((field) -> {
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