/**
 * @Title: RoleService.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:34:29
 */
package com.srct.service.tanya.role.service;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.GetRoleDetailsBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.QuerySubordinateBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.bo.UpdateRoleInfoBO;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Sharp
 */
public interface RoleService {

    String getRoleType();

    String getSubordinateRoleType();

    RoleInfoBO create(CreateRoleBO bo);

    RoleInfoBO update(UpdateRoleInfoBO bo);

    QueryRespVO<RoleInfoBO> getSubordinate(QuerySubordinateBO bo);

    RoleInfoBO getDetails(GetRoleDetailsBO bo);

    RoleInfoBO invite(ModifyRoleBO bo);

    RoleInfoBO kickout(ModifyRoleBO bo);

    RoleInfoBO del(ModifyRoleBO bo);

    RoleInfoBO getSelfDetails(UserInfo userInfo);

    Integer getRoleIdByUser(UserInfo userInfo);

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

    default PageInfo<?> buildPageInfo(QueryReqVO req) {
        PageInfo<?> pageInfo = new PageInfo<>();
        if (req.getCurrentPage() != null) {
            pageInfo.setPageNum(req.getCurrentPage());
        }
        if (req.getPageSize() != null) {
            pageInfo.setPageSize(req.getPageSize());
        }
        return pageInfo;
    }
}
