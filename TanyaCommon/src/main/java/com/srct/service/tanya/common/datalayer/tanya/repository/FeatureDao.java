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
import com.srct.service.tanya.common.datalayer.tanya.entity.Feature;
import com.srct.service.tanya.common.datalayer.tanya.entity.FeatureExample;
import com.srct.service.tanya.common.datalayer.tanya.mapper.FeatureMapper;
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

@Repository("tanyaFeatureDao")
public class FeatureDao {

    @Autowired
    FeatureMapper featureMapper;

    @CacheEvict(value = "Feature", allEntries = true)
    public Feature updateFeature(Feature feature) {
        int res = 0;
        if (feature.getId() == null) {
            feature.setCreateAt(new Date());
            res = featureMapper.insertSelective(feature);
        } else {
            feature.setUpdateAt(new Date());
            res = featureMapper.updateByPrimaryKeySelective(feature);
        }
        if (res == 0) {
            throw new ServiceException("update Feature error");
        }
        return feature;
    }

    @CacheEvict(value = "Feature", allEntries = true)
    public Feature updateFeatureStrict(Feature feature) {
        int res = 0;
        if (feature.getId() == null) {
            feature.setCreateAt(new Date());
            res = featureMapper.insert(feature);
        } else {
            feature.setUpdateAt(new Date());
            res = featureMapper.updateByPrimaryKey(feature);
        }
        if (res == 0) {
            throw new ServiceException("update Feature error");
        }
        return feature;
    }

    @CacheEvict(value = "Feature", allEntries = true)
    public Integer updateFeatureByExample(Feature feature, FeatureExample example) {
        return featureMapper.updateByExampleSelective(feature, example);
    }

    @CacheEvict(value = "Feature", allEntries = true)
    public Integer updateFeatureByExampleStrict(Feature feature, FeatureExample example) {
        return featureMapper.updateByExample(feature, example);
    }

    @CacheEvict(value = "Feature", allEntries = true)
    public Integer delFeature(Feature feature) {
        FeatureExample example = getFeatureExample(feature);
        feature.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return featureMapper.updateByExampleSelective(feature, example);
    }

    @CacheEvict(value = "Feature", allEntries = true)
    public Integer delFeatureByExample(FeatureExample example) {
        Integer res = 0;
        List<Feature> featureList = getFeatureByExample(example);
        for (Feature feature : featureList) {
            feature.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            res += featureMapper.updateByPrimaryKey(feature);
        }
        return res;
    }

    public Long countFeature(Feature feature) {
        FeatureExample example = getFeatureExample(feature);
        return featureMapper.countByExample(example);
    }

    public Long countFeatureByExample(FeatureExample example) {
        return featureMapper.countByExample(example);
    }

    @Cacheable(value = "Feature", key = "'valid_' + #valid")
    @CacheExpire(expire = 3600L)
    public List<Feature> getAllFeatureList(Byte valid) {
        FeatureExample example = new FeatureExample();
        FeatureExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }

        return featureMapper.selectByExample(example);
    }

    @Cacheable(value = "Feature", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<Feature> getAllFeatureList(Byte valid, PageInfo<?> pageInfo) {
        FeatureExample example = new FeatureExample();
        FeatureExample.Criteria criteria = example.createCriteria();
        if (!valid.equals(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID)) {
            criteria.andValidEqualTo(valid);
        }
        PageHelper.startPage(pageInfo);
        List<Feature> res = featureMapper.selectByExample(example);
        return new PageInfo<Feature>(res);
    }

    @Cacheable(value = "Feature", key = "'id_' + #id")
    @CacheExpire(expire = 24 * 3600L)
    public Feature getFeatureById(Integer id) {
        return featureMapper.selectByPrimaryKey(id);
    }

    @Cacheable(value = "Feature", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public PageInfo<Feature> getFeatureSelective(Feature feature, PageInfo<?> pageInfo) {
        FeatureExample example = getFeatureExample(feature);
        PageHelper.startPage(pageInfo);
        List<Feature> res = featureMapper.selectByExample(example);
        return new PageInfo<Feature>(res);
    }

    @Cacheable(value = "Feature", keyGenerator = "CacheKeyByParam")
    @CacheExpire(expire = 3600L)
    public List<Feature> getFeatureSelective(Feature feature) {
        FeatureExample example = getFeatureExample(feature);
        List<Feature> res = featureMapper.selectByExample(example);
        return res;
    }

    public PageInfo<Feature> getFeatureByExample(FeatureExample example, PageInfo<?> pageInfo) {
        PageHelper.startPage(pageInfo);
        List<Feature> res = featureMapper.selectByExample(example);
        return new PageInfo<Feature>(res);
    }

    public List<Feature> getFeatureByExample(FeatureExample example) {
        List<Feature> res = featureMapper.selectByExample(example);
        return res;
    }

    private FeatureExample getFeatureExample(Feature feature) {
        FeatureExample example = new FeatureExample();
        FeatureExample.Criteria criteria = example.createCriteria();
        HashMap<String, Object> valueMap = ReflectionUtil.getHashMap(feature);
        ReflectionUtil.getFieldList(feature).forEach((field) -> {
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
