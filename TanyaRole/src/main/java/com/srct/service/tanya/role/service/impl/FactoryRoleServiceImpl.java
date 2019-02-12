/**
 * @Title: FactoryRoleServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:38:41
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
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.MerchantInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

/**
 * @author Sharp
 *
 */
@Service
public class FactoryRoleServiceImpl implements RoleService {

    private final static int DEFAULT_PERIOD_VALUE = 1;
    private final static int DEFAULT_PERIOD_TYPE = Calendar.YEAR;

    @Autowired
    private FactoryInfoDao factoryInfoDao;

    @Autowired
    private MerchantInfoDao merchantInfoDao;

    @Autowired
    private FactoryMerchantMapDao factoryMerchantMapDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleInfoDao roleInfoDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#getRoleType()
     */
    @Override
    public String getRoleType() {
        return "factory";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#create(com.srct.service.tanya.role.bo.CreateRoleBO)
     */
    @Override
    public RoleInfoBO create(CreateRoleBO bo) {

        MerchantInfo merchantInfo = getMerchantInfoByCreater(bo);

        FactoryInfo factoryInfo = makeFoctoryInfo(bo);

        makeFactoryMerchantRelationship(merchantInfo, factoryInfo);

        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(factoryInfo, res);
        res.setRoleType(getRoleType());

        return res;
    }

    /**
     * @param merchantInfo
     * @param factoryInfo
     */
    private void makeFactoryMerchantRelationship(MerchantInfo merchantInfo, FactoryInfo factoryInfo) {
        Date now = new Date();
        FactoryMerchantMap factoryMerchantMap = new FactoryMerchantMap();
        factoryMerchantMap.setFactoryId(factoryInfo.getId());
        factoryMerchantMap.setMerchantId(merchantInfo.getId());
        factoryMerchantMap.setStartAt(now);
        factoryMerchantMap.setEndAt(merchantInfo.getEndAt());
        factoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        factoryMerchantMapDao.updateFactoryMerchantMap(factoryMerchantMap);
    }

    /**
     * @param bo
     * @param now
     * @return
     */
    private FactoryInfo makeFoctoryInfo(CreateRoleBO bo) {
        Date now = new Date();
        FactoryInfo factoryInfo = new FactoryInfo();
        BeanUtil.copyProperties(bo, factoryInfo);
        factoryInfo.setStartAt(now);
        factoryInfo.setEndAt(getDefaultPeriod(now, DEFAULT_PERIOD_TYPE, DEFAULT_PERIOD_VALUE));
        factoryInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        factoryInfo = factoryInfoDao.updateFactoryInfo(factoryInfo);
        return factoryInfo;
    }

    /**
     * @param bo
     * @param now
     * @return
     */
    private MerchantInfo getMerchantInfoByCreater(CreateRoleBO bo) {
        Date now = new Date();

        UserInfo creater = bo.getCreaterInfo();

        MerchantInfo merchantInfo = null;
        MerchantInfoExample example = new MerchantInfoExample();
        MerchantInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(creater.getId());
        criteria.andEndAtGreaterThanOrEqualTo(now);
        criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            merchantInfo = merchantInfoDao.getMerchantInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("no such user as role " + bo.getCreaterRole().getRole());
        }
        return merchantInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#getSubordinate(java.lang.String)
     */
    @Override
    public List<RoleInfoBO> getSubordinate(UserInfo userInfo) {
        if (userInfo == null) {
            return getAllFactory();

        }

        MerchantInfo MerchantInfoEx = new MerchantInfo();
        MerchantInfoEx.setUserId(userInfo.getId());
        MerchantInfoEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        MerchantInfo merchantInfo = null;
        try {
            merchantInfo = merchantInfoDao.getMerchantInfoSelective(MerchantInfoEx).get(0);
        } catch (Exception e) {
            Log.e("dont find the user id {} for {}", userInfo.getId(), "merchant");
            throw new ServiceException("dont find the user id " + userInfo.getId() + " for merchant");
        }

        FactoryMerchantMap factoryMerchantMapEx = new FactoryMerchantMap();
        factoryMerchantMapEx.setMerchantId(merchantInfo.getId());
        factoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<FactoryMerchantMap> factoryMerchantMapList =
            factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx);

        List<RoleInfoBO> boList = new ArrayList<>();

        for (FactoryMerchantMap factoryMerchantMap : factoryMerchantMapList) {
            FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfobyId(factoryMerchantMap.getFactoryId());
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(factoryInfo, bo);
            bo.setRoleType(getRoleType());
            boList.add(bo);

        }
        return boList;
    }

    /**
     * @return
     */
    private List<RoleInfoBO> getAllFactory() {
        List<FactoryInfo> factoryInfoList =
            factoryInfoDao.getAllFactoryInfoList(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<RoleInfoBO> boList = new ArrayList<>();

        for (FactoryInfo factory : factoryInfoList) {
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(factory, bo);
            bo.setRoleType(getRoleType());
            boList.add(bo);

        }
        return boList;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.role.service.RoleService#invite(com.srct.service.tanya.role.bo.ModifyRoleBO)
     */
    @Override
    public RoleInfoBO kickout(ModifyRoleBO bo) {

        FactoryInfo target = factoryInfoDao.getFactoryInfobyId(bo.getId());
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
        factoryInfoDao.updateFactoryInfoStrict(target);

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

        FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfobyId(bo.getId());
        factoryInfo.setUserId(targetUserInfo.getId());
        factoryInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        FactoryInfo target = factoryInfoDao.updateFactoryInfo(factoryInfo);

        userService.addRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

}
