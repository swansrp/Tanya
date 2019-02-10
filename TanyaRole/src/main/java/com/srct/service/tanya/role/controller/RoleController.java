/**
 * @Title: RoleController.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.controller
 * @author srct
 * @date 2019-01-29 19:32:31
 */
package com.srct.service.tanya.role.controller;

import java.util.List;

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
import com.srct.service.exception.NotImplementException;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.tanya.role.vo.CreateRoleVO;
import com.srct.service.tanya.role.vo.RoleInfoVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sharp
 *
 */

@Api(value = "RoleController")
@RestController("RoleController")
@RequestMapping(value = "/role")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    private HttpServletRequest request;

    @ApiOperation(value = "创建新角色", notes = "根据传入人员角色创建角色。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<RoleInfoBO>.Resp> createRole(@RequestBody CreateRoleVO vo) {
        String guid = (String)request.getAttribute("guid");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***createRole***\nguid {} role {}", guid, role.getRole());

        CreateRoleBO bo = new CreateRoleBO();
        BeanUtil.copyProperties(vo, bo);
        switch (role.getRole()) {
            case "superAdmin":
                bo.setRoleType("admin");
                break;
            case "admin":
                bo.setRoleType("merchant");
                break;
            case "merchant":
                bo.setRoleType("factory");
                break;
            case "factory":
                bo.setRoleType("trader");
                break;
            case "trader":
                bo.setRoleType("salesman");
                break;
            case "salesman":
                break;
            default:
                throw new ServiceException("Role Type Exception: " + role.getRole());

        }
        RoleService roleService = (RoleService)BeanUtil.getBean(bo.getRoleType() + "RoleServiceImpl");
        return TanyaExceptionHandler.generateResponse(roleService.create(bo));
    }

    @ApiOperation(value = "获取下属信息", notes = "根据传入人员角色获取下属信息。")
    @RequestMapping(value = "/subordinate", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<RoleInfoBO>>.Resp> getSubordinate() {
        String guid = (String)request.getAttribute("guid");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        RoleService roleService = (RoleService)BeanUtil.getBean(role.getRole() + "RoleServiceImpl");
        throw new NotImplementException();
        // return TanyaExceptionHandler.generateResponse(roleService.getSubordinate(guid));
    }

    @ApiOperation(value = "邀请成为下属", notes = "通过扫描对方二维码邀请成为下属")
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<RoleInfoBO>.Resp>
        invite(@RequestBody RoleInfoVO vo, @RequestParam(value = "target", required = true) String targetGuid) {
        String guid = (String)request.getAttribute("guid");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        ModifyRoleBO bo = new ModifyRoleBO();
        RoleService roleService = (RoleService)BeanUtil.getBean(role.getRole() + "RoleServiceImpl");

        throw new NotImplementException();
        // return TanyaExceptionHandler.generateResponse(roleService.operate(bo));
    }

    @ApiOperation(value = "剔除下属", notes = "将某个下属人员剔除")
    @RequestMapping(value = "/kickout", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<RoleInfoBO>.Resp> kickout(@RequestBody RoleInfoVO vo) {
        String guid = (String)request.getAttribute("guid");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        ModifyRoleBO bo = new ModifyRoleBO();
        RoleService roleService = (RoleService)BeanUtil.getBean(role.getRole() + "RoleServiceImpl");

        throw new NotImplementException();
        // return TanyaExceptionHandler.generateResponse(roleService.operate(bo));
    }

}
