/**
 * @Title: RoleController.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.controller
 * @author srct
 * @date 2019-01-29 19:32:31
 */
package com.srct.service.tanya.role.controller;

import java.util.ArrayList;
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
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.GetRoleDetailsBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.bo.UpdateRoleInfoBO;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.tanya.role.vo.CreateRoleVO;
import com.srct.service.tanya.role.vo.ModifyRoleVO;
import com.srct.service.tanya.role.vo.RoleDetailsVO;
import com.srct.service.tanya.role.vo.RoleInfoVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @Autowired
    private UserService userService;

    @ApiOperation(value = "创建新角色", notes = "根据传入人员角色创建角色。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> createRole(@RequestBody CreateRoleVO vo) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***createRole***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        CreateRoleBO bo = new CreateRoleBO();
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);
        BeanUtil.copyProperties(vo, bo);

        bo.setRoleType(getTargetRoleType(vo.getRoleType(), role));

        RoleService roleService = (RoleService)BeanUtil.getBean(bo.getRoleType() + "RoleServiceImpl");
        RoleInfoBO resBO = roleService.create(bo);

        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        return TanyaExceptionHandler.generateResponse(resVO);
    }

    @ApiOperation(value = "更新角色信息", notes = "更新制定角色详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "goodsnum", value = "商品数量",
            required = false),
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "tradernum", value = "销售员数量",
            required = false)})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> updateRole(
        @RequestBody RoleDetailsVO vo,
        @RequestParam(value = "goodsnum", required = false) Integer goodsNumber,
        @RequestParam(value = "tradernum", required = false) Integer traderNumber) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***updateRole***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        UpdateRoleInfoBO bo = new UpdateRoleInfoBO();
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);
        BeanUtil.copyProperties(vo, bo);

        bo.setTargetId(vo.getId());
        bo.setRoleType(getTargetRoleType(vo.getRoleType(), role));
        bo.setGoodsNumber(goodsNumber);
        bo.setTraderNumber(traderNumber);

        RoleService roleService = (RoleService)BeanUtil.getBean(bo.getRoleType() + "RoleServiceImpl");
        RoleInfoBO resBO = roleService.update(bo);

        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        return TanyaExceptionHandler.generateResponse(resVO);
    }

    @ApiOperation(value = "获取下属信息", notes = "根据传入人员角色获取下属信息。")
    @RequestMapping(value = "/subordinate", method = RequestMethod.GET)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "Interger", name = "roletype",
        value = "角色类型 {admin, merchant, factory, trader, salesman}", required = false)})
    public ResponseEntity<CommonResponse<List<RoleInfoVO>>.Resp>
        getSubordinate(@RequestParam(value = "roletype", required = false) String roletype) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");

        String roleType = getTargetRoleType(roletype, role);
        Log.i("***getSubordinate***");
        Log.i("guid {}, role type {} target type {}", info.getGuid(), role.getComment(), roleType);

        RoleService roleService = (RoleService)BeanUtil.getBean(roleType + "RoleServiceImpl");
        if (role.getRole().equals("superAdmin"))
            info = null;
        List<RoleInfoBO> resBOList = roleService.getSubordinate(info);

        List<RoleInfoVO> resVOList = new ArrayList<>();
        for (RoleInfoBO resBO : resBOList) {
            RoleInfoVO resVO = convertRoleInfoVO(resBO);
            resVOList.add(resVO);
        }
        return TanyaExceptionHandler.generateResponse(resVOList);
    }

    @ApiOperation(value = "邀请成为下属", notes = "通过扫描对方二维码邀请成为下属")
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "target",
        value = "邀请对象guid，从二维码获取", required = true)})
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp>
        invite(@RequestBody ModifyRoleVO vo, @RequestParam(value = "target", required = true) String targetGuid) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");

        ModifyRoleBO bo = new ModifyRoleBO();
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);
        BeanUtil.copyProperties(vo, bo);
        bo.setTargetGuid(targetGuid);

        bo.setRoleType(getTargetRoleType(vo.getRoleType(), role));

        RoleService roleService = (RoleService)BeanUtil.getBean(bo.getRoleType() + "RoleServiceImpl");
        RoleInfoBO resBO = roleService.invite(bo);

        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        return TanyaExceptionHandler.generateResponse(resVO);
    }

    @ApiOperation(value = "剔除下属", notes = "将某个下属人员剔除")
    @RequestMapping(value = "/kickout", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> kickout(@RequestBody ModifyRoleVO vo) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");

        ModifyRoleBO bo = new ModifyRoleBO();
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);
        BeanUtil.copyProperties(vo, bo);

        bo.setRoleType(getTargetRoleType(vo.getRoleType(), role));

        RoleService roleService = (RoleService)BeanUtil.getBean(bo.getRoleType() + "RoleServiceImpl");
        RoleInfoBO resBO = roleService.kickout(bo);

        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        return TanyaExceptionHandler.generateResponse(resVO);
    }

    @ApiOperation(value = "获取下属详细信息", notes = "根据传入人员角色获取下属信息。")
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "roletype",
            value = "角色类型 {admin, merchant, factory, trader, salesman}", required = true),
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "角色id", required = true)})
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> getDetails(
        @RequestParam(value = "roletype", required = false) String roletype,
        @RequestParam(value = "id", required = false) Integer id) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");

        String roleType = getTargetRoleType(roletype, role);
        Log.i("***getDetails***");
        Log.i("guid {}, role type {} target type {}", info.getGuid(), role.getComment(), roleType);

        RoleService roleService = (RoleService)BeanUtil.getBean(roleType + "RoleServiceImpl");

        GetRoleDetailsBO bo = new GetRoleDetailsBO();
        if (role.getRole().equals("superAdmin"))
            info = null;
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);
        bo.setId(id);

        RoleInfoBO resBO = roleService.getDetails(bo);
        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        return TanyaExceptionHandler.generateResponse(resVO);
    }

    /**
     * @param roletype
     * @param role
     * @return
     */
    private String getTargetRoleType(String roletype, RoleInfo role) {
        String targetRoleType = null;
        switch (role.getRole()) {
            case "superAdmin":
                targetRoleType = roletype;
                break;
            case "admin":
                targetRoleType = "merchant";
                break;
            case "merchant":
                targetRoleType = "factory";
                break;
            case "factory":
                targetRoleType = "trader";
                break;
            case "trader":
                targetRoleType = "salesman";
                break;
            case "salesman":
            default:
                throw new ServiceException("Dont find the target Role Type for " + role.getRole());
        }
        if (roletype != null && !roletype.equals(targetRoleType)) {
            throw new ServiceException("invalid role type " + roletype + " is chosen");
        }
        return targetRoleType;
    }

    /**
     * @param resBO
     * @return
     */
    private RoleInfoVO convertRoleInfoVO(RoleInfoBO resBO) {
        RoleInfoVO resVO = new RoleInfoVO();
        BeanUtil.copyProperties(resBO, resVO);
        if (resVO.getUserId() != null) {
            UserInfo user = userService.getUserbyUserId(resVO.getUserId());
            resVO.setUserName(user.getName());
            resVO.setUserComment(user.getComment());
        }
        return resVO;
    }

}
