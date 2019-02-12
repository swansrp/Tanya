/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: Sharp   
 * @date: 2019/02/12
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
import com.srct.service.tanya.common.datalayer.tanya.entity.PermissionInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.PermissionInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.PermissionInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "PermissionInfo")
@RestController("tanyaPermissionInfoController")
@RequestMapping(value = "/portal/admin/tanya/permissioninfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class PermissionInfoController {

    @Autowired
    PermissionInfoDao permissionInfoDao;

    @ApiOperation(value = "更新PermissionInfo", notes = "传入PermissionInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "PermissionInfoEntityVO", name = "vo", value = "PermissionInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updatePermissionInfo(@RequestBody PermissionInfoEntityVO vo) {
        PermissionInfo permissionInfo = new PermissionInfo();
        BeanUtil.copyProperties(vo, permissionInfo);
        Integer id = permissionInfoDao.updatePermissionInfo(permissionInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询PermissionInfo", notes = "传入PermissionInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "PermissionInfoEntityVO", name = "vo", value = "PermissionInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<PermissionInfo>>.Resp> getPermissionInfoSelective(
            @RequestBody PermissionInfoEntityVO vo
            ) {
        List<PermissionInfo> res = new ArrayList<>();
        PermissionInfo permissionInfo = new PermissionInfo();
        BeanUtil.copyProperties(vo, permissionInfo);
        res.addAll(permissionInfoDao.getPermissionInfoSelective(permissionInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询PermissionInfo", notes = "返回id对应的PermissionInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "PermissionInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<PermissionInfo>>.Resp> getPermissionInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<PermissionInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(permissionInfoDao.getAllPermissionInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(permissionInfoDao.getPermissionInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除PermissionInfo", notes = "软删除主键为id的PermissionInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "PermissionInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delPermissionInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        PermissionInfo permissionInfo = new PermissionInfo();
        permissionInfo.setId(id);
        permissionInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = permissionInfoDao.updatePermissionInfo(permissionInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
