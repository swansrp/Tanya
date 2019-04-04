/**
 * @Title: FactoryRoleServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:38:41
 */
package com.srct.service.tanya.role.service.impl;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMapExample;
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
import com.srct.service.tanya.role.bo.GetRoleDetailsBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.PermissionDetailsBO;
import com.srct.service.tanya.role.bo.QuerySubordinateBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.bo.UpdateRoleInfoBO;
import com.srct.service.tanya.role.service.FactoryRoleService;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Sharp
 */
@Service
public class FactoryRoleServiceImpl implements RoleService, FactoryRoleService {

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

    @Override
    public String getRoleType() {
        return "factory";
    }

    @Override
    public String getSubordinateRoleType() {
        return "trader";
    }

    @Override
    public RoleInfoBO create(CreateRoleBO bo) {

        MerchantInfo merchantInfo;
        try {
            merchantInfo = getMerchantInfoByCreater(bo.getCreaterInfo());
        } catch (Exception e) {
            throw new ServiceException(
                    "creater role " + bo.getCreaterRole().getRole() + " dont allow create " + getRoleType());
        }

        FactoryInfo factoryInfo = makeFactoryInfo(bo);

        makeFactoryMerchantRelationship(merchantInfo, factoryInfo);

        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(factoryInfo, res);
        res.setRoleType(getRoleType());

        return res;
    }

    private void makeFactoryMerchantRelationship(MerchantInfo merchantInfo, FactoryInfo factoryInfo) {
        Date now = new Date();
        FactoryMerchantMap factoryMerchantMap = new FactoryMerchantMap();
        factoryMerchantMap.setFactoryId(factoryInfo.getId());
        factoryMerchantMap.setMerchantId(merchantInfo.getId());
        factoryMerchantMap.setGoodsNumber(0);
        factoryMerchantMap.setTraderNumber(0);
        factoryMerchantMap.setCampaignNumber(0);
        factoryMerchantMap.setDiscountNumber(0);
        factoryMerchantMap.setStartAt(now);
        factoryMerchantMap.setEndAt(merchantInfo.getEndAt());

        factoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        factoryMerchantMapDao.updateFactoryMerchantMap(factoryMerchantMap);
    }

    private FactoryInfo makeFactoryInfo(CreateRoleBO bo) {
        Date now = new Date();
        FactoryInfo factoryInfo = new FactoryInfo();
        BeanUtil.copyProperties(bo, factoryInfo);
        factoryInfo.setStartAt(now);
        factoryInfo.setEndAt(getDefaultPeriod(now, DEFAULT_PERIOD_TYPE, DEFAULT_PERIOD_VALUE));
        factoryInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        factoryInfo = factoryInfoDao.updateFactoryInfo(factoryInfo);
        return factoryInfo;
    }

    public MerchantInfo getMerchantInfoByCreater(UserInfo creater) {
        //Date now = new Date();

        MerchantInfoExample example = new MerchantInfoExample();
        MerchantInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(creater.getId());
        // criteria.andEndAtGreaterThanOrEqualTo(now);
        // criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return merchantInfoDao.getMerchantInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public QueryRespVO<RoleInfoBO> getSubordinate(QuerySubordinateBO req) {
        UserInfo userInfo = req.getUserInfo();
        if (userInfo == null) {
            return getAllFactory(req);
        }

        MerchantInfo merchantInfo;
        try {
            merchantInfo = getMerchantInfoByCreater(userInfo);
        } catch (Exception e) {
            Log.e("dont find the user id {} for {}", userInfo.getId(), "merchant");
            throw new ServiceException("dont find the user id " + userInfo.getId() + " for merchant");
        }
        PageInfo pageInfo = buildPageInfo(req);
        FactoryMerchantMap factoryMerchantMapEx = new FactoryMerchantMap();
        factoryMerchantMapEx.setMerchantId(merchantInfo.getId());
        factoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<FactoryMerchantMap> factoryMerchantMapList =
                factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx, pageInfo);

        QueryRespVO<RoleInfoBO> res = new QueryRespVO<>();
        res.buildPageInfo(pageInfo);

        for (FactoryMerchantMap factoryMerchantMap : factoryMerchantMapList) {
            FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfoById(factoryMerchantMap.getFactoryId());
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(factoryInfo, bo);
            bo.setRoleType(getRoleType());
            PermissionDetailsBO permissionDetailsBO = new PermissionDetailsBO();
            BeanUtil.copyProperties(factoryMerchantMap, permissionDetailsBO);
            bo.setPermissionDetails(permissionDetailsBO);
            res.getInfo().add(bo);
        }
        return res;
    }

    @Override
    public RoleInfoBO getDetails(GetRoleDetailsBO bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        FactoryMerchantMap factoryMerchantMap = null;
        if (userInfo != null) {
            MerchantInfo merchantInfo;
            try {
                merchantInfo = getMerchantInfoByCreater(userInfo);
            } catch (Exception e) {
                Log.e("dont find the user id {} for {}", userInfo.getId(), "merchant");
                throw new ServiceException("dont find the user id " + userInfo.getId() + " for merchant");
            }
            FactoryMerchantMap factoryMerchantMapEx = new FactoryMerchantMap();
            factoryMerchantMapEx.setFactoryId(bo.getId());
            factoryMerchantMapEx.setMerchantId(merchantInfo.getId());
            factoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            try {
                factoryMerchantMap = factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx).get(0);
            } catch (Exception e) {
                throw new ServiceException("factory id " + bo.getId() + " merchant id " + merchantInfo.getId()
                        + "cant find the relationship");
            }
        }

        FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfoById(bo.getId());
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(factoryInfo, res);
        res.setRoleType(getRoleType());
        if (factoryMerchantMap != null) {
            PermissionDetailsBO permissionDetailsBO = new PermissionDetailsBO();
            BeanUtil.copyProperties(factoryMerchantMap, permissionDetailsBO);
            res.setPermissionDetails(permissionDetailsBO);
        }
        return res;
    }

    @Override
    public RoleInfoBO getSelfDetails(UserInfo userInfo) {
        Integer id = getRoleIdbyUser(userInfo);
        FactoryMerchantMap factoryMerchantMapEx = new FactoryMerchantMap();
        factoryMerchantMapEx.setFactoryId(id);
        factoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<FactoryMerchantMap> factoryMerchantMapList =
                factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx);

        if (factoryMerchantMapList == null || factoryMerchantMapList.size() == 0) {
            throw new ServiceException("没有找到上级商业渠道");
        }
        FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfoById(id);
        return makeRoleInfoBO(factoryInfo, factoryMerchantMapList.get(0));
    }

    private QueryRespVO<RoleInfoBO> getAllFactory(QuerySubordinateBO req) {
        PageInfo pageInfo = buildPageInfo(req);
        List<FactoryInfo> factoryInfoList =
                factoryInfoDao.getAllFactoryInfoList(DataSourceCommonConstant.DATABASE_COMMON_VALID, pageInfo);
        QueryRespVO<RoleInfoBO> res = new QueryRespVO<>();
        res.buildPageInfo(pageInfo);

        for (FactoryInfo factory : factoryInfoList) {
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(factory, bo);
            bo.setRoleType(getRoleType());
            res.getInfo().add(bo);
        }
        return res;
    }

    @Override
    public RoleInfoBO kickout(ModifyRoleBO bo) {

        FactoryInfo target = factoryInfoDao.getFactoryInfoById(bo.getId());
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

        FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfoById(bo.getId());
        factoryInfo.setUserId(targetUserInfo.getId());
        factoryInfo.setContact(targetUserInfo.getPhone());
        factoryInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        FactoryInfo target = factoryInfoDao.updateFactoryInfo(factoryInfo);

        userService.addRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

    @Override
    public RoleInfoBO update(UpdateRoleInfoBO bo) {
        MerchantInfo merchantInfo;
        if (bo.getSuperiorId() != null) {
            merchantInfo = merchantInfoDao.getMerchantInfoById(bo.getSuperiorId());
        } else if (bo.getCreaterInfo() != null) {
            try {
                merchantInfo = getMerchantInfoByCreater(bo.getCreaterInfo());
            } catch (Exception e) {
                throw new ServiceException("no such user as role " + bo.getCreaterRole().getRole());
            }

            if ((bo.getStartAt() != null && bo.getStartAt().before(merchantInfo.getStartAt())) || (bo.getEndAt() != null
                    && bo.getEndAt().after(merchantInfo.getEndAt()))) {
                throw new ServiceException(
                        "invalid Period " + bo.getStartAt() + " - " + bo.getEndAt() + " for its Superior "
                                + merchantInfo.getStartAt() + " - " + merchantInfo.getEndAt());
            }
        } else {
            throw new ServiceException("dont have enough information to update factory");
        }

        List<FactoryInfo> factoryInfoList = new ArrayList<>();
        if (bo.getTargetId() != null) {
            FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfoById(bo.getTargetId());
            BeanUtil.copyProperties(bo, factoryInfo);
            factoryInfo = factoryInfoDao.updateFactoryInfo(factoryInfo);
            factoryInfoList.add(factoryInfo);
        } else if (bo.getSuperiorId() != null) {
            try {
                factoryInfoList = getFactoryListByMerchantId(bo.getSuperiorId());
                for (FactoryInfo factoryInfo : factoryInfoList) {
                    boolean update = false;
                    if (bo.getStartAt() != null && bo.getStartAt().after(factoryInfo.getStartAt())) {
                        factoryInfo.setStartAt(bo.getStartAt());
                        update = true;
                    }
                    if (bo.getEndAt() != null && bo.getEndAt().before(factoryInfo.getEndAt())) {
                        factoryInfo.setEndAt(bo.getEndAt());
                        update = true;
                    }
                    if (update) {
                        factoryInfoDao.updateFactoryInfo(factoryInfo);
                    }
                }
            } catch (Exception e) {
                Log.i(e.getMessage());
                return null;
            }
        } else {
            throw new ServiceException("Cant update for " + getRoleType());
        }

        for (FactoryInfo factoryInfo : factoryInfoList) {
            if (bo.getStartAt() != null || bo.getEndAt() != null || bo.getPermissionDetails() != null) {
                FactoryMerchantMap factoryMerchantMap;
                try {
                    FactoryMerchantMap factoryMerchantMapEx = new FactoryMerchantMap();
                    factoryMerchantMapEx.setMerchantId(merchantInfo.getId());
                    factoryMerchantMapEx.setFactoryId(factoryInfo.getId());
                    factoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
                    factoryMerchantMap =
                            factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx).get(0);
                } catch (Exception e) {
                    throw new ServiceException("get the relationship failed");
                }

                if (bo.getSuperiorId() != null) {
                    if (bo.getStartAt() != null && bo.getStartAt().after(factoryMerchantMap.getStartAt())) {
                        factoryMerchantMap.setStartAt(bo.getStartAt());
                    }
                    if (bo.getEndAt() != null && bo.getEndAt().before(factoryMerchantMap.getEndAt())) {
                        factoryMerchantMap.setEndAt(bo.getEndAt());
                    }
                } else {
                    if (bo.getPermissionDetails() != null) {
                        BeanUtil.copyProperties(bo.getPermissionDetails(), factoryMerchantMap);
                    }
                }

                factoryMerchantMapDao.updateFactoryMerchantMap(factoryMerchantMap);
            }

            if (bo.getStartAt() != null || bo.getEndAt() != null) {
                UpdateRoleInfoBO updateSubRoleBO = new UpdateRoleInfoBO();
                updateSubRoleBO.setStartAt(bo.getStartAt());
                updateSubRoleBO.setEndAt(bo.getEndAt());
                updateSubRoleBO.setSuperiorId(merchantInfo.getId());
                RoleService subordinateService =
                        (RoleService) BeanUtil.getBean(getSubordinateRoleType() + "RoleServiceImpl");
                subordinateService.update(updateSubRoleBO);
            }
        }

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(factoryInfoList.get(0), resBO);

        return resBO;
    }

    private List<FactoryInfo> getFactoryListByMerchantId(Integer id) {

        FactoryMerchantMap factoryMerchantMapEx = new FactoryMerchantMap();
        factoryMerchantMapEx.setMerchantId(id);
        factoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        List<FactoryMerchantMap> factoryMerchantMapList =
                factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx);

        if (factoryMerchantMapList == null || factoryMerchantMapList.size() == 0) {
            throw new ServiceException("There is no Factory for merchant id " + id);
        }

        List<FactoryInfo> factoryInfoList = new ArrayList<>();

        for (FactoryMerchantMap relationship : factoryMerchantMapList) {
            factoryInfoList.add(factoryInfoDao.getFactoryInfoById(relationship.getFactoryId()));
        }
        return factoryInfoList;
    }

    @Override
    public Integer getRoleIdbyUser(UserInfo userInfo) {
        return getFactoryInfoByUser(userInfo).getId();
    }

    @Override
    public FactoryInfo getFactoryInfoByUser(UserInfo userInfo) {
        FactoryInfoExample example = new FactoryInfoExample();
        FactoryInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userInfo.getId());
        // criteria.andEndAtGreaterThanOrEqualTo(now);
        // criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return factoryInfoDao.getFactoryInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("no factory have the user " + userInfo.getName());
        }
    }

    @Override
    public FactoryMerchantMap getFactoryMerchantMapByFactoryInfo(FactoryInfo factoryInfo) {
        Date now = new Date();
        FactoryMerchantMapExample example = new FactoryMerchantMapExample();
        FactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andFactoryIdEqualTo(factoryInfo.getId());
        criteria.andEndAtGreaterThanOrEqualTo(now);
        criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return factoryMerchantMapDao.getFactoryMerchantMapByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("no factory Merchant relationship for factory id " + factoryInfo.getId());
        }
    }

    @Override
    public RoleInfoBO del(ModifyRoleBO bo) {
        FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfoById(bo.getId());
        if (factoryInfo.getUserId() != null) {
            throw new ServiceException(
                    "Dont allow to del role " + factoryInfo.getId() + " without kickout the user " + factoryInfo
                            .getUserId());
        }
        factoryInfoDao.delFactoryInfo(factoryInfo);
        return makeRoleInfoBO(factoryInfo);
    }

    private RoleInfoBO makeRoleInfoBO(FactoryInfo factoryInfo) {
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(factoryInfo, res);
        res.setRoleType(getRoleType());
        return res;
    }

    private RoleInfoBO makeRoleInfoBO(FactoryInfo factoryInfo, FactoryMerchantMap map) {
        RoleInfoBO res = makeRoleInfoBO(factoryInfo);
        PermissionDetailsBO permissionDetailsBO = new PermissionDetailsBO();
        BeanUtil.copyProperties(map, permissionDetailsBO);
        res.setPermissionDetails(permissionDetailsBO);
        return res;
    }

    @Override
    public List<FactoryInfo> getFactoryInfoListByMerchantInfo(MerchantInfo merchantInfo) {
        FactoryMerchantMap factoryMerchantMapEx = new FactoryMerchantMap();
        factoryMerchantMapEx.setMerchantId(merchantInfo.getId());
        factoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<FactoryMerchantMap> factoryMerchantMapList =
                factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx);
        List<FactoryInfo> factoryInfoList = new ArrayList<>();
        for (FactoryMerchantMap map : factoryMerchantMapList) {
            factoryInfoList.add(factoryInfoDao.getFactoryInfoById(map.getFactoryId()));
        }
        return factoryInfoList;
    }

}
