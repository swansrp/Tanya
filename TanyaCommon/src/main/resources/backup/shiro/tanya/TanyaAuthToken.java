/**
 * Title: TanyaAuthToken.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.shiro.tanya
 * @author Sharp
 * @date 2019-02-07 10:55:59
 */
package com.srct.service.tanya.common.config.shiro.tanya;

import org.apache.shiro.authc.UsernamePasswordToken;

import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class TanyaAuthToken extends UsernamePasswordToken {
    private String wechatAuthCode;

    private String guid;

    private String wechatOpenId;

    public TanyaAuthToken(final String wechatAuthCode, final String username, final String password,
        final boolean rememberMe) {
        super(username, password, rememberMe);
        this.setWechatAuthCode(wechatAuthCode);
    }
}
