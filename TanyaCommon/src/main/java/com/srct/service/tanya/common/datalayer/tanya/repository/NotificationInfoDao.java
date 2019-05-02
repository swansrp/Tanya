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
import com.srct.service.tanya.common.datalayer.tanya.entity.NotificationInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.NotificationInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.NotificationInfoMapper;
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

@Repository("tanyaNotificationInfoDao")
public class NotificationInfoDao {

    @Autowired
    NotificationInfoMapper notificationInfoMapper;

    @CacheEvict(value = "NotificationInfo", allEntries = true)
    public NotificationInfo updateNotificationInfo(NotificationInfo notificationInfo) {
        int res = 0;
        if (notificationInfo.getId() == null) {
            notificationInfo.setCreateAt(new Date());
            res = notificationInfoMapper.insertSelective(notificationInfo);
        } else {
            notificationInfo.setUpdateAt(new Date());
            res = notificationInfoMapper.updateByPrimaryKeySelective(notificationInfo);
        }
        if (res == 0) {
            throw new ServiceException("update NotificationInfo error");
        }
        return notificationInfo;
    }

    @CacheEvict(value = "NotificationInfo", allEntries = true)
    public NotificationInfo updateNotificationInfoStrict(NotificationInfo notificationInfo) {
        int res = 0;
        if (notificationInfo.getId() == null) {
            notificationInfo.setCreateAt(new Date());
            res = notificationInfoMapper.insert(notificationInfo);
        } else {
            notificationInfo.setUpdateAt(new Date());
            res = notificationInfoMapper.updateByPrimaryKey(notificationInfo);
        }
        if (res == 0) {
            throw new ServiceException("update NotificationInfo error");
        }
        return notificationInfo;
    }

    @CacheEvict(value = "NotificationInfo", allEntries = true)
    public Integer updateNotificationInfoByExample(NotificationInfo notificationInfo, NotificationInfoExample example) {
        return notificationInfoMapper.updateByExampleSelective(notificationInfo, example);
    }

    @CacheEvict(value = "NotificationInfo", allEntries = true)
    public Integer updateNotificationInfoByExampleStrict(NotificationInfo notificationInfo,
            NotificationInfoExample example) {
        return notificationInfoMapper.updateByExample(notificationInfo, example);
    }

    @CacheEvict(value = "NotificationInfo", allEntries = true)
    public Integer delNotificationInfo(NotificationInfo notificationInfo) {
        NotificationInfoExample example = getNotificationInfoExample(notificationInfo);
        notificationInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return notificationInfoMapper.updateByExampleSelective(notificationInfo, example);
    }

    @CacheEvict(value = "NotificationInfo", allEntries = true)
    public Integer delNotificationInfoByExample(NotificationInfoExample example) {
        Integer res = 0;
        List<NotificationInfo> notificationInfoList = getNotificationInfoByExample(example);
        for (NotificationInfo notificationInfo : notificationInfoList) {
            notificationInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += notificationInfoMapper.updateByPrimaryKey(notificationInfo);
        }
        return res;
    }

    public Long countNotificationInfo(NotificationInfo notificationInfo) {
        NotificationInfoExample example = getNotificationInfoExample(notificationInfo);
        return notificationInfoMapper.countByExample(example);
    }

    public Long countNotificationInfoByExample(NotificationInfoExample example) {
        return notificationInfoMapper.countByExample(example);
    }

    @Cacheable(value = "NotificationInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<NotificationInfo> getAllNotificationInfoList(Byte valid) {
        NotificationInfoExample example = new NotificationInfoExample();
        NotificationInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return notificationInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "NotificationInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<NotificationInfo> getAllNotificationInfoList(Byte valid, PageInfo<?> pageInfo) {
        NotificationInfoExample example = new NotificationInfoExample();
        NotificationInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<NotificationInfo> res = notificationInfoMapper.selectByExample(example);
        return new PageInfo<NotificationInfo>(res);
    }

    @Cacheable(value = "NotificationInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public NotificationInfo getNotificationInfoById(Integer id) {
        return notificationInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "NotificationInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<NotificationInfo> getNotificationInfoSelective(NotificationInfo notificationInfo,
            PageInfo<?> pageInfo) {
        NotificationInfoExample example = getNotificationInfoExample(notificationInfo);
        PageHelper.startPage(pageInfo);
        List<NotificationInfo> res = notificationInfoMapper.selectByExample(example);
        return new PageInfo<NotificationInfo>(res);
    }

    @Cacheable(value = "NotificationInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<NotificationInfo> getNotificationInfoSelective(NotificationInfo notificationInfo) {
        NotificationInfoExample example = getNotificationInfoExample(notificationInfo);
        List<NotificationInfo> res = notificationInfoMapper.selectByExample(example);
        return res;
    }

    public PageInfo<NotificationInfo> getNotificationInfoByExample(NotificationInfoExample example,
            PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<NotificationInfo> res = notificationInfoMapper.selectByExample(example);
        return new PageInfo<NotificationInfo>(res);
    }

    public List<NotificationInfo> getNotificationInfoByExample(NotificationInfoExample example) {
        List<NotificationInfo> res = notificationInfoMapper.selectByExample(example);
        return res;
    }

    private NotificationInfoExample getNotificationInfoExample(NotificationInfo notificationInfo) {
        NotificationInfoExample example = new NotificationInfoExample();
        NotificationInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(notificationInfo);
        ReflectionUtil.getFieldList(notificationInfo).forEach((field) -> {
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
