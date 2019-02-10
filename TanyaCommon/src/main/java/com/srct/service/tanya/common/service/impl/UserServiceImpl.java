/**
 * @Title: UserServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.service.impl
 * @author Sharp
 * @date 2019-01-30 11:06:53
 */
package com.srct.service.tanya.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#regUser(com.srct.service.tanya.common.vo.user.UserRegReqVO)
     */
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
        return userInfo;
    }

    private UserInfo createUser(UserRegReqBO bo) {
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(bo, userInfo);
        userInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        userInfoDao.updateUserInfo(userInfo);
        return userInfo;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#reg(java.lang.String)
     */
    @Override
    public UserLoginRespBO reg(String wechatCode) {

        OpenIdBO openIdBO = wechatService.getOpenId(wechatCode);
        String wechatId = openIdBO.getOpenId();

        UserInfo userInfo = null;
        try {
            UserRegReqBO bo = new UserRegReqBO();
            bo.setWechatId(wechatId);
            bo.setGuid(RandomUtil.getUUID());
            userInfo = createUser(bo);
        } catch (Exception e) {
            throw new ServiceException("register wechatid " + wechatId + " failed\n" + e.getMessage());
        }

        UserLoginRespBO res = getRoleInfo(userInfo);

        return res;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#regbyOpenId(java.lang.String)
     */
    @Override
    public UserLoginRespBO regbyOpenId(String openId) {
        UserInfo userInfo = null;
        try {
            UserRegReqBO bo = new UserRegReqBO();
            bo.setWechatId(openId);
            bo.setGuid(RandomUtil.getUUID());
            userInfo = createUser(bo);
        } catch (Exception e) {
            throw new ServiceException("register wechatid " + openId + " failed\n" + e.getMessage());
        }

        UserLoginRespBO res = getRoleInfo(userInfo);

        return res;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#reg(java.lang.String, java.lang.String)
     */
    @Override
    public UserLoginRespBO reg(String name, String password) {

        UserInfo userInfo = null;
        try {
            UserRegReqBO bo = new UserRegReqBO();
            bo.setUsername(name);
            bo.setPassword(password);
            bo.setGuid(RandomUtil.getUUID());
            userInfo = createUser(bo);
        } catch (Exception e) {
            throw new ServiceException("register user " + name + " failed\n" + e.getMessage());
        }

        UserLoginRespBO res = getRoleInfo(userInfo);
        return res;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#reg(java.lang.String)
     */
    @Override
    public UserLoginRespBO login(String wechatCode) {

        OpenIdBO openIdBO = wechatService.getOpenId(wechatCode);

        String wechatId = openIdBO.getOpenId();
        UserInfo userInfoEx = new UserInfo();
        userInfoEx.setWechatId(wechatId);
        userInfoEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfoEx);

        UserInfo userInfo = null;
        try {
            userInfo = userInfoList.get(0);
            userInfo.setLastAt(new Date());
            userInfoDao.getUserInfoSelective(userInfo);
        } catch (Exception e) {
            throw new NoSuchUserException(wechatId);
        }

        UserLoginRespBO res = getRoleInfo(userInfo);

        return res;
    }

    /* (non-Javadoc)
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
            userInfoDao.getUserInfoSelective(userInfo);
        } else {
            if (!sessionService.retry(guid)) {
                userInfo.setState("1");
                userInfoDao.updateUserInfo(userInfo);
            }
            throw new AccountOrPasswordIncorrectException();
        }

        UserLoginRespBO res = getRoleInfo(userInfo);
        return res;
    }

    private UserLoginRespBO getRoleInfo(UserInfo userInfo) {
        UserLoginRespBO res = new UserLoginRespBO();
        res.setGuid(userInfo.getGuid());
        // res.setSn(String.format("%08d", userInfo.getId()));
        res.setWechatOpenId(userInfo.getWechatId());
        res.setRole(new ArrayList<RoleInfo>());

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

        userRoleList.forEach(userRole -> {
            RoleInfo roleInfo = roleInfoDao.getRoleInfobyId(userRole.getRoleId());
            if (roleInfo.getValid().equals(DataSourceCommonConstant.DATABASE_COMMON_VALID))
                res.getRole().add(roleInfo);
        });

        return res;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#getRole(com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo)
     */
    @Override
    public List<RoleInfo> getRole(UserInfo userInfo) {
        return getRoleInfo(userInfo).getRole();
    }

    /* (non-Javadoc)
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

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#getUserbyEmail(java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#cleanUserPassword()
     */
    @Override
    public UserInfo cleanUserPassword(UserInfo userInfo) {
        UserInfo info = userInfoDao.getUserInfobyId(userInfo.getId());
        info.setPassword("");
        return userInfoDao.updateUserInfo(info);
    }

}
