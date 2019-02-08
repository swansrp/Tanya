/**
 * @Title: ShiroService.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.service
 * @author Sharp
 * @date 2019-02-03 17:26:51
 */
package com.srct.service.tanya.common.service;

import java.util.Set;

import com.srct.service.tanya.common.datalayer.tanya.entity.PermissionInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

/**
 * @author Sharp
 *
 */
public interface ShiroService {

    public UserInfo findByOpenId(String openId);

    public UserInfo findByUserName(String userName);

    public int update(UserInfo user);

    public int insert(UserInfo user);

    public int del(String username);

    public Set<RoleInfo> findRolesByUserGuid(String guid);

    public Set<PermissionInfo> findPermissionsByRole(Set<RoleInfo> roles);

    public UserInfo findByGuid(String guid);
}
