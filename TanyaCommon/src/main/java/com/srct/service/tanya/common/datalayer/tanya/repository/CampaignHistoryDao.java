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
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistory;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistoryExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.CampaignHistoryMapper;
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

@Repository("tanyaCampaignHistoryDao")
public class CampaignHistoryDao {

    @Autowired
    CampaignHistoryMapper campaignHistoryMapper;

    @CacheEvict(value = "CampaignHistory", allEntries = true)
    public CampaignHistory updateCampaignHistory(CampaignHistory campaignHistory) {
        int res = 0;
        if (campaignHistory.getId() == null) {
            campaignHistory.setCreateAt(new Date());
            res = campaignHistoryMapper.insertSelective(campaignHistory);
        } else {
            campaignHistory.setUpdateAt(new Date());
            res = campaignHistoryMapper.updateByPrimaryKeySelective(campaignHistory);
        }
        if (res == 0) {
            throw new ServiceException("update CampaignHistory error");
        }
        return campaignHistory;
    }

    @CacheEvict(value = "CampaignHistory", allEntries = true)
    public CampaignHistory updateCampaignHistoryStrict(CampaignHistory campaignHistory) {
        int res = 0;
        if (campaignHistory.getId() == null) {
            campaignHistory.setCreateAt(new Date());
            res = campaignHistoryMapper.insert(campaignHistory);
        } else {
            campaignHistory.setUpdateAt(new Date());
            res = campaignHistoryMapper.updateByPrimaryKey(campaignHistory);
        }
        if (res == 0) {
            throw new ServiceException("update CampaignHistory error");
        }
        return campaignHistory;
    }

    @CacheEvict(value = "CampaignHistory", allEntries = true)
    public Integer updateCampaignHistoryByExample(CampaignHistory campaignHistory, CampaignHistoryExample example) {
        return campaignHistoryMapper.updateByExampleSelective(campaignHistory, example);
    }

    @CacheEvict(value = "CampaignHistory", allEntries = true)
    public Integer updateCampaignHistoryByExampleStrict(CampaignHistory campaignHistory,
            CampaignHistoryExample example) {
        return campaignHistoryMapper.updateByExample(campaignHistory, example);
    }

    @CacheEvict(value = "CampaignHistory", allEntries = true)
    public Integer delCampaignHistory(CampaignHistory campaignHistory) {
        CampaignHistoryExample example = getCampaignHistoryExample(campaignHistory);
        campaignHistory.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return campaignHistoryMapper.updateByExampleSelective(campaignHistory, example);
    }

    @CacheEvict(value = "CampaignHistory", allEntries = true)
    public Integer delCampaignHistoryByExample(CampaignHistoryExample example) {
        Integer res = 0;
        List<CampaignHistory> campaignHistoryList = getCampaignHistoryByExample(example);
        for (CampaignHistory campaignHistory : campaignHistoryList) {
            campaignHistory.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += campaignHistoryMapper.updateByPrimaryKey(campaignHistory);
        }
        return res;
    }

    public Long countCampaignHistory(CampaignHistory campaignHistory) {
        CampaignHistoryExample example = getCampaignHistoryExample(campaignHistory);
        return campaignHistoryMapper.countByExample(example);
    }

    public Long countCampaignHistoryByExample(CampaignHistoryExample example) {
        return campaignHistoryMapper.countByExample(example);
    }

    @Cacheable(value = "CampaignHistory", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<CampaignHistory> getAllCampaignHistoryList(Byte valid) {
        CampaignHistoryExample example = new CampaignHistoryExample();
        CampaignHistoryExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return campaignHistoryMapper.selectByExample(example);
    }

    @Cacheable(value = "CampaignHistory", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<CampaignHistory> getAllCampaignHistoryList(Byte valid, PageInfo<?> pageInfo) {
        CampaignHistoryExample example = new CampaignHistoryExample();
        CampaignHistoryExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<CampaignHistory> res = campaignHistoryMapper.selectByExample(example);
        return new PageInfo<CampaignHistory>(res);
    }

    @Cacheable(value = "CampaignHistory", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public CampaignHistory getCampaignHistoryById(Integer id) {
        return campaignHistoryMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "CampaignHistory", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<CampaignHistory> getCampaignHistorySelective(CampaignHistory campaignHistory,
            PageInfo<?> pageInfo) {
        CampaignHistoryExample example = getCampaignHistoryExample(campaignHistory);
        PageHelper.startPage(pageInfo);
        List<CampaignHistory> res = campaignHistoryMapper.selectByExample(example);
        return new PageInfo<CampaignHistory>(res);
    }

    @Cacheable(value = "CampaignHistory", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<CampaignHistory> getCampaignHistorySelective(CampaignHistory campaignHistory) {
        CampaignHistoryExample example = getCampaignHistoryExample(campaignHistory);
        List<CampaignHistory> res = campaignHistoryMapper.selectByExample(example);
        return res;
    }

    public PageInfo<CampaignHistory> getCampaignHistoryByExample(CampaignHistoryExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<CampaignHistory> res = campaignHistoryMapper.selectByExample(example);
        return new PageInfo<CampaignHistory>(res);
    }

    public List<CampaignHistory> getCampaignHistoryByExample(CampaignHistoryExample example) {
        List<CampaignHistory> res = campaignHistoryMapper.selectByExample(example);
        return res;
    }

    private CampaignHistoryExample getCampaignHistoryExample(CampaignHistory campaignHistory) {
        CampaignHistoryExample example = new CampaignHistoryExample();
        CampaignHistoryExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(campaignHistory);
        ReflectionUtil.getFieldList(campaignHistory).forEach((field) -> {
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
