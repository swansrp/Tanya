/**
 * @Title: SalesmanRoleServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:39:38
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
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanTraderMapDao;
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
public class SalesmanRoleServiceImpl implements RoleService {

    private final static int DEFAULT_PERIOD_VALUE = 1;
    private final static int DEFAULT_PERIOD_TYPE = Calendar.YEAR;

    @Autowired
    private SalesmanInfoDao salesmanInfoDao;

    @Autowired
    private TraderInfoDao traderInfoDao;

    @Autowired
    private SalesmanTraderMapDao salesmanTraderMapDao;

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
        return "salesman";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#create(com.srct.service.tanya.role.bo.CreateRoleBO)
     */
    @Override
    public RoleInfoBO create(CreateRoleBO bo) {

        TraderInfo traderInfo = getTraderInfoByCreater(bo);

        SalesmanInfo salesmanInfo = makeSalesmanInfo(bo);

        makeSalesmanTraderRelationship(traderInfo, salesmanInfo);

        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(salesmanInfo, res);
        res.setRoleType(getRoleType());

        return res;
    }

    /**
     * @param traderInfo
     * @param salesmanInfo
     */
    private void makeSalesmanTraderRelationship(TraderInfo traderInfo, SalesmanInfo salesmanInfo) {
        Date now = new Date();
        SalesmanTraderMap salesmanTraderMap = new SalesmanTraderMap();
        salesmanTraderMap.setSalesmanId(salesmanInfo.getId());
        salesmanTraderMap.setTraderId(traderInfo.getId());
        salesmanTraderMap.setStartAt(now);
        salesmanTraderMap.setEndAt(getDefaultPeriod(now, DEFAULT_PERIOD_TYPE, DEFAULT_PERIOD_VALUE));
        salesmanTraderMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        salesmanTraderMapDao.updateSalesmanTraderMap(salesmanTraderMap);
    }

    /**
     * @param bo
     * @return
     */
    private SalesmanInfo makeSalesmanInfo(CreateRoleBO bo) {
        SalesmanInfo salesmanInfo = new SalesmanInfo();
        BeanUtil.copyProperties(bo, salesmanInfo);
        salesmanInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        salesmanInfo = salesmanInfoDao.updateSalesmanInfo(salesmanInfo);
        return salesmanInfo;
    }

    /**
     * @param bo
     * @return
     */
    private TraderInfo getTraderInfoByCreater(CreateRoleBO bo) {
        TraderInfo traderInfo = null;
        UserInfo creater = bo.getCreaterInfo();
        TraderInfoExample example = new TraderInfoExample();
        TraderInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(creater.getId());
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            traderInfo = traderInfoDao.getTraderInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("no such user as role " + bo.getCreaterRole().getRole());
        }
        return traderInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.role.service.RoleService#getSubordinate(java.lang.String)
     */
    @Override
    public List<RoleInfoBO> getSubordinate(UserInfo userInfo) {
        if (userInfo == null) {
            return getAllSalesman();
        }

        TraderInfo traderInfoEx = new TraderInfo();
        traderInfoEx.setUserId(userInfo.getId());
        traderInfoEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        TraderInfo traderInfo = null;
        try {
            traderInfo = traderInfoDao.getTraderInfoSelective(traderInfoEx).get(0);
        } catch (Exception e) {
            Log.e("dont find the user id {} for {}", userInfo.getId(), "factory");
            throw new ServiceException("dont find the user id " + userInfo.getId() + " for factory");
        }

        SalesmanTraderMap salesmanTraderMapEx = new SalesmanTraderMap();
        salesmanTraderMapEx.setTraderId(traderInfo.getId());
        salesmanTraderMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<SalesmanTraderMap> salesmanTraderMapList =
            salesmanTraderMapDao.getSalesmanTraderMapSelective(salesmanTraderMapEx);

        List<RoleInfoBO> boList = new ArrayList<>();

        for (SalesmanTraderMap salesmanTraderMap : salesmanTraderMapList) {
            SalesmanInfo salesmanInfo = salesmanInfoDao.getSalesmanInfobyId(salesmanTraderMap.getSalesmanId());
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(salesmanInfo, bo);
            bo.setRoleType(getRoleType());
            boList.add(bo);

        }
        return boList;
    }

    /**
     * @return
     */
    private List<RoleInfoBO> getAllSalesman() {
        List<SalesmanInfo> salesmanInfoList =
            salesmanInfoDao.getAllSalesmanInfoList(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<RoleInfoBO> boList = new ArrayList<>();

        for (SalesmanInfo salesman : salesmanInfoList) {
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(salesman, bo);
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

        SalesmanInfo target = salesmanInfoDao.getSalesmanInfobyId(bo.getId());
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
        salesmanInfoDao.updateSalesmanInfoStrict(target);

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

        SalesmanInfo salesmanInfo = salesmanInfoDao.getSalesmanInfobyId(bo.getId());
        salesmanInfo.setUserId(targetUserInfo.getId());
        salesmanInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        SalesmanInfo target = salesmanInfoDao.updateSalesmanInfo(salesmanInfo);

        userService.addRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

}
