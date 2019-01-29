/**
 * @Title: RoleController.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.controller
 * @author srct
 * @date 2019-01-29 19:32:31
 */
package com.srct.service.tanya.role.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author srct
 *
 */

@Api(value = "RoleController")
@RestController("RoleController")
@RequestMapping(value = "/role")
@CrossOrigin(origins = "*")
public class RoleController {

    @ApiOperation(value = "创建新角色", notes = "根据传入人员角色创建角色。")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<String>.Resp>
        updateAdminInfo(@RequestParam(value = "token", required = true) String token, @RequestBody String role) {
        String roleType = null;
        return TanyaExceptionHandler.generateResponse(roleType);
    }

}
