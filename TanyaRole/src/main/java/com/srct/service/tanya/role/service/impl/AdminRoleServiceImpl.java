/**
 * @Title: AdminRoleService.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:34:52
 */
package com.srct.service.tanya.role.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.AdminInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.AdminInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.AdminInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.GetRoleDetailsBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.bo.UpdateRoleInfoBO;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

/**
 * @author Sharp
 *
 */
@Service
public class AdminRoleServiceImpl implements RoleService {

    private final static int DEFAULT_PERIOD_VALUE = 5;
    private final static int DEFAULT_PERIOD_TYPE = Calendar.YEAR;

    @Autowired
    private RoleInfoDao roleInfoDao;

    @Autowired
    private AdminInfoDao adminInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserService userService;

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#test()
     */
    @Override
    public String getRoleType() {
        return "admin";
    }

    @Override
    public String getSubordinateRoleType() {
        return "merchant";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#create(com.srct.service.tanya.role.bo.CreateRoleBO)
     */
    @Override
    public RoleInfoBO create(CreateRoleBO bo) {

        AdminInfo adminInfo = makeAdminInfo(bo);

        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(adminInfo, res);
        res.setRoleType(getRoleType());

        return res;
    }

    /**
     * @param bo
     * @return
     */
    private AdminInfo makeAdminInfo(CreateRoleBO bo) {
        Date now = new Date();

        AdminInfo adminInfo = new AdminInfo();
        BeanUtil.copyProperties(bo, adminInfo);
        adminInfo.setStartAt(now);
        adminInfo.setEndAt(getDefaultPeriod(now, DEFAULT_PERIOD_TYPE, DEFAULT_PERIOD_VALUE));
        adminInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        adminInfoDao.updateAdminInfo(adminInfo);
        return adminInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#getSubordinate(java.lang.String)
     */
    @Override
    public List<RoleInfoBO> getSubordinate(UserInfo userInfo) {
        List<AdminInfo> adminInfoList =
            adminInfoDao.getAllAdminInfoList(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<RoleInfoBO> boList = new ArrayList<>();

        for (AdminInfo admin : adminInfoList) {
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(admin, bo);
            bo.setRoleType(getRoleType());
            boList.add(bo);

        }
        return boList;

    }

    @Override
    public RoleInfoBO getDetails(GetRoleDetailsBO bo) {
        AdminInfo adminInfo = adminInfoDao.getAdminInfobyId(bo.getId());
        return makeRoleInfoBO(adminInfo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#operate(com.srct.service.tanya.role.bo.ModifyRoleBO)
     */
    @Override
    public RoleInfoBO kickout(ModifyRoleBO bo) {

        AdminInfo target = adminInfoDao.getAdminInfobyId(bo.getId());
        if (target == null) {
            Log.e("role {} id {} is not exsited", bo.getRoleType(), bo.getId());
            throw new ServiceException("role " + bo.getRoleType() + " id " + bo.getId() + " is not exstied");
        }

        if (target.getUserId() == null) {
            Log.e("role {} id {} dont have user", bo.getRoleType(), bo.getId());
            throw new ServiceException("role " + bo.getRoleType() + " id " + bo.getId() + " Dont have user");
        }

        UserInfo targetUserInfo = userInfoDao.getUserInfobyId(target.getUserId());
        List<RoleInfo> targetRoleInfoList = userService.getRole(targetUserInfo);

        if (targetRoleInfoList == null || targetRoleInfoList.size() == 0) {
            throw new ServiceException("guid " + bo.getTargetGuid() + " dont have a role ");
        }

        target.setUserId(null);
        adminInfoDao.updateAdminInfoStrict(target);

        userService.removeRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

    @Override
    public RoleInfoBO invite(ModifyRoleBO bo) {
        UserInfo targetUserInfo = userService.getUserbyGuid(bo.getTargetGuid());
        List<RoleInfo> targetRoleInfoList = userService.getRole(targetUserInfo);

        if (targetRoleInfoList != null && targetRoleInfoList.size() > 0) {
            throw new ServiceException(
                "guid " + bo.getTargetGuid() + " already have a role " + targetRoleInfoList.get(0).getRole());
        }

        AdminInfo admin = adminInfoDao.getAdminInfobyId(bo.getId());
        admin.setUserId(targetUserInfo.getId());
        admin.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        AdminInfo target = adminInfoDao.updateAdminInfo(admin);

        userService.addRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

    @Override
    public RoleInfoBO update(UpdateRoleInfoBO bo) {
        AdminInfo adminInfo = adminInfoDao.getAdminInfobyId(bo.getTargetId());
        BeanUtil.copyProperties(bo, adminInfo);

        adminInfo = adminInfoDao.updateAdminInfo(adminInfo);

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(adminInfo, resBO);

        return resBO;
    }

    @Override
    public Integer getRoleIdbyUser(UserInfo userInfo) {
        AdminInfo adminInfo = null;
        AdminInfoExample example = new AdminInfoExample();
        AdminInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userInfo.getId());
        // criteria.andEndAtGreaterThanOrEqualTo(now);
        // criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            adminInfo = adminInfoDao.getAdminInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("no admin have the user " + userInfo.getName());
        }
        return adminInfo.getId();
    }

    @Override
    public RoleInfoBO del(ModifyRoleBO bo) {
        AdminInfo adminInfo = adminInfoDao.getAdminInfobyId(bo.getId());
        if (adminInfo.getUserId() != null) {
            throw new ServiceException(
                "Dont allow to del role " + adminInfo.getId() + " without kickout the user " + adminInfo.getUserId());
        }
        adminInfoDao.delAdminInfo(adminInfo);
        RoleInfoBO res = makeRoleInfoBO(adminInfo);
        return res;
    }

    /**
     * @param adminInfo
     * @return
     */
    private RoleInfoBO makeRoleInfoBO(AdminInfo adminInfo) {
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(adminInfo, res);
        res.setRoleType(getRoleType());
        return res;
    }

}
