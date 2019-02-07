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
import com.srct.service.service.WechatService;
import com.srct.service.tanya.common.bo.user.UserLoginRespBO;
import com.srct.service.tanya.common.bo.user.UserRegReqBO;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserRoleMapDao;
import com.srct.service.tanya.common.exception.NoSuchUserException;
import com.srct.service.tanya.common.service.TokenService;
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
    TokenService tokenService;

    @Autowired
    WechatService wechatService;

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#regUser(com.srct.service.tanya.common.vo.user.UserRegReqVO)
     */
    @Override
    public UserInfo updateUser(UserRegReqBO bo) {
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
            Log.i("No such user with wechat code {}", wechatCode);
            UserRegReqBO bo = new UserRegReqBO();
            bo.setWechatId(wechatId);
            bo.setGuid(RandomUtil.getUUID());
            userInfo = updateUser(bo);
        }

        UserLoginRespBO res = getRoleInfo(userInfo);

        return res;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#reg(java.lang.String, java.lang.String)
     */
    @Override
    public UserLoginRespBO reg(String name, String password) {

        UserInfo userInfoEx = new UserInfo();
        userInfoEx.setName(name);
        userInfoEx.setPassword(password);
        userInfoEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfoEx);

        UserInfo userInfo = null;
        try {
            userInfo = userInfoList.get(0);
            userInfo.setLastAt(new Date());
            userInfoDao.getUserInfoSelective(userInfo);
        } catch (Exception e) {
            Log.i("No such user with name-password {} {}", name, MD5Util.MD5(password));
            UserRegReqBO bo = new UserRegReqBO();
            bo.setName(name);
            bo.setPassword(password);
            bo.setGuid(RandomUtil.getUUID());
            userInfo = updateUser(bo);
        }

        UserLoginRespBO res = getRoleInfo(userInfo);
        return res;
    }

    private UserLoginRespBO getRoleInfo(UserInfo userInfo) {
        UserLoginRespBO res = new UserLoginRespBO();
        res.setGuid(userInfo.getGuid());
        res.setSn(String.format("%08d", userInfo.getId()));
        res.setRole(new ArrayList<String>());

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
                res.getRole().add(roleInfo.getRole());
        });

        return res;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.UserService#getRole(com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo)
     */
    @Override
    public List<String> getRole(UserInfo userInfo) {
        return getRoleInfo(userInfo).getRole();
    }

}
