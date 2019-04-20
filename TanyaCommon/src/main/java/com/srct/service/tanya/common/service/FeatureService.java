/**
 * Title: FeatureService
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-5 16:51
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.common.service
 */
package com.srct.service.tanya.common.service;

import java.util.List;
import java.util.Map;

public interface FeatureService {
    List<String> getFeatureList();

    Map<String, String> getFeatureMap();

    String getFeature(String key, String defaultValue);

    String setFeature(String key, String value);

    boolean getFeatureExpected(String key, String expectedValue);

}
