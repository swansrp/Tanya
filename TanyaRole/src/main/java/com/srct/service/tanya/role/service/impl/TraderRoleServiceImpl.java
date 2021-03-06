/**
 * @Title: TraderRoleServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:39:20
 */
package com.srct.service.tanya.role.service.impl;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanTraderMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderFactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.GetRoleDetailsBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.PermissionDetailsBO;
import com.srct.service.tanya.role.bo.QuerySubordinateBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.bo.UpdateRoleInfoBO;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.tanya.role.service.TraderRoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sharp
 */
@Service
public class TraderRoleServiceImpl implements RoleService, TraderRoleService {

    @Autowired
    private FactoryInfoDao factoryInfoDao;

    @Autowired
    private TraderInfoDao traderInfoDao;

    @Autowired
    private SalesmanInfoDao salesmanInfoDao;

    @Autowired
    private FactoryMerchantMapDao factoryMerchantMapDao;

    @Autowired
    private TraderFactoryMerchantMapDao traderFactoryMerchantMapDao;

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

        FactoryInfo factoryInfo;
        try {
            factoryInfo = getFactoryInfoByCreator(bo.getCreaterInfo());
        } catch (Exception e) {
            throw new ServiceException("添加人角色 " + bo.getCreaterRole().getRole() + " 不允许添加 " + getRoleType());
        }

        FactoryMerchantMap factoryMerchantMap = getFactoryMerchantMapByFactoryInfo(factoryInfo);

        List<TraderFactoryMerchantMap> traderFactoryMerchantMapList =
                getTraderFactoryMerchantMapByFactoryMerchantMap(factoryMerchantMap);
        if (traderFactoryMerchantMapList != null && traderFactoryMerchantMapList.size() >= factoryMerchantMap
                .getTraderNumber()) {
            throw new ServiceException("数量已达上限,不能再添加了");
        }

        TraderInfo traderInfo = makeTraderInfo(bo);

        makeTraderFactoryRelationship(factoryInfo, factoryMerchantMap, traderInfo);

        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(traderInfo, res);
        res.setRoleType(getRoleType());
        return res;
    }

    @Override
    public RoleInfoBO del(ModifyRoleBO bo) {
        TraderInfo traderInfo = traderInfoDao.getTraderInfoById(bo.getId());
        if (traderInfo.getUserId() != null) {
            throw new ServiceException(
                    "Dont allow to del role " + traderInfo.getId() + " without kickout the user " + traderInfo
                            .getUserId());
        }
        traderInfoDao.delTraderInfo(traderInfo);
        return makeRoleInfoBO(traderInfo);
    }

    @Override
    public RoleInfoBO getDetails(GetRoleDetailsBO bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        if (userInfo != null) {
            FactoryInfo factoryInfo;
            try {
                factoryInfo = getFactoryInfoByCreator(userInfo);
            } catch (Exception e) {
                Log.e("dont find the user id {} for {}", userInfo.getId(), "factory");
                throw new ServiceException("dont find the user id " + userInfo.getId() + " for factory");
            }

            FactoryMerchantMap factoryMerchantMap = getFactoryMerchantMapByFactoryInfo(factoryInfo);

            TraderFactoryMerchantMap traderFactoryMerchantMapEx = new TraderFactoryMerchantMap();
            traderFactoryMerchantMapEx.setTraderId(bo.getId());
            traderFactoryMerchantMapEx.setFactoryId(factoryInfo.getId());
            traderFactoryMerchantMapEx.setFactoryMerchantMapId(factoryMerchantMap.getId());
            traderFactoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

            if (traderFactoryMerchantMapDao.countTraderFactoryMerchantMap(traderFactoryMerchantMapEx) == 0) {
                throw new ServiceException("查询目标不是您的下属");
            }
        }

        TraderInfo traderInfo = traderInfoDao.getTraderInfoById(bo.getId());
        FactoryInfo factoryInfo = getFactoryInfoByTraderInfo(traderInfo);
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(traderInfo, res);
        res.setStartAt(factoryInfo.getStartAt());
        res.setEndAt(factoryInfo.getEndAt());
        res.setRoleType(getRoleType());
        return res;
    }

    @Override
    public FactoryInfo getFactoryInfoByTraderInfo(TraderInfo traderInfo) {
        Date now = new Date();

        TraderFactoryMerchantMapExample example = new TraderFactoryMerchantMapExample();
        TraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andEndAtGreaterThanOrEqualTo(now);
        criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andTraderIdEqualTo(traderInfo.getId());
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            Integer factoryId =
                    traderFactoryMerchantMapDao.getTraderFactoryMerchantMapByExample(example).get(0).getFactoryId();
            return factoryInfoDao.getFactoryInfoById(factoryId);
        } catch (Exception e) {
            throw new ServiceException(
                    "no valid Factory Merchant relationship for the trader " + traderInfo.getTitle());
        }

    }

    @Override
    public FactoryInfo getFactoryInfoByUser(UserInfo userInfo) {
        Integer factoryId = getTraderFactoryMerchantMap(userInfo).getFactoryId();
        return factoryInfoDao.getFactoryInfoById(factoryId);
    }

    @Override
    public Integer getRoleIdByUser(UserInfo userInfo) {
        return getTraderInfoByUser(userInfo).getId();
    }

    @Override
    public String getRoleType() {
        return "trader";
    }

    @Override
    public List<SalesmanInfo> getSalesmanInfoListByTraderInfo(UserInfo userInfo) {
        TraderInfo traderInfo = getTraderInfoByUser(userInfo);
        return getSalesmanInfoListByTraderInfo(traderInfo);
    }

    @Override
    public List<SalesmanInfo> getSalesmanInfoListByTraderInfo(TraderInfo traderInfo) {
        SalesmanTraderMap salesmanTraderMap = new SalesmanTraderMap();
        salesmanTraderMap.setTraderId(traderInfo.getId());
        salesmanTraderMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<SalesmanTraderMap> salesmanTraderMapList =
                salesmanTraderMapDao.getSalesmanTraderMapSelective(salesmanTraderMap);

        List<SalesmanInfo> res = new ArrayList<>();
        salesmanTraderMapList.forEach(map -> res.add(salesmanInfoDao.getSalesmanInfoById(map.getSalesmanId())));
        return res;
    }

    @Override
    public RoleInfoBO getSelfDetails(UserInfo userInfo) {
        Integer id = getRoleIdByUser(userInfo);
        TraderInfo traderInfo = traderInfoDao.getTraderInfoById(id);
        FactoryInfo factoryInfo = getFactoryInfoByTraderInfo(traderInfo);
        FactoryMerchantMap factoryMerchantMapEx = new FactoryMerchantMap();
        factoryMerchantMapEx.setFactoryId(factoryInfo.getId());
        factoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<FactoryMerchantMap> factoryMerchantMapList =
                factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx);

        if (factoryMerchantMapList == null || factoryMerchantMapList.size() == 0) {
            throw new ServiceException("没有找到上级商业渠道");
        }
        return makeRoleInfoBO(traderInfo, factoryMerchantMapList.get(0));
    }

    @Override
    public QueryRespVO<RoleInfoBO> getSubordinate(QuerySubordinateBO req) {
        UserInfo userInfo = req.getUserInfo();
        if (userInfo == null) {
            return getAllTrader(req);
        }

        FactoryInfo factoryInfo;
        if (req.getFactoryId() != null) {
            factoryInfo = factoryInfoDao.getFactoryInfoById(req.getFactoryId());
        } else {
            try {
                factoryInfo = getFactoryInfoByCreator(userInfo);
            } catch (Exception e) {
                Log.e("dont find the user id {} for {}", userInfo.getId(), "factory");
                throw new ServiceException("dont find the user id " + userInfo.getId() + " for factory");
            }
        }
        FactoryMerchantMap factoryMerchantMap = getFactoryMerchantMapByFactoryInfo(factoryInfo);
        PageInfo pageInfo = buildPageInfo(req);
        List<TraderFactoryMerchantMap> traderFactoryMerchantMapList =
                getTraderFactoryMerchantMapByFactoryMerchantMap(factoryMerchantMap);
        List<Integer> traderIdList =
                ReflectionUtil.getFieldList(traderFactoryMerchantMapList, "traderId", Integer.class);
        if (traderIdList.size() == 0) {
            traderIdList.add(0);
        }
        TraderInfoExample example = new TraderInfoExample();
        TraderInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(traderIdList);
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

        PageInfo<TraderInfo> traderInfoList = traderInfoDao.getTraderInfoByExample(example, pageInfo);

        QueryRespVO<RoleInfoBO> res = new QueryRespVO<>();
        res.buildPageInfo(traderInfoList);
        if (traderInfoList != null && traderInfoList.getList().size() > 0) {
            for (TraderInfo traderInfo : traderInfoList.getList()) {
                RoleInfoBO bo = new RoleInfoBO();
                BeanUtil.copyProperties(traderInfo, bo);
                bo.setRoleType(getRoleType());
                res.getInfo().add(bo);
            }
        }
        return res;
    }

    @Override
    public String getSubordinateRoleType() {
        return "salesman";
    }

    @Override
    public TraderFactoryMerchantMap getTraderFactoryMerchantMap(UserInfo userInfo) {
        Date now = new Date();
        Integer traderId = getRoleIdByUser(userInfo);

        TraderFactoryMerchantMapExample example = new TraderFactoryMerchantMapExample();
        TraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andEndAtGreaterThanOrEqualTo(now);
        criteria.andTraderIdEqualTo(traderId);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return traderFactoryMerchantMapDao.getTraderFactoryMerchantMapByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException(
                    "no valid Trader Factory Merchant relationship for the user " + userInfo.getName());
        }
    }

    @Override
    public TraderFactoryMerchantMap getTraderFactoryMerchantMap(TraderInfo traderInfo) {
        Date now = new Date();
        Integer traderId = traderInfo.getId();

        TraderFactoryMerchantMapExample example = new TraderFactoryMerchantMapExample();
        TraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andEndAtGreaterThanOrEqualTo(now);
        criteria.andTraderIdEqualTo(traderId);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return traderFactoryMerchantMapDao.getTraderFactoryMerchantMapByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException(
                    "no valid Trader Factory Merchant relationship for the trader " + traderInfo.getId());
        }
    }

    @Override
    public List<TraderFactoryMerchantMap> getTraderFactoryMerchantMap(FactoryInfo factoryInfo) {
        List<TraderFactoryMerchantMap> traderFactoryMerchantMapList = new ArrayList<>();
        List<TraderInfo> traderInfoList = getTraderInfoList(factoryInfo);
        traderInfoList.forEach(traderInfo -> traderFactoryMerchantMapList.add(getTraderFactoryMerchantMap(traderInfo)));
        return traderFactoryMerchantMapList;
    }

    @Override
    public TraderInfo getTraderInfoByUser(UserInfo userInfo) {
        TraderInfoExample example = new TraderInfoExample();
        TraderInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userInfo.getId());
        // criteria.andEndAtGreaterThanOrEqualTo(now);
        // criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return traderInfoDao.getTraderInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("no trader have the user " + userInfo.getName());
        }
    }

    @Override
    public List<TraderInfo> getTraderInfoList(FactoryInfo factoryInfo) {
        return getTraderListByFactoryId(factoryInfo.getId());
    }

    @Override
    public RoleInfoBO invite(ModifyRoleBO bo) {
        UserInfo targetUserInfo = userService.getUserbyGuid(bo.getTargetGuid());
        List<RoleInfo> targetRoleInfoList = userService.getRole(targetUserInfo);

        if (targetRoleInfoList != null && targetRoleInfoList.size() > 0) {
            throw new ServiceException(
                    "guid " + bo.getTargetGuid() + " already have a role " + targetRoleInfoList.get(0).getRole());
        }

        TraderInfo traderInfo = traderInfoDao.getTraderInfoById(bo.getId());
        traderInfo.setId(bo.getId());
        traderInfo.setUserId(targetUserInfo.getId());
        traderInfo.setContact(targetUserInfo.getPhone());
        traderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        TraderInfo target = traderInfoDao.updateTraderInfo(traderInfo);

        userService.addRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

    @Override
    public RoleInfoBO kickout(ModifyRoleBO bo) {

        TraderInfo target = traderInfoDao.getTraderInfoById(bo.getId());
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

        if (targetRoleInfoList == null || targetRoleInfoList.size() == 0) {
            throw new ServiceException("guid " + bo.getTargetGuid() + " dont have a role ");
        }

        target.setUserId(null);
        target.setContact(null);
        traderInfoDao.updateTraderInfoStrict(target);

        userService.removeRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

    @Override
    public RoleInfoBO update(UpdateRoleInfoBO bo) {

        FactoryInfo factoryInfo;
        if (bo.getSuperiorId() != null) {
            factoryInfo = factoryInfoDao.getFactoryInfoById(bo.getSuperiorId());
        } else if (bo.getCreaterInfo() != null) {
            try {
                factoryInfo = getFactoryInfoByCreator(bo.getCreaterInfo());
            } catch (Exception e) {
                throw new ServiceException("no such user as role " + bo.getCreaterRole().getRole());
            }
            boolean startAtValid = bo.getStartAt() != null && bo.getStartAt().before(factoryInfo.getStartAt());
            boolean endAtValid = bo.getEndAt() != null && bo.getEndAt().after(factoryInfo.getEndAt());
            if (startAtValid || endAtValid) {
                throw new ServiceException(
                        "无效的有效期设置 " + bo.getStartAt() + " - " + bo.getEndAt() + " 他的上级有效期为 " + factoryInfo.getStartAt()
                                + " - " + factoryInfo.getEndAt());
            }
        } else {
            throw new ServiceException("dont have enough information to update trader");
        }

        List<TraderInfo> traderInfoList = new ArrayList<>();
        if (bo.getTargetId() != null) {
            TraderInfo traderInfo = traderInfoDao.getTraderInfoById(bo.getTargetId());
            BeanUtil.copyProperties(bo, traderInfo);
            traderInfo = traderInfoDao.updateTraderInfo(traderInfo);
            traderInfoList.add(traderInfo);
        } else if (bo.getSuperiorId() != null) {
            traderInfoList = getTraderListByFactoryId(bo.getSuperiorId());
            if (traderInfoList == null || traderInfoList.size() == 0) {
                Log.i("There is no Trader for factory id {}", factoryInfo.getId());
                return null;
            }
        } else {
            throw new ServiceException("Cant update for " + getRoleType());
        }
        if (bo.getStartAt() != null || bo.getEndAt() != null) {
            FactoryMerchantMap factoryMerchantMap = getFactoryMerchantMapByFactoryInfo(factoryInfo);
            for (TraderInfo traderInfo : traderInfoList) {
                TraderFactoryMerchantMap traderFactoryMerchantMap =
                        getTraderFactoryMerchantMap(factoryMerchantMap, traderInfo);
                if (bo.getSuperiorId() != null) {
                    updateValidPeriod(bo, traderFactoryMerchantMap);
                } else {
                    if (bo.getPermissionDetails() != null) {
                        BeanUtil.copyProperties(bo, traderFactoryMerchantMap);
                    }
                    traderFactoryMerchantMap.setStartAt(bo.getStartAt());
                    traderFactoryMerchantMap.setEndAt(bo.getEndAt());
                }
                traderFactoryMerchantMapDao.updateTraderFactoryMerchantMap(traderFactoryMerchantMap);
            }
        }

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(traderInfoList.get(0), resBO);

        return resBO;
    }

    private QueryRespVO<RoleInfoBO> getAllTrader(QuerySubordinateBO req) {
        PageInfo pageInfo = buildPageInfo(req);
        PageInfo<TraderInfo> tradeInfoList =
                traderInfoDao.getAllTraderInfoList(DataSourceCommonConstant.DATABASE_COMMON_VALID, pageInfo);
        QueryRespVO<RoleInfoBO> res = new QueryRespVO<>();
        res.buildPageInfo(tradeInfoList);
        for (TraderInfo trade : tradeInfoList.getList()) {
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(trade, bo);
            bo.setRoleType(getRoleType());
            res.getInfo().add(bo);
        }
        return res;
    }

    private TraderFactoryMerchantMap getDefaultTraderFactoryMerchantMap(FactoryMerchantMap factoryMerchantMap) {
        TraderFactoryMerchantMapExample example = new TraderFactoryMerchantMapExample();
        TraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andFactoryIdEqualTo(factoryMerchantMap.getFactoryId());
        criteria.andFactoryMerchantMapIdEqualTo(factoryMerchantMap.getId());
        criteria.andTraderIdIsNull();
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return traderFactoryMerchantMapDao.getTraderFactoryMerchantMapByExample(example).get(0);
    }

    private FactoryInfo getFactoryInfoByCreator(UserInfo creator) {
        //Date now = new Date();

        FactoryInfoExample example = new FactoryInfoExample();
        FactoryInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(creator.getId());
        // criteria.andEndAtGreaterThanOrEqualTo(now);
        // criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return factoryInfoDao.getFactoryInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("");
        }
    }

    private FactoryMerchantMap getFactoryMerchantMapByFactoryInfo(FactoryInfo factoryInfo) {
        //Date now = new Date();

        FactoryMerchantMapExample example = new FactoryMerchantMapExample();
        FactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andFactoryIdEqualTo(factoryInfo.getId());
        // criteria.andEndAtGreaterThanOrEqualTo(now);
        // criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return factoryMerchantMapDao.getFactoryMerchantMapByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("no such Merchant for factory " + factoryInfo.getTitle());
        }
    }

    private TraderFactoryMerchantMap getTraderFactoryMerchantMap(FactoryMerchantMap factoryMerchantMap,
            TraderInfo traderInfo) {
        TraderFactoryMerchantMap traderFactoryMerchantMap;
        try {
            TraderFactoryMerchantMap traderFactoryMerchantMapEx = new TraderFactoryMerchantMap();
            traderFactoryMerchantMapEx.setFactoryId(factoryMerchantMap.getFactoryId());
            traderFactoryMerchantMapEx.setFactoryMerchantMapId(factoryMerchantMap.getId());
            traderFactoryMerchantMapEx.setTraderId(traderInfo.getId());
            traderFactoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            traderFactoryMerchantMap =
                    traderFactoryMerchantMapDao.getTraderFactoryMerchantMapSelective(traderFactoryMerchantMapEx).get(0);
        } catch (Exception e) {
            Log.i(e);
            throw new ServiceException("get the relationship failed");
        }
        return traderFactoryMerchantMap;
    }

    private List<TraderFactoryMerchantMap> getTraderFactoryMerchantMapByFactoryMerchantMap(
            FactoryMerchantMap factoryMerchantMap) {

        TraderFactoryMerchantMap traderFactoryMerchantMapEx = new TraderFactoryMerchantMap();
        traderFactoryMerchantMapEx.setFactoryId(factoryMerchantMap.getFactoryId());
        traderFactoryMerchantMapEx.setFactoryMerchantMapId(factoryMerchantMap.getId());
        traderFactoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return traderFactoryMerchantMapDao.getTraderFactoryMerchantMapSelective(traderFactoryMerchantMapEx);
    }

    private PageInfo<TraderFactoryMerchantMap> getTraderFactoryMerchantMapByFactoryMerchantMap(
            FactoryMerchantMap factoryMerchantMap, PageInfo pageInfo) {

        TraderFactoryMerchantMap traderFactoryMerchantMapEx = new TraderFactoryMerchantMap();
        traderFactoryMerchantMapEx.setFactoryId(factoryMerchantMap.getFactoryId());
        traderFactoryMerchantMapEx.setFactoryMerchantMapId(factoryMerchantMap.getId());
        traderFactoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return traderFactoryMerchantMapDao.getTraderFactoryMerchantMapSelective(traderFactoryMerchantMapEx, pageInfo);
    }

    private List<TraderInfo> getTraderListByFactoryId(Integer id) {
        FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfoById(id);
        FactoryMerchantMap factoryMerchantMap = getFactoryMerchantMapByFactoryInfo(factoryInfo);
        List<TraderFactoryMerchantMap> traderFactoryMerchantMapList =
                getTraderFactoryMerchantMapByFactoryMerchantMap(factoryMerchantMap);

        List<TraderInfo> traderInfoList = new ArrayList<>();
        for (TraderFactoryMerchantMap relationship : traderFactoryMerchantMapList) {
            if (relationship.getTraderId() != null) {
                traderInfoList.add(traderInfoDao.getTraderInfoById(relationship.getTraderId()));
            }
        }

        return traderInfoList;
    }

    private RoleInfoBO makeRoleInfoBO(TraderInfo traderInfo) {
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(traderInfo, res);
        res.setRoleType(getRoleType());
        return res;
    }

    private RoleInfoBO makeRoleInfoBO(TraderInfo traderInfo, FactoryMerchantMap map) {
        RoleInfoBO res = makeRoleInfoBO(traderInfo);
        PermissionDetailsBO permissionDetailsBO = new PermissionDetailsBO();
        BeanUtil.copyProperties(map, permissionDetailsBO);
        res.setPermissionDetails(permissionDetailsBO);
        return res;
    }

    private void makeTraderFactoryRelationship(FactoryInfo factoryInfo, FactoryMerchantMap factoryMerchantMap,
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

    private TraderInfo makeTraderInfo(CreateRoleBO bo) {
        TraderInfo traderInfo = new TraderInfo();
        BeanUtil.copyProperties(bo, traderInfo);
        traderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        traderInfo = traderInfoDao.updateTraderInfo(traderInfo);
        return traderInfo;

    }

    private void updateValidPeriod(UpdateRoleInfoBO bo, TraderFactoryMerchantMap traderFactoryMerchantMap) {
        if (bo.getStartAt() != null) {
            traderFactoryMerchantMap.setStartAt(bo.getStartAt());
        }
        if (bo.getEndAt() != null) {
            traderFactoryMerchantMap.setEndAt(bo.getEndAt());
        }
    }

}
