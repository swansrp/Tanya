/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository
 * @author: sharuopeng
 * @date: 2019/04/21
 */
package com.srct.service.tanya.common.datalayer.tanya.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.redis.CacheExpire;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopTraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopTraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.ShopTraderFactoryMerchantMapMapper;
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

@Repository("tanyaShopTraderFactoryMerchantMapDao")
public class ShopTraderFactoryMerchantMapDao {

    @Autowired
    ShopTraderFactoryMerchantMapMapper shopTraderFactoryMerchantMapMapper;

    @CacheEvict(value = "ShopTraderFactoryMerchantMap", allEntries = true)
    public ShopTraderFactoryMerchantMap updateShopTraderFactoryMerchantMap(
            ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap) {
        int res = 0;
        if (shopTraderFactoryMerchantMap.getId() == null) {
            shopTraderFactoryMerchantMap.setCreateAt(new Date());
            res = shopTraderFactoryMerchantMapMapper.insertSelective(shopTraderFactoryMerchantMap);
        } else {
            shopTraderFactoryMerchantMap.setUpdateAt(new Date());
            res = shopTraderFactoryMerchantMapMapper.updateByPrimaryKeySelective(shopTraderFactoryMerchantMap);
        }
        if (res == 0) {
            throw new ServiceException("update ShopTraderFactoryMerchantMap error");
        }
        return shopTraderFactoryMerchantMap;
    }

    @CacheEvict(value = "ShopTraderFactoryMerchantMap", allEntries = true)
    public ShopTraderFactoryMerchantMap updateShopTraderFactoryMerchantMapStrict(
            ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap) {
        int res = 0;
        if (shopTraderFactoryMerchantMap.getId() == null) {
            shopTraderFactoryMerchantMap.setCreateAt(new Date());
            res = shopTraderFactoryMerchantMapMapper.insert(shopTraderFactoryMerchantMap);
        } else {
            shopTraderFactoryMerchantMap.setUpdateAt(new Date());
            res = shopTraderFactoryMerchantMapMapper.updateByPrimaryKey(shopTraderFactoryMerchantMap);
        }
        if (res == 0) {
            throw new ServiceException("update ShopTraderFactoryMerchantMap error");
        }
        return shopTraderFactoryMerchantMap;
    }

    @CacheEvict(value = "ShopTraderFactoryMerchantMap", allEntries = true)
    public Integer updateShopTraderFactoryMerchantMapByExample(
            ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap, ShopTraderFactoryMerchantMapExample example) {
        return shopTraderFactoryMerchantMapMapper.updateByExampleSelective(shopTraderFactoryMerchantMap, example);
    }

    @CacheEvict(value = "ShopTraderFactoryMerchantMap", allEntries = true)
    public Integer updateShopTraderFactoryMerchantMapByExampleStrict(
            ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap, ShopTraderFactoryMerchantMapExample example) {
        return shopTraderFactoryMerchantMapMapper.updateByExample(shopTraderFactoryMerchantMap, example);
    }

    @CacheEvict(value = "ShopTraderFactoryMerchantMap", allEntries = true)
    public Integer delShopTraderFactoryMerchantMap(ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap) {
        ShopTraderFactoryMerchantMapExample example =
                getShopTraderFactoryMerchantMapExample(shopTraderFactoryMerchantMap);
        shopTraderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return shopTraderFactoryMerchantMapMapper.updateByExampleSelective(shopTraderFactoryMerchantMap, example);
    }

    @CacheEvict(value = "ShopTraderFactoryMerchantMap", allEntries = true)
    public Integer delShopTraderFactoryMerchantMapByExample(ShopTraderFactoryMerchantMapExample example) {
        Integer res = 0;
        List<ShopTraderFactoryMerchantMap> shopTraderFactoryMerchantMapList =
                getShopTraderFactoryMerchantMapByExample(example);
        for (ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap : shopTraderFactoryMerchantMapList) {
            shopTraderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += shopTraderFactoryMerchantMapMapper.updateByPrimaryKey(shopTraderFactoryMerchantMap);
        }
        return res;
    }

    public Long countShopTraderFactoryMerchantMap(ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap) {
        ShopTraderFactoryMerchantMapExample example =
                getShopTraderFactoryMerchantMapExample(shopTraderFactoryMerchantMap);
        return shopTraderFactoryMerchantMapMapper.countByExample(example);
    }

    public Long countShopTraderFactoryMerchantMapByExample(ShopTraderFactoryMerchantMapExample example) {
        return shopTraderFactoryMerchantMapMapper.countByExample(example);
    }

    @Cacheable(value = "ShopTraderFactoryMerchantMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<ShopTraderFactoryMerchantMap> getAllShopTraderFactoryMerchantMapList(Byte valid) {
        ShopTraderFactoryMerchantMapExample example = new ShopTraderFactoryMerchantMapExample();
        ShopTraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return shopTraderFactoryMerchantMapMapper.selectByExample(example);
    }

    @Cacheable(value = "ShopTraderFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<ShopTraderFactoryMerchantMap> getAllShopTraderFactoryMerchantMapList(Byte valid,
            PageInfo<?> pageInfo) {
        ShopTraderFactoryMerchantMapExample example = new ShopTraderFactoryMerchantMapExample();
        ShopTraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<ShopTraderFactoryMerchantMap> res = shopTraderFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<ShopTraderFactoryMerchantMap>(res);
    }

    @Cacheable(value = "ShopTraderFactoryMerchantMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public ShopTraderFactoryMerchantMap getShopTraderFactoryMerchantMapById(Integer id) {
        return shopTraderFactoryMerchantMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "ShopTraderFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<ShopTraderFactoryMerchantMap> getShopTraderFactoryMerchantMapSelective(
            ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap, PageInfo<?> pageInfo) {
        ShopTraderFactoryMerchantMapExample example =
                getShopTraderFactoryMerchantMapExample(shopTraderFactoryMerchantMap);
        PageHelper.startPage(pageInfo);
        List<ShopTraderFactoryMerchantMap> res = shopTraderFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<ShopTraderFactoryMerchantMap>(res);
    }

    @Cacheable(value = "ShopTraderFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<ShopTraderFactoryMerchantMap> getShopTraderFactoryMerchantMapSelective(
            ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap) {
        ShopTraderFactoryMerchantMapExample example =
                getShopTraderFactoryMerchantMapExample(shopTraderFactoryMerchantMap);
        List<ShopTraderFactoryMerchantMap> res = shopTraderFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    public PageInfo<ShopTraderFactoryMerchantMap> getShopTraderFactoryMerchantMapByExample(
            ShopTraderFactoryMerchantMapExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<ShopTraderFactoryMerchantMap> res = shopTraderFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<ShopTraderFactoryMerchantMap>(res);
    }

    public List<ShopTraderFactoryMerchantMap> getShopTraderFactoryMerchantMapByExample(
            ShopTraderFactoryMerchantMapExample example) {
        List<ShopTraderFactoryMerchantMap> res = shopTraderFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    private ShopTraderFactoryMerchantMapExample getShopTraderFactoryMerchantMapExample(
            ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap) {
        ShopTraderFactoryMerchantMapExample example = new ShopTraderFactoryMerchantMapExample();
        ShopTraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(shopTraderFactoryMerchantMap);
        ReflectionUtil.getFieldList(shopTraderFactoryMerchantMap).forEach((field) -> {
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
