/**
 * @Title: RoleService.java
 *         Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:34:29
 */
package com.srct.service.tanya.role.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.utils.log.Log;

/**
 * @author Sharp
 *
 */
public interface RoleService {

    public String getRoleType();

    public RoleInfoBO create(CreateRoleBO bo);

    public List<RoleInfoBO> getSubordinate(UserInfo userInfo);

    public RoleInfoBO invite(ModifyRoleBO bo);

    public RoleInfoBO kickout(ModifyRoleBO bo);

    default Date getDefaultPeriod(Date date, int defaultType, int defaultValue) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(defaultType, c.get(defaultType) + defaultValue);
        return c.getTime();
    }

    default RoleInfo getRoleInfo(RoleInfoDao roleInfoDao) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRole(getRoleType());
        roleInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return roleInfoDao.getRoleInfoSelective(roleInfo).get(0);
        } catch (Exception e) {
            Log.i(e);
            throw new ServiceException("no such role " + getRoleType());
        }
    }

}
