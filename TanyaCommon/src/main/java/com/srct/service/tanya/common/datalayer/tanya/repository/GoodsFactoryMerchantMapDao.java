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
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.GoodsFactoryMerchantMapMapper;
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

@Repository("tanyaGoodsFactoryMerchantMapDao")
public class GoodsFactoryMerchantMapDao {

    @Autowired
    GoodsFactoryMerchantMapMapper goodsFactoryMerchantMapMapper;

    @CacheEvict(value = "GoodsFactoryMerchantMap", allEntries = true)
    public GoodsFactoryMerchantMap updateGoodsFactoryMerchantMap(GoodsFactoryMerchantMap goodsFactoryMerchantMap) {
        int res = 0;
        if (goodsFactoryMerchantMap.getId() == null) {
            goodsFactoryMerchantMap.setCreateAt(new Date());
            res = goodsFactoryMerchantMapMapper.insertSelective(goodsFactoryMerchantMap);
        } else {
            goodsFactoryMerchantMap.setUpdateAt(new Date());
            res = goodsFactoryMerchantMapMapper.updateByPrimaryKeySelective(goodsFactoryMerchantMap);
        }
        if (res == 0) {
            throw new ServiceException("update GoodsFactoryMerchantMap error");
        }
        return goodsFactoryMerchantMap;
    }

    @CacheEvict(value = "GoodsFactoryMerchantMap", allEntries = true)
    public GoodsFactoryMerchantMap updateGoodsFactoryMerchantMapStrict(
            GoodsFactoryMerchantMap goodsFactoryMerchantMap) {
        int res = 0;
        if (goodsFactoryMerchantMap.getId() == null) {
            goodsFactoryMerchantMap.setCreateAt(new Date());
            res = goodsFactoryMerchantMapMapper.insert(goodsFactoryMerchantMap);
        } else {
            goodsFactoryMerchantMap.setUpdateAt(new Date());
            res = goodsFactoryMerchantMapMapper.updateByPrimaryKey(goodsFactoryMerchantMap);
        }
        if (res == 0) {
            throw new ServiceException("update GoodsFactoryMerchantMap error");
        }
        return goodsFactoryMerchantMap;
    }

    @CacheEvict(value = "GoodsFactoryMerchantMap", allEntries = true)
    public Integer updateGoodsFactoryMerchantMapByExample(GoodsFactoryMerchantMap goodsFactoryMerchantMap,
            GoodsFactoryMerchantMapExample example) {
        return goodsFactoryMerchantMapMapper.updateByExampleSelective(goodsFactoryMerchantMap, example);
    }

    @CacheEvict(value = "GoodsFactoryMerchantMap", allEntries = true)
    public Integer updateGoodsFactoryMerchantMapByExampleStrict(GoodsFactoryMerchantMap goodsFactoryMerchantMap,
            GoodsFactoryMerchantMapExample example) {
        return goodsFactoryMerchantMapMapper.updateByExample(goodsFactoryMerchantMap, example);
    }

    @CacheEvict(value = "GoodsFactoryMerchantMap", allEntries = true)
    public Integer delGoodsFactoryMerchantMap(GoodsFactoryMerchantMap goodsFactoryMerchantMap) {
        GoodsFactoryMerchantMapExample example = getGoodsFactoryMerchantMapExample(goodsFactoryMerchantMap);
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return goodsFactoryMerchantMapMapper.updateByExampleSelective(goodsFactoryMerchantMap, example);
    }

    @CacheEvict(value = "GoodsFactoryMerchantMap", allEntries = true)
    public Integer delGoodsFactoryMerchantMapByExample(GoodsFactoryMerchantMapExample example) {
        Integer res = 0;
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList = getGoodsFactoryMerchantMapByExample(example);
        for (GoodsFactoryMerchantMap goodsFactoryMerchantMap : goodsFactoryMerchantMapList) {
            goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += goodsFactoryMerchantMapMapper.updateByPrimaryKey(goodsFactoryMerchantMap);
        }
        return res;
    }

    public Long countGoodsFactoryMerchantMap(GoodsFactoryMerchantMap goodsFactoryMerchantMap) {
        GoodsFactoryMerchantMapExample example = getGoodsFactoryMerchantMapExample(goodsFactoryMerchantMap);
        return goodsFactoryMerchantMapMapper.countByExample(example);
    }

    public Long countGoodsFactoryMerchantMapByExample(GoodsFactoryMerchantMapExample example) {
        return goodsFactoryMerchantMapMapper.countByExample(example);
    }

    @Cacheable(value = "GoodsFactoryMerchantMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<GoodsFactoryMerchantMap> getAllGoodsFactoryMerchantMapList(Byte valid) {
        GoodsFactoryMerchantMapExample example = new GoodsFactoryMerchantMapExample();
        GoodsFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return goodsFactoryMerchantMapMapper.selectByExample(example);
    }

    @Cacheable(value = "GoodsFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<GoodsFactoryMerchantMap> getAllGoodsFactoryMerchantMapList(Byte valid, PageInfo<?> pageInfo) {
        GoodsFactoryMerchantMapExample example = new GoodsFactoryMerchantMapExample();
        GoodsFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<GoodsFactoryMerchantMap> res = goodsFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<GoodsFactoryMerchantMap>(res);
    }

    @Cacheable(value = "GoodsFactoryMerchantMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public GoodsFactoryMerchantMap getGoodsFactoryMerchantMapById(Integer id) {
        return goodsFactoryMerchantMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "GoodsFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapSelective(
            GoodsFactoryMerchantMap goodsFactoryMerchantMap, PageInfo<?> pageInfo) {
        GoodsFactoryMerchantMapExample example = getGoodsFactoryMerchantMapExample(goodsFactoryMerchantMap);
        PageHelper.startPage(pageInfo);
        List<GoodsFactoryMerchantMap> res = goodsFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<GoodsFactoryMerchantMap>(res);
    }

    @Cacheable(value = "GoodsFactoryMerchantMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapSelective(
            GoodsFactoryMerchantMap goodsFactoryMerchantMap) {
        GoodsFactoryMerchantMapExample example = getGoodsFactoryMerchantMapExample(goodsFactoryMerchantMap);
        List<GoodsFactoryMerchantMap> res = goodsFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    public PageInfo<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapByExample(GoodsFactoryMerchantMapExample example,
            PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<GoodsFactoryMerchantMap> res = goodsFactoryMerchantMapMapper.selectByExample(example);
        return new PageInfo<GoodsFactoryMerchantMap>(res);
    }

    public List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapByExample(GoodsFactoryMerchantMapExample example) {
        List<GoodsFactoryMerchantMap> res = goodsFactoryMerchantMapMapper.selectByExample(example);
        return res;
    }

    private GoodsFactoryMerchantMapExample getGoodsFactoryMerchantMapExample(
            GoodsFactoryMerchantMap goodsFactoryMerchantMap) {
        GoodsFactoryMerchantMapExample example = new GoodsFactoryMerchantMapExample();
        GoodsFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(goodsFactoryMerchantMap);
        ReflectionUtil.getFields(goodsFactoryMerchantMap).forEach((field) -> {
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
