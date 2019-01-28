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
import com.srct.service.tanya.common.datalayer.tanya.entity.AdminInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.AdminInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.AdminInfoMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: AdminInfoDao
 * @Description: TODO
 */
@Repository("tanyaAdminInfoDao")
public class AdminInfoDao {

    @Autowired
    AdminInfoMapper adminInfoMapper;

    @CacheEvict(value = "AdminInfo", allEntries = true)
    public Integer updateAdminInfo(AdminInfo info) {
        Integer id = null;
        if (info.getId() == null) {
            adminInfoMapper.insert(info);
        } else {
            adminInfoMapper.updateByPrimaryKeySelective(info);
        }
        id = info.getId();
        return id;
    }
	
    @Cacheable(value = "AdminInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<AdminInfo> getAllAdminInfoList(Byte valid) {
        AdminInfoExample example = new AdminInfoExample();
        AdminInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return adminInfoMapper.selectByExample(example);
    }
	
    @Cacheable(value = "AdminInfo", key = "'id_' + #id")
	@CacheExpire(expire = 24 * 3600L)
    public AdminInfo getAdminInfobyId(Integer id) {
        return adminInfoMapper.selectByPrimaryKey(id);
    }

	@Cacheable(value = "AdminInfo", key = "CacheKeyByParam")
	@CacheExpire(expire = 3600L)
    public List<AdminInfo> getAdminInfoSelective(AdminInfo adminInfo) {
        AdminInfoExample example = new AdminInfoExample();
        AdminInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(adminInfo);
        ReflectionUtil.getFieldList(adminInfo).forEach((field) -> {
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
        List<AdminInfo> res = adminInfoMapper.selectByExample(example);
        return res;
    }	
}