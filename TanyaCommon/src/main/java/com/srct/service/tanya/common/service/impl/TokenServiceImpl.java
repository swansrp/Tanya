/**
 * @Title: TokenServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.service.impl
 * @author Sharp
 * @date 2019-01-29 20:43:39
 */
package com.srct.service.tanya.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.service.service.RedisService;
import com.srct.service.tanya.common.service.TokenService;
import com.srct.service.utils.security.RandomUtil;

/**
 * @author Sharp
 *
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    RedisService redisService;

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.TokenService#genToken(java.lang.String)
     */
    @Override
    public String genToken(String guid) {
        String token = RandomUtil.getUUID();
        redisService.setex("AuthToken::" + token, 600, guid);
        return token;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.TokenService#getGuidByToken(java.lang.String)
     */
    @Override
    public String getGuidByToken(String token) {
        String guid = redisService.get("AuthToken::" + token, String.class);
        return null;
    }

}
