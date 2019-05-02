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
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.ShopInfoMapper;
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

@Repository("tanyaShopInfoDao")
public class ShopInfoDao {

    @Autowired
    ShopInfoMapper shopInfoMapper;

    @CacheEvict(value = "ShopInfo", allEntries = true)
    public ShopInfo updateShopInfo(ShopInfo shopInfo) {
        int res = 0;
        if (shopInfo.getId() == null) {
            shopInfo.setCreateAt(new Date());
            res = shopInfoMapper.insertSelective(shopInfo);
        } else {
            shopInfo.setUpdateAt(new Date());
            res = shopInfoMapper.updateByPrimaryKeySelective(shopInfo);
        }
        if (res == 0) {
            throw new ServiceException("update ShopInfo error");
        }
        return shopInfo;
    }

    @CacheEvict(value = "ShopInfo", allEntries = true)
    public ShopInfo updateShopInfoStrict(ShopInfo shopInfo) {
        int res = 0;
        if (shopInfo.getId() == null) {
            shopInfo.setCreateAt(new Date());
            res = shopInfoMapper.insert(shopInfo);
        } else {
            shopInfo.setUpdateAt(new Date());
            res = shopInfoMapper.updateByPrimaryKey(shopInfo);
        }
        if (res == 0) {
            throw new ServiceException("update ShopInfo error");
        }
        return shopInfo;
    }

    @CacheEvict(value = "ShopInfo", allEntries = true)
    public Integer updateShopInfoByExample(ShopInfo shopInfo, ShopInfoExample example) {
        return shopInfoMapper.updateByExampleSelective(shopInfo, example);
    }

    @CacheEvict(value = "ShopInfo", allEntries = true)
    public Integer updateShopInfoByExampleStrict(ShopInfo shopInfo, ShopInfoExample example) {
        return shopInfoMapper.updateByExample(shopInfo, example);
    }

    @CacheEvict(value = "ShopInfo", allEntries = true)
    public Integer delShopInfo(ShopInfo shopInfo) {
        ShopInfoExample example = getShopInfoExample(shopInfo);
        shopInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return shopInfoMapper.updateByExampleSelective(shopInfo, example);
    }

    @CacheEvict(value = "ShopInfo", allEntries = true)
    public Integer delShopInfoByExample(ShopInfoExample example) {
        Integer res = 0;
        List<ShopInfo> shopInfoList = getShopInfoByExample(example);
        for (ShopInfo shopInfo : shopInfoList) {
            shopInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += shopInfoMapper.updateByPrimaryKey(shopInfo);
        }
        return res;
    }

    public Long countShopInfo(ShopInfo shopInfo) {
        ShopInfoExample example = getShopInfoExample(shopInfo);
        return shopInfoMapper.countByExample(example);
    }

    public Long countShopInfoByExample(ShopInfoExample example) {
        return shopInfoMapper.countByExample(example);
    }

    @Cacheable(value = "ShopInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<ShopInfo> getAllShopInfoList(Byte valid) {
        ShopInfoExample example = new ShopInfoExample();
        ShopInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return shopInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "ShopInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<ShopInfo> getAllShopInfoList(Byte valid, PageInfo<?> pageInfo) {
        ShopInfoExample example = new ShopInfoExample();
        ShopInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<ShopInfo> res = shopInfoMapper.selectByExample(example);
        return new PageInfo<ShopInfo>(res);
    }

    @Cacheable(value = "ShopInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public ShopInfo getShopInfoById(Integer id) {
        return shopInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "ShopInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<ShopInfo> getShopInfoSelective(ShopInfo shopInfo, PageInfo<?> pageInfo) {
        ShopInfoExample example = getShopInfoExample(shopInfo);
        PageHelper.startPage(pageInfo);
        List<ShopInfo> res = shopInfoMapper.selectByExample(example);
        return new PageInfo<ShopInfo>(res);
    }

    @Cacheable(value = "ShopInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<ShopInfo> getShopInfoSelective(ShopInfo shopInfo) {
        ShopInfoExample example = getShopInfoExample(shopInfo);
        List<ShopInfo> res = shopInfoMapper.selectByExample(example);
        return res;
    }

    public PageInfo<ShopInfo> getShopInfoByExample(ShopInfoExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<ShopInfo> res = shopInfoMapper.selectByExample(example);
        return new PageInfo<ShopInfo>(res);
    }

    public List<ShopInfo> getShopInfoByExample(ShopInfoExample example) {
        List<ShopInfo> res = shopInfoMapper.selectByExample(example);
        return res;
    }

    private ShopInfoExample getShopInfoExample(ShopInfo shopInfo) {
        ShopInfoExample example = new ShopInfoExample();
        ShopInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(shopInfo);
        ReflectionUtil.getFieldList(shopInfo).forEach((field) -> {
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
