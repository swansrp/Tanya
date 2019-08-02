/**
 * @Title: SalesmanRoleServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:39:38
 */
package com.srct.service.tanya.role.service.impl;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMapExample;
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
import com.srct.service.tanya.role.bo.GetRoleDetailsBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.QuerySubordinateBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.bo.UpdateRoleInfoBO;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.tanya.role.service.SalesmanRoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Sharp
 */
@Service
public class SalesmanRoleServiceImpl implements RoleService, SalesmanRoleService {

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

    @Override
    public RoleInfoBO create(CreateRoleBO bo) {
        TraderInfo traderInfo;
        try {
            traderInfo = getTraderInfoByCreator(bo.getCreaterInfo());
        } catch (Exception e) {
            throw new ServiceException(
                    "creater role " + bo.getCreaterRole().getRole() + " dont allow create " + getRoleType());
        }

        SalesmanInfo salesmanInfo = makeSalesmanInfo(bo);

        makeSalesmanTraderRelationship(traderInfo, salesmanInfo);

        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(salesmanInfo, res);
        res.setRoleType(getRoleType());

        return res;
    }

    @Override
    public RoleInfoBO del(ModifyRoleBO bo) {
        SalesmanInfo salesmanInfo = salesmanInfoDao.getSalesmanInfoById(bo.getId());
        if (salesmanInfo.getUserId() != null) {
            throw new ServiceException(
                    "Dont allow to del role " + salesmanInfo.getId() + " without kickout the user " + salesmanInfo
                            .getUserId());
        }
        salesmanInfoDao.delSalesmanInfo(salesmanInfo);
        return makeRoleInfoBO(salesmanInfo);
    }

    @Override
    public RoleInfoBO getDetails(GetRoleDetailsBO bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        if (userInfo != null) {
            TraderInfo traderInfo;
            try {
                traderInfo = getTraderInfoByCreator(userInfo);
            } catch (Exception e) {
                Log.e("dont find the user id {} for {}", userInfo.getId(), "trader");
                throw new ServiceException("dont find the user id " + userInfo.getId() + " for trader");
            }

            SalesmanTraderMap salesmanTraderMapEx = new SalesmanTraderMap();
            salesmanTraderMapEx.setSalesmanId(bo.getId());
            salesmanTraderMapEx.setTraderId(traderInfo.getId());
            salesmanTraderMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

            if (salesmanTraderMapDao.countSalesmanTraderMap(salesmanTraderMapEx) == 0) {
                throw new ServiceException("salesman id " + bo.getId() + " trader id " + traderInfo.getId()
                        + "cant find the relationship");
            }
        }

        SalesmanInfo salesmanInfo = salesmanInfoDao.getSalesmanInfoById(bo.getId());
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(salesmanInfo, res);
        res.setRoleType(getRoleType());
        return res;
    }

    @Override
    public Integer getRoleIdByUser(UserInfo userInfo) {
        return getSalesmanInfoListByUser(userInfo).get(0).getId();
    }

    @Override
    public String getRoleType() {
        return "salesman";
    }

    @Override
    public List<SalesmanInfo> getSalesmanInfoListByUser(UserInfo userInfo) {

        SalesmanInfo salesmanInfo = new SalesmanInfo();
        salesmanInfo.setUserId(userInfo.getId());
        salesmanInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return salesmanInfoDao.getSalesmanInfoSelective(salesmanInfo);
        } catch (Exception e) {
            throw new ServiceException("no salesman have the user " + userInfo.getName());
        }
    }

    @Override
    public List<SalesmanTraderMap> getSalesmanTraderMap(UserInfo userInfo) {
        //Date now = new Date();
        List<SalesmanInfo> salesmanInfoList = getSalesmanInfoListByUser(userInfo);
        List<Integer> salesmanIdList = new ArrayList<>();
        salesmanInfoList.forEach(salesmanInfo -> salesmanIdList.add(salesmanInfo.getId()));

        SalesmanTraderMapExample example = new SalesmanTraderMapExample();
        SalesmanTraderMapExample.Criteria criteria = example.createCriteria();
        //criteria.andEndAtGreaterThanOrEqualTo(now);
        //criteria.andStartAtLessThanOrEqualTo(now);
        if (salesmanIdList.size() == 0) {
            salesmanIdList.add(0);
        }
        criteria.andSalesmanIdIn(salesmanIdList);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return salesmanTraderMapDao.getSalesmanTraderMapByExample(example);
        } catch (Exception e) {
            throw new ServiceException("no valid Salesman Trader relationship for the user " + userInfo.getName());
        }
    }

    @Override
    public RoleInfoBO getSelfDetails(UserInfo userInfo) {
        Integer id = getRoleIdByUser(userInfo);
        SalesmanInfo salesmanInfo = salesmanInfoDao.getSalesmanInfoById(id);
        return makeRoleInfoBO(salesmanInfo);
    }

    @Override
    public QueryRespVO<RoleInfoBO> getSubordinate(QuerySubordinateBO req) {
        UserInfo userInfo = req.getUserInfo();
        if (userInfo == null) {
            return getAllSalesman(req);
        }

        TraderInfo traderInfo;
        if (req.getTraderId() != null) {
            traderInfo = traderInfoDao.getTraderInfoById(req.getTraderId());
        } else {
            try {
                traderInfo = getTraderInfoByCreator(userInfo);
            } catch (Exception e) {
                Log.e("dont find the user id {} for {}", userInfo.getId(), "factory");
                throw new ServiceException("dont find the user id " + userInfo.getId() + " for factory");
            }
        }
        PageInfo pageInfo = buildPageInfo(req);
        SalesmanTraderMap salesmanTraderMapEx = new SalesmanTraderMap();
        salesmanTraderMapEx.setTraderId(traderInfo.getId());
        salesmanTraderMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<SalesmanTraderMap> salesmanTraderMapList =
                salesmanTraderMapDao.getSalesmanTraderMapSelective(salesmanTraderMapEx);
        List<Integer> salesmanIdList = ReflectionUtil.getFieldList(salesmanTraderMapList, "salesmanId", Integer.class);
        if (CollectionUtils.isEmpty(salesmanIdList)) {
            salesmanIdList.add(0);
        }
        SalesmanInfoExample example = new SalesmanInfoExample();
        SalesmanInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(salesmanIdList);
        if (req.getTargetIsExisted() != null) {
            if (req.getTargetIsExisted()) {
                criteria.andUserIdIsNotNull();
            } else {
                criteria.andUserIdIsNull();
            }
        }
        if (req.getTitle() != null) {
            criteria.andTitleLike("%" + req.getTitle() + "%");
        }
        if (req.getQueryEndAt() != null && req.getQueryStartAt() != null) {
            criteria.andCreateAtBetween(req.getQueryStartAt(), req.getQueryEndAt());
        }
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        PageInfo<SalesmanInfo> salesmanInfoList = salesmanInfoDao.getSalesmanInfoByExample(example, pageInfo);

        QueryRespVO<RoleInfoBO> res = new QueryRespVO<>();
        res.buildPageInfo(salesmanInfoList);

        for (SalesmanInfo salesmanInfo : salesmanInfoList.getList()) {
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(salesmanInfo, bo);
            bo.setRoleType(getRoleType());
            res.getInfo().add(bo);
        }
        return res;
    }

    @Override
    public String getSubordinateRoleType() {
        return null;
    }

    @Override
    public List<TraderInfo> getTraderInfoByUser(UserInfo userInfo) {
        List<TraderInfo> res = new ArrayList<>();
        getSalesmanTraderMap(userInfo).forEach(
                salesmanTraderMap -> res.add(traderInfoDao.getTraderInfoById(salesmanTraderMap.getTraderId())));
        return res;
    }

    @Override
    public RoleInfoBO invite(ModifyRoleBO bo) {
        UserInfo targetUserInfo = userService.getUserbyGuid(bo.getTargetGuid());
        List<RoleInfo> targetRoleInfoList = userService.getRole(targetUserInfo);

        if (targetRoleInfoList != null && targetRoleInfoList.size() > 0) {
            for (RoleInfo targetRoleInfo : targetRoleInfoList) {
                if (!targetRoleInfo.getRole().equals(getRoleType())) {
                    throw new ServiceException(
                            "guid " + bo.getTargetGuid() + " already have a role " + targetRoleInfoList.get(0)
                                    .getRole());
                }
            }
        }
        TraderInfo creator = getTraderInfoByCreator(bo.getCreaterInfo());
        List<TraderInfo> superiorList = getTraderInfoByUser(targetUserInfo);
        if (superiorList.contains(creator)) {
            throw new ServiceException("已经是" + creator.getTitle() + "的促销员了");
        }

        SalesmanInfo salesmanInfo = salesmanInfoDao.getSalesmanInfoById(bo.getId());
        salesmanInfo.setUserId(targetUserInfo.getId());
        salesmanInfo.setContact(targetUserInfo.getPhone());
        salesmanInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        SalesmanInfo target = salesmanInfoDao.updateSalesmanInfo(salesmanInfo);

        userService.addRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

    @Override
    public RoleInfoBO kickout(ModifyRoleBO bo) {

        SalesmanInfo target = salesmanInfoDao.getSalesmanInfoById(bo.getId());
        if (target == null) {
            Log.e("role {} id {} is not exsited", bo.getRoleType(), bo.getId());
            throw new ServiceException("role " + bo.getRoleType() + " id " + bo.getId() + " is not exstied");
        }

        if (target.getUserId() == null) {
            Log.e("role {} id {} dont have user", bo.getRoleType(), bo.getId());
            throw new ServiceException("role " + bo.getRoleType() + " id " + bo.getId() + " Dont have user");
        }

        UserInfo targetUserInfo = userInfoDao.getUserInfoById(target.getUserId());
        List<RoleInfo> targetRoleInfoList = userService.getRole(targetUserInfo);

        Log.ii(targetRoleInfoList);
        if (targetRoleInfoList == null || targetRoleInfoList.size() == 0) {
            throw new ServiceException("guid " + bo.getTargetGuid() + " dont have a role ");
        }

        target.setUserId(null);
        target.setContact(null);
        salesmanInfoDao.updateSalesmanInfoStrict(target);

        if (targetRoleInfoList.size() - 1 == 0) {
            userService.removeRole(targetUserInfo, getRoleInfo(roleInfoDao));
        }

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

    @Override
    public RoleInfoBO update(UpdateRoleInfoBO bo) {

        SalesmanInfo salesmanInfo = salesmanInfoDao.getSalesmanInfoById(bo.getTargetId());
        BeanUtil.copyProperties(bo, salesmanInfo);

        salesmanInfo = salesmanInfoDao.updateSalesmanInfo(salesmanInfo);

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(salesmanInfo, resBO);

        return resBO;
    }

    private QueryRespVO<RoleInfoBO> getAllSalesman(QuerySubordinateBO req) {
        PageInfo pageInfo = buildPageInfo(req);
        PageInfo<SalesmanInfo> salesmanInfoList =
                salesmanInfoDao.getAllSalesmanInfoList(DataSourceCommonConstant.DATABASE_COMMON_VALID, pageInfo);
        QueryRespVO<RoleInfoBO> res = new QueryRespVO<>();
        res.buildPageInfo(salesmanInfoList);

        for (SalesmanInfo salesman : salesmanInfoList.getList()) {
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(salesman, bo);
            bo.setRoleType(getRoleType());
            res.getInfo().add(bo);
        }
        return res;
    }

    private TraderInfo getTraderInfoByCreator(UserInfo creator) {
        TraderInfo traderInfo;
        TraderInfoExample example = new TraderInfoExample();
        TraderInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(creator.getId());
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            traderInfo = traderInfoDao.getTraderInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("");
        }
        return traderInfo;
    }

    private RoleInfoBO makeRoleInfoBO(SalesmanInfo salesmanInfo) {
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(salesmanInfo, res);
        res.setRoleType(getRoleType());
        return res;
    }

    private SalesmanInfo makeSalesmanInfo(CreateRoleBO bo) {
        SalesmanInfo salesmanInfo = new SalesmanInfo();
        BeanUtil.copyProperties(bo, salesmanInfo);
        salesmanInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        salesmanInfo = salesmanInfoDao.updateSalesmanInfo(salesmanInfo);
        return salesmanInfo;
    }

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

}
