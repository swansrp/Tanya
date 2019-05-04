/**
 * @Title: UserService.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.service
 * @author Sharp
 * @date 2019-01-30 11:04:24
 */
package com.srct.service.tanya.common.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.srct.service.tanya.common.bo.user.UserLoginRespBO;
import com.srct.service.tanya.common.bo.user.UserRegReqBO;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

/**
 * @author Sharp
 *
 */
@Validated
public interface UserService {

    UserInfo updateUser(UserRegReqBO vo);

    UserLoginRespBO reg(String wechatCode);

    UserLoginRespBO regbyOpenId(String openId);

    UserLoginRespBO reg(String name, String password);

    UserLoginRespBO login(String wecharCode);

    UserLoginRespBO login(String name, String password);

    List<RoleInfo> getRole(UserInfo userInfo);

    UserInfo getUserbyGuid(String guid);

    UserInfo getUserbyEmail(String email);

    UserInfo getUserbyUserId(Integer userId);

    UserInfo cleanUserPassword(UserInfo userInfo);

    List<RoleInfo> addRole(UserInfo userInfo, RoleInfo roleInfo);

    List<RoleInfo> removeRole(UserInfo userInfo, RoleInfo roleInfo);

    void ssoLogin(String token, String guid);

}
