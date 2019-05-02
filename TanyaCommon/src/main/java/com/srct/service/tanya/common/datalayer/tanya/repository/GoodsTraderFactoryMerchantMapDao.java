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
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsTraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsTraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.GoodsTraderFactoryMerchantMapMapper;
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

@Repository("tanyaGoodsTraderFactoryMerchantMapDao")
public class GoodsTraderFactoryMerchantMapDao {

    @Autowired
    GoodsTraderFactoryMerchantMapMapper goodsTraderFactoryMerchantMapMapper;

    @CacheEvict(value = "GoodsTraderFactoryMerchantMap", allEntries = true)
    public GoodsTraderFactoryMerchantMap updateGoodsTraderFactoryMerchantMap(
            GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap) {
        int res = 0;
        if (goodsTraderFactoryMerchantMap.getId() == null) {
            goodsTraderFactoryMerchantMap.setCreateAt(new Date());
            res = goodsTraderFactoryMerchantMapMapper.insertSelective(goodsTraderFactoryMerchantMap);
        } else {
            goodsTraderFactoryMerchantMap.setUpdateAt(new Date());
            res = goodsTraderFactoryMerchantMapMapper.updateByPrimaryKeySelective(goodsTraderFactoryMerchantMap);
        }
        if (res == 0) {
            throw new ServiceException("update GoodsTraderFactoryMerchantMap error");
        }
        return goodsTraderFactoryMerchantMap;
    }

    @CacheEvict(value = "GoodsTraderFactoryMerchantMap", allEntries = true)
    public GoodsTraderFactoryMerchantMap updateGoodsTraderFactoryMerchantMapStrict(
            GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap) {
        int res = 0;
        if (goodsTraderFactoryMerchantMap.getId() == null) {
            goodsTraderFactoryMerchantMap.setCreateAt(new Date());
            res = goodsTraderFactoryMerchantMapMapper.insert(goodsTraderFactoryMerchantMap);
        } else {
            goodsTraderFactoryMerchantMap.setUpdateAt(new Date());
            res = goodsTraderFactoryMerchantMapMapper.updateByPrimaryKey(goodsTraderFactoryMerchantMap);
        }
        if (res == 0) {
            throw new ServiceException("update GoodsTraderFactoryMerchantMap error");
        }
        return goodsTraderFactoryMerchantMap;
    }

    @CacheEvict(value = "GoodsTraderFactoryMerchantMap", allEntries = true)
    public Integer updateGoodsTraderFactoryMerchantMapByExample(
            GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap, GoodsTraderFactoryMerchantMapExample example) {
        return goodsTraderFactoryMerchantMapMapper.updateByExampleSelective(goodsTraderFactoryMerchantMap, example);
    }

    @CacheEvict(value = "GoodsTraderFactoryMerchantMap", allEntries = true)
    public Integer updateGoodsTraderFactoryMerchantMapByExampleStrict(
            GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap, GoodsTraderFactoryMerchantMapExample example) {
        return goodsTraderFactoryMerchantMapMapper.updateByExample(goodsTraderFactoryMerchantMap, example);
    }

    @CacheEvict(value = "GoodsTraderFactoryMerchantMap", allEntries = true)
    public Integer delGoodsTraderFactoryMerchantMap(GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap) {
        GoodsTraderFactoryMerchantMapExample example =
                getGoodsTraderFactoryMerchantMapExample(goodsTraderFactoryMerchantMap);
        goodsTraderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return goodsTraderFactoryMerchantMapMapper.updateByExampleSelective(goodsTraderFactoryMerchantMap, example);
    }

    @CacheEvict(value = "GoodsTraderFactoryMerchantMap", allEntries = true)
    public Integer delGoodsTraderFactoryMerchantMapByExample(GoodsTraderFactoryMerchantMapExample example) {
        Integer res = 0;
        List<GoodsTraderFactoryMerchantMap> goodsTraderFactoryMerchantMapList =
                getGoodsTraderFactoryMerchantMapByExample(example);
        for (GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap : goodsTraderFactoryMerchantMapList) {
            goodsTraderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += goodsTraderFactoryMerchantMapMapper.updateByPrimaryKey(goodsTraderFactoryMerchantMap);
        }
        return res;
    }

    public Long countGoodsTraderFactoryMerchantMap(GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap) {
        GoodsTraderFactoryMerchantMapExample example =
                getGoodsTraderFactoryMerchantMapExample(goodsTraderFactoryMerchantMap);
        return goodsTraderFactoryMerchantMapMapper.countByExample(example);
    }

    public Long countGoodsTraderFactoryMerchantMapByExample(GoodsTraderFactoryMerchantMapExample example) {
        return goodsTraderFactoryMerchantMapMapper.countByExample(example);
    }

    @Cacheable(value = "GoodsTraderFactoryMerchantMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<GoodsTraderFactoryMerchantMap> getAllGoodsTraderFactoryMerchantMapList(Byte valid) {
        GoodsTraderFactoryMerchantMapExample example = new GoodsTraderFactoryMerchantMapExample();
        GoodsTraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return goodsTraderFactoryMerchantMapMapper.selectByExample(example);
    }

    @Cacheable(value = "GoodsTraderFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<GoodsTraderFactoryMerchantMap> getAllGoodsTraderFactoryMerchantMapList(Byte valid,
            PageInfo<?> pageInfo) {
        GoodsTraderFactoryMerchantMapExample example = new GoodsTraderFactoryMerchantMapExample();
        GoodsTraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<GoodsTraderFactoryMerchantMap> res = goodsTraderFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<GoodsTraderFactoryMerchantMap>(res);
    }

    @Cacheable(value = "GoodsTraderFactoryMerchantMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public GoodsTraderFactoryMerchantMap getGoodsTraderFactoryMerchantMapById(Integer id) {
        return goodsTraderFactoryMerchantMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "GoodsTraderFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<GoodsTraderFactoryMerchantMap> getGoodsTraderFactoryMerchantMapSelective(
            GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap, PageInfo<?> pageInfo) {
        GoodsTraderFactoryMerchantMapExample example =
                getGoodsTraderFactoryMerchantMapExample(goodsTraderFactoryMerchantMap);
        PageHelper.startPage(pageInfo);
        List<GoodsTraderFactoryMerchantMap> res = goodsTraderFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<GoodsTraderFactoryMerchantMap>(res);
    }

    @Cacheable(value = "GoodsTraderFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<GoodsTraderFactoryMerchantMap> getGoodsTraderFactoryMerchantMapSelective(
            GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap) {
        GoodsTraderFactoryMerchantMapExample example =
                getGoodsTraderFactoryMerchantMapExample(goodsTraderFactoryMerchantMap);
        List<GoodsTraderFactoryMerchantMap> res = goodsTraderFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    public PageInfo<GoodsTraderFactoryMerchantMap> getGoodsTraderFactoryMerchantMapByExample(
            GoodsTraderFactoryMerchantMapExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<GoodsTraderFactoryMerchantMap> res = goodsTraderFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<GoodsTraderFactoryMerchantMap>(res);
    }

    public List<GoodsTraderFactoryMerchantMap> getGoodsTraderFactoryMerchantMapByExample(
            GoodsTraderFactoryMerchantMapExample example) {
        List<GoodsTraderFactoryMerchantMap> res = goodsTraderFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    private GoodsTraderFactoryMerchantMapExample getGoodsTraderFactoryMerchantMapExample(
            GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap) {
        GoodsTraderFactoryMerchantMapExample example = new GoodsTraderFactoryMerchantMapExample();
        GoodsTraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(goodsTraderFactoryMerchantMap);
        ReflectionUtil.getFieldList(goodsTraderFactoryMerchantMap).forEach((field) -> {
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
