/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya
 * @author: sharuopeng
 */
package com.srct.service.tanya.portal.controller.admin.tanya;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RolePermissionMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.RolePermissionMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.RolePermissionMapEntityVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.DBUtil;
import com.srct.service.vo.QueryRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiImplicitParam(paramType = "body", dataType = "RolePermissionMapEntityVO", name = "vo", value = "RolePermissionMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateRolePermissionMap(
            @RequestBody RolePermissionMapEntityVO vo) {
        RolePermissionMap rolePermissionMap = new RolePermissionMap();
        BeanUtil.copyProperties(vo, rolePermissionMap);
        Integer id = rolePermissionMapDao.updateRolePermissionMap(rolePermissionMap).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询RolePermissionMap", notes = "传入RolePermissionMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "RolePermissionMapEntityVO", name = "vo", value = "RolePermissionMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<RolePermissionMap>>.Resp> getRolePermissionMapSelective(
            @RequestBody RolePermissionMapEntityVO vo) {
        QueryRespVO<RolePermissionMap> res = new QueryRespVO<>();
        RolePermissionMap rolePermissionMap = new RolePermissionMap();
        BeanUtil.copyProperties(vo, rolePermissionMap);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        res.getInfo().addAll(rolePermissionMapDao.getRolePermissionMapSelective(rolePermissionMap));
        res.buildPageInfo(pageInfo);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询RolePermissionMap", notes = "返回id对应的RolePermissionMap,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "RolePermissionMap的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<RolePermissionMap>>.Resp> getRolePermissionMap(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<RolePermissionMap> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            res.getInfo().addAll(rolePermissionMapDao
                    .getAllRolePermissionMapList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID, pageInfo));
        } else {
            res.getInfo().add(rolePermissionMapDao.getRolePermissionMapById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除RolePermissionMap", notes = "软删除主键为id的RolePermissionMap")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "RolePermissionMap的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delRolePermissionMap(@RequestParam(value = "id") Integer id) {
        RolePermissionMap rolePermissionMap = new RolePermissionMap();
        rolePermissionMap.setId(id);
        rolePermissionMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = rolePermissionMapDao.updateRolePermissionMap(rolePermissionMap).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
