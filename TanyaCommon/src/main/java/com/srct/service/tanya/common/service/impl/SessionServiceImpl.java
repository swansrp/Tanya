/**
 * @Title: TokenServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved. @Project Name:
 * TanyaCommon @Package: com.srct.service.tanya.common.service.impl
 * @author Sharp
 * @date 2019-01-29 20:43:39
 */
package com.srct.service.tanya.common.service.impl;

import com.srct.service.service.RedisService;
import com.srct.service.tanya.common.service.SessionService;
import com.srct.service.utils.log.Log;
import com.srct.service.utils.security.EncryptUtil;
import com.srct.service.utils.security.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sharp
 */
@Service
public class SessionServiceImpl implements SessionService {

    private static final String BASE_KEY = "TanyaSession:";

    private static final String WECHAT_PREFIX = "WECHAT_";

    private static final String AUTH_PREFIX = "AUTH_";

    private static final String RETRY_KEY = BASE_KEY + "RetryLimit:";

    private static final String AUTHTOKEN_KEY = BASE_KEY + AUTH_PREFIX + "Token:";

    private static final String WECHATTOKEN_KEY = BASE_KEY + WECHAT_PREFIX + "Token:";

    private static final String SESSION_KEY = BASE_KEY + AUTH_PREFIX + "Session:";

    private static final String WECHAT_SESSION_KEY = BASE_KEY + WECHAT_PREFIX + "Session:";

    private static final String RESET_PASSWORD_KEY = BASE_KEY + "Password:";

    private static final int SESSION_TIMEOUT = 3600 * 4;

    private static final String AES_KEY = "Tanya";

    @Autowired
    RedisService redisService;

    @Override
    public String genWechatToken(String guid) {
        String token = WECHAT_PREFIX + RandomUtil.getUUID();

        String oldToken = redisService.get(WECHAT_SESSION_KEY + guid, String.class);
        if (oldToken != null) {
            Log.i("already login {} focus to logoff", guid);
            redisService.delete(WECHATTOKEN_KEY + oldToken);
            redisService.delete(WECHAT_SESSION_KEY + guid);
        }
        redisService.set(WECHATTOKEN_KEY + token, SESSION_TIMEOUT, guid);
        redisService.set(WECHAT_SESSION_KEY + guid, SESSION_TIMEOUT, WECHATTOKEN_KEY + token);
        redisService.delete(RETRY_KEY + guid);
        return token;
    }

    @Override
    public String genToken(String guid) {
        String token = "AUTH" + RandomUtil.getUUID();

        String oldToken = redisService.get(SESSION_KEY + guid, String.class);
        if (oldToken != null) {
            Log.i("already login {} focus to logoff", guid);
            redisService.delete(AUTHTOKEN_KEY + oldToken);
            redisService.delete(SESSION_KEY + guid);
        }
        redisService.set(AUTHTOKEN_KEY + token, SESSION_TIMEOUT, guid);
        redisService.set(SESSION_KEY + guid, SESSION_TIMEOUT, token);
        redisService.delete(RETRY_KEY + guid);
        return token;
    }

    @Override
    public String getGuidByToken(String token) {
        if (token.startsWith(WECHAT_PREFIX)) {
            return redisService.get(WECHATTOKEN_KEY + token, String.class);
        } else {
            return redisService.get(AUTHTOKEN_KEY + token, String.class);
        }
    }

    public boolean retry(String guid) {
        // 获取用户登录次数
        AtomicInteger retryCount = redisService.get(RETRY_KEY + guid, AtomicInteger.class);

        Log.i("guid {}, retryCount {}", guid, retryCount);
        if (retryCount == null) {
            // 如果用户没有登陆过,登陆次数加1 并放入缓存
            retryCount = new AtomicInteger(0);
        }
        if (retryCount.incrementAndGet() > 5) {
            Log.i("锁定用户  {}", guid);
            return false;
        }

        redisService.set(RETRY_KEY + guid, retryCount);
        return true;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.SessionService#getResetPasswordToken(java.lang.String)
     */
    @Override
    public String getResetPasswordToken(String guid) {
        String token = RandomUtil.getUUID();

        redisService.set(RESET_PASSWORD_KEY + token, guid);
        return EncryptUtil.encryptBase64(token, AES_KEY);
    }

    @Override
    public String getGuidbyResetPasswordToken(String token) {
        String oriToken = EncryptUtil.decryptBase64(token, AES_KEY);
        String guid = redisService.get(RESET_PASSWORD_KEY + oriToken, String.class);
        redisService.delete(RESET_PASSWORD_KEY + oriToken);
        return guid;
    }
}
