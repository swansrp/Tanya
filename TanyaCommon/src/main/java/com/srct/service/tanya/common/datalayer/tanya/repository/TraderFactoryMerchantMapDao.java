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
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.TraderFactoryMerchantMapMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: TraderFactoryMerchantMapDao
 * @Description: TODO
 */
@Repository("tanyaTraderFactoryMerchantMapDao")
public class TraderFactoryMerchantMapDao {

    @Autowired
    TraderFactoryMerchantMapMapper traderFactoryMerchantMapMapper;

    @CacheEvict(value = "TraderFactoryMerchantMap", allEntries = true)
    public Integer updateTraderFactoryMerchantMap(TraderFactoryMerchantMap info) {
        Integer id = null;
        if (info.getId() == null) {
            traderFactoryMerchantMapMapper.insert(info);
        } else {
            traderFactoryMerchantMapMapper.updateByPrimaryKeySelective(info);
        }
        id = info.getId();
        return id;
    }
	
    @Cacheable(value = "TraderFactoryMerchantMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<TraderFactoryMerchantMap> getAllTraderFactoryMerchantMapList(Byte valid) {
        TraderFactoryMerchantMapExample example = new TraderFactoryMerchantMapExample();
        TraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return traderFactoryMerchantMapMapper.selectByExample(example);
    }
	
    @Cacheable(value = "TraderFactoryMerchantMap", key = "'id_' + #id")
	@CacheExpire(expire = 24 * 3600L)
    public TraderFactoryMerchantMap getTraderFactoryMerchantMapbyId(Integer id) {
        return traderFactoryMerchantMapMapper.selectByPrimaryKey(id);
    }

	@Cacheable(value = "TraderFactoryMerchantMap", key = "CacheKeyByParam")
	@CacheExpire(expire = 3600L)
    public List<TraderFactoryMerchantMap> getTraderFactoryMerchantMapSelective(TraderFactoryMerchantMap traderFactoryMerchantMap) {
        TraderFactoryMerchantMapExample example = new TraderFactoryMerchantMapExample();
        TraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(traderFactoryMerchantMap);
        ReflectionUtil.getFieldList(traderFactoryMerchantMap).forEach((field) -> {
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
        List<TraderFactoryMerchantMap> res = traderFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }	
}