

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
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.UserRoleMapMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: UserRoleMapDao
 * @Description: TODO
 */
@Repository("tanyaUserRoleMapDao")
public class UserRoleMapDao {

    @Autowired
    UserRoleMapMapper userRoleMapMapper;

    @CacheEvict(value = "UserRoleMap", allEntries = true)
    public Integer updateUserRoleMap(UserRoleMap info) {
        Integer id = null;
        if (info.getId() == null) {
            info.setCreateAt(new Date());
            userRoleMapMapper.insertSelective(info);
        } else {
            info.setUpdateAt(new Date());
            userRoleMapMapper.updateByPrimaryKeySelective(info);
        }
        id = info.getId();
        return id;
    }
	
    @Cacheable(value = "UserRoleMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<UserRoleMap> getAllUserRoleMapList(Byte valid) {
        UserRoleMapExample example = new UserRoleMapExample();
        UserRoleMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return userRoleMapMapper.selectByExample(example);
    }
	
    @Cacheable(value = "UserRoleMap", key = "'id_' + #id")
	@CacheExpire(expire = 24 * 3600L)
    public UserRoleMap getUserRoleMapbyId(Integer id) {
        return userRoleMapMapper.selectByPrimaryKey(id);
    }

	@Cacheable(value = "UserRoleMap", keyGenerator = "CacheKeyByParam")
	@CacheExpire(expire = 3600L)
    public List<UserRoleMap> getUserRoleMapSelective(UserRoleMap userRoleMap) {
        UserRoleMapExample example = new UserRoleMapExample();
        UserRoleMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(userRoleMap);
        ReflectionUtil.getFieldList(userRoleMap).forEach((field) -> {
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
        List<UserRoleMap> res = userRoleMapMapper.selectByExample(example);
        return res;
    }	
}