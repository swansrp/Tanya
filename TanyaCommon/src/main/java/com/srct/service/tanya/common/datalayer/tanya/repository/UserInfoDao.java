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
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.UserInfoMapper;
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

@Repository("tanyaUserInfoDao")
public class UserInfoDao {

    @Autowired
    UserInfoMapper userInfoMapper;

    @CacheEvict(value = "UserInfo", allEntries = true)
    public UserInfo updateUserInfo(UserInfo userInfo) {
        int res = 0;
        if (userInfo.getId() == null) {
            userInfo.setCreateAt(new Date());
            res = userInfoMapper.insertSelective(userInfo);
        } else {
            userInfo.setUpdateAt(new Date());
            res = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }
        if (res == 0) {
            throw new ServiceException("update UserInfo error");
        }
        return userInfo;
    }

    @CacheEvict(value = "UserInfo", allEntries = true)
    public UserInfo updateUserInfoStrict(UserInfo userInfo) {
        int res = 0;
        if (userInfo.getId() == null) {
            userInfo.setCreateAt(new Date());
            res = userInfoMapper.insert(userInfo);
        } else {
            userInfo.setUpdateAt(new Date());
            res = userInfoMapper.updateByPrimaryKey(userInfo);
        }
        if (res == 0) {
            throw new ServiceException("update UserInfo error");
        }
        return userInfo;
    }

    @CacheEvict(value = "UserInfo", allEntries = true)
    public Integer updateUserInfoByExample(UserInfo userInfo, UserInfoExample example) {
        return userInfoMapper.updateByExampleSelective(userInfo, example);
    }

    @CacheEvict(value = "UserInfo", allEntries = true)
    public Integer updateUserInfoByExampleStrict(UserInfo userInfo, UserInfoExample example) {
        return userInfoMapper.updateByExample(userInfo, example);
    }

    @CacheEvict(value = "UserInfo", allEntries = true)
    public Integer delUserInfo(UserInfo userInfo) {
        UserInfoExample example = getUserInfoExample(userInfo);
        userInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return userInfoMapper.updateByExampleSelective(userInfo, example);
    }

    @CacheEvict(value = "UserInfo", allEntries = true)
    public Integer delUserInfoByExample(UserInfoExample example) {
        Integer res = 0;
        List<UserInfo> userInfoList = getUserInfoByExample(example);
        for (UserInfo userInfo : userInfoList) {
            userInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += userInfoMapper.updateByPrimaryKey(userInfo);
        }
        return res;
    }

    public Long countUserInfo(UserInfo userInfo) {
        UserInfoExample example = getUserInfoExample(userInfo);
        return userInfoMapper.countByExample(example);
    }

    public Long countUserInfoByExample(UserInfoExample example) {
        return userInfoMapper.countByExample(example);
    }

    @Cacheable(value = "UserInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<UserInfo> getAllUserInfoList(Byte valid) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return userInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "UserInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<UserInfo> getAllUserInfoList(Byte valid, PageInfo<?> pageInfo) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<UserInfo> res = userInfoMapper.selectByExample(example);
        return new PageInfo<UserInfo>(res);
    }

    @Cacheable(value = "UserInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public UserInfo getUserInfoById(Integer id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "UserInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<UserInfo> getUserInfoSelective(UserInfo userInfo, PageInfo<?> pageInfo) {
        UserInfoExample example = getUserInfoExample(userInfo);
        PageHelper.startPage(pageInfo);
        List<UserInfo> res = userInfoMapper.selectByExample(example);
        return new PageInfo<UserInfo>(res);
    }

    @Cacheable(value = "UserInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<UserInfo> getUserInfoSelective(UserInfo userInfo) {
        UserInfoExample example = getUserInfoExample(userInfo);
        List<UserInfo> res = userInfoMapper.selectByExample(example);
        return res;
    }

    public PageInfo<UserInfo> getUserInfoByExample(UserInfoExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<UserInfo> res = userInfoMapper.selectByExample(example);
        return new PageInfo<UserInfo>(res);
    }

    public List<UserInfo> getUserInfoByExample(UserInfoExample example) {
        List<UserInfo> res = userInfoMapper.selectByExample(example);
        return res;
    }

    private UserInfoExample getUserInfoExample(UserInfo userInfo) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(userInfo);
        ReflectionUtil.getFields(userInfo).forEach((field) -> {
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
