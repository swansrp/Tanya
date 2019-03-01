/**
 * Title: PortalRoleController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaPortal
 * @Package: com.srct.service.tanya.portal.controller.role
 * @author Sharp
 * @date 2019-02-10 15:00:56
 */
package com.srct.service.tanya.portal.controller.role;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMapExample;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserRoleMapDao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Sharp
 *
 */
@Api(value = "修改权限")
@RestController("portalRoleController")
@RequestMapping(value = "/portal/role")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class PortalRoleController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRoleMapDao userRoleMapDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @ApiOperation(value = "修改权限", notes = "修改登录者权限")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "Interger", name = "roleid", value = "roleId",
        required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<String>.Resp>
        modifyRoleId(@RequestParam(value = "roleid", required = true) Integer roleId) {
        String guid = (String)request.getAttribute("guid");
        UserInfo userInfo = new UserInfo();
        userInfo.setGuid(guid);
        userInfo = userInfoDao.getUserInfoSelective(userInfo).get(0);

        UserRoleMapExample example = new UserRoleMapExample();
        UserRoleMapExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userInfo.getId());

        UserRoleMap userRoleMap = new UserRoleMap();
        userRoleMap.setUserId(userInfo.getId());
        userRoleMap.setRoleId(roleId);

        userRoleMapDao.updateUserRoleMapByExample(userRoleMap, example);
        return TanyaExceptionHandler.generateResponse("");
    }

}
