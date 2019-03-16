/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: sharuopeng   
 * @date: 2019/03/16
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
import com.srct.service.tanya.common.datalayer.tanya.entity.AdminInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.AdminInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.AdminInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "AdminInfo")
@RestController("tanyaAdminInfoController")
@RequestMapping(value = "/portal/admin/tanya/admininfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class AdminInfoController {

    @Autowired
    AdminInfoDao adminInfoDao;

    @ApiOperation(value = "更新AdminInfo", notes = "传入AdminInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "AdminInfoEntityVO", name = "vo", value = "AdminInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateAdminInfo(@RequestBody AdminInfoEntityVO vo) {
        AdminInfo adminInfo = new AdminInfo();
        BeanUtil.copyProperties(vo, adminInfo);
        Integer id = adminInfoDao.updateAdminInfo(adminInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询AdminInfo", notes = "传入AdminInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "AdminInfoEntityVO", name = "vo", value = "AdminInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<AdminInfo>>.Resp> getAdminInfoSelective(
            @RequestBody AdminInfoEntityVO vo
            ) {
        List<AdminInfo> res = new ArrayList<>();
        AdminInfo adminInfo = new AdminInfo();
        BeanUtil.copyProperties(vo, adminInfo);
        res.addAll(adminInfoDao.getAdminInfoSelective(adminInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询AdminInfo", notes = "返回id对应的AdminInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "AdminInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<AdminInfo>>.Resp> getAdminInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<AdminInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(adminInfoDao.getAllAdminInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(adminInfoDao.getAdminInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除AdminInfo", notes = "软删除主键为id的AdminInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "AdminInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delAdminInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setId(id);
        adminInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = adminInfoDao.updateAdminInfo(adminInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
