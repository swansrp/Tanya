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
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.MerchantInfoMapper;
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

@Repository("tanyaMerchantInfoDao")
public class MerchantInfoDao {

    @Autowired
    MerchantInfoMapper merchantInfoMapper;

    @CacheEvict(value = "MerchantInfo", allEntries = true)
    public MerchantInfo updateMerchantInfo(MerchantInfo merchantInfo) {
        int res = 0;
        if (merchantInfo.getId() == null) {
            merchantInfo.setCreateAt(new Date());
            res = merchantInfoMapper.insertSelective(merchantInfo);
        } else {
            merchantInfo.setUpdateAt(new Date());
            res = merchantInfoMapper.updateByPrimaryKeySelective(merchantInfo);
        }
        if (res == 0) {
            throw new ServiceException("update MerchantInfo error");
        }
        return merchantInfo;
    }

    @CacheEvict(value = "MerchantInfo", allEntries = true)
    public MerchantInfo updateMerchantInfoStrict(MerchantInfo merchantInfo) {
        int res = 0;
        if (merchantInfo.getId() == null) {
            merchantInfo.setCreateAt(new Date());
            res = merchantInfoMapper.insert(merchantInfo);
        } else {
            merchantInfo.setUpdateAt(new Date());
            res = merchantInfoMapper.updateByPrimaryKey(merchantInfo);
        }
        if (res == 0) {
            throw new ServiceException("update MerchantInfo error");
        }
        return merchantInfo;
    }

    @CacheEvict(value = "MerchantInfo", allEntries = true)
    public Integer updateMerchantInfoByExample(MerchantInfo merchantInfo, MerchantInfoExample example) {
        return merchantInfoMapper.updateByExampleSelective(merchantInfo, example);
    }

    @CacheEvict(value = "MerchantInfo", allEntries = true)
    public Integer updateMerchantInfoByExampleStrict(MerchantInfo merchantInfo, MerchantInfoExample example) {
        return merchantInfoMapper.updateByExample(merchantInfo, example);
    }

    @CacheEvict(value = "MerchantInfo", allEntries = true)
    public Integer delMerchantInfo(MerchantInfo merchantInfo) {
        MerchantInfoExample example = getMerchantInfoExample(merchantInfo);
        merchantInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return merchantInfoMapper.updateByExampleSelective(merchantInfo, example);
    }

    @CacheEvict(value = "MerchantInfo", allEntries = true)
    public Integer delMerchantInfoByExample(MerchantInfoExample example) {
        Integer res = 0;
        List<MerchantInfo> merchantInfoList = getMerchantInfoByExample(example);
        for (MerchantInfo merchantInfo : merchantInfoList) {
            merchantInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += merchantInfoMapper.updateByPrimaryKey(merchantInfo);
        }
        return res;
    }

    public Long countMerchantInfo(MerchantInfo merchantInfo) {
        MerchantInfoExample example = getMerchantInfoExample(merchantInfo);
        return merchantInfoMapper.countByExample(example);
    }

    public Long countMerchantInfoByExample(MerchantInfoExample example) {
        return merchantInfoMapper.countByExample(example);
    }

    @Cacheable(value = "MerchantInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<MerchantInfo> getAllMerchantInfoList(Byte valid) {
        MerchantInfoExample example = new MerchantInfoExample();
        MerchantInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return merchantInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "MerchantInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<MerchantInfo> getAllMerchantInfoList(Byte valid, PageInfo<?> pageInfo) {
        MerchantInfoExample example = new MerchantInfoExample();
        MerchantInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<MerchantInfo> res = merchantInfoMapper.selectByExample(example);
        return new PageInfo<MerchantInfo>(res);
    }

    @Cacheable(value = "MerchantInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public MerchantInfo getMerchantInfoById(Integer id) {
        return merchantInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "MerchantInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<MerchantInfo> getMerchantInfoSelective(MerchantInfo merchantInfo, PageInfo<?> pageInfo) {
        MerchantInfoExample example = getMerchantInfoExample(merchantInfo);
        PageHelper.startPage(pageInfo);
        List<MerchantInfo> res = merchantInfoMapper.selectByExample(example);
        return new PageInfo<MerchantInfo>(res);
    }

    @Cacheable(value = "MerchantInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<MerchantInfo> getMerchantInfoSelective(MerchantInfo merchantInfo) {
        MerchantInfoExample example = getMerchantInfoExample(merchantInfo);
        List<MerchantInfo> res = merchantInfoMapper.selectByExample(example);
        return res;
    }

    public PageInfo<MerchantInfo> getMerchantInfoByExample(MerchantInfoExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<MerchantInfo> res = merchantInfoMapper.selectByExample(example);
        return new PageInfo<MerchantInfo>(res);
    }

    public List<MerchantInfo> getMerchantInfoByExample(MerchantInfoExample example) {
        List<MerchantInfo> res = merchantInfoMapper.selectByExample(example);
        return res;
    }

    private MerchantInfoExample getMerchantInfoExample(MerchantInfo merchantInfo) {
        MerchantInfoExample example = new MerchantInfoExample();
        MerchantInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(merchantInfo);
        ReflectionUtil.getFields(merchantInfo).forEach((field) -> {
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
