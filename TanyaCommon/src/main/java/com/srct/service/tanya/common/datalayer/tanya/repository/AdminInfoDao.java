/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository
 * @author: sharuopeng
 * @date: 2019/04/04
 */
package com.srct.service.tanya.common.datalayer.tanya.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.redis.CacheExpire;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.AdminInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.AdminInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.AdminInfoMapper;
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


/**
 * @ClassName: AdminInfoDao
 * @Description: Basic Repository 
 */
@Repository("tanyaAdminInfoDao")
public class AdminInfoDao {

    @Autowired
    AdminInfoMapper adminInfoMapper;

    @CacheEvict(value = "AdminInfo", allEntries = true)
    public AdminInfo updateAdminInfo(AdminInfo adminInfo) {
        int res = 0;
        if (adminInfo.getId() == null) {
            adminInfo.setCreateAt(new Date());
            res = adminInfoMapper.insertSelective(adminInfo);
        } else {
            adminInfo.setUpdateAt(new Date());
            res = adminInfoMapper.updateByPrimaryKeySelective(adminInfo);
        }
        if (res == 0) {
            throw new ServiceException("update AdminInfo error");
        }
        return adminInfo;
    }

    @CacheEvict(value = "AdminInfo", allEntries = true)
    public AdminInfo updateAdminInfoStrict(AdminInfo adminInfo) {
        int res = 0;
        if (adminInfo.getId() == null) {
            adminInfo.setCreateAt(new Date());
            res = adminInfoMapper.insert(adminInfo);
        } else {
            adminInfo.setUpdateAt(new Date());
            res = adminInfoMapper.updateByPrimaryKey(adminInfo);
        }
        if (res == 0) {
            throw new ServiceException("update AdminInfo error");
        }
        return adminInfo;
    }

    @CacheEvict(value = "AdminInfo", allEntries = true)
    public Integer updateAdminInfoByExample(AdminInfo adminInfo, AdminInfoExample example) {
        return adminInfoMapper.updateByExampleSelective(adminInfo, example);
    }

    @CacheEvict(value = "AdminInfo", allEntries = true)
    public Integer updateAdminInfoByExampleStrict(AdminInfo adminInfo, AdminInfoExample example) {
        return adminInfoMapper.updateByExample(adminInfo, example);
    }

    @CacheEvict(value = "AdminInfo", allEntries = true)
    public Integer delAdminInfo(AdminInfo adminInfo) {
        AdminInfoExample example = getAdminInfoExample(adminInfo);
        adminInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return adminInfoMapper.updateByExampleSelective(adminInfo, example);
    }

    @CacheEvict(value = "AdminInfo", allEntries = true)
    public Integer delAdminInfoByExample(AdminInfoExample example) {
        Integer res = 0;
        List<AdminInfo> adminInfoList = getAdminInfoByExample(example);
        for (AdminInfo adminInfo : adminInfoList) {
            adminInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += adminInfoMapper.updateByPrimaryKey(adminInfo);
        }
        return res;
    }

    public Long countAdminInfo(AdminInfo adminInfo) {
        AdminInfoExample example = getAdminInfoExample(adminInfo);
        return adminInfoMapper.countByExample(example);
    }

    public Long countAdminInfoByExample(AdminInfoExample example) {
        return adminInfoMapper.countByExample(example);
    }

    @Cacheable(value = "AdminInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<AdminInfo> getAllAdminInfoList(Byte valid) {
        AdminInfoExample example = new AdminInfoExample();
        AdminInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        return adminInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "AdminInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<AdminInfo> getAllAdminInfoList(Byte valid, PageInfo<?> pageInfo) {
        AdminInfoExample example = new AdminInfoExample();
        AdminInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<AdminInfo> res = adminInfoMapper.selectByExample(example);
        pageInfo = new PageInfo<AdminInfo>(res);
        return res;
    }

    @Cacheable(value = "AdminInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public AdminInfo getAdminInfoById(Integer id) {
        return adminInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "AdminInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<AdminInfo> getAdminInfoSelective(AdminInfo adminInfo, PageInfo<?> pageInfo) {
        AdminInfoExample example = getAdminInfoExample(adminInfo);
        PageHelper.startPage(pageInfo);
        List<AdminInfo> res = adminInfoMapper.selectByExample(example);
        pageInfo = new PageInfo<AdminInfo>(res);
        return res;
    }

    @Cacheable(value = "AdminInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<AdminInfo> getAdminInfoSelective(AdminInfo adminInfo) {
        AdminInfoExample example = getAdminInfoExample(adminInfo);
        List<AdminInfo> res = adminInfoMapper.selectByExample(example);
        return res;
    }

    public List<AdminInfo> getAdminInfoByExample(AdminInfoExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<AdminInfo> res = adminInfoMapper.selectByExample(example);
        pageInfo = new PageInfo<AdminInfo>(res);
        return res;
    }

    public List<AdminInfo> getAdminInfoByExample(AdminInfoExample example) {
        List<AdminInfo> res = adminInfoMapper.selectByExample(example);
        return res;
    }

    private AdminInfoExample getAdminInfoExample(AdminInfo adminInfo) {
        AdminInfoExample example = new AdminInfoExample();
        AdminInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(adminInfo);
        ReflectionUtil.getFieldList(adminInfo).forEach((field) -> {
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
