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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.bo.user.UserRegReqBO;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
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
    public ResponseEntity<CommonResponse<String>.Resp> login(
        @RequestParam(value = "name", required = false) String username,
        @RequestParam(value = "pw", required = false) String password,
        @RequestParam(value = "wechatcode", required = false) String wechatAuthCode,
        @RequestParam(value = "rememberme", required = false) boolean rememberMe,
        HttpServletResponse response) throws IOException, ServletException {

        Log.i("**********login**********");

        return TanyaExceptionHandler.generateResponse("");
    }

    @ApiOperation(value = "用户注册", notes = "用户登入系统，获取session信息")
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<String>.Resp> reg(
        @RequestParam(value = "name", required = false) String username,
        @RequestParam(value = "pw", required = false) String password,
        @RequestParam(value = "wechatcode", required = false) String wechatAuthCode,
        HttpServletResponse response) throws ServletException, IOException {

        Log.i("**********register**********");
        Log.i("name: {} pw: {}, wechat: {}", username, password, wechatAuthCode);

        return TanyaExceptionHandler.generateResponse("");
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
    public ResponseEntity<CommonResponse<UserInfo>.Resp> info() {
        Log.i("**********info**********");

        return TanyaExceptionHandler.generateResponse(new UserInfo());
    }

}
