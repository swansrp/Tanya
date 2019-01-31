/**
 * @Title: LoginController.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.controller
 * @author Sharp
 * @date 2019-01-30 11:00:55
 */
package com.srct.service.tanya.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.bo.user.UserLoginRespBO;
import com.srct.service.tanya.common.bo.user.UserRegReqBO;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.service.TokenService;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.user.vo.UserLoginReqVO;
import com.srct.service.tanya.user.vo.UserLoginRespVO;
import com.srct.service.tanya.user.vo.UserRegReqVO;
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
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<UserLoginRespVO>.Resp> login(@RequestBody UserLoginReqVO vo) {
        UserLoginRespVO res = new UserLoginRespVO();
        UserLoginRespBO bo = null;
        if (vo.getWechatCode() != null) {
            bo = userService.login(vo.getWechatCode());
        } else if (vo.getName() != null && vo.getPassword() != null) {
            bo = userService.login(vo.getName(), vo.getPassword());
        }

        String token = tokenService.genToken(bo.getGuid());
        request.getSession().setAttribute("AuthToken", token);

        BeanUtil.copyProperties(bo, res);
        res.setRegistered(bo.getGuid() != null);

        return TanyaExceptionHandler.generateResponse(res);
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

}
