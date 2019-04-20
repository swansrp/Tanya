/**
 * Title: MenuController
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-9 18:38
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.user.controller
 */
package com.srct.service.tanya.user.controller;

import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.user.service.MenuService;
import com.srct.service.tanya.user.vo.QueryMenuVO;
import com.srct.service.utils.log.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.srct.service.config.annotation.Auth.AuthType.USER;

@Api(value = "WEB权限相关", tags = "WEB权限相关")
@RestController("MenuController")
@RequestMapping(value = "/menu")
@CrossOrigin(origins = "*")
public class MenuController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MenuService menuService;

    @Auth(role = USER)
    @ApiOperation(value = "获取本人菜单", notes = "根据传入人员角色获取本人菜单权限。")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<QueryMenuVO>>.Resp> getPermitTree() {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");

        Log.i("***queryMenu***");
        Log.i("guid {}, role type {} target type {}", info.getGuid(), role.getComment(), role.getRole());

        List<QueryMenuVO> vo = menuService.queryMenu(role);

        return TanyaExceptionHandler.generateResponse(vo);
    }
}
