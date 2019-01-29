

/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository 
 * @author: srct   
 * @date: 2019/01/29
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
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.SalesmanTraderMapMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: SalesmanTraderMapDao
 * @Description: TODO
 */
@Repository("tanyaSalesmanTraderMapDao")
public class SalesmanTraderMapDao {

    @Autowired
    SalesmanTraderMapMapper salesmanTraderMapMapper;

    @CacheEvict(value = "SalesmanTraderMap", allEntries = true)
    public Integer updateSalesmanTraderMap(SalesmanTraderMap info) {
        Integer id = null;
        if (info.getId() == null) {
            info.setCreateAt(new Date());
            salesmanTraderMapMapper.insertSelective(info);
        } else {
            info.setUpdateAt(new Date());
            salesmanTraderMapMapper.updateByPrimaryKeySelective(info);
        }
        id = info.getId();
        return id;
    }
	
    @Cacheable(value = "SalesmanTraderMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<SalesmanTraderMap> getAllSalesmanTraderMapList(Byte valid) {
        SalesmanTraderMapExample example = new SalesmanTraderMapExample();
        SalesmanTraderMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return salesmanTraderMapMapper.selectByExample(example);
    }
	
    @Cacheable(value = "SalesmanTraderMap", key = "'id_' + #id")
	@CacheExpire(expire = 24 * 3600L)
    public SalesmanTraderMap getSalesmanTraderMapbyId(Integer id) {
        return salesmanTraderMapMapper.selectByPrimaryKey(id);
    }

	@Cacheable(value = "SalesmanTraderMap", key = "CacheKeyByParam")
	@CacheExpire(expire = 3600L)
    public List<SalesmanTraderMap> getSalesmanTraderMapSelective(SalesmanTraderMap salesmanTraderMap) {
        SalesmanTraderMapExample example = new SalesmanTraderMapExample();
        SalesmanTraderMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(salesmanTraderMap);
        ReflectionUtil.getFieldList(salesmanTraderMap).forEach((field) -> {
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
        List<SalesmanTraderMap> res = salesmanTraderMapMapper.selectByExample(example);
        return res;
    }	
}