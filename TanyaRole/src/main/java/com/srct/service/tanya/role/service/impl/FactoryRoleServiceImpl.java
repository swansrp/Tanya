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
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.bo.UpdateRoleInfoBO;
import com.srct.service.tanya.role.service.FactoryRoleService;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

/**
 * @author Sharp
 *
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

        MerchantInfo merchantInfo = null;
        try {
            merchantInfo = getMerchantInfoByCreater(bo.getCreaterInfo());
        } catch (Exception e) {
            throw new ServiceException(
                "creater role " + bo.getCreaterRole().getRole() + " dont allow create " + getRoleType());
        }

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
        factoryMerchantMap.setGoodsNumber(5);
        factoryMerchantMap.setTraderNumber(5);
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
    public MerchantInfo getMerchantInfoByCreater(UserInfo creater) {
        Date now = new Date();

        MerchantInfo merchantInfo = null;
        MerchantInfoExample example = new MerchantInfoExample();
        MerchantInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(creater.getId());
        // criteria.andEndAtGreaterThanOrEqualTo(now);
        // criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            merchantInfo = merchantInfoDao.getMerchantInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException("");
        }
        return merchantInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.srct.service.tanya.role.service.RoleService#getSubordinate(java.lang.
     * String)
     */
    @Override
    public List<RoleInfoBO> getSubordinate(UserInfo userInfo) {
        if (userInfo == null) {
            return getAllFactory();

        }

        MerchantInfo merchantInfo = null;
        try {
            merchantInfo = getMerchantInfoByCreater(userInfo);
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
            bo.setGoodsNumber(factoryMerchantMap.getGoodsNumber());
            bo.setTraderNumber(factoryMerchantMap.getTraderNumber());
            boList.add(bo);

        }
        return boList;
    }

    @Override
    public RoleInfoBO getDetails(GetRoleDetailsBO bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        FactoryMerchantMap factoryMerchantMap = null;
        if (userInfo != null) {
            MerchantInfo merchantInfo = null;
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
            factoryMerchantMap = null;
            try {
                factoryMerchantMap = factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx).get(0);
            } catch (Exception e) {
                throw new ServiceException(
                    "factory id " + bo.getId() + " merchant id " + merchantInfo.getId() + "cant find the relationship");
            }
        }

        FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfobyId(bo.getId());
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(factoryInfo, res);
        res.setRoleType(getRoleType());
        if (factoryMerchantMap != null) {
            res.setGoodsNumber(factoryMerchantMap.getGoodsNumber());
            res.setTraderNumber(factoryMerchantMap.getTraderNumber());
        }
        return res;
    }

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

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.srct.service.tanya.role.service.RoleService#invite(com.srct.service.tanya
     * .role.bo.ModifyRoleBO)
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

    @Override
    public RoleInfoBO update(UpdateRoleInfoBO bo) {
        MerchantInfo merchantInfo = null;
        if (bo.getSuperiorId() != null) {
            merchantInfo = merchantInfoDao.getMerchantInfobyId(bo.getSuperiorId());
        } else if (bo.getCreaterInfo() != null) {
            try {
                merchantInfo = getMerchantInfoByCreater(bo.getCreaterInfo());
            } catch (Exception e) {
                throw new ServiceException("no such user as role " + bo.getCreaterRole().getRole());
            }

            if ((bo.getStartAt() != null && bo.getStartAt().before(merchantInfo.getStartAt()))
                || (bo.getEndAt() != null && bo.getEndAt().after(merchantInfo.getEndAt()))) {
                throw new ServiceException("invalid Period " + bo.getStartAt() + " - " + bo.getEndAt()
                    + " for its Superior " + merchantInfo.getStartAt() + " - " + merchantInfo.getEndAt());
            }
        }

        List<FactoryInfo> factoryInfoList = new ArrayList<>();
        if (bo.getTargetId() != null) {
            FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfobyId(bo.getTargetId());
            BeanUtil.copyProperties(bo, factoryInfo);
            factoryInfo = factoryInfoDao.updateFactoryInfo(factoryInfo);
            factoryInfoList.add(factoryInfo);
        } else if (bo.getSuperiorId() != null) {
            try {
                factoryInfoList = getFactoryListByMerchantId(bo.getSuperiorId());
                for (FactoryInfo factoryInfo : factoryInfoList) {
                    boolean update = false;
                    if (bo.getStartAt().after(factoryInfo.getStartAt())) {
                        factoryInfo.setStartAt(bo.getStartAt());
                        update = true;
                    }
                    if (bo.getEndAt().before(factoryInfo.getEndAt())) {
                        factoryInfo.setEndAt(bo.getEndAt());
                        update = true;
                    }
                    if (update) {
                        factoryInfo = factoryInfoDao.updateFactoryInfo(factoryInfo);
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

            if (bo.getStartAt() != null || bo.getEndAt() != null || bo.getGoodsNumber() != null
                || bo.getTraderNumber() != null) {
                FactoryMerchantMap factoryMerchantMap = null;
                try {
                    FactoryMerchantMap factoryMerchantMapEx = new FactoryMerchantMap();
                    factoryMerchantMapEx.setMerchantId(merchantInfo.getId());
                    factoryMerchantMapEx.setFactoryId(factoryInfo.getId());
                    factoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
                    factoryMerchantMap =
                        factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx).get(0);
                } catch (Exception e) {
                    throw new ServiceException("get the relationship falied");
                }

                if (bo.getSuperiorId() != null) {
                    if (bo.getStartAt().after(factoryMerchantMap.getStartAt())) {
                        factoryMerchantMap.setStartAt(bo.getStartAt());
                    }
                    if (bo.getEndAt().before(factoryMerchantMap.getEndAt())) {
                        factoryMerchantMap.setEndAt(bo.getEndAt());
                    }
                } else {
                    BeanUtil.copyProperties(bo, factoryMerchantMap);
                }

                factoryMerchantMapDao.updateFactoryMerchantMap(factoryMerchantMap);
            }

            if (bo.getStartAt() != null || bo.getEndAt() != null) {
                UpdateRoleInfoBO updateSubRoleBO = new UpdateRoleInfoBO();
                updateSubRoleBO.setStartAt(bo.getStartAt());
                updateSubRoleBO.setEndAt(bo.getEndAt());
                updateSubRoleBO.setSuperiorId(merchantInfo.getId());
                RoleService subordinateService =
                    (RoleService)BeanUtil.getBean(getSubordinateRoleType() + "RoleServiceImpl");
                subordinateService.update(updateSubRoleBO);
            }
        }

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(factoryInfoList.get(0), resBO);

        return resBO;
    }

    /**
     * @param id
     * @return
     */
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
            factoryInfoList.add(factoryInfoDao.getFactoryInfobyId(relationship.getFactoryId()));
        }
        return factoryInfoList;
    }

    @Override
    public Integer getRoleIdbyUser(UserInfo userInfo) {
        return getFactoryInfoByUser(userInfo).getId();
    }

    @Override
    public FactoryInfo getFactoryInfoByUser(UserInfo userInfo) {
        FactoryInfo factoryInfo = null;
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
        FactoryInfo factoryInfo = factoryInfoDao.getFactoryInfobyId(bo.getId());
        if (factoryInfo.getUserId() != null) {
            throw new ServiceException("Dont allow to del role " + factoryInfo.getId() + " without kickout the user "
                + factoryInfo.getUserId());
        }
        factoryInfoDao.delFactoryInfo(factoryInfo);
        RoleInfoBO res = makeRoleInfoBO(factoryInfo);
        return res;
    }

    /**
     * @param factoryInfo
     * @return
     */
    private RoleInfoBO makeRoleInfoBO(FactoryInfo factoryInfo) {
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(factoryInfo, res);
        res.setRoleType(getRoleType());
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
            factoryInfoList.add(factoryInfoDao.getFactoryInfobyId(map.getFactoryId()));
        }
        return factoryInfoList;
    }

}
