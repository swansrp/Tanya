/**
 * @Title: RoleService.java
 *         Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:34:29
 */
package com.srct.service.tanya.role.service;

import java.util.List;

import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;

/**
 * @author Sharp
 *
 */
public interface RoleService {

    public String getRoleType();

    public RoleInfoBO create(CreateRoleBO bo);

    public List<RoleInfoBO> getSubordinate(String guid);

    public RoleInfoBO operate(ModifyRoleBO bo);

}
