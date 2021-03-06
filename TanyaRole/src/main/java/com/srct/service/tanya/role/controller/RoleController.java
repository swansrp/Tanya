/**
 * @Title: RoleController.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.controller
 * @author srct
 * @date 2019-01-29 19:32:31
 */
package com.srct.service.tanya.role.controller;

import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.service.SessionService;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.role.bo.*;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.tanya.role.vo.*;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryRespVO;
import com.srct.service.vo.ReqBaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.srct.service.config.annotation.Auth.AuthType.USER;

/**
 * @author Sharp
 */
@Auth(role = USER)
@Api(value = "权限相关操作", tags = "权限操作")
@RestController("RoleController")
@RequestMapping(value = "/role")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    @ApiOperation(value = "创建新角色", notes = "根据传入人员角色创建角色。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> createRole(@RequestBody CreateRoleVO vo) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***createRole***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        CreateRoleBO bo = new CreateRoleBO();

        BeanUtil.copyProperties(vo, bo);
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);

        bo.setRoleType(getTargetRoleType(vo.getRoleType(), role));

        RoleService roleService = (RoleService) BeanUtil.getBean(bo.getRoleType() + "RoleServiceImpl");
        RoleInfoBO resBO = roleService.create(bo);

        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        return TanyaExceptionHandler.generateResponse(resVO);
    }

    private String getTargetRoleType(String roleType, RoleInfo role) {
        String targetRoleType;
        switch (role.getRole()) {
            case "superAdmin":
                targetRoleType = roleType;
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
        if (roleType != null && !roleType.equals(targetRoleType)) {
            throw new ServiceException("invalid role type " + roleType + " is chosen");
        }
        return targetRoleType;
    }

    private RoleInfoVO convertRoleInfoVO(RoleInfoBO resBO) {
        RoleInfoVO resVO = new RoleInfoVO();
        BeanUtil.copyProperties(resBO, resVO);
        if (resBO.getPermissionDetails() != null) {
            PermissionDetailsVO permissionDetailsVO = new PermissionDetailsVO();
            BeanUtil.copyProperties(resBO.getPermissionDetails(), permissionDetailsVO);
            resVO.setPermissionDetailsVO(permissionDetailsVO);
        }
        if (resVO.getUserId() != null) {
            UserInfo user = userService.getUserbyUserId(resVO.getUserId());
            resVO.setUserName(user.getName());
            resVO.setContact(user.getPhone());
            resVO.setUserComment(user.getComment());
        }
        return resVO;
    }

    @ApiOperation(value = "删除下属角色", notes = "将某个下属空角色删除")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> del(@RequestBody ModifyRoleVO vo) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");

        ModifyRoleBO bo = new ModifyRoleBO();

        BeanUtil.copyProperties(vo, bo);
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);

        bo.setRoleType(getTargetRoleType(vo.getRoleType(), role));

        RoleService roleService = (RoleService) BeanUtil.getBean(bo.getRoleType() + "RoleServiceImpl");
        RoleInfoBO resBO = roleService.del(bo);

        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        return TanyaExceptionHandler.generateResponse(resVO);
    }

    @ApiOperation(value = "获取下属详细信息", notes = "根据传入人员角色获取下属信息。")
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "roletype", value = "角色类型 {admin, merchant, factory, trader, salesman}"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "角色id")})
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> getDetails(
            @RequestParam(value = "roletype", required = false) String roletype,
            @RequestParam(value = "id", required = false) Integer id) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");

        String roleType = getTargetRoleType(roletype, role);
        Log.i("***getDetails***");
        Log.i("guid {}, role type {} target type {}", info.getGuid(), role.getComment(), roleType);

        RoleService roleService = (RoleService) BeanUtil.getBean(roleType + "RoleServiceImpl");

        GetRoleDetailsBO bo = new GetRoleDetailsBO();
        if (role.getRole().equals("superAdmin")) {
            info = null;
        }
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);
        bo.setId(id);

        RoleInfoBO resBO = roleService.getDetails(bo);
        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        return TanyaExceptionHandler.generateResponse(resVO);
    }

    @ApiOperation(value = "获取下属信息", notes = "根据传入人员角色获取下属信息。")
    @RequestMapping(value = "/subordinate", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "roleType", value = "角色类型 {admin, merchant, factory, trader, salesman}"),
            @ApiImplicitParam(paramType = "query", dataType = "Boolean", name = "target", value = "是否已经添加人员"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "title", value = "角色名称")})
    public ResponseEntity<CommonResponse<QueryRespVO<RoleInfoVO>>.Resp> getSubordinate(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "roleType", required = false) String targetRoleType,
            @RequestParam(value = "target", required = false) Boolean targetIsExisted,
            @RequestParam(value = "title", required = false) String title) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");

        String roleType = getTargetRoleType(targetRoleType, role);
        Log.i("***getSubordinate***");
        Log.i("guid {}, role type {} target type {}", info.getGuid(), role.getComment(), roleType);

        RoleService roleService = (RoleService) BeanUtil.getBean(roleType + "RoleServiceImpl");
        QuerySubordinateBO req = new QuerySubordinateBO();
        req.setCurrentPage(currentPage);
        req.setPageSize(pageSize);
        if (!role.getRole().equals("superAdmin")) {
            req.setUserInfo(info);
        }
        req.setTargetIsExisted(targetIsExisted);
        if (StringUtils.hasText(title)) {
            req.setTitle(title);
        }
        QueryRespVO<RoleInfoBO> resBO = roleService.getSubordinate(req);
        QueryRespVO<RoleInfoVO> resVO = new QueryRespVO<>();
        resVO.setTotalPages(resBO.getTotalPages());
        resVO.setTotalSize(resBO.getTotalSize());
        resVO.setPageSize(resBO.getPageSize());
        resVO.setCurrentPage(resBO.getCurrentPage());
        resBO.getInfo().forEach(bo -> {
            RoleInfoVO vo = convertRoleInfoVO(bo);
            resVO.getInfo().add(vo);
        });

        return TanyaExceptionHandler.generateResponse(resVO);
    }

    @ApiOperation(value = "获取下属信息汇总信息", notes = "根据传入人员角色获取下属信息对应时间段的汇总信息。")
    @RequestMapping(value = "/subordinate/summary", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "roleType", value = "查看目标角色类型 {admin, merchant, factory, trader, salesman}", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "查看目标 上级角色Id", required = true)})
    public ResponseEntity<CommonResponse<Integer>.Resp> getSubordinateSummary(ReqBaseVO req,
                                                                              @RequestParam(value = "roleType") String targetRoleType, @RequestParam(value = "id") Integer id) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");

        Log.i("***getSubordinateSummary***");
        Log.i("guid {}, role type {} target type {}", info.getGuid(), role.getComment(), targetRoleType);
        String roleType = targetRoleType;
        QuerySubordinateBO bo = new QuerySubordinateBO();
        if (role.getRole().equals("superAdmin")) {

        } else if (role.getRole().equals("admin")) {
            setSuperiorRoleId(targetRoleType, id, bo);
            bo.setUserInfo(info);
        } else {
            roleType = getTargetRoleType(targetRoleType, role);
            bo.setUserInfo(info);
        }

        RoleService roleService = (RoleService) BeanUtil.getBean(roleType + "RoleServiceImpl");

        bo.setQueryStartAt(req.getQueryStartAt());
        bo.setQueryEndAt(req.getQueryEndAt());

        QueryRespVO<RoleInfoBO> resBO = roleService.getSubordinate(bo);
        Integer res = 0;
        if (!CollectionUtils.isEmpty(resBO.getInfo())) {
            res = resBO.getInfo().size();
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    private void setSuperiorRoleId(@RequestParam("roleType") String targetRoleType, @RequestParam("id") Integer id,
                                   QuerySubordinateBO bo) {
        switch (targetRoleType) {
            case "merchant":
                bo.setAdminId(id);
                break;
            case "factory":
                bo.setMerchantId(id);
                break;
            case "trader":
                bo.setFactoryId(id);
                break;
            case "salesman":
                bo.setTraderId(id);
                break;
            default:
                throw new ServiceException("错误的查询角色");
        }
    }

    @ApiOperation(value = "邀请成为下属", notes = "通过扫描对方二维码邀请成为下属")
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "target", value = "邀请对象guid，从二维码获取", required = true)})
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> invite(@RequestBody ModifyRoleVO vo,
                                                                  @RequestParam(value = "target") String targetGuid) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");

        ModifyRoleBO bo = new ModifyRoleBO();

        BeanUtil.copyProperties(vo, bo);
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);
        bo.setTargetGuid(targetGuid);

        bo.setRoleType(getTargetRoleType(vo.getRoleType(), role));

        RoleService roleService = (RoleService) BeanUtil.getBean(bo.getRoleType() + "RoleServiceImpl");
        RoleInfoBO resBO = roleService.invite(bo);

        RoleInfoVO resVO = convertRoleInfoVO(resBO);
        // 目标人员变更角色,强迫其小程序重新登录
        sessionService.genWechatToken(targetGuid);
        return TanyaExceptionHandler.generateResponse(resVO);
    }

    @ApiOperation(value = "剔除下属", notes = "将某个下属人员剔除")
    @RequestMapping(value = "/kickout", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> kickout(@RequestBody ModifyRoleVO vo) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");

        ModifyRoleBO bo = new ModifyRoleBO();

        BeanUtil.copyProperties(vo, bo);
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);

        bo.setRoleType(getTargetRoleType(vo.getRoleType(), role));

        RoleService roleService = (RoleService) BeanUtil.getBean(bo.getRoleType() + "RoleServiceImpl");
        RoleInfoBO resBO = roleService.kickout(bo);

        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        // 目标人员变更角色,强迫其小程序重新登录
        if (vo.getUserId() != null) {
            UserInfo targetUserInfo = userService.getUserbyUserId(vo.getUserId());
            if (targetUserInfo != null) {
                sessionService.genWechatToken(targetUserInfo.getGuid());
            }
        }
        return TanyaExceptionHandler.generateResponse(resVO);
    }

    @ApiOperation(value = "获取本人详细信息", notes = "根据传入人员角色获取本人信息。")
    @RequestMapping(value = "/self", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> selfDetails() {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");

        Log.i("***getDetails***");
        Log.i("guid {}, role type {} target type {}", info.getGuid(), role.getComment(), role.getRole());

        if (role.getRole().equals("superAdmin")) {
            throw new ServiceException("超级管理员不能查看自身信息");
        }

        RoleService roleService = (RoleService) BeanUtil.getBean(role.getRole() + "RoleServiceImpl");

        RoleInfoBO resBO = roleService.getSelfDetails(info);
        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        return TanyaExceptionHandler.generateResponse(resVO);
    }

    @ApiOperation(value = "更新角色信息", notes = "更新制定角色详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "RoleDetailsVO", name = "details", value = "角色基本详情")})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<RoleInfoVO>.Resp> updateRole(@RequestBody RoleDetailsVO details) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***updateRole***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        UpdateRoleInfoBO bo = new UpdateRoleInfoBO();

        BeanUtil.copyProperties(details, bo);
        bo.setCreaterInfo(info);
        bo.setCreaterRole(role);

        bo.setTargetId(details.getId());
        bo.setRoleType(getTargetRoleType(details.getRoleType(), role));
        PermissionDetailsBO permissionBO = new PermissionDetailsBO();
        BeanUtil.copyProperties(details.getPermission(), permissionBO);
        bo.setPermissionDetails(permissionBO);

        RoleService roleService = (RoleService) BeanUtil.getBean(bo.getRoleType() + "RoleServiceImpl");
        RoleInfoBO resBO = roleService.update(bo);

        RoleInfoVO resVO = convertRoleInfoVO(resBO);

        return TanyaExceptionHandler.generateResponse(resVO);
    }
}
