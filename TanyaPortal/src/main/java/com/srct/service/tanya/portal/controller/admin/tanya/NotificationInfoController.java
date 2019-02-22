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
import com.srct.service.tanya.common.datalayer.tanya.entity.NotificationInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.NotificationInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.NotificationInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "NotificationInfo")
@RestController("tanyaNotificationInfoController")
@RequestMapping(value = "/portal/admin/tanya/notificationinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class NotificationInfoController {

    @Autowired
    NotificationInfoDao notificationInfoDao;

    @ApiOperation(value = "更新NotificationInfo", notes = "传入NotificationInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "NotificationInfoEntityVO", name = "vo", value = "NotificationInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateNotificationInfo(@RequestBody NotificationInfoEntityVO vo) {
        NotificationInfo notificationInfo = new NotificationInfo();
        BeanUtil.copyProperties(vo, notificationInfo);
        Integer id = notificationInfoDao.updateNotificationInfo(notificationInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询NotificationInfo", notes = "传入NotificationInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "NotificationInfoEntityVO", name = "vo", value = "NotificationInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<NotificationInfo>>.Resp> getNotificationInfoSelective(
            @RequestBody NotificationInfoEntityVO vo
            ) {
        List<NotificationInfo> res = new ArrayList<>();
        NotificationInfo notificationInfo = new NotificationInfo();
        BeanUtil.copyProperties(vo, notificationInfo);
        res.addAll(notificationInfoDao.getNotificationInfoSelective(notificationInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询NotificationInfo", notes = "返回id对应的NotificationInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "NotificationInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<NotificationInfo>>.Resp> getNotificationInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<NotificationInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(notificationInfoDao.getAllNotificationInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(notificationInfoDao.getNotificationInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除NotificationInfo", notes = "软删除主键为id的NotificationInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "NotificationInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delNotificationInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        NotificationInfo notificationInfo = new NotificationInfo();
        notificationInfo.setId(id);
        notificationInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = notificationInfoDao.updateNotificationInfo(notificationInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
