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
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

/**
 * @author Sharp
 *
 */
@Validated
public interface UserService {

    public UserInfo updateUser(UserRegReqBO vo);

    public UserLoginRespBO login(String wecharCode);

    public UserLoginRespBO login(String name, String password);

    public List<String> getRole(UserInfo userInfo);

}
