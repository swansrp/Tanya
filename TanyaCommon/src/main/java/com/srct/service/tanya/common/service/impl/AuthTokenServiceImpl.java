/**
 * Title: AuthTokenServiceImpl
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-20 16:38
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.common.service.impl
 */
package com.srct.service.tanya.common.service.impl;

import com.srct.service.config.annotation.Auth;
import com.srct.service.exception.UserNotLoginException;
import com.srct.service.service.AuthTokenService;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.exception.UserNotInRoleException;
import com.srct.service.tanya.common.service.SessionService;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.utils.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    @Autowired
    UserService userService;
    @Autowired
    private SessionService tokenService;

    @Override
    public void validate(HttpServletRequest request, HttpServletResponse response, Auth.AuthType authType) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        String method = request.getMethod();
        Log.i("[{}] {}?{} - {}", method, requestURI, queryString, authType);
        switch (authType) {
            case GUEST:
                try {
                    validate(request, response);
                } catch (UserNotInRoleException e) {
                    return;
                }
                break;
            case USER:
                validate(request, response);
                break;
            case UNLOGIN:
                break;
            default:
                break;
        }

    }

    private void validate(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("x-access-token");
        if (token == null) {
            throw new UserNotLoginException();
        }
        String guid = getGuid(token);
        if (!buildUserInfo(request, guid)) {
            throw new UserNotLoginException();
        }
        if (!buildRoleInfo(request, guid)) {
            throw new UserNotInRoleException();
        }

    }

    private String getGuid(String token) {
        String guid = tokenService.getGuidByToken(token);
        Log.i("token: {} -- guid: {} ", token, guid);
        return guid;
    }

    private boolean buildUserInfo(HttpServletRequest request, String guid) {
        if (guid != null) {
            UserInfo info = userService.getUserbyGuid(guid);
            request.setAttribute("guid", guid);
            request.setAttribute("user", info);
            return true;
        }
        return false;
    }

    private boolean buildRoleInfo(HttpServletRequest request, String guid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setGuid(guid);
        List<RoleInfo> roles = userService.getRole(userInfo);
        if (roles != null && roles.size() > 0) {
            request.setAttribute("role", roles.get(0));
            return true;
        }
        return false;
    }
}
