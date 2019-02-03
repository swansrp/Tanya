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
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.TraderInfoMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: TraderInfoDao
 * @Description: TODO
 */
@Repository("tanyaTraderInfoDao")
public class TraderInfoDao {

    @Autowired
    TraderInfoMapper traderInfoMapper;

    @CacheEvict(value = "TraderInfo", allEntries = true)
    public TraderInfo updateTraderInfo(TraderInfo traderInfo) {
        if (traderInfo.getId() == null) {
            traderInfo.setCreateAt(new Date());
            traderInfoMapper.insertSelective(traderInfo);
        } else {
            traderInfo.setUpdateAt(new Date());
            traderInfoMapper.updateByPrimaryKeySelective(traderInfo);
        }
        return traderInfo;
    }

    @CacheEvict(value = "TraderInfo", allEntries = true)
    public Integer updateTraderInfoByExample(TraderInfo traderInfo, TraderInfoExample example) {
        return traderInfoMapper.updateByExampleSelective(traderInfo, example);
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
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return traderInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "TraderInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public TraderInfo getTraderInfobyId(Integer id) {
        return traderInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "TraderInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<TraderInfo> getTraderInfoSelective(TraderInfo traderInfo) {
        TraderInfoExample example = getTraderInfoExample(traderInfo);
        List<TraderInfo> res = traderInfoMapper.selectByExample(example);
        return res;
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