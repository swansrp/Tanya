/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository 
 * @author: sharuopeng   
 * @date: 2019/02/23
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.redis.CacheExpire;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.GoodsInfoMapper;

import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.StringUtil;


/**
 * @ClassName: GoodsInfoDao
 * @Description: TODO
 */
@Repository("tanyaGoodsInfoDao")
public class GoodsInfoDao {

    @Autowired
    GoodsInfoMapper goodsInfoMapper;

    @CacheEvict(value = "GoodsInfo", allEntries = true)
    public GoodsInfo updateGoodsInfo(GoodsInfo goodsInfo) {
        int res = 0;
        if (goodsInfo.getId() == null) {
            goodsInfo.setCreateAt(new Date());
            res = goodsInfoMapper.insertSelective(goodsInfo);
        } else {
            goodsInfo.setUpdateAt(new Date());
            res = goodsInfoMapper.updateByPrimaryKeySelective(goodsInfo);
        }
        if(res == 0) {
            throw new ServiceException("update GoodsInfo error");
        }
        return goodsInfo;
    }

    @CacheEvict(value = "GoodsInfo", allEntries = true)
    public GoodsInfo updateGoodsInfoStrict(GoodsInfo goodsInfo) {
        int res = 0;
        if (goodsInfo.getId() == null) {
            goodsInfo.setCreateAt(new Date());
            res = goodsInfoMapper.insert(goodsInfo);
        } else {
            goodsInfo.setUpdateAt(new Date());
            res = goodsInfoMapper.updateByPrimaryKey(goodsInfo);
        }
        if(res == 0) {
            throw new ServiceException("update GoodsInfo error");
        }
        return goodsInfo;
    }

    @CacheEvict(value = "GoodsInfo", allEntries = true)
    public Integer updateGoodsInfoByExample(GoodsInfo goodsInfo, GoodsInfoExample example) {
        return goodsInfoMapper.updateByExampleSelective(goodsInfo, example);
    }

    @CacheEvict(value = "GoodsInfo", allEntries = true)
    public Integer updateGoodsInfoByExampleStrict(GoodsInfo goodsInfo, GoodsInfoExample example) {
        return goodsInfoMapper.updateByExample(goodsInfo, example);
    }

    @CacheEvict(value = "GoodsInfo", allEntries = true)
    public Integer delGoodsInfo(GoodsInfo goodsInfo) {
        GoodsInfoExample example = getGoodsInfoExample(goodsInfo);
        goodsInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return goodsInfoMapper.updateByExampleSelective(goodsInfo, example);
    }

    @CacheEvict(value = "GoodsInfo", allEntries = true)
    public Integer delGoodsInfoByExample(GoodsInfoExample example) {
        Integer res = 0;
        List<GoodsInfo> goodsInfoList = getGoodsInfoByExample(example);
        for (GoodsInfo goodsInfo : goodsInfoList) {
            goodsInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += goodsInfoMapper.updateByPrimaryKey(goodsInfo);
        }
        return res;
    }

    public Long countGoodsInfo(GoodsInfo goodsInfo) {
        GoodsInfoExample example = getGoodsInfoExample(goodsInfo);
        return goodsInfoMapper.countByExample(example);
    }

    public Long countGoodsInfoByExample(GoodsInfoExample example) {
        return goodsInfoMapper.countByExample(example);
    }

    @Cacheable(value = "GoodsInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<GoodsInfo> getAllGoodsInfoList(Byte valid) {
        GoodsInfoExample example = new GoodsInfoExample();
        GoodsInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return goodsInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "GoodsInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public GoodsInfo getGoodsInfobyId(Integer id) {
        return goodsInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "GoodsInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<GoodsInfo> getShopInfoSelective(GoodsInfo goodsInfo, PageInfo<?> pageInfo) {
        GoodsInfoExample example = getGoodsInfoExample(goodsInfo);
        PageHelper.startPage(pageInfo);
        List<GoodsInfo> res = goodsInfoMapper.selectByExample(example);
        pageInfo = new PageInfo<GoodsInfo>(res);
        return res;
    }
    @Cacheable(value = "GoodsInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<GoodsInfo> getGoodsInfoSelective(GoodsInfo goodsInfo) {
        GoodsInfoExample example = getGoodsInfoExample(goodsInfo);
        List<GoodsInfo> res = goodsInfoMapper.selectByExample(example);
        return res;
    }

    public List<GoodsInfo> getGoodsInfoByExample(GoodsInfoExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<GoodsInfo> res = goodsInfoMapper.selectByExample(example);
        pageInfo = new PageInfo<GoodsInfo>(res);
        return res;
    }
    public List<GoodsInfo> getGoodsInfoByExample(GoodsInfoExample example) {
        List<GoodsInfo> res = goodsInfoMapper.selectByExample(example);
        return res;
    }

    private GoodsInfoExample getGoodsInfoExample(GoodsInfo goodsInfo) {
        GoodsInfoExample example = new GoodsInfoExample();
        GoodsInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(goodsInfo);
        ReflectionUtil.getFieldList(goodsInfo).forEach((field) -> {
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