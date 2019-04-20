/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository
 * @author: sharuopeng
 * @date: 2019/04/20
 */
package com.srct.service.tanya.common.datalayer.tanya.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.redis.CacheExpire;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.TraderInfoMapper;
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

@Repository("tanyaTraderInfoDao")
public class TraderInfoDao {

    @Autowired
    TraderInfoMapper traderInfoMapper;

    @CacheEvict(value = "TraderInfo", allEntries = true)
    public TraderInfo updateTraderInfo(TraderInfo traderInfo) {
        int res = 0;
        if (traderInfo.getId() == null) {
            traderInfo.setCreateAt(new Date());
            res = traderInfoMapper.insertSelective(traderInfo);
        } else {
            traderInfo.setUpdateAt(new Date());
            res = traderInfoMapper.updateByPrimaryKeySelective(traderInfo);
        }
        if (res == 0) {
            throw new ServiceException("update TraderInfo error");
        }
        return traderInfo;
    }

    @CacheEvict(value = "TraderInfo", allEntries = true)
    public TraderInfo updateTraderInfoStrict(TraderInfo traderInfo) {
        int res = 0;
        if (traderInfo.getId() == null) {
            traderInfo.setCreateAt(new Date());
            res = traderInfoMapper.insert(traderInfo);
        } else {
            traderInfo.setUpdateAt(new Date());
            res = traderInfoMapper.updateByPrimaryKey(traderInfo);
        }
        if (res == 0) {
            throw new ServiceException("update TraderInfo error");
        }
        return traderInfo;
    }

    @CacheEvict(value = "TraderInfo", allEntries = true)
    public Integer updateTraderInfoByExample(TraderInfo traderInfo, TraderInfoExample example) {
        return traderInfoMapper.updateByExampleSelective(traderInfo, example);
    }

    @CacheEvict(value = "TraderInfo", allEntries = true)
    public Integer updateTraderInfoByExampleStrict(TraderInfo traderInfo, TraderInfoExample example) {
        return traderInfoMapper.updateByExample(traderInfo, example);
    }

    @CacheEvict(value = "TraderInfo", allEntries = true)
    public Integer delTraderInfo(TraderInfo traderInfo) {
        TraderInfoExample example = getTraderInfoExample(traderInfo);
        traderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return traderInfoMapper.updateByExampleSelective(traderInfo, example);
    }

    @CacheEvict(value = "TraderInfo", allEntries = true)
    public Integer delTraderInfoByExample(TraderInfoExample example) {
        Integer res = 0;
        List<TraderInfo> traderInfoList = getTraderInfoByExample(example);
        for (TraderInfo traderInfo : traderInfoList) {
            traderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += traderInfoMapper.updateByPrimaryKey(traderInfo);
        }
        return res;
    }

    public Long countTraderInfo(TraderInfo traderInfo) {
        TraderInfoExample example = getTraderInfoExample(traderInfo);
        return traderInfoMapper.countByExample(example);
    }

    public Long countTraderInfoByExample(TraderInfoExample example) {
        return traderInfoMapper.countByExample(example);
    }

    @Cacheable(value = "TraderInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<TraderInfo> getAllTraderInfoList(Byte valid) {
        TraderInfoExample example = new TraderInfoExample();
        TraderInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return traderInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "TraderInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<TraderInfo> getAllTraderInfoList(Byte valid, PageInfo<?> pageInfo) {
        TraderInfoExample example = new TraderInfoExample();
        TraderInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<TraderInfo> res = traderInfoMapper.selectByExample(example);
        return new PageInfo<TraderInfo>(res);
    }

    @Cacheable(value = "TraderInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public TraderInfo getTraderInfoById(Integer id) {
        return traderInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "TraderInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<TraderInfo> getTraderInfoSelective(TraderInfo traderInfo, PageInfo<?> pageInfo) {
        TraderInfoExample example = getTraderInfoExample(traderInfo);
        PageHelper.startPage(pageInfo);
        List<TraderInfo> res = traderInfoMapper.selectByExample(example);
        return new PageInfo<TraderInfo>(res);
    }

    @Cacheable(value = "TraderInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<TraderInfo> getTraderInfoSelective(TraderInfo traderInfo) {
        TraderInfoExample example = getTraderInfoExample(traderInfo);
        List<TraderInfo> res = traderInfoMapper.selectByExample(example);
        return res;
    }

    public PageInfo<TraderInfo> getTraderInfoByExample(TraderInfoExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<TraderInfo> res = traderInfoMapper.selectByExample(example);
        return new PageInfo<TraderInfo>(res);
    }

    public List<TraderInfo> getTraderInfoByExample(TraderInfoExample example) {
        List<TraderInfo> res = traderInfoMapper.selectByExample(example);
        return res;
    }

    private TraderInfoExample getTraderInfoExample(TraderInfo traderInfo) {
        TraderInfoExample example = new TraderInfoExample();
        TraderInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(traderInfo);
        ReflectionUtil.getFieldList(traderInfo).forEach((field) -> {
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
