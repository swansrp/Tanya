/**
 * Title: ShiroRealm.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.shiro
 * @author Sharp
 * @date 2019-02-05 21:43:59
 */
package com.srct.service.tanya.common.config.shiro;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.srct.service.bo.wechat.OpenIdBO;
import com.srct.service.exception.ServiceException;
import com.srct.service.service.WechatService;
import com.srct.service.tanya.common.config.shiro.tanya.TanyaAuthToken;
import com.srct.service.tanya.common.config.shiro.utils.MyByteSource;
import com.srct.service.tanya.common.datalayer.tanya.entity.PermissionInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.service.ShiroService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

/**
 * @author Sharp
 *
 */
public class ShiroRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    private ShiroService shiroService;
    private WechatService wechatService;

    public ShiroService getShiroService() {
        if (shiroService == null) {
            shiroService = (ShiroService)BeanUtil.getBean("shiroServiceImpl");
        }
        return shiroService;
    }

    public WechatService getWechatService() {
        if (wechatService == null) {
            wechatService = (WechatService)BeanUtil.getBean("wechatServiceImpl");
        }
        return wechatService;
    }

    /**
     * 验证用户身份
     * 
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {

        logger.info("doGetAuthenticationInfo");

        // 获取用户名密码 第一种方式
        // String username = (String) authenticationToken.getPrincipal();
        // String password = new String((char[]) authenticationToken.getCredentials());

        // 获取用户名 密码 第二种方式
        TanyaAuthToken authToken = (TanyaAuthToken)authenticationToken;
        UserInfo user = null;
        String wechatCode = authToken.getWechatAuthCode();

        SimpleAuthenticationInfo info = null;

        try {
            if (authToken.getWechatAuthCode() != null) {
                String guid = authToken.getGuid();
                String openId = authToken.getWechatOpenId();
                if (guid == null || openId == null) {
                    OpenIdBO bo = getWechatService().getOpenId(wechatCode);
                    guid = user.getGuid();
                    openId = bo.getOpenId();
                    authToken.setGuid(guid);
                    authToken.setWechatOpenId(openId);
                }
                user = getShiroService().findByGuid(guid);
                info = new SimpleAuthenticationInfo(guid, null, getName());
            } else if (authToken.getUsername() != null && authToken.getPassword() != null) {
                String username = authToken.getUsername();
                String password = new String(authToken.getPassword());
                // 从数据库查询用户信息
                user = getShiroService().findByUserName(username);
                authToken.setGuid(user.getGuid());
                info = new SimpleAuthenticationInfo(user.getGuid(), user.getPassword(),
                    new MyByteSource(user.getUsername()), getName());
            } else {
                throw new ServiceException("There is no valid auth info");
            }
        } catch (Exception e) {
            Log.i(e);
            throw new UnknownAccountException(e.getMessage());
        }
        // 可以在这里直接对用户名校验,或者调用 CredentialsMatcher 校验

        // 这里将 密码对比 注销掉,否则 无法锁定 要将密码对比 交给 密码比较器
        // if (!password.equals(user.getPassword())) {
        // throw new IncorrectCredentialsException("用户名或密码错误！");
        // }
        if ("1".equals(user.getState())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }

        return info;
    }

    /**
     * 授权用户权限 授权的方法是在碰到<shiro:hasPermission name=''></shiro:hasPermission>标签的时候调用的
     * 它会去检测shiro框架中的权限(这里的permissions)是否包含有该标签的name值,如果有,里面的内容显示 如果没有,里面的内容不予显示(这就完成了对于权限的认证.)
     *
     * shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo(); 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。
     *
     * 在这个方法中主要是使用类：SimpleAuthorizationInfo 进行角色的添加和权限的添加。 authorizationInfo.addRole(role.getRole());
     * authorizationInfo.addStringPermission(p.getPermission());
     *
     * 当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限 authorizationInfo.setRoles(roles);
     * authorizationInfo.setStringPermissions(stringPermissions);
     *
     * 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "perms[权限添加]"); 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问
     *
     * 如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]"); 就说明访问/add这个链接必须要有 "权限添加"
     * 这个权限和具有 "100002" 这个角色才可以访问
     * 
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        logger.info("doGetAuthorizationInfo");

        // 获取用户
        String guid = (String)SecurityUtils.getSubject().getPrincipal();
        // 从数据库查询用户信息
        UserInfo user = getShiroService().findByGuid(guid);

        // 获取用户角色
        Set<RoleInfo> roles = getShiroService().findRolesByUserGuid(user.getGuid());
        // 添加角色
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (RoleInfo role : roles) {
            authorizationInfo.addRole(role.getRole());
        }

        // 获取用户权限
        Set<PermissionInfo> permissions = getShiroService().findPermissionsByRole(roles);
        // 添加权限
        for (PermissionInfo permission : permissions) {
            authorizationInfo.addStringPermission(permission.getPermission());
        }

        return authorizationInfo;
    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     * 
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     * 
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 清除当前用户的 身份 和 权限 缓存信息
     * 
     * @param principals
     */
    public void clearCurrentUserCachedInfo(PrincipalCollection principals) {
        clearCachedAuthorizationInfo(principals);
        clearCachedAuthenticationInfo(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的 认证缓存 和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
