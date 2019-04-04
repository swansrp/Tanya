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
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMapExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.SalesmanTraderMapMapper;
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
 * @ClassName: SalesmanTraderMapDao
 * @Description: Basic Repository 
 */
@Repository("tanyaSalesmanTraderMapDao")
public class SalesmanTraderMapDao {

    @Autowired
    SalesmanTraderMapMapper salesmanTraderMapMapper;

    @CacheEvict(value = "SalesmanTraderMap", allEntries = true)
    public SalesmanTraderMap updateSalesmanTraderMap(SalesmanTraderMap salesmanTraderMap) {
        int res = 0;
        if (salesmanTraderMap.getId() == null) {
            salesmanTraderMap.setCreateAt(new Date());
            res = salesmanTraderMapMapper.insertSelective(salesmanTraderMap);
        } else {
            salesmanTraderMap.setUpdateAt(new Date());
            res = salesmanTraderMapMapper.updateByPrimaryKeySelective(salesmanTraderMap);
        }
        if (res == 0) {
            throw new ServiceException("update SalesmanTraderMap error");
        }
        return salesmanTraderMap;
    }

    @CacheEvict(value = "SalesmanTraderMap", allEntries = true)
    public SalesmanTraderMap updateSalesmanTraderMapStrict(SalesmanTraderMap salesmanTraderMap) {
        int res = 0;
        if (salesmanTraderMap.getId() == null) {
            salesmanTraderMap.setCreateAt(new Date());
            res = salesmanTraderMapMapper.insert(salesmanTraderMap);
        } else {
            salesmanTraderMap.setUpdateAt(new Date());
            res = salesmanTraderMapMapper.updateByPrimaryKey(salesmanTraderMap);
        }
        if (res == 0) {
            throw new ServiceException("update SalesmanTraderMap error");
        }
        return salesmanTraderMap;
    }

    @CacheEvict(value = "SalesmanTraderMap", allEntries = true)
    public Integer updateSalesmanTraderMapByExample(SalesmanTraderMap salesmanTraderMap,
            SalesmanTraderMapExample example) {
        return salesmanTraderMapMapper.updateByExampleSelective(salesmanTraderMap, example);
    }

    @CacheEvict(value = "SalesmanTraderMap", allEntries = true)
    public Integer updateSalesmanTraderMapByExampleStrict(SalesmanTraderMap salesmanTraderMap,
            SalesmanTraderMapExample example) {
        return salesmanTraderMapMapper.updateByExample(salesmanTraderMap, example);
    }

    @CacheEvict(value = "SalesmanTraderMap", allEntries = true)
    public Integer delSalesmanTraderMap(SalesmanTraderMap salesmanTraderMap) {
        SalesmanTraderMapExample example = getSalesmanTraderMapExample(salesmanTraderMap);
        salesmanTraderMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return salesmanTraderMapMapper.updateByExampleSelective(salesmanTraderMap, example);
    }

    @CacheEvict(value = "SalesmanTraderMap", allEntries = true)
    public Integer delSalesmanTraderMapByExample(SalesmanTraderMapExample example) {
        Integer res = 0;
        List<SalesmanTraderMap> salesmanTraderMapList = getSalesmanTraderMapByExample(example);
        for (SalesmanTraderMap salesmanTraderMap : salesmanTraderMapList) {
            salesmanTraderMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += salesmanTraderMapMapper.updateByPrimaryKey(salesmanTraderMap);
        }
        return res;
    }

    public Long countSalesmanTraderMap(SalesmanTraderMap salesmanTraderMap) {
        SalesmanTraderMapExample example = getSalesmanTraderMapExample(salesmanTraderMap);
        return salesmanTraderMapMapper.countByExample(example);
    }

    public Long countSalesmanTraderMapByExample(SalesmanTraderMapExample example) {
        return salesmanTraderMapMapper.countByExample(example);
    }

    @Cacheable(value = "SalesmanTraderMap", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<SalesmanTraderMap> getAllSalesmanTraderMapList(Byte valid) {
        SalesmanTraderMapExample example = new SalesmanTraderMapExample();
        SalesmanTraderMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        return salesmanTraderMapMapper.selectByExample(example);
    }

    @Cacheable(value = "SalesmanTraderMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<SalesmanTraderMap> getAllSalesmanTraderMapList(Byte valid, PageInfo<?> pageInfo) {
        SalesmanTraderMapExample example = new SalesmanTraderMapExample();
        SalesmanTraderMapExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<SalesmanTraderMap> res = salesmanTraderMapMapper.selectByExample(example);
        pageInfo = new PageInfo<SalesmanTraderMap>(res);
        return res;
    }

    @Cacheable(value = "SalesmanTraderMap", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public SalesmanTraderMap getSalesmanTraderMapById(Integer id) {
        return salesmanTraderMapMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "SalesmanTraderMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<SalesmanTraderMap> getSalesmanTraderMapSelective(SalesmanTraderMap salesmanTraderMap,
            PageInfo<?> pageInfo) {
        SalesmanTraderMapExample example = getSalesmanTraderMapExample(salesmanTraderMap);
        PageHelper.startPage(pageInfo);
        List<SalesmanTraderMap> res = salesmanTraderMapMapper.selectByExample(example);
        pageInfo = new PageInfo<SalesmanTraderMap>(res);
        return res;
    }

    @Cacheable(value = "SalesmanTraderMap", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<SalesmanTraderMap> getSalesmanTraderMapSelective(SalesmanTraderMap salesmanTraderMap) {
        SalesmanTraderMapExample example = getSalesmanTraderMapExample(salesmanTraderMap);
        List<SalesmanTraderMap> res = salesmanTraderMapMapper.selectByExample(example);
        return res;
    }

    public List<SalesmanTraderMap> getSalesmanTraderMapByExample(SalesmanTraderMapExample example,
            PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<SalesmanTraderMap> res = salesmanTraderMapMapper.selectByExample(example);
        pageInfo = new PageInfo<SalesmanTraderMap>(res);
        return res;
    }

    public List<SalesmanTraderMap> getSalesmanTraderMapByExample(SalesmanTraderMapExample example) {
        List<SalesmanTraderMap> res = salesmanTraderMapMapper.selectByExample(example);
        return res;
    }

    private SalesmanTraderMapExample getSalesmanTraderMapExample(SalesmanTraderMap salesmanTraderMap) {
        SalesmanTraderMapExample example = new SalesmanTraderMapExample();
        SalesmanTraderMapExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(salesmanTraderMap);
        ReflectionUtil.getFieldList(salesmanTraderMap).forEach((field) -> {
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
