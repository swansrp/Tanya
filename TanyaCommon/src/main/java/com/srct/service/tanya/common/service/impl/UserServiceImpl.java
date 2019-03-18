/**
 * @Title: UserServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.service.impl
 * @author Sharp
 * @date 2019-01-30 11:06:53
 */
package com.srct.service.tanya.common.service.impl;

import com.srct.service.bo.wechat.OpenIdBO;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.AccountOrPasswordIncorrectException;
import com.srct.service.exception.ServiceException;
import com.srct.service.service.WechatService;
import com.srct.service.tanya.common.bo.user.UserLoginRespBO;
import com.srct.service.tanya.common.bo.user.UserRegReqBO;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMapExample;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserRoleMapDao;
import com.srct.service.tanya.common.exception.NoSuchUserException;
import com.srct.service.tanya.common.exception.UserAccountLockedException;
import com.srct.service.tanya.common.service.SessionService;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.JSONUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.utils.security.MD5Util;
import com.srct.service.utils.security.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sharp
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    UserRoleMapDao userRoleMapDao;

    @Autowired
    RoleInfoDao roleInfoDao;

    @Autowired
    SessionService sessionService;

    @Autowired
    WechatService wechatService;

    @Override
    public UserInfo updateUser(UserRegReqBO bo) {
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(bo, userInfo);

        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andGuidEqualTo(bo.getGuid());
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        userInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        Integer num = userInfoDao.updateUserInfoByExample(userInfo, example);
        if (num == null || num == 0) {
            throw new ServiceException("更新用户信息失败");
        }
        return userInfo;
    }

    private UserInfo createUser(UserRegReqBO bo) {
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(bo, userInfo);
        userInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        userInfoDao.updateUserInfo(userInfo);
        return userInfo;
    }

    @Override
    public UserLoginRespBO reg(String wechatCode) {

        OpenIdBO openIdBO = wechatService.getOpenId(wechatCode);
        String wechatId = openIdBO.getOpenId();

        UserInfo userInfo;
        try {
            UserRegReqBO bo = new UserRegReqBO();
            bo.setWechatId(wechatId);
            bo.setGuid(RandomUtil.getUUID());
            userInfo = createUser(bo);
        } catch (Exception e) {
            throw new ServiceException("register wechatid " + wechatId + " failed\n" + e.getMessage());
        }

        return getRoleInfo(userInfo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.common.service.UserService#regbyOpenId(java.lang.String)
     */
    @Override
    public UserLoginRespBO regbyOpenId(String openId) {
        UserInfo userInfo;
        try {
            UserRegReqBO bo = new UserRegReqBO();
            bo.setWechatId(openId);
            bo.setGuid(RandomUtil.getUUID());
            userInfo = createUser(bo);
        } catch (Exception e) {
            throw new ServiceException("register wechatid " + openId + " failed\n" + e.getMessage());
        }

        return getRoleInfo(userInfo);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.common.service.UserService#reg(java.lang.String, java.lang.String)
     */
    @Override
    public UserLoginRespBO reg(String name, String password) {

        UserInfo userInfo;
        try {
            UserRegReqBO bo = new UserRegReqBO();
            bo.setUsername(name);
            bo.setPassword(password);
            bo.setGuid(RandomUtil.getUUID());
            userInfo = createUser(bo);
        } catch (Exception e) {
            throw new ServiceException("register user " + name + " failed\n" + e.getMessage());
        }

        return getRoleInfo(userInfo);
    }

    @Override
    public UserLoginRespBO login(String wechatCode) {

        OpenIdBO openIdBO = wechatService.getOpenId(wechatCode);

        String wechatId = openIdBO.getOpenId();
        UserInfo userInfoEx = new UserInfo();
        userInfoEx.setWechatId(wechatId);
        userInfoEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfoEx);

        UserInfo userInfo;
        try {
            userInfo = userInfoList.get(0);
            userInfo.setLastAt(new Date());
            userInfoDao.updateUserInfo(userInfo);
        } catch (Exception e) {
            throw new NoSuchUserException(wechatId);
        }

        return getRoleInfo(userInfo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.common.service.UserService#reg(java.lang.String, java.lang.String)
     */
    @Override
    public UserLoginRespBO login(String name, String password) {

        UserInfo userInfoEx = new UserInfo();
        userInfoEx.setUsername(name);
        userInfoEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfoEx);

        if (userInfoList == null || userInfoList.size() != 1) {
            throw new NoSuchUserException("No such user with name " + name);
        }
        UserInfo userInfo = userInfoList.get(0);
        String guid = userInfo.getGuid();

        if (!userInfo.getState().equals("0")) {
            throw new UserAccountLockedException(guid);
        }

        if (MD5Util.verify(password, userInfo.getPassword())) {
            userInfo.setLastAt(new Date());
            userInfoDao.updateUserInfo(userInfo);
        } else {
            if (!sessionService.retry(guid)) {
                userInfo.setState("1");
                userInfoDao.updateUserInfo(userInfo);
            }
            throw new AccountOrPasswordIncorrectException();
        }

        return getRoleInfo(userInfo);
    }

    private UserLoginRespBO getRoleInfo(UserInfo userInfo) {
        UserLoginRespBO res = new UserLoginRespBO();
        res.setGuid(userInfo.getGuid());
        // res.setSn(String.format("%08d", userInfo.getId()));
        res.setWechatOpenId(userInfo.getWechatId());
        res.setRole(new ArrayList<>());

        Integer userId = userInfo.getId();
        if (userId == null) {
            try {
                userId = userInfoDao.getUserInfoSelective(userInfo).get(0).getId();
            } catch (Exception e) {
                Log.w("no such user");
                throw new NoSuchUserException(JSONUtil.toJSONString(userInfo));
            }
        }
        UserRoleMap userRoleMap = new UserRoleMap();
        userRoleMap.setUserId(userId);
        userRoleMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<UserRoleMap> userRoleList = userRoleMapDao.getUserRoleMapSelective(userRoleMap);

        if (userRoleList == null || userRoleList.size() == 0) {
            Log.i("No Role for user {}", userInfo.getGuid());
        } else {
            userRoleList.forEach(userRole -> {
                if (userRole.getRoleId() != null) {
                    RoleInfo roleInfo = roleInfoDao.getRoleInfobyId(userRole.getRoleId());
                    if (roleInfo.getValid().equals(DataSourceCommonConstant.DATABASE_COMMON_VALID))
                        res.getRole().add(roleInfo);
                }
            });
        }

        return res;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.srct.service.tanya.common.service.UserService#getRole(com.srct.service.tanya.common.datalayer.tanya.entity.
     * UserInfo)
     */
    @Override
    public List<RoleInfo> getRole(UserInfo userInfo) {
        return getRoleInfo(userInfo).getRole();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.common.service.UserService#getUserbyGuid(java.lang.String)
     */
    @Override
    public UserInfo getUserbyGuid(String guid) {
        UserInfo userInfoEx = new UserInfo();
        userInfoEx.setGuid(guid);
        userInfoEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfoEx);

        try {
            return userInfoList.get(0);
        } catch (Exception e) {
            throw new NoSuchUserException("No such user with guid " + guid);
        }
    }

    @Override
    public UserInfo getUserbyEmail(String email) {
        UserInfo userInfoEx = new UserInfo();
        userInfoEx.setEmail(email);
        userInfoEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfoEx);

        try {
            return userInfoList.get(0);
        } catch (Exception e) {
            throw new NoSuchUserException("No such user with email " + email);
        }
    }

    @Override
    public UserInfo getUserbyUserId(Integer userId) {

        UserInfo userInfo = userInfoDao.getUserInfobyId(userId);

        if (userInfo == null)
            throw new NoSuchUserException("No such user with id " + userId);

        return userInfo;
    }

    @Override
    public UserInfo cleanUserPassword(UserInfo userInfo) {
        UserInfo info = userInfoDao.getUserInfobyId(userInfo.getId());
        info.setPassword("");
        return userInfoDao.updateUserInfo(info);
    }

    @Override
    public List<RoleInfo> addRole(UserInfo userInfo, RoleInfo roleInfo) {

        UserRoleMapExample example = new UserRoleMapExample();
        UserRoleMapExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userInfo.getId());

        UserRoleMap userRoleMapEx = new UserRoleMap();
        userRoleMapEx.setUserId(userInfo.getId());
        userRoleMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        UserRoleMap userRoleMap;
        try {
            userRoleMap = userRoleMapDao.getUserRoleMapSelective(userRoleMapEx).get(0);
        } catch (Exception e) {
            Log.i("user id " + userInfo.getId() + " Dont have a role map -- create");
            userRoleMap = new UserRoleMap();
            userRoleMap.setUserId(userInfo.getId());
            userRoleMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        }
        userRoleMap.setRoleId(roleInfo.getId());

        userRoleMapDao.updateUserRoleMap(userRoleMap);

        List<RoleInfo> res = new ArrayList<>();
        List<UserRoleMap> userRoleList = userRoleMapDao.getUserRoleMapSelective(userRoleMapEx);

        userRoleList.forEach(userRole -> {
            if (userRole.getRoleId() != null) {
                RoleInfo role = roleInfoDao.getRoleInfobyId(userRole.getRoleId());
                if (role.getValid().equals(DataSourceCommonConstant.DATABASE_COMMON_VALID))
                    res.add(roleInfo);
            }
        });

        return res;
    }

    @Override
    public List<RoleInfo> removeRole(UserInfo userInfo, RoleInfo roleInfo) {

        UserRoleMapExample example = new UserRoleMapExample();
        UserRoleMapExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userInfo.getId());

        UserRoleMap userRoleMapEx = new UserRoleMap();
        userRoleMapEx.setUserId(userInfo.getId());
        userRoleMapEx.setRoleId(roleInfo.getId());
        userRoleMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        UserRoleMap userRoleMap;
        try {
            userRoleMap = userRoleMapDao.getUserRoleMapSelective(userRoleMapEx).get(0);
        } catch (Exception e) {
            Log.e("user id " + userInfo.getId() + " Dont have role " + roleInfo.getRole());
            throw new ServiceException("user id " + userInfo.getId() + " Dont have role " + roleInfo.getRole());
        }

        userRoleMap.setRoleId(null);

        userRoleMapDao.updateUserRoleMapStrict(userRoleMap);

        List<RoleInfo> res = new ArrayList<>();
        userRoleMapEx.setRoleId(null);
        List<UserRoleMap> userRoleList = userRoleMapDao.getUserRoleMapSelective(userRoleMapEx);

        userRoleList.forEach(userRole -> {
            if (userRole.getRoleId() != null) {
                RoleInfo role = roleInfoDao.getRoleInfobyId(userRole.getRoleId());
                if (role.getValid().equals(DataSourceCommonConstant.DATABASE_COMMON_VALID))
                    res.add(roleInfo);
            }
        });

        return res;
    }

}
