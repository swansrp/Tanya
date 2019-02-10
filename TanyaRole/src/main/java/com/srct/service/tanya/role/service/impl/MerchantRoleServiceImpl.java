/**
 * @Title: MerchantRoleServiceImpl.java
 *         Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:36:21
 */
package com.srct.service.tanya.role.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.service.RoleService;

/**
 * @author Sharp
 *
 */
@Service
public class MerchantRoleServiceImpl implements RoleService {

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#getRoleType()
     */
    @Override
    public String getRoleType() {
        return "Merchant";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#create(com.srct.service.tanya.role.bo.CreateRoleBO)
     */
    @Override
    public RoleInfoBO create(CreateRoleBO bo) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#getSubordinate(java.lang.String)
     */
    @Override
    public List<RoleInfoBO> getSubordinate(String guid) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#operate(com.srct.service.tanya.role.bo.ModifyRoleBO)
     */
    @Override
    public RoleInfoBO operate(ModifyRoleBO bo) {
        // TODO Auto-generated method stub
        return null;
    }

}
