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

    public UserInfo updateUser(UserRegReqBO vo);

    public UserLoginRespBO reg(String wechatCode);

    public UserLoginRespBO regbyOpenId(String openId);

    public UserLoginRespBO reg(String name, String password);

    public UserLoginRespBO login(String wecharCode);

    public UserLoginRespBO login(String name, String password);

    public List<RoleInfo> getRole(UserInfo userInfo);

    public UserInfo getUserbyGuid(String guid);

    public UserInfo getUserbyEmail(String email);

    public UserInfo getUserbyUserId(Integer userId);

    public UserInfo cleanUserPassword(UserInfo userInfo);

    public List<RoleInfo> addRole(UserInfo userInfo, RoleInfo roleInfo);

    public List<RoleInfo> removeRole(UserInfo userInfo, RoleInfo roleInfo);

}
