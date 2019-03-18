/**
 * Title: PortalRoleController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaPortal
 * @Package: com.srct.service.tanya.portal.controller.role
 * @author Sharp
 * @date 2019-02-10 15:00:56
 */
package com.srct.service.tanya.portal.controller.role;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserRoleMapDao;
import com.srct.service.tanya.common.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sharp
 *
 */
@Api(value = "修改权限")
@RestController("portalRoleController")
@RequestMapping(value = "/portal/role")
@CrossOrigin(origins = "*")
public class PortalRoleController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRoleMapDao userRoleMapDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private SessionService sessionService;

    @ApiOperation(value = "修改openId", notes = "将openId注入到指定用户信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "username",
        value = "userName", required = true),})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/openid", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<UserInfo>.Resp>
    modifyRoleId(@RequestParam(value = "username") String userName) {
        String guid = (String)request.getAttribute("guid");
        UserInfo userInfo = new UserInfo();
        userInfo.setGuid(guid);
        userInfo = userInfoDao.getUserInfoSelective(userInfo).get(0);
        if (userInfo.getWechatId() != null) {
            String openId = userInfo.getWechatId();
            userInfo.setWechatId(null);
            userInfoDao.updateUserInfoStrict(userInfo);

            UserInfo target = new UserInfo();
            target.setUsername(userName);
            target = userInfoDao.getUserInfoSelective(target).get(0);
            target.setWechatId(openId);
            userInfoDao.updateUserInfo(target);

            // disable the old token
            sessionService.genToken(guid);
            sessionService.genWechatToken(guid);

            return TanyaExceptionHandler.generateResponse(target);
        } else {
            throw new ServiceException("login user dont have openId");
        }

    }

    @ApiOperation(value = "获取GUID", notes = "已登录用户获取guid")
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/guid", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<String>.Resp> getGuid() {
        String guid = (String)request.getAttribute("guid");
        return TanyaExceptionHandler.generateResponse(guid);
    }

}
