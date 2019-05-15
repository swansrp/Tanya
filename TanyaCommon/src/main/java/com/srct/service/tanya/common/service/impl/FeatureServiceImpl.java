/**
 * Title: FeatureServiceImpl
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-5 16:53
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.common.service.impl
 */
package com.srct.service.tanya.common.service.impl;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.tanya.common.datalayer.tanya.entity.Feature;
import com.srct.service.tanya.common.datalayer.tanya.repository.FeatureDao;
import com.srct.service.tanya.common.service.FeatureService;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureDao featureDao;

    @Override
    public List<String> getFeatureList() {
        List<Feature> featureList = featureDao.getAllFeatureList(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return (List<String>) ReflectionUtil.getFieldList(featureList, "key");
    }

    @Override
    public Map<String, String> getFeatureMap() {
        Map<String, String> featureMap = new HashMap<>();
        List<Feature> featureList = featureDao.getAllFeatureList(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        featureList.forEach(feature -> featureMap.put(feature.getKey(), feature.getValue()));
        return featureMap;
    }

    @Override
    public String getFeature(String key, String defaultValue) {
        Feature feature = new Feature();
        feature.setKey(key);
        feature.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return featureDao.getFeatureSelective(feature).get(0).getValue();
        } catch (Exception e) {
            Log.i("没有[{}]系统功能列表", key);
            return defaultValue;
        }
    }

    @Override
    public String setFeature(String key, String value) {
        Feature featureEx = new Feature();
        featureEx.setKey(key);
        featureEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            featureEx = featureDao.getFeatureSelective(featureEx).get(0);
        } catch (Exception e) {
            Log.i("新建feature {}", key);
        } finally {
            featureEx.setValue(value);
        }
        return featureDao.updateFeatureStrict(featureEx).getValue();
    }

    @Override
    public boolean getFeatureExpected(String key, String expectedValue) {
        return expectedValue.equals(getFeature(key, null));
    }
}
