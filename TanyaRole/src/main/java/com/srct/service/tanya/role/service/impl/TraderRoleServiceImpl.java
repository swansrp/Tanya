/**
 * @Title: TraderRoleServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:39:20
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
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderFactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderInfoDao;
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
public class TraderRoleServiceImpl implements RoleService {

    private final static int DEFAULT_PERIOD_VALUE = 1;
    private final static int DEFAULT_PERIOD_TYPE = Calendar.YEAR;

    @Autowired
    private FactoryInfoDao factoryInfoDao;

    @Autowired
    private TraderInfoDao traderInfoDao;

    @Autowired
    private FactoryMerchantMapDao factoryMerchantMapDao;

    @Autowired
    private TraderFactoryMerchantMapDao traderFactoryMerchantMapDao;

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
        return "trader";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#create(com.srct.service.tanya.role.bo.CreateRoleBO)
     */
    @Override
    public RoleInfoBO create(CreateRoleBO bo) {

        FactoryInfo factoryInfo = getFactoryInfoByCreater(bo);

        FactoryMerchantMap factoryMerchantMap = getFactoryMerchantMapByFactoryInfo(factoryInfo);

        TraderInfo traderInfo = makeTraderInfo(bo);

        makeTraderFactoryRelationship(factoryInfo, factoryMerchantMap, traderInfo);
        return null;
    }

    /**
     * @param factoryInfo
     * @param merchantInfo
     * @param talesmanInfo
     */
    private void makeTraderFactoryRelationship(
        FactoryInfo factoryInfo,
        FactoryMerchantMap factoryMerchantMap,
        TraderInfo traderInfo) {
        Date now = new Date();
        TraderFactoryMerchantMap traderFactoryMerchantMap = new TraderFactoryMerchantMap();
        traderFactoryMerchantMap.setFactoryId(factoryInfo.getId());
        traderFactoryMerchantMap.setTraderId(traderInfo.getId());
        traderFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
        traderFactoryMerchantMap.setStartAt(now);
        traderFactoryMerchantMap.setEndAt(factoryMerchantMap.getEndAt());
        traderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        traderFactoryMerchantMapDao.updateTraderFactoryMerchantMap(traderFactoryMerchantMap);

    }

    /**
     * @param bo
     * @return
     */
    private TraderInfo makeTraderInfo(CreateRoleBO bo) {
        Date now = new Date();
        TraderInfo traderInfo = new TraderInfo();
        BeanUtil.copyProperties(bo, traderInfo);
        traderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        traderInfo = traderInfoDao.updateTraderInfo(traderInfo);
        return traderInfo;

    }

    /**
     * @param factoryInfo
     * @return
     */
    private FactoryMerchantMap getFactoryMerchantMapByFactoryInfo(FactoryInfo factoryInfo) {
        Date now = new Date();

        FactoryMerchantMap factoryMerchantMap = new FactoryMerchantMap();
        FactoryMerchantMapExample example = new FactoryMerchantMapExample();
        FactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andFactoryIdEqualTo(factoryInfo.getId());
        criteria.andEndAtGreaterThanOrEqualTo(now);
        criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            factoryMerchantMap = factoryMerchantMapDao.getFactoryMerchantMapByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("no such Merchant for factory " + factoryInfo.getTitle());
        }

        return factoryMerchantMap;// TODO Auto-generated method stub
    }

    /**
     * @param bo
     * @return
     */
    private FactoryInfo getFactoryInfoByCreater(CreateRoleBO bo) {
        Date now = new Date();

        UserInfo creater = bo.getCreaterInfo();

        FactoryInfo factoryInfo = null;
        FactoryInfoExample example = new FactoryInfoExample();
        FactoryInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(creater.getId());
        criteria.andEndAtGreaterThanOrEqualTo(now);
        criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            factoryInfo = factoryInfoDao.getFactoryInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("no such user as role " + bo.getCreaterRole().getRole());
        }
        return factoryInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#getSubordinate(java.lang.String)
     */
    @Override
    public List<RoleInfoBO> getSubordinate(UserInfo userInfo) {
        if (userInfo == null) {
            return getAllTrader();
        }

        FactoryInfo factoryInfoEx = new FactoryInfo();
        factoryInfoEx.setUserId(userInfo.getId());
        factoryInfoEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        FactoryInfo factoryInfo = null;
        try {
            factoryInfo = factoryInfoDao.getFactoryInfoSelective(factoryInfoEx).get(0);
        } catch (Exception e) {
            Log.e("dont find the user id {} for {}", userInfo.getId(), "factory");
            throw new ServiceException("dont find the user id " + userInfo.getId() + " for factory");
        }

        TraderFactoryMerchantMap traderFactoryMerchantMapEx = new TraderFactoryMerchantMap();
        traderFactoryMerchantMapEx.setFactoryId(factoryInfo.getId());
        traderFactoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<TraderFactoryMerchantMap> traderFactoryMerchantMapList =
            traderFactoryMerchantMapDao.getTraderFactoryMerchantMapSelective(traderFactoryMerchantMapEx);

        List<RoleInfoBO> boList = new ArrayList<>();

        for (TraderFactoryMerchantMap traderFactoryMerchantMap : traderFactoryMerchantMapList) {
            TraderInfo traderInfo = traderInfoDao.getTraderInfobyId(traderFactoryMerchantMap.getTraderId());
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
    private List<RoleInfoBO> getAllTrader() {
        List<TraderInfo> tradeInfoList =
            traderInfoDao.getAllTraderInfoList(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<RoleInfoBO> boList = new ArrayList<>();

        for (TraderInfo trade : tradeInfoList) {
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(trade, bo);
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

        TraderInfo target = traderInfoDao.getTraderInfobyId(bo.getId());
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
        traderInfoDao.updateTraderInfoStrict(target);

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

        TraderInfo traderInfo = traderInfoDao.getTraderInfobyId(bo.getId());
        traderInfo.setId(bo.getId());
        traderInfo.setUserId(targetUserInfo.getId());
        traderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        TraderInfo target = traderInfoDao.updateTraderInfo(traderInfo);

        userService.addRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

}
