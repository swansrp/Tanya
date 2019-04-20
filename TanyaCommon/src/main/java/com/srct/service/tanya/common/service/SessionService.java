/**
 * @Title: TokenService.java
 * Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.service
 * @author Sharp
 * @date 2019-01-29 20:41:54
 */
package com.srct.service.tanya.common.service;

/**
 * @author Sharp
 */
public interface SessionService {
    String genWechatToken(String guid);

    String genToken(String guid);

    String getGuidByToken(String token);

    boolean retry(String guid);

    String getResetPasswordToken(String guid);

    String getGuidByResetPasswordToken(String token);

    void logoffByGuid(String guid);
}
