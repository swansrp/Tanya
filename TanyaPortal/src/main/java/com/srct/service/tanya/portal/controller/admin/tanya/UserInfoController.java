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
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.UserInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "UserInfo")
@RestController("tanyaUserInfoController")
@RequestMapping(value = "/portal/admin/tanya/userinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class UserInfoController {

    @Autowired
    UserInfoDao userInfoDao;

    @ApiOperation(value = "更新UserInfo", notes = "传入UserInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "UserInfoEntityVO", name = "vo", value = "UserInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateUserInfo(@RequestBody UserInfoEntityVO vo) {
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(vo, userInfo);
        Integer id = userInfoDao.updateUserInfo(userInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询UserInfo", notes = "传入UserInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "UserInfoEntityVO", name = "vo", value = "UserInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<UserInfo>>.Resp> getUserInfoSelective(
            @RequestBody UserInfoEntityVO vo
            ) {
        List<UserInfo> res = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(vo, userInfo);
        res.addAll(userInfoDao.getUserInfoSelective(userInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询UserInfo", notes = "返回id对应的UserInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "UserInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<UserInfo>>.Resp> getUserInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<UserInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(userInfoDao.getAllUserInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(userInfoDao.getUserInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除UserInfo", notes = "软删除主键为id的UserInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "UserInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delUserInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = userInfoDao.updateUserInfo(userInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
