/**
 * Title: LoginController.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.controller
 * @author Sharp
 * @date 2019-02-07 22:52:49
 */
package com.srct.service.tanya.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.exception.AccountOrPasswordIncorrectException;
import com.srct.service.exception.ServiceException;
import com.srct.service.exception.UserNotLoginException;
import com.srct.service.tanya.common.bo.user.UserRegReqBO;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.config.shiro.tanya.TanyaAuthToken;
import com.srct.service.tanya.common.exception.NoSuchUserException;
import com.srct.service.tanya.common.service.TokenService;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.common.vo.UserRegReqVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sharp
 *
 */
@Api(value = "LoginController")
@RestController("LoginController")
@RequestMapping(value = "")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "用户登入", notes = "用户登入系统，获取session信息")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(
        @RequestParam(value = "name", required = false) String username,
        @RequestParam(value = "pw", required = false) String password,
        @RequestParam(value = "wechatcode", required = false) String wechatAuthCode,
        @RequestParam(value = "rememberme", required = false) boolean rememberMe,
        HttpServletResponse response) throws IOException {

        Log.i("**********login**********");
        Subject subject = SecurityUtils.getSubject();
        String name = (String)subject.getPrincipal();

        if (name == null) {
            Log.i("Have not login");
            TanyaAuthToken authToken = new TanyaAuthToken(wechatAuthCode, username, password, rememberMe);
            try {
                // 登录操作
                subject.login(authToken);
                response.sendRedirect("/info");
            } catch (Exception e) {
                // 登录失败从request中获取shiro处理的异常信息 shiroLoginFailure:就是shiro异常类的全类名
                String exception = (String)request.getAttribute("shiroLoginFailure");

                if (e instanceof UnknownAccountException) {
                    throw new NoSuchUserException(exception);
                }

                if (e instanceof IncorrectCredentialsException) {
                    throw new AccountOrPasswordIncorrectException();
                }

                if (e instanceof LockedAccountException) {
                    throw new ServiceException("account has been locked");
                }

                response.sendRedirect("/register");
            }
        }
    }

    @ApiOperation(value = "用户注册", notes = "用户登入系统，获取session信息")
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void login(
        @RequestParam(value = "name", required = false) String username,
        @RequestParam(value = "pw", required = false) String password,
        @RequestParam(value = "wechatcode", required = false) String wechatAuthCode,
        HttpServletResponse response) {

        Log.i("**********register**********");
        if (wechatAuthCode != null) {
            userService.reg(wechatAuthCode);
        } else if (username != null && password != null) {
            userService.reg(username, password);
        } else {
            throw new ServiceException("cant regitser user info");
        }

        TanyaAuthToken authToken = new TanyaAuthToken(wechatAuthCode, username, password, false);
        try {
            Subject subject = SecurityUtils.getSubject();
            // 登录操作
            subject.login(authToken);
            response.sendRedirect("/info");
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @ApiOperation(value = "更新用户信息", notes = "输入用户详细信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<String>.Resp>
        reg(@RequestParam(value = "guid", required = true) String guid, @RequestBody UserRegReqVO vo) {
        Log.i(guid);
        UserRegReqBO bo = new UserRegReqBO();
        BeanUtil.copyProperties(vo, bo);
        userService.updateUser(bo);
        return TanyaExceptionHandler.generateResponse("");
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户详细信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<String>.Resp> info() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String)subject.getPrincipal();
        if (username == null) {
            throw new UserNotLoginException();
        } else {
            return TanyaExceptionHandler.generateResponse(username);
        }
    }

}
