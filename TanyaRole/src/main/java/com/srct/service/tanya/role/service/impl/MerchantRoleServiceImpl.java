/**
 * @Title: MerchantRoleServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service.impl
 * @author Sharp
 * @date 2019-01-29 19:36:21
 */
package com.srct.service.tanya.role.service.impl;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.config.FeatureConstant;
import com.srct.service.tanya.common.datalayer.tanya.entity.AdminInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.AdminInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantAdminMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.AdminInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.MerchantAdminMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.MerchantInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.common.service.FeatureService;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.role.bo.CreateRoleBO;
import com.srct.service.tanya.role.bo.GetRoleDetailsBO;
import com.srct.service.tanya.role.bo.ModifyRoleBO;
import com.srct.service.tanya.role.bo.PermissionDetailsBO;
import com.srct.service.tanya.role.bo.QuerySubordinateBO;
import com.srct.service.tanya.role.bo.RoleInfoBO;
import com.srct.service.tanya.role.bo.UpdateRoleInfoBO;
import com.srct.service.tanya.role.service.MerchantRoleService;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.DateUtils;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Sharp
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
    private AdminInfoDao adminInfoDao;

    @Autowired
    private MerchantAdminMapDao merchantAdminMapDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserService userService;

    @Autowired
    private FeatureService featureService;

    @Override
    public RoleInfoBO create(CreateRoleBO bo) {

        AdminInfo adminInfo;
        try {
            adminInfo = getAdminInfoByCreator(bo.getCreaterInfo());
        } catch (Exception e) {
            throw new ServiceException("不允许[" + bo.getCreaterRole().getRole() + "]角色创建" + getRoleType());
        }

        MerchantInfo merchantInfo = makeMerchantInfo(bo);
        makeMerchantAdminRelationship(adminInfo, merchantInfo);

        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(merchantInfo, res);
        res.setRoleType(getRoleType());

        return res;
    }

    @Override
    public RoleInfoBO del(ModifyRoleBO bo) {
        MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoById(bo.getId());
        if (merchantInfo.getUserId() != null) {
            throw new ServiceException(
                    "Dont allow to del role " + merchantInfo.getId() + " without kickout the user " + merchantInfo
                            .getUserId());
        }
        merchantInfoDao.delMerchantInfo(merchantInfo);
        return makeRoleInfoBO(merchantInfo);
    }

    @Override
    public RoleInfoBO getDetails(GetRoleDetailsBO bo) {
        MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoById(bo.getId());
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(merchantInfo, res);
        res.setRoleType(getRoleType());
        return res;
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
    public Integer getRoleIdByUser(UserInfo userInfo) {
        return getMerchantInfoByUser(userInfo).getId();
    }

    @Override
    public String getRoleType() {
        return "merchant";
    }

    @Override
    public RoleInfoBO getSelfDetails(UserInfo userInfo) {
        Integer id = getRoleIdByUser(userInfo);
        MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoById(id);
        return makeRoleInfoBO(merchantInfo);
    }

    @Override
    public QueryRespVO<RoleInfoBO> getSubordinate(QuerySubordinateBO req) {
        UserInfo userInfo = req.getUserInfo();
        if (userInfo == null) {
            return getAllMerchant(req);
        }
        if (featureService.getFeatureExpected(FeatureConstant.MERCHANT_BIND_ADMIN_FEATURE, "0")) {
            return getAllMerchant(req);
        }

        AdminInfo adminInfo;
        if (req.getAdminId() != null) {
            adminInfo = adminInfoDao.getAdminInfoById(req.getAdminId());
        } else {
            try {
                adminInfo = getAdminInfoByCreator(userInfo);
            } catch (Exception e) {
                throw new ServiceException("不允许用户[" + userInfo.getId() + "]获取渠道下属" + getRoleType());
            }
        }

        QueryRespVO<RoleInfoBO> res = new QueryRespVO<>();
        PageInfo pageInfo = buildPageInfo(req);
        List<MerchantAdminMap> merchantAdminMapList = getMerchantAdminMapsListByAdminInfo(adminInfo);
        List<Integer> merchantIdList = ReflectionUtil.getFieldList(merchantAdminMapList, "merchantId", Integer.class);
        if (merchantIdList.size() == 0) {
            merchantIdList.add(0);
        }
        MerchantInfoExample example = new MerchantInfoExample();
        MerchantInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(merchantIdList);
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

        PageInfo<MerchantInfo> merchantInfoList = merchantInfoDao.getMerchantInfoByExample(example, pageInfo);
        res.buildPageInfo(merchantInfoList);
        for (MerchantInfo merchantInfo : merchantInfoList.getList()) {
            RoleInfoBO bo = buildRoleInfoBO(adminInfo, merchantInfo);
            res.getInfo().add(bo);
        }
        return res;
    }

    @Override
    public String getSubordinateRoleType() {
        return "factory";
    }

    @Override
    public RoleInfoBO invite(ModifyRoleBO bo) {
        UserInfo targetUserInfo = userService.getUserbyGuid(bo.getTargetGuid());
        List<RoleInfo> targetRoleInfoList = userService.getRole(targetUserInfo);

        if (targetRoleInfoList != null && targetRoleInfoList.size() > 0) {
            throw new ServiceException(
                    "guid " + bo.getTargetGuid() + " already have a role " + targetRoleInfoList.get(0).getRole());
        }

        MerchantInfo merchant = merchantInfoDao.getMerchantInfoById(bo.getId());
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
    public RoleInfoBO kickout(ModifyRoleBO bo) {

        MerchantInfo target = merchantInfoDao.getMerchantInfoById(bo.getId());
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
        merchantInfoDao.updateMerchantInfoStrict(target);

        userService.removeRole(targetUserInfo, getRoleInfo(roleInfoDao));

        RoleInfoBO resBO = new RoleInfoBO();
        resBO.setRoleType(getRoleType());
        BeanUtil.copyProperties(target, resBO);

        return resBO;
    }

    @Override
    public RoleInfoBO update(UpdateRoleInfoBO bo) {
        AdminInfo adminInfo;
        if (bo.getSuperiorId() != null) {
            adminInfo = adminInfoDao.getAdminInfoById(bo.getSuperiorId());
        } else if (bo.getCreaterInfo() != null) {
            try {
                adminInfo = getAdminInfoByCreator(bo.getCreaterInfo());
            } catch (Exception e) {
                throw new ServiceException("更新者不是[" + bo.getCreaterRole().getRole() + "]角色");
            }
        } else {
            throw new ServiceException("不能更新渠道角色信息");
        }

        MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoById(bo.getTargetId());
        BeanUtil.copyProperties(bo, merchantInfo);

        merchantInfo = merchantInfoDao.updateMerchantInfo(merchantInfo);

        if (bo.getStartAt() != null || bo.getEndAt() != null) {
            UpdateRoleInfoBO updateSubRoleBO = new UpdateRoleInfoBO();
            updateSubRoleBO.setStartAt(bo.getStartAt());
            updateSubRoleBO.setEndAt(bo.getEndAt());
            updateSubRoleBO.setSuperiorId(merchantInfo.getId());
            RoleService subordinateService =
                    (RoleService) BeanUtil.getBean(getSubordinateRoleType() + "RoleServiceImpl");
            subordinateService.update(updateSubRoleBO);
        }
        return buildRoleInfoBO(adminInfo, merchantInfo);
    }

    private PermissionDetailsBO buildPermissionDetailsBO(MerchantAdminMap merchantAdminMap) {
        PermissionDetailsBO permissionDetailsBO = new PermissionDetailsBO();
        if (merchantAdminMap != null) {
            BeanUtil.copyProperties(merchantAdminMap, permissionDetailsBO);
        }
        return permissionDetailsBO;
    }

    private RoleInfoBO buildRoleInfoBO(AdminInfo adminInfo, MerchantInfo merchantInfo) {
        RoleInfoBO resBO = new RoleInfoBO();
        BeanUtil.copyProperties(merchantInfo, resBO);
        resBO.setRoleType(getRoleType());
        List<MerchantAdminMap> merchantAdminMaps =
                getMerchantAdminMapsListByAdminInfoAndMerchantInfo(adminInfo, merchantInfo);
        PermissionDetailsBO permissionDetailsBO = buildPermissionDetailsBO(merchantAdminMaps.get(0));
        resBO.setPermissionDetails(permissionDetailsBO);
        return resBO;
    }

    private AdminInfo getAdminInfoByCreator(UserInfo creator) {
        AdminInfoExample example = new AdminInfoExample();
        AdminInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(creator.getId());
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        try {
            return adminInfoDao.getAdminInfoByExample(example).get(0);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private QueryRespVO<RoleInfoBO> getAllMerchant(QuerySubordinateBO req) {
        PageInfo pageInfo = buildPageInfo(req);
        PageInfo<MerchantInfo> merchantInfoList =
                merchantInfoDao.getAllMerchantInfoList(DataSourceCommonConstant.DATABASE_COMMON_VALID, pageInfo);
        QueryRespVO<RoleInfoBO> res = new QueryRespVO<>();
        res.buildPageInfo(merchantInfoList);
        for (MerchantInfo merchant : merchantInfoList.getList()) {
            RoleInfoBO bo = new RoleInfoBO();
            BeanUtil.copyProperties(merchant, bo);
            bo.setRoleType(getRoleType());
            res.getInfo().add(bo);
        }
        return res;
    }

    private List<MerchantAdminMap> getMerchantAdminMapsListByAdminInfo(AdminInfo adminInfo) {
        MerchantAdminMap merchantAdminMapEx = new MerchantAdminMap();
        merchantAdminMapEx.setAdminId(adminInfo.getId());
        merchantAdminMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return merchantAdminMapDao.getMerchantAdminMapSelective(merchantAdminMapEx);
    }

    private List<MerchantAdminMap> getMerchantAdminMapsListByAdminInfoAndMerchantInfo(AdminInfo adminInfo,
            MerchantInfo merchantInfo) {
        MerchantAdminMap merchantAdminMapEx = new MerchantAdminMap();
        merchantAdminMapEx.setAdminId(adminInfo.getId());
        merchantAdminMapEx.setMerchantId(merchantInfo.getId());
        merchantAdminMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return merchantAdminMapDao.getMerchantAdminMapSelective(merchantAdminMapEx);
    }

    private void makeMerchantAdminRelationship(AdminInfo adminInfo, MerchantInfo merchantInfo) {
        MerchantAdminMap merchantAdminMap = new MerchantAdminMap();
        merchantAdminMap.setAdminId(adminInfo.getId());
        merchantAdminMap.setMerchantId(merchantInfo.getId());
        merchantAdminMap.setFactoryNumber(2);
        merchantAdminMap.setSign(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        merchantAdminMap.setStartAt(merchantInfo.getStartAt());
        merchantAdminMap.setEndAt(merchantInfo.getEndAt());
        merchantAdminMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        merchantAdminMapDao.updateMerchantAdminMap(merchantAdminMap);
    }

    private MerchantInfo makeMerchantInfo(CreateRoleBO bo) {
        Date now = new Date();

        MerchantInfo merchantInfo = new MerchantInfo();
        BeanUtil.copyProperties(bo, merchantInfo);
        merchantInfo.setStartAt(now);
        merchantInfo.setEndAt(DateUtils.addMonth(now, 1));
        merchantInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        merchantInfoDao.updateMerchantInfo(merchantInfo);
        return merchantInfo;
    }

    private RoleInfoBO makeRoleInfoBO(MerchantInfo merchantInfo) {
        RoleInfoBO res = new RoleInfoBO();
        BeanUtil.copyProperties(merchantInfo, res);
        res.setRoleType(getRoleType());
        return res;
    }

}
