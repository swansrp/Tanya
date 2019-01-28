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
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.SalesmanInfoMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: SalesmanInfoDao
 * @Description: TODO
 */
@Repository("tanyaSalesmanInfoDao")
public class SalesmanInfoDao {

    @Autowired
    SalesmanInfoMapper salesmanInfoMapper;

    @CacheEvict(value = "SalesmanInfo", allEntries = true)
    public Integer updateSalesmanInfo(SalesmanInfo info) {
        Integer id = null;
        if (info.getId() == null) {
            salesmanInfoMapper.insert(info);
        } else {
            salesmanInfoMapper.updateByPrimaryKeySelective(info);
        }
        id = info.getId();
        return id;
    }
	
    @Cacheable(value = "SalesmanInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<SalesmanInfo> getAllSalesmanInfoList(Byte valid) {
        SalesmanInfoExample example = new SalesmanInfoExample();
        SalesmanInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return salesmanInfoMapper.selectByExample(example);
    }
	
    @Cacheable(value = "SalesmanInfo", key = "'id_' + #id")
	@CacheExpire(expire = 24 * 3600L)
    public SalesmanInfo getSalesmanInfobyId(Integer id) {
        return salesmanInfoMapper.selectByPrimaryKey(id);
    }

	@Cacheable(value = "SalesmanInfo", key = "CacheKeyByParam")
	@CacheExpire(expire = 3600L)
    public List<SalesmanInfo> getSalesmanInfoSelective(SalesmanInfo salesmanInfo) {
        SalesmanInfoExample example = new SalesmanInfoExample();
        SalesmanInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(salesmanInfo);
        ReflectionUtil.getFieldList(salesmanInfo).forEach((field) -> {
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
        List<SalesmanInfo> res = salesmanInfoMapper.selectByExample(example);
        return res;
    }	
}