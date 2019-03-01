/**
 * Title: CommonController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.controller
 * @author Sharp
 * @date 2019-02-12 13:30:23
 */
package com.srct.service.tanya.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.exception.UserNotLoginException;
import com.srct.service.tanya.common.exception.UserNotInRoleException;
import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sharp
 *
 */
@Api(value = "系统自动重定向")
@RestController("commonController")
@RequestMapping(value = "")
@CrossOrigin(origins = "*")
public class CommonController {

    @ApiOperation(value = "用户未登入", notes = "返回错误,告知前台登录")
    @RequestMapping(value = "/unlogin")
    public ResponseEntity<CommonResponse<String>.Resp> unlogin() {
        Log.i("**********unlogin**********");
        throw new UserNotLoginException();
    }

    @ApiOperation(value = "用户无角色", notes = "返回错误,告知前台登录")
    @RequestMapping(value = "/norole")
    public ResponseEntity<CommonResponse<String>.Resp> noRole() {
        Log.i("**********norole**********");
        throw new UserNotInRoleException();
    }

}
