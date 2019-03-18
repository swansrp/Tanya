/**
 * @Title: MerchantRoleServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:36:21
 */
package com.srct.service.tanya.role.service.impl;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.MerchantInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.GetRoleDetailsBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.bo.UpdateRoleInfoBO;
import com.srct.service.tanya.role.service.MerchantRoleService;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Sharp
 *
 */
@Service
public class MerchantRoleServiceImpl implements RoleService, MerchantRoleService {

    private final static int DEFAULT_PERIOD_VALUE = 1;
    private final static int DEFAULT_PERIOD_TYPE = Calendar.YEAR;

    @Autowired
    private RoleInfoDao roleInfoDao;

    @Autowired
    private MerchantInfoDao merchantInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserService userService;

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#getRoleType()
     */
    @Override
    public String getRoleType() {
        return "merchant";
    }

    @Override
    public String getSubordinateRoleType() {
        return "factory";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#create(com.srct.service.tanya.role.bo.CreateRoleBO)
     */
    @Override
    public RoleInfoBO create(CreateRoleBO bo) {

        MerchantInfo merchantInfo = makeMerchantInfo(bo);

        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(merchantInfo, res);
        res.setRoleType(getRoleType());

        return res;
    }

    private MerchantInfo makeMerchantInfo(CreateRoleBO bo) {
        Date now = new Date();

        MerchantInfo merchantInfo = new MerchantInfo();
        BeanUtil.copyProperties(bo, merchantInfo);
        merchantInfo.setStartAt(now);
        merchantInfo.setEndAt(getDefaultPeriod(now, DEFAULT_PERIOD_TYPE, DEFAULT_PERIOD_VALUE));
        merchantInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        merchantInfoDao.updateMerchantInfo(merchantInfo);
        return merchantInfo;
    }

    @Override
    public List<RoleInfoBO> getSubordinate(UserInfo userInfo) {

        List<MerchantInfo> merchatInfoList =
            merchantInfoDao.getAllMerchantInfoList(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<RoleInfoBO> boList = new ArrayList<>();

        for (MerchantInfo merchant : merchatInfoList) {
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(merchant, bo);
            bo.setRoleType(getRoleType());
            boList.add(bo);

        }
        return boList;

    }

    @Override
    public RoleInfoBO getDetails(GetRoleDetailsBO bo) {
        MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfobyId(bo.getId());
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(merchantInfo, res);
        res.setRoleType(getRoleType());
        return res;
    }

    @Override
    public RoleInfoBO getSelfDetails(UserInfo userInfo) {
        Integer id = getRoleIdbyUser(userInfo);
        MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfobyId(id);
        return makeRoleInfoBO(merchantInfo);
    }

    @Override
    public RoleInfoBO kickout(ModifyRoleBO bo) {

        MerchantInfo target = merchantInfoDao.getMerchantInfobyId(bo.getId());
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
        target.setContact(null);
        merchantInfoDao.updateMerchantInfoStrict(target);

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

        MerchantInfo merchant = merchantInfoDao.getMerchantInfobyId(bo.getId());
        merchant.setUserId(targetUserInfo.getId());
        merchant.setContact(targetUserInfo.getPhone());
        merchant.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        MerchantInfo target = merchantInfoDao.updateMerchantInfo(merchant);

        userService.addRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

    @Override
    public RoleInfoBO update(UpdateRoleInfoBO bo) {
        MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfobyId(bo.getTargetId());
        BeanUtil.copyProperties(bo, merchantInfo);

        merchantInfo = merchantInfoDao.updateMerchantInfo(merchantInfo);

        if (bo.getStartAt() != null || bo.getEndAt() != null) {
            UpdateRoleInfoBO updateSubRoleBO = new UpdateRoleInfoBO();
            updateSubRoleBO.setStartAt(bo.getStartAt());
            updateSubRoleBO.setEndAt(bo.getEndAt());
            updateSubRoleBO.setSuperiorId(merchantInfo.getId());
            RoleService subordinateService =
                (RoleService)BeanUtil.getBean(getSubordinateRoleType() + "RoleServiceImpl");
            subordinateService.update(updateSubRoleBO);
        }

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(merchantInfo, resBO);

        return resBO;
    }

    @Override
    public Integer getRoleIdbyUser(UserInfo userInfo) {
        return getMerchantInfoByUser(userInfo).getId();
    }

    @Override
    public MerchantInfo getMerchantInfoByUser(UserInfo userInfo) {
        MerchantInfo merchantInfo;
        MerchantInfoExample example = new MerchantInfoExample();
        MerchantInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userInfo.getId());
        // criteria.andEndAtGreaterThanOrEqualTo(now);
        // criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            merchantInfo = merchantInfoDao.getMerchantInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("no merchant have the user " + userInfo.getName());
        }
        return merchantInfo;
    }

    @Override
    public RoleInfoBO del(ModifyRoleBO bo) {
        MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfobyId(bo.getId());
        if (merchantInfo.getUserId() != null) {
            throw new ServiceException("Dont allow to del role " + merchantInfo.getId() + " without kickout the user "
                + merchantInfo.getUserId());
        }
        merchantInfoDao.delMerchantInfo(merchantInfo);
        return makeRoleInfoBO(merchantInfo);
    }

    private RoleInfoBO makeRoleInfoBO(MerchantInfo merchantInfo) {
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(merchantInfo, res);
        res.setRoleType(getRoleType());
        return res;
    }

}
