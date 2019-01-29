/**
 * @Title: FactoryRoleServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:38:41
 */
package com.srct.service.tanya.role.service.impl;

import org.springframework.stereotype.Service;

import com.srct.service.tanya.role.service.RoleService;

/**
 * @author Sharp
 *
 */
@Service
public class FactoryRoleServiceImpl implements RoleService {

    /* (non-Javadoc)
     * @see com.srct.service.tanya.role.service.RoleService#getRoleType()
     */
    @Override
    public String getRoleType() {
        return "Factory";
    }

}
