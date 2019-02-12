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
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserRoleMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.UserRoleMapEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "UserRoleMap")
@RestController("tanyaUserRoleMapController")
@RequestMapping(value = "/portal/admin/tanya/userrolemap")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class UserRoleMapController {

    @Autowired
    UserRoleMapDao userRoleMapDao;

    @ApiOperation(value = "更新UserRoleMap", notes = "传入UserRoleMap值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "UserRoleMapEntityVO", name = "vo", value = "UserRoleMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateUserRoleMap(@RequestBody UserRoleMapEntityVO vo) {
        UserRoleMap userRoleMap = new UserRoleMap();
        BeanUtil.copyProperties(vo, userRoleMap);
        Integer id = userRoleMapDao.updateUserRoleMap(userRoleMap).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询UserRoleMap", notes = "传入UserRoleMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "UserRoleMapEntityVO", name = "vo", value = "UserRoleMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<UserRoleMap>>.Resp> getUserRoleMapSelective(
            @RequestBody UserRoleMapEntityVO vo
            ) {
        List<UserRoleMap> res = new ArrayList<>();
        UserRoleMap userRoleMap = new UserRoleMap();
        BeanUtil.copyProperties(vo, userRoleMap);
        res.addAll(userRoleMapDao.getUserRoleMapSelective(userRoleMap));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询UserRoleMap", notes = "返回id对应的UserRoleMap,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "UserRoleMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<UserRoleMap>>.Resp> getUserRoleMap(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<UserRoleMap> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(userRoleMapDao.getAllUserRoleMapList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(userRoleMapDao.getUserRoleMapbyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除UserRoleMap", notes = "软删除主键为id的UserRoleMap")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "UserRoleMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delUserRoleMap(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        UserRoleMap userRoleMap = new UserRoleMap();
        userRoleMap.setId(id);
        userRoleMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = userRoleMapDao.updateUserRoleMap(userRoleMap).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
