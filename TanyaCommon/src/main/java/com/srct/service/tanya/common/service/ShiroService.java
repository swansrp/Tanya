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

    UserInfo findByUserName(String userName);

    int insert(UserInfo user);

    int del(String username);

    Set<RoleInfo> findRolesByUserGuid(String guid);

    Set<PermissionInfo> findPermissionsByRole(Set<RoleInfo> roles);
}
