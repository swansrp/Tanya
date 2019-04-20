/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository
 * @author: sharuopeng
 * @date: 2019/04/20
 */
package com.srct.service.tanya.common.datalayer.tanya.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.redis.CacheExpire;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.ShopFactoryMerchantMapMapper;
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

@Repository("tanyaShopFactoryMerchantMapDao")
public class ShopFactoryMerchantMapDao {

    @Autowired
    ShopFactoryMerchantMapMapper shopFactoryMerchantMapMapper;

    @CacheEvict(value = "ShopFactoryMerchantMap", allEntries = true)
    public ShopFactoryMerchantMap updateShopFactoryMerchantMap(ShopFactoryMerchantMap shopFactoryMerchantMap) {
        int res = 0;
        if (shopFactoryMerchantMap.getId() == null) {
            shopFactoryMerchantMap.setCreateAt(new Date());
            res = shopFactoryMerchantMapMapper.insertSelective(shopFactoryMerchantMap);
        } else {
            shopFactoryMerchantMap.setUpdateAt(new Date());
            res = shopFactoryMerchantMapMapper.updateByPrimaryKeySelective(shopFactoryMerchantMap);
        }
        if (res == 0) {
            throw new ServiceException("update ShopFactoryMerchantMap error");
        }
        return shopFactoryMerchantMap;
    }

    @CacheEvict(value = "ShopFactoryMerchantMap", allEntries = true)
    public ShopFactoryMerchantMap updateShopFactoryMerchantMapStrict(ShopFactoryMerchantMap shopFactoryMerchantMap) {
        int res = 0;
        if (shopFactoryMerchantMap.getId() == null) {
            shopFactoryMerchantMap.setCreateAt(new Date());
            res = shopFactoryMerchantMapMapper.insert(shopFactoryMerchantMap);
        } else {
            shopFactoryMerchantMap.setUpdateAt(new Date());
            res = shopFactoryMerchantMapMapper.updateByPrimaryKey(shopFactoryMerchantMap);
        }
        if (res == 0) {
            throw new ServiceException("update ShopFactoryMerchantMap error");
        }
        return shopFactoryMerchantMap;
    }

    @CacheEvict(value = "ShopFactoryMerchantMap", allEntries = true)
    public Integer updateShopFactoryMerchantMapByExample(ShopFactoryMerchantMap shopFactoryMerchantMap,
            ShopFactoryMerchantMapExample example) {
        return shopFactoryMerchantMapMapper.updateByExampleSelective(shopFactoryMerchantMap, example);
    }

    @CacheEvict(value = "ShopFactoryMerchantMap", allEntries = true)
    public Integer updateShopFactoryMerchantMapByExampleStrict(ShopFactoryMerchantMap shopFactoryMerchantMap,
            ShopFactoryMerchantMapExample example) {
        return shopFactoryMerchantMapMapper.updateByExample(shopFactoryMerchantMap, example);
    }

    @CacheEvict(value = "ShopFactoryMerchantMap", allEntries = true)
    public Integer delShopFactoryMerchantMap(ShopFactoryMerchantMap shopFactoryMerchantMap) {
        ShopFactoryMerchantMapExample example = getShopFactoryMerchantMapExample(shopFactoryMerchantMap);
        shopFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return shopFactoryMerchantMapMapper.updateByExampleSelective(shopFactoryMerchantMap, example);
    }

    @CacheEvict(value = "ShopFactoryMerchantMap", allEntries = true)
    public Integer delShopFactoryMerchantMapByExample(ShopFactoryMerchantMapExample example) {
        Integer res = 0;
        List<ShopFactoryMerchantMap> shopFactoryMerchantMapList = getShopFactoryMerchantMapByExample(example);
        for (ShopFactoryMerchantMap shopFactoryMerchantMap : shopFactoryMerchantMapList) {
            shopFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += shopFactoryMerchantMapMapper.updateByPrimaryKey(shopFactoryMerchantMap);
        }
        return res;
    }

    public Long countShopFactoryMerchantMap(ShopFactoryMerchantMap shopFactoryMerchantMap) {
        ShopFactoryMerchantMapExample example = getShopFactoryMerchantMapExample(shopFactoryMerchantMap);
        return shopFactoryMerchantMapMapper.countByExample(example);
    }

    public Long countShopFactoryMerchantMapByExample(ShopFactoryMerchantMapExample example) {
        return shopFactoryMerchantMapMapper.countByExample(example);
    }

    @Cacheable(value = "ShopFactoryMerchantMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<ShopFactoryMerchantMap> getAllShopFactoryMerchantMapList(Byte valid) {
        ShopFactoryMerchantMapExample example = new ShopFactoryMerchantMapExample();
        ShopFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return shopFactoryMerchantMapMapper.selectByExample(example);
    }

    @Cacheable(value = "ShopFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<ShopFactoryMerchantMap> getAllShopFactoryMerchantMapList(Byte valid, PageInfo<?> pageInfo) {
        ShopFactoryMerchantMapExample example = new ShopFactoryMerchantMapExample();
        ShopFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<ShopFactoryMerchantMap> res = shopFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<ShopFactoryMerchantMap>(res);
    }

    @Cacheable(value = "ShopFactoryMerchantMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public ShopFactoryMerchantMap getShopFactoryMerchantMapById(Integer id) {
        return shopFactoryMerchantMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "ShopFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<ShopFactoryMerchantMap> getShopFactoryMerchantMapSelective(
            ShopFactoryMerchantMap shopFactoryMerchantMap, PageInfo<?> pageInfo) {
        ShopFactoryMerchantMapExample example = getShopFactoryMerchantMapExample(shopFactoryMerchantMap);
        PageHelper.startPage(pageInfo);
        List<ShopFactoryMerchantMap> res = shopFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<ShopFactoryMerchantMap>(res);
    }

    @Cacheable(value = "ShopFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<ShopFactoryMerchantMap> getShopFactoryMerchantMapSelective(
            ShopFactoryMerchantMap shopFactoryMerchantMap) {
        ShopFactoryMerchantMapExample example = getShopFactoryMerchantMapExample(shopFactoryMerchantMap);
        List<ShopFactoryMerchantMap> res = shopFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    public PageInfo<ShopFactoryMerchantMap> getShopFactoryMerchantMapByExample(ShopFactoryMerchantMapExample example,
            PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<ShopFactoryMerchantMap> res = shopFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<ShopFactoryMerchantMap>(res);
    }

    public List<ShopFactoryMerchantMap> getShopFactoryMerchantMapByExample(ShopFactoryMerchantMapExample example) {
        List<ShopFactoryMerchantMap> res = shopFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    private ShopFactoryMerchantMapExample getShopFactoryMerchantMapExample(
            ShopFactoryMerchantMap shopFactoryMerchantMap) {
        ShopFactoryMerchantMapExample example = new ShopFactoryMerchantMapExample();
        ShopFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(shopFactoryMerchantMap);
        ReflectionUtil.getFieldList(shopFactoryMerchantMap).forEach((field) -> {
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
