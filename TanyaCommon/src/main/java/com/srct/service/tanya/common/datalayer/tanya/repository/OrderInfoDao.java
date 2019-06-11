/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository
 * @author: sharuopeng
 * @date: 2019/06/10
 */
package com.srct.service.tanya.common.datalayer.tanya.repository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.redis.CacheExpire;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.OrderInfoMapper;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Repository("tanyaOrderInfoDao")
public class OrderInfoDao {

    @Autowired
	private OrderInfoMapper orderInfoMapper;

    @CacheEvict(value = "OrderInfo", allEntries = true)
    public OrderInfo updateOrderInfo(OrderInfo orderInfo) {
        int res = 0;
        if (orderInfo.getId() == null){
	        orderInfo.setCreateAt(new Date());
            res = orderInfoMapper.insertSelective(orderInfo);
        } else{
	        orderInfo.setUpdateAt(new Date());
            res = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        } if (res == 0) {
            throw new ServiceException("update OrderInfo error");
        }
        return orderInfo;
    }

    @CacheEvict(value = "OrderInfo", allEntries = true)
    public OrderInfo updateOrderInfoStrict(OrderInfo orderInfo) {
        int res = 0;
        if (orderInfo.getId() == null){
	        orderInfo.setCreateAt(new Date());
            res = orderInfoMapper.insert(orderInfo);
        } else{
	        orderInfo.setUpdateAt(new Date());
            res = orderInfoMapper.updateByPrimaryKey(orderInfo);
        } if (res == 0) {
            throw new ServiceException("update OrderInfo error");
        }
        return orderInfo;
    }

    @CacheEvict(value = "OrderInfo", allEntries = true)
    public Integer updateOrderInfoByExample(OrderInfo orderInfo, OrderInfoExample example) {
        return orderInfoMapper.updateByExampleSelective(orderInfo, example);
    }

    @CacheEvict(value = "OrderInfo", allEntries = true)
    public Integer updateOrderInfoByExampleStrict(OrderInfo orderInfo, OrderInfoExample example) {
        return orderInfoMapper.updateByExample(orderInfo, example);
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
	    for (OrderInfo orderInfo :orderInfoList){
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
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return orderInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "OrderInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<OrderInfo> getAllOrderInfoList(Byte valid, PageInfo<?> pageInfo) {
		OrderInfoExample example = new OrderInfoExample();
		OrderInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<OrderInfo> res = orderInfoMapper.selectByExample(example); return new PageInfo<OrderInfo>(res);
    }

    @Cacheable(value = "OrderInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public OrderInfo getOrderInfoById(Integer id) {
        return orderInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "OrderInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<OrderInfo> getOrderInfoSelective(OrderInfo orderInfo, PageInfo<?> pageInfo) {
		OrderInfoExample example = getOrderInfoExample(orderInfo);
        PageHelper.startPage(pageInfo);
        List<OrderInfo> res = orderInfoMapper.selectByExample(example); return new PageInfo<OrderInfo>(res);
    }

    @Cacheable(value = "OrderInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<OrderInfo> getOrderInfoSelective(OrderInfo orderInfo) {
		OrderInfoExample example = getOrderInfoExample(orderInfo);
        List<OrderInfo> res = orderInfoMapper.selectByExample(example); return res;
    }

    public PageInfo<OrderInfo> getOrderInfoByExample(OrderInfoExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<OrderInfo> res = orderInfoMapper.selectByExample(example); return new PageInfo<OrderInfo>(res);
    }

    public List<OrderInfo> getOrderInfoByExample(OrderInfoExample example) {
        List<OrderInfo> res = orderInfoMapper.selectByExample(example); return res;
    }

    private OrderInfoExample getOrderInfoExample(OrderInfo orderInfo) {
		OrderInfoExample example = new OrderInfoExample();
		OrderInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(orderInfo);
        ReflectionUtil.getFields(orderInfo).forEach((field) -> {
            if (valueMap.get(field.getName()) != null) {
                Method criteriaMethod = null;
                try {
                    String criteriaMethodName = "and" + StringUtil.firstUpperCamelCase(field.getName()) + "EqualTo";
                    criteriaMethod = criteria.getClass().getMethod(criteriaMethodName, field.getType());
                } catch (NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
                if (criteriaMethod == null)
                    throw new ServiceException("");
                try {
                    criteriaMethod.invoke(criteria, valueMap.get(field.getName()));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        return example;
    }
}
