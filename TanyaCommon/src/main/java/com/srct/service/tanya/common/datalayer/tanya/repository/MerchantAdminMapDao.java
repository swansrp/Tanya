/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.datalayer.tanya.repository
 * @author: sharuopeng
 * @date: 2019/04/04
 */
package com.srct.service.tanya.common.datalayer.tanya.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.redis.CacheExpire;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantAdminMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantAdminMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.MerchantAdminMapMapper;
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


/**
 * @ClassName: MerchantAdminMapDao
 * @Description: Basic Repository
 */
@Repository("tanyaMerchantAdminMapDao")
public class MerchantAdminMapDao {

    @Autowired
    MerchantAdminMapMapper merchantAdminMapMapper;

    @CacheEvict(value = "MerchantAdminMap", allEntries = true)
    public MerchantAdminMap updateMerchantAdminMap(MerchantAdminMap merchantAdminMap) {
        int res = 0;
        if (merchantAdminMap.getId() == null) {
            merchantAdminMap.setCreateAt(new Date());
            res = merchantAdminMapMapper.insertSelective(merchantAdminMap);
        } else {
            merchantAdminMap.setUpdateAt(new Date());
            res = merchantAdminMapMapper.updateByPrimaryKeySelective(merchantAdminMap);
        }
        if (res == 0) {
            throw new ServiceException("update MerchantAdminMap error");
        }
        return merchantAdminMap;
    }

    @CacheEvict(value = "MerchantAdminMap", allEntries = true)
    public MerchantAdminMap updateMerchantAdminMapStrict(MerchantAdminMap merchantAdminMap) {
        int res = 0;
        if (merchantAdminMap.getId() == null) {
            merchantAdminMap.setCreateAt(new Date());
            res = merchantAdminMapMapper.insert(merchantAdminMap);
        } else {
            merchantAdminMap.setUpdateAt(new Date());
            res = merchantAdminMapMapper.updateByPrimaryKey(merchantAdminMap);
        }
        if (res == 0) {
            throw new ServiceException("update MerchantAdminMap error");
        }
        return merchantAdminMap;
    }

    @CacheEvict(value = "MerchantAdminMap", allEntries = true)
    public Integer updateMerchantAdminMapByExample(MerchantAdminMap merchantAdminMap, MerchantAdminMapExample example) {
        return merchantAdminMapMapper.updateByExampleSelective(merchantAdminMap, example);
    }

    @CacheEvict(value = "MerchantAdminMap", allEntries = true)
    public Integer updateMerchantAdminMapByExampleStrict(MerchantAdminMap merchantAdminMap,
            MerchantAdminMapExample example) {
        return merchantAdminMapMapper.updateByExample(merchantAdminMap, example);
    }

    @CacheEvict(value = "MerchantAdminMap", allEntries = true)
    public Integer delMerchantAdminMap(MerchantAdminMap merchantAdminMap) {
        MerchantAdminMapExample example = getMerchantAdminMapExample(merchantAdminMap);
        merchantAdminMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return merchantAdminMapMapper.updateByExampleSelective(merchantAdminMap, example);
    }

    @CacheEvict(value = "MerchantAdminMap", allEntries = true)
    public Integer delMerchantAdminMapByExample(MerchantAdminMapExample example) {
        Integer res = 0;
        List<MerchantAdminMap> merchantAdminMapList = getMerchantAdminMapByExample(example);
        for (MerchantAdminMap merchantAdminMap : merchantAdminMapList) {
            merchantAdminMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += merchantAdminMapMapper.updateByPrimaryKey(merchantAdminMap);
        }
        return res;
    }

    public Long countMerchantAdminMap(MerchantAdminMap merchantAdminMap) {
        MerchantAdminMapExample example = getMerchantAdminMapExample(merchantAdminMap);
        return merchantAdminMapMapper.countByExample(example);
    }

    public Long countMerchantAdminMapByExample(MerchantAdminMapExample example) {
        return merchantAdminMapMapper.countByExample(example);
    }

    @Cacheable(value = "MerchantAdminMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<MerchantAdminMap> getAllMerchantAdminMapList(Byte valid) {
        MerchantAdminMapExample example = new MerchantAdminMapExample();
        MerchantAdminMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        return merchantAdminMapMapper.selectByExample(example);
    }

    @Cacheable(value = "MerchantAdminMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<MerchantAdminMap> getAllMerchantAdminMapList(Byte valid, PageInfo<?> pageInfo) {
        MerchantAdminMapExample example = new MerchantAdminMapExample();
        MerchantAdminMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<MerchantAdminMap> res = merchantAdminMapMapper.selectByExample(example);
        pageInfo = new PageInfo<MerchantAdminMap>(res);
        return res;
    }

    @Cacheable(value = "MerchantAdminMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public MerchantAdminMap getMerchantAdminMapById(Integer id) {
        return merchantAdminMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "MerchantAdminMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<MerchantAdminMap> getMerchantAdminMapSelective(MerchantAdminMap merchantAdminMap,
            PageInfo<?> pageInfo) {
        MerchantAdminMapExample example = getMerchantAdminMapExample(merchantAdminMap);
        PageHelper.startPage(pageInfo);
        List<MerchantAdminMap> res = merchantAdminMapMapper.selectByExample(example);
        pageInfo = new PageInfo<MerchantAdminMap>(res);
        return res;
    }

    @Cacheable(value = "MerchantAdminMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<MerchantAdminMap> getMerchantAdminMapSelective(MerchantAdminMap merchantAdminMap) {
        MerchantAdminMapExample example = getMerchantAdminMapExample(merchantAdminMap);
        List<MerchantAdminMap> res = merchantAdminMapMapper.selectByExample(example);
        return res;
    }

    public List<MerchantAdminMap> getMerchantAdminMapByExample(MerchantAdminMapExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<MerchantAdminMap> res = merchantAdminMapMapper.selectByExample(example);
        pageInfo = new PageInfo<MerchantAdminMap>(res);
        return res;
    }

    public List<MerchantAdminMap> getMerchantAdminMapByExample(MerchantAdminMapExample example) {
        List<MerchantAdminMap> res = merchantAdminMapMapper.selectByExample(example);
        return res;
    }

    private MerchantAdminMapExample getMerchantAdminMapExample(MerchantAdminMap merchantAdminMap) {
        MerchantAdminMapExample example = new MerchantAdminMapExample();
        MerchantAdminMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(merchantAdminMap);
        ReflectionUtil.getFieldList(merchantAdminMap).forEach((field) -> {
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
