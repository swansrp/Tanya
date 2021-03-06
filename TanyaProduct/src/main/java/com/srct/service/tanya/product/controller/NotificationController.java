/**
 * Title: NotificationController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaUser
 * @Package: com.srct.service.tanya.user.controller
 * @author sharuopeng
 * @date 2019-02-28 18:11:37
 */
package com.srct.service.tanya.product.controller;

import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.NotificationService;
import com.srct.service.tanya.product.vo.NotificationInfoReqVO;
import com.srct.service.tanya.product.vo.NotificationInfoRespVO;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.srct.service.config.annotation.Auth.AuthType.USER;

/**
 * @author sharuopeng
 */
@Auth(role = USER)
@Api(value = "公告", tags = "公告")
@RestController("NotificationController")
@RequestMapping(value = "/notify")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final static String productType = "公告";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private NotificationService notificationService;

    @ApiOperation(value = "新增/更新公告", notes = "只有factory等级可以添加公告 若传入id则为更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<NotificationInfoRespVO>>.Resp> modifyNotification(
            @RequestBody NotificationInfoReqVO req) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***modifyNotification***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<NotificationInfoReqVO> notification = new ProductBO<>();
        notification.setProductType(productType);
        notification.setReq(req);
        notification.setCreatorInfo(info);
        notification.setCreatorRole(role);
        QueryRespVO<NotificationInfoRespVO> notificationInfoVOList =
                notificationService.updateNotificationInfo(notification);

        return TanyaExceptionHandler.generateResponse(notificationInfoVOList);
    }

    @ApiOperation(value = "获取公告", notes = "获取公告详情,无id则返回改公告列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "公告id")})
    public ResponseEntity<CommonResponse<QueryRespVO<NotificationInfoRespVO>>.Resp> getNotification(
            @RequestBody QueryReqVO req, @RequestParam(value = "id", required = false) Integer notificationId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***getNotification***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> notification = new ProductBO<>();
        notification.setProductType(productType);
        notification.setCreatorInfo(info);
        notification.setCreatorRole(role);
        notification.setProductId(notificationId);
        notification.setReq(req);
        QueryRespVO<NotificationInfoRespVO> notificationInfoVOList =
                notificationService.getNotificationInfo(notification);

        return TanyaExceptionHandler.generateResponse(notificationInfoVOList);
    }

    @ApiOperation(value = "删除公告", notes = "只有factory等级可以删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "notificationid", value = "公告id", required = true)})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<QueryRespVO<NotificationInfoRespVO>>.Resp> del(
            @RequestParam(value = "notificationid") Integer notificationId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***DelNotification***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<NotificationInfoReqVO> notification = new ProductBO<>();
        notification.setProductType(productType);
        notification.setCreatorInfo(info);
        notification.setCreatorRole(role);
        notification.setProductId(notificationId);
        QueryRespVO<NotificationInfoRespVO> notificationInfoVOList =
                notificationService.delNotificationInfo(notification);

        return TanyaExceptionHandler.generateResponse(notificationInfoVOList);
    }

}
