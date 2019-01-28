/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository 
 * @author: Sharp   
 * @date: 2019/01/28
 */
package com.srct.service.tanya.common.datalayer.tanya.repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    public Integer updateCampaignSalesmanMap(CampaignSalesmanMap info) {
        Integer id = null;
        if (info.getId() == null) {
            campaignSalesmanMapMapper.insert(info);
        } else {
            campaignSalesmanMapMapper.updateByPrimaryKeySelective(info);
        }
        id = info.getId();
        return id;
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

	@Cacheable(value = "CampaignSalesmanMap", key = "CacheKeyByParam")
	@CacheExpire(expire = 3600L)
    public List<CampaignSalesmanMap> getCampaignSalesmanMapSelective(CampaignSalesmanMap campaignSalesmanMap) {
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
        List<CampaignSalesmanMap> res = campaignSalesmanMapMapper.selectByExample(example);
        return res;
    }	
}