/**
 * @Title: ShiroServiceImpl.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.service.impl
 * @author Sharp
 * @date 2019-02-03 17:28:46
 */
package com.srct.service.tanya.common.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.PermissionInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.RolePermissionMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.PermissionInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.RoleInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.RolePermissionMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserRoleMapDao;
import com.srct.service.tanya.common.exception.NoSuchUserException;
import com.srct.service.tanya.common.service.ShiroService;

/**
 * @author Sharp
 *
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    @Lazy
    UserInfoDao userInfoDao;

    @Autowired
    @Lazy
    RoleInfoDao roleInfoDao;

    @Autowired
    @Lazy
    PermissionInfoDao permissionInfoDao;

    @Autowired
    @Lazy
    UserRoleMapDao userRoleMapDao;

    @Autowired
    @Lazy
    RolePermissionMapDao rolePermissionMapDao;

    @Override
    public UserInfo findByUserName(String userName) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userName);
        List<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfo);
        if (userInfoList == null || userInfoList.size() == 0) {
            throw new NoSuchUserException("userName = " + userName);
        }
        if (userInfoList.size() != 1) {
            throw new ServiceException("Multiuser name as " + userName);
        }
        return userInfoList.get(0);
    }

    @Override
    public UserInfo findByOpenId(String openId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setWechatId(openId);
        List<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfo);
        if (userInfoList == null || userInfoList.size() == 0) {
            throw new NoSuchUserException("wechatId = " + openId);
        }
        if (userInfoList.size() != 1) {
            throw new ServiceException("Multiuser wechatId as " + openId);
        }
        return userInfoList.get(0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.srct.service.tanya.common.service.ShiroService#insert(com.srct.service.tanya.common.datalayer.tanya.entity.
     * UserInfo)
     */
    @Override
    public int insert(UserInfo user) {
        return userInfoDao.updateUserInfo(user).getId();
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.ShiroService#update(com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo)
     */
    @Override
    public int update(UserInfo user) {
        return userInfoDao.updateUserInfo(user).getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.common.service.ShiroService#del(java.lang.String)
     */
    @Override
    public int del(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        return userInfoDao.delUserInfo(userInfo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.srct.service.tanya.common.service.ShiroService#findRolesByUser(com.srct.service.tanya.common.datalayer.tanya.
     * entity.UserInfo)
     */
    @Override
    public Set<RoleInfo> findRolesByUserGuid(String guid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setGuid(guid);
        List<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfo);
        if (userInfoList == null || userInfoList.size() == 0) {
            throw new NoSuchUserException("guid = " + guid);
        }
        if (userInfoList.size() != 1) {
            throw new ServiceException("Multiuser guid as " + guid);
        }
        UserRoleMap userRoleMap = new UserRoleMap();
        userRoleMap.setUserId(userInfoList.get(0).getId());
        List<UserRoleMap> userRoleMapList = userRoleMapDao.getUserRoleMapSelective(userRoleMap);

        Set<RoleInfo> roleInfoSet = new HashSet<>();
        for (UserRoleMap map : userRoleMapList) {
            roleInfoSet.add(roleInfoDao.getRoleInfobyId(map.getRoleId()));
        }
        return roleInfoSet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.srct.service.tanya.common.service.ShiroService#findPermissionsByRole(java.util.Set)
     */
    @Override
    public Set<PermissionInfo> findPermissionsByRole(Set<RoleInfo> roles) {
        Set<PermissionInfo> permissionInfoSet = new HashSet<>();
        for (RoleInfo role : roles) {
            RolePermissionMap rolePermissionMap = new RolePermissionMap();
            rolePermissionMap.setRoleId(role.getId());
            List<RolePermissionMap> rolePermissionMaps =
                rolePermissionMapDao.getRolePermissionMapSelective(rolePermissionMap);
            for (RolePermissionMap map : rolePermissionMaps) {
                permissionInfoSet.add(permissionInfoDao.getPermissionInfobyId(map.getPerimissionId()));
            }
        }
        return permissionInfoSet;
    }

    /* (non-Javadoc)
     * @see com.srct.service.tanya.common.service.ShiroService#findByGuid(java.lang.String)
     */
    @Override
    public UserInfo findByGuid(String guid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setGuid(guid);
        List<UserInfo> userInfoList = userInfoDao.getUserInfoSelective(userInfo);
        if (userInfoList == null || userInfoList.size() == 0) {
            throw new NoSuchUserException("guid = " + guid);
        }
        if (userInfoList.size() != 1) {
            throw new ServiceException("Multiuser guid as " + guid);
        }
        return userInfoList.get(0);
    }

}
