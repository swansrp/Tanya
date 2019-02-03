/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: srct   
 * @date: 2019/02/03
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
import com.srct.service.tanya.common.datalayer.tanya.entity.RolePermissionMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.RolePermissionMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.RolePermissionMapEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "RolePermissionMap")
@RestController("tanyaRolePermissionMapController")
@RequestMapping(value = "/portal/admin/tanya/rolepermissionmap")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class RolePermissionMapController {

    @Autowired
    RolePermissionMapDao rolePermissionMapDao;

    @ApiOperation(value = "更新RolePermissionMap", notes = "传入RolePermissionMap值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "RolePermissionMapEntityVO", name = "vo", value = "RolePermissionMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateRolePermissionMap(@RequestBody RolePermissionMapEntityVO vo) {
        RolePermissionMap rolePermissionMap = new RolePermissionMap();
        BeanUtil.copyProperties(vo, rolePermissionMap);
        Integer id = rolePermissionMapDao.updateRolePermissionMap(rolePermissionMap).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询RolePermissionMap", notes = "传入RolePermissionMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "RolePermissionMapEntityVO", name = "vo", value = "RolePermissionMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<RolePermissionMap>>.Resp> getRolePermissionMapSelective(
            @RequestBody RolePermissionMapEntityVO vo
            ) {
        List<RolePermissionMap> res = new ArrayList<>();
        RolePermissionMap rolePermissionMap = new RolePermissionMap();
        BeanUtil.copyProperties(vo, rolePermissionMap);
        res.addAll(rolePermissionMapDao.getRolePermissionMapSelective(rolePermissionMap));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询RolePermissionMap", notes = "返回id对应的RolePermissionMap,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "RolePermissionMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<RolePermissionMap>>.Resp> getRolePermissionMap(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<RolePermissionMap> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(rolePermissionMapDao.getAllRolePermissionMapList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(rolePermissionMapDao.getRolePermissionMapbyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除RolePermissionMap", notes = "软删除主键为id的RolePermissionMap")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "RolePermissionMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delRolePermissionMap(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        RolePermissionMap rolePermissionMap = new RolePermissionMap();
        rolePermissionMap.setId(id);
        rolePermissionMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = rolePermissionMapDao.updateRolePermissionMap(rolePermissionMap).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
