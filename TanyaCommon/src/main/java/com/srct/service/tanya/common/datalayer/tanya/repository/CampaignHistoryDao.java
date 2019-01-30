

/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository 
 * @author: Sharp   
 * @date: 2019/01/30
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
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistory;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistoryExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.CampaignHistoryMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: CampaignHistoryDao
 * @Description: TODO
 */
@Repository("tanyaCampaignHistoryDao")
public class CampaignHistoryDao {

    @Autowired
    CampaignHistoryMapper campaignHistoryMapper;

    @CacheEvict(value = "CampaignHistory", allEntries = true)
    public CampaignHistory updateCampaignHistory(CampaignHistory campaignHistory) {
        if (campaignHistory.getId() == null) {
            CampaignHistoryExample example = getCampaignHistoryExample(campaignHistory);
            campaignHistory.setUpdateAt(new Date());
            int updateNum = campaignHistoryMapper.updateByExampleSelective(campaignHistory, example);
            if (updateNum == 0) {
                campaignHistory.setCreateAt(new Date());
                campaignHistoryMapper.insertSelective(campaignHistory);
            }
        } else {
            campaignHistory.setUpdateAt(new Date());
            campaignHistoryMapper.updateByPrimaryKeySelective(campaignHistory);
        }
        return campaignHistory;
    }

    @Cacheable(value = "CampaignHistory", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<CampaignHistory> getAllCampaignHistoryList(Byte valid) {
        CampaignHistoryExample example = new CampaignHistoryExample();
        CampaignHistoryExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return campaignHistoryMapper.selectByExample(example);
    }
    
    @Cacheable(value = "CampaignHistory", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public CampaignHistory getCampaignHistorybyId(Integer id) {
        return campaignHistoryMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "CampaignHistory", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<CampaignHistory> getCampaignHistorySelective(CampaignHistory campaignHistory) {
        CampaignHistoryExample example = getCampaignHistoryExample(campaignHistory);
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