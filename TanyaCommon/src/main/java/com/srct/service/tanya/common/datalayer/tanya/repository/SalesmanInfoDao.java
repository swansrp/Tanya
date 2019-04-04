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
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.SalesmanInfoMapper;
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
 * @ClassName: SalesmanInfoDao
 * @Description: Basic Repository 
 */
@Repository("tanyaSalesmanInfoDao")
public class SalesmanInfoDao {

    @Autowired
    SalesmanInfoMapper salesmanInfoMapper;

    @CacheEvict(value = "SalesmanInfo", allEntries = true)
    public SalesmanInfo updateSalesmanInfo(SalesmanInfo salesmanInfo) {
        int res = 0;
        if (salesmanInfo.getId() == null) {
            salesmanInfo.setCreateAt(new Date());
            res = salesmanInfoMapper.insertSelective(salesmanInfo);
        } else {
            salesmanInfo.setUpdateAt(new Date());
            res = salesmanInfoMapper.updateByPrimaryKeySelective(salesmanInfo);
        }
        if (res == 0) {
            throw new ServiceException("update SalesmanInfo error");
        }
        return salesmanInfo;
    }

    @CacheEvict(value = "SalesmanInfo", allEntries = true)
    public SalesmanInfo updateSalesmanInfoStrict(SalesmanInfo salesmanInfo) {
        int res = 0;
        if (salesmanInfo.getId() == null) {
            salesmanInfo.setCreateAt(new Date());
            res = salesmanInfoMapper.insert(salesmanInfo);
        } else {
            salesmanInfo.setUpdateAt(new Date());
            res = salesmanInfoMapper.updateByPrimaryKey(salesmanInfo);
        }
        if (res == 0) {
            throw new ServiceException("update SalesmanInfo error");
        }
        return salesmanInfo;
    }

    @CacheEvict(value = "SalesmanInfo", allEntries = true)
    public Integer updateSalesmanInfoByExample(SalesmanInfo salesmanInfo, SalesmanInfoExample example) {
        return salesmanInfoMapper.updateByExampleSelective(salesmanInfo, example);
    }

    @CacheEvict(value = "SalesmanInfo", allEntries = true)
    public Integer updateSalesmanInfoByExampleStrict(SalesmanInfo salesmanInfo, SalesmanInfoExample example) {
        return salesmanInfoMapper.updateByExample(salesmanInfo, example);
    }

    @CacheEvict(value = "SalesmanInfo", allEntries = true)
    public Integer delSalesmanInfo(SalesmanInfo salesmanInfo) {
        SalesmanInfoExample example = getSalesmanInfoExample(salesmanInfo);
        salesmanInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return salesmanInfoMapper.updateByExampleSelective(salesmanInfo, example);
    }

    @CacheEvict(value = "SalesmanInfo", allEntries = true)
    public Integer delSalesmanInfoByExample(SalesmanInfoExample example) {
        Integer res = 0;
        List<SalesmanInfo> salesmanInfoList = getSalesmanInfoByExample(example);
        for (SalesmanInfo salesmanInfo : salesmanInfoList) {
            salesmanInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += salesmanInfoMapper.updateByPrimaryKey(salesmanInfo);
        }
        return res;
    }

    public Long countSalesmanInfo(SalesmanInfo salesmanInfo) {
        SalesmanInfoExample example = getSalesmanInfoExample(salesmanInfo);
        return salesmanInfoMapper.countByExample(example);
    }

    public Long countSalesmanInfoByExample(SalesmanInfoExample example) {
        return salesmanInfoMapper.countByExample(example);
    }

    @Cacheable(value = "SalesmanInfo", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<SalesmanInfo> getAllSalesmanInfoList(Byte valid) {
        SalesmanInfoExample example = new SalesmanInfoExample();
        SalesmanInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        return salesmanInfoMapper.selectByExample(example);
    }

    @Cacheable(value = "SalesmanInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<SalesmanInfo> getAllSalesmanInfoList(Byte valid, PageInfo<?> pageInfo) {
        SalesmanInfoExample example = new SalesmanInfoExample();
        SalesmanInfoExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<SalesmanInfo> res = salesmanInfoMapper.selectByExample(example);
        pageInfo = new PageInfo<SalesmanInfo>(res);
        return res;
    }

    @Cacheable(value = "SalesmanInfo", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public SalesmanInfo getSalesmanInfoById(Integer id) {
        return salesmanInfoMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "SalesmanInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<SalesmanInfo> getSalesmanInfoSelective(SalesmanInfo salesmanInfo, PageInfo<?> pageInfo) {
        SalesmanInfoExample example = getSalesmanInfoExample(salesmanInfo);
        PageHelper.startPage(pageInfo);
        List<SalesmanInfo> res = salesmanInfoMapper.selectByExample(example);
        pageInfo = new PageInfo<SalesmanInfo>(res);
        return res;
    }

    @Cacheable(value = "SalesmanInfo", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<SalesmanInfo> getSalesmanInfoSelective(SalesmanInfo salesmanInfo) {
        SalesmanInfoExample example = getSalesmanInfoExample(salesmanInfo);
        List<SalesmanInfo> res = salesmanInfoMapper.selectByExample(example);
        return res;
    }

    public List<SalesmanInfo> getSalesmanInfoByExample(SalesmanInfoExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<SalesmanInfo> res = salesmanInfoMapper.selectByExample(example);
        pageInfo = new PageInfo<SalesmanInfo>(res);
        return res;
    }

    public List<SalesmanInfo> getSalesmanInfoByExample(SalesmanInfoExample example) {
        List<SalesmanInfo> res = salesmanInfoMapper.selectByExample(example);
        return res;
    }

    private SalesmanInfoExample getSalesmanInfoExample(SalesmanInfo salesmanInfo) {
        SalesmanInfoExample example = new SalesmanInfoExample();
        SalesmanInfoExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(salesmanInfo);
        ReflectionUtil.getFieldList(salesmanInfo).forEach((field) -> {
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
