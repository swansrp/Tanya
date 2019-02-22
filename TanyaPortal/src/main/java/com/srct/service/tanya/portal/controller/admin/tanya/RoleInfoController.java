/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: sharuopeng   
 * @date: 2019/02/23
 */
package com.srct.service.tanya.portal.controller.admin.tanya;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.RoleInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "RoleInfo")
@RestController("tanyaRoleInfoController")
@RequestMapping(value = "/portal/admin/tanya/roleinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class RoleInfoController {

    @Autowired
    RoleInfoDao roleInfoDao;

    @ApiOperation(value = "更新RoleInfo", notes = "传入RoleInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "RoleInfoEntityVO", name = "vo", value = "RoleInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateRoleInfo(@RequestBody RoleInfoEntityVO vo) {
        RoleInfo roleInfo = new RoleInfo();
        BeanUtil.copyProperties(vo, roleInfo);
        Integer id = roleInfoDao.updateRoleInfo(roleInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询RoleInfo", notes = "传入RoleInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "RoleInfoEntityVO", name = "vo", value = "RoleInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<RoleInfo>>.Resp> getRoleInfoSelective(
            @RequestBody RoleInfoEntityVO vo
            ) {
        List<RoleInfo> res = new ArrayList<>();
        RoleInfo roleInfo = new RoleInfo();
        BeanUtil.copyProperties(vo, roleInfo);
        res.addAll(roleInfoDao.getRoleInfoSelective(roleInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询RoleInfo", notes = "返回id对应的RoleInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "RoleInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<RoleInfo>>.Resp> getRoleInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<RoleInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(roleInfoDao.getAllRoleInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(roleInfoDao.getRoleInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除RoleInfo", notes = "软删除主键为id的RoleInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "RoleInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delRoleInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setId(id);
        roleInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = roleInfoDao.updateRoleInfo(roleInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
