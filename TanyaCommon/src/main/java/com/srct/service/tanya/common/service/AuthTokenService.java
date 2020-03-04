package com.srct.service.tanya.common.service;

import com.srct.service.config.annotation.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: AuthTokenService
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2020/3/4 15:45
 * @description Project Name: Tanya
 * @Package: com.srct.service.tanya.common.service
 */
public interface AuthTokenService {
    void validate(HttpServletRequest request, HttpServletResponse response, Auth.AuthType authType);
}
