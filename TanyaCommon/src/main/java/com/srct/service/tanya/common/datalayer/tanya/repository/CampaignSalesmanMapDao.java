/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository 
 * @author: Sharp   
 * @date: 2019/02/12
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
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.CampaignSalesmanMapMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: CampaignSalesmanMapDao
 * @Description: TODO
 */
@Repository("tanyaCampaignSalesmanMapDao")
public class CampaignSalesmanMapDao {

    @Autowired
    CampaignSalesmanMapMapper campaignSalesmanMapMapper;

    @CacheEvict(value = "CampaignSalesmanMap", allEntries = true)
    public CampaignSalesmanMap updateCampaignSalesmanMap(CampaignSalesmanMap campaignSalesmanMap) {
        int res = 0;
        if (campaignSalesmanMap.getId() == null) {
            campaignSalesmanMap.setCreateAt(new Date());
            res = campaignSalesmanMapMapper.insertSelective(campaignSalesmanMap);
        } else {
            campaignSalesmanMap.setUpdateAt(new Date());
            res = campaignSalesmanMapMapper.updateByPrimaryKeySelective(campaignSalesmanMap);
        }
        if(res == 0) {
            throw new ServiceException("update CampaignSalesmanMap error");
        }
        return campaignSalesmanMap;
    }

    @CacheEvict(value = "CampaignSalesmanMap", allEntries = true)
    public CampaignSalesmanMap updateCampaignSalesmanMapStrict(CampaignSalesmanMap campaignSalesmanMap) {
        int res = 0;
        if (campaignSalesmanMap.getId() == null) {
            campaignSalesmanMap.setCreateAt(new Date());
            res = campaignSalesmanMapMapper.insert(campaignSalesmanMap);
        } else {
            campaignSalesmanMap.setUpdateAt(new Date());
            res = campaignSalesmanMapMapper.updateByPrimaryKey(campaignSalesmanMap);
        }
        if(res == 0) {
            throw new ServiceException("update CampaignSalesmanMap error");
        }
        return campaignSalesmanMap;
    }

    @CacheEvict(value = "CampaignSalesmanMap", allEntries = true)
    public Integer updateCampaignSalesmanMapByExample(CampaignSalesmanMap campaignSalesmanMap, CampaignSalesmanMapExample example) {
        return campaignSalesmanMapMapper.updateByExampleSelective(campaignSalesmanMap, example);
    }

    @CacheEvict(value = "CampaignSalesmanMap", allEntries = true)
    public Integer updateCampaignSalesmanMapByExampleStrict(CampaignSalesmanMap campaignSalesmanMap, CampaignSalesmanMapExample example) {
        return campaignSalesmanMapMapper.updateByExample(campaignSalesmanMap, example);
    }

    @CacheEvict(value = "CampaignSalesmanMap", allEntries = true)
    public Integer delCampaignSalesmanMap(CampaignSalesmanMap campaignSalesmanMap) {
        CampaignSalesmanMapExample example = getCampaignSalesmanMapExample(campaignSalesmanMap);
        campaignSalesmanMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return campaignSalesmanMapMapper.updateByExampleSelective(campaignSalesmanMap, example);
    }

    @CacheEvict(value = "CampaignSalesmanMap", allEntries = true)
    public Integer delCampaignSalesmanMapByExample(CampaignSalesmanMapExample example) {
        Integer res = 0;
        List<CampaignSalesmanMap> campaignSalesmanMapList = getCampaignSalesmanMapByExample(example);
        for (CampaignSalesmanMap campaignSalesmanMap : campaignSalesmanMapList) {
            campaignSalesmanMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += campaignSalesmanMapMapper.updateByPrimaryKey(campaignSalesmanMap);
        }
        return res;
    }

    public Long countCampaignSalesmanMap(CampaignSalesmanMap campaignSalesmanMap) {
        CampaignSalesmanMapExample example = getCampaignSalesmanMapExample(campaignSalesmanMap);
        return campaignSalesmanMapMapper.countByExample(example);
    }

    public Long countCampaignSalesmanMapByExample(CampaignSalesmanMapExample example) {
        return campaignSalesmanMapMapper.countByExample(example);
    }

    @Cacheable(value = "CampaignSalesmanMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<CampaignSalesmanMap> getAllCampaignSalesmanMapList(Byte valid) {
        CampaignSalesmanMapExample example = new CampaignSalesmanMapExample();
        CampaignSalesmanMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return campaignSalesmanMapMapper.selectByExample(example);
    }

    @Cacheable(value = "CampaignSalesmanMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public CampaignSalesmanMap getCampaignSalesmanMapbyId(Integer id) {
        return campaignSalesmanMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "CampaignSalesmanMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<CampaignSalesmanMap> getCampaignSalesmanMapSelective(CampaignSalesmanMap campaignSalesmanMap) {
        CampaignSalesmanMapExample example = getCampaignSalesmanMapExample(campaignSalesmanMap);
        List<CampaignSalesmanMap> res = campaignSalesmanMapMapper.selectByExample(example);
        return res;
    }

    public List<CampaignSalesmanMap> getCampaignSalesmanMapByExample(CampaignSalesmanMapExample example) {
        List<CampaignSalesmanMap> res = campaignSalesmanMapMapper.selectByExample(example);
        return res;
    }

    private CampaignSalesmanMapExample getCampaignSalesmanMapExample(CampaignSalesmanMap campaignSalesmanMap) {
        CampaignSalesmanMapExample example = new CampaignSalesmanMapExample();
        CampaignSalesmanMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(campaignSalesmanMap);
        ReflectionUtil.getFieldList(campaignSalesmanMap).forEach((field) -> {
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