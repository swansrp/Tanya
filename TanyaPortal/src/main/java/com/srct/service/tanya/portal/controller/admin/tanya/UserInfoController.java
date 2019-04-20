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
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.UserInfoEntityVO;
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
            @ApiImplicitParam(paramType = "body", dataType = "UserInfoEntityVO", name = "vo", value = "UserInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateUserInfo(@RequestBody UserInfoEntityVO vo) {
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(vo, userInfo);
        Integer id = userInfoDao.updateUserInfo(userInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询UserInfo", notes = "传入UserInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserInfoEntityVO", name = "vo", value = "UserInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<UserInfo>>.Resp> getUserInfoSelective(
            @RequestBody UserInfoEntityVO vo) {
        QueryRespVO<UserInfo> res = new QueryRespVO<>();
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(vo, userInfo);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        PageInfo<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfo, pageInfo);
        res.getInfo().addAll(userInfoList.getList());
        res.buildPageInfo(userInfoList);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询UserInfo", notes = "返回id对应的UserInfo,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "UserInfo的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<UserInfo>>.Resp> getUserInfo(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<UserInfo> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            PageInfo<UserInfo> userInfoList =
                    userInfoDao.getAllUserInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID, pageInfo);
            res.getInfo().addAll(userInfoList.getList());
            res.buildPageInfo(userInfoList);
        } else {
            res.getInfo().add(userInfoDao.getUserInfoById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除UserInfo", notes = "软删除主键为id的UserInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "UserInfo的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delUserInfo(@RequestParam(value = "id") Integer id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = userInfoDao.updateUserInfo(userInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
