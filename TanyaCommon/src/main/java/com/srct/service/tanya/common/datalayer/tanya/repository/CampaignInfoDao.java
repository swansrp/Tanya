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
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.CampaignInfoMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: CampaignInfoDao
 * @Description: TODO
 */
@Repository("tanyaCampaignInfoDao")
public class CampaignInfoDao {

    @Autowired
    CampaignInfoMapper campaignInfoMapper;

    @CacheEvict(value = "CampaignInfo", allEntries = true)
    public CampaignInfo updateCampaignInfo(CampaignInfo campaignInfo) {
        int res = 0;
        if (campaignInfo.getId() == null) {
            campaignInfo.setCreateAt(new Date());
            res = campaignInfoMapper.insertSelective(campaignInfo);
        } else {
            campaignInfo.setUpdateAt(new Date());
            res = campaignInfoMapper.updateByPrimaryKeySelective(campaignInfo);
        }
        if(res == 0) {
            throw new ServiceException("update CampaignInfo error");
        }
        return campaignInfo;
    }

    @CacheEvict(value = "CampaignInfo", allEntries = true)
    public CampaignInfo updateCampaignInfoStrict(CampaignInfo campaignInfo) {
        int res = 0;
        if (campaignInfo.getId() == null) {
            campaignInfo.setCreateAt(new Date());
            res = campaignInfoMapper.insert(campaignInfo);
        } else {
            campaignInfo.setUpdateAt(new Date());
            res = campaignInfoMapper.updateByPrimaryKey(campaignInfo);
        }
        if(res == 0) {
            throw new ServiceException("update CampaignInfo error");
        }
        return campaignInfo;
    }

    @CacheEvict(value = "CampaignInfo", allEntries = true)
    public Integer updateCampaignInfoByExample(CampaignInfo campaignInfo, CampaignInfoExample example) {
        return campaignInfoMapper.updateByExampleSelective(campaignInfo, example);
    }

    @CacheEvict(value = "CampaignInfo", allEntries = true)
    public Integer updateCampaignInfoByExampleStrict(CampaignInfo campaignInfo, CampaignInfoExample example) {
        return campaignInfoMapper.updateByExample(campaignInfo, example);
    }

    @CacheEvict(value = "CampaignInfo", allEntries = true)
    public Integer delCampaignInfo(CampaignInfo campaignInfo) {
        CampaignInfoExample example = getCampaignInfoExample(campaignInfo);
        campaignInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return campaignInfoMapper.updateByExampleSelective(campaignInfo, example);
    }

    @CacheEvict(value = "CampaignInfo", allEntries = true)
    public Integer delCampaignInfoByExample(CampaignInfoExample example) {
        Integer res = 0;
        List<CampaignInfo> campaignInfoList = getCampaignInfoByExample(example);
        for (CampaignInfo campaignInfo : campaignInfoList) {
            campaignInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += campaignInfoMapper.updateByPrimaryKey(campaignInfo);
        }
        return res;
    }

    public Long countCampaignInfo(CampaignInfo campaignInfo) {
        CampaignInfoExample example = getCampaignInfoExample(campaignInfo);
        return campaignInfoMapper.countByExample(example);
    }

    public Long countCampaignInfoByExample(CampaignInfoExample example) {
        return campaignInfoMapper.countByExample(example);
    }

    @Cacheable(value = "CampaignInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<CampaignInfo> getAllCampaignInfoList(Byte valid) {
        CampaignInfoExample example = new CampaignInfoExample();
        CampaignInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return campaignInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "CampaignInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public CampaignInfo getCampaignInfobyId(Integer id) {
        return campaignInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "CampaignInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<CampaignInfo> getCampaignInfoSelective(CampaignInfo campaignInfo) {
        CampaignInfoExample example = getCampaignInfoExample(campaignInfo);
        List<CampaignInfo> res = campaignInfoMapper.selectByExample(example);
        return res;
    }

    public List<CampaignInfo> getCampaignInfoByExample(CampaignInfoExample example) {
        List<CampaignInfo> res = campaignInfoMapper.selectByExample(example);
        return res;
    }

    private CampaignInfoExample getCampaignInfoExample(CampaignInfo campaignInfo) {
        CampaignInfoExample example = new CampaignInfoExample();
        CampaignInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(campaignInfo);
        ReflectionUtil.getFieldList(campaignInfo).forEach((field) -> {
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