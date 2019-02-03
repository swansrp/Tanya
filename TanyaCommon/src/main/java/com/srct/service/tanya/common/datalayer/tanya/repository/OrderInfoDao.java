/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository 
 * @author: srct   
 * @date: 2019/02/03
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
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.OrderInfoMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: OrderInfoDao
 * @Description: TODO
 */
@Repository("tanyaOrderInfoDao")
public class OrderInfoDao {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @CacheEvict(value = "OrderInfo", allEntries = true)
    public OrderInfo updateOrderInfo(OrderInfo orderInfo) {
        if (orderInfo.getId() == null) {
            orderInfo.setCreateAt(new Date());
            orderInfoMapper.insertSelective(orderInfo);
        } else {
            orderInfo.setUpdateAt(new Date());
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        }
        return orderInfo;
    }

    @CacheEvict(value = "OrderInfo", allEntries = true)
    public Integer updateOrderInfoByExample(OrderInfo orderInfo, OrderInfoExample example) {
        return orderInfoMapper.updateByExampleSelective(orderInfo, example);
    }

    @CacheEvict(value = "OrderInfo", allEntries = true)
    public Integer delOrderInfo(OrderInfo orderInfo) {
        OrderInfoExample example = getOrderInfoExample(orderInfo);
        orderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return orderInfoMapper.updateByExampleSelective(orderInfo, example);
    }

    @CacheEvict(value = "OrderInfo", allEntries = true)
    public Integer delOrderInfoByExample(OrderInfoExample example) {
        Integer res = 0;
        List<OrderInfo> orderInfoList = getOrderInfoByExample(example);
        for (OrderInfo orderInfo : orderInfoList) {
            orderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += orderInfoMapper.updateByPrimaryKey(orderInfo);
        }
        return res;
    }

    public Long countOrderInfo(OrderInfo orderInfo) {
        OrderInfoExample example = getOrderInfoExample(orderInfo);
        return orderInfoMapper.countByExample(example);
    }

    public Long countOrderInfoByExample(OrderInfoExample example) {
        return orderInfoMapper.countByExample(example);
    }

    @Cacheable(value = "OrderInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<OrderInfo> getAllOrderInfoList(Byte valid) {
        OrderInfoExample example = new OrderInfoExample();
        OrderInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return orderInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "OrderInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public OrderInfo getOrderInfobyId(Integer id) {
        return orderInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "OrderInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<OrderInfo> getOrderInfoSelective(OrderInfo orderInfo) {
        OrderInfoExample example = getOrderInfoExample(orderInfo);
        List<OrderInfo> res = orderInfoMapper.selectByExample(example);
        return res;
    }

    public List<OrderInfo> getOrderInfoByExample(OrderInfoExample example) {
        List<OrderInfo> res = orderInfoMapper.selectByExample(example);
        return res;
    }

    private OrderInfoExample getOrderInfoExample(OrderInfo orderInfo) {
        OrderInfoExample example = new OrderInfoExample();
        OrderInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(orderInfo);
        ReflectionUtil.getFieldList(orderInfo).forEach((field) -> {
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