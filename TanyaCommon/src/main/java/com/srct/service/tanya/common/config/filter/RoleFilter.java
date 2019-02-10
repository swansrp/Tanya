/**
 * @Title: RoleFilter.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.filter
 * @author Sharp
 * @date 2019-01-29 20:20:14
 */
package com.srct.service.tanya.common.config.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.service.SessionService;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.utils.log.Log;

public class RoleFilter implements Filter {

    /**
     * 封装，不需要过滤的list列表
     */
    protected static List<String> patterns = new ArrayList<String>();

    /**
     * 封装，必须有身份list列表
     */
    protected static List<String> rolePatterns = new ArrayList<String>();

    @Autowired
    SessionService tokenService;

    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        Log.i("...RoleFilter init...");
        String ignoresParam = config.getInitParameter("exclusions");
        if (StringUtils.isNotEmpty(ignoresParam)) {
            String prefixIgnores[] = ignoresParam.split(",");
            for (String ignore : prefixIgnores) {
                patterns.add(ignore);
            }
        }

        String roleRequiredPaths = config.getInitParameter("roleRequired");
        if (StringUtils.isNotEmpty(roleRequiredPaths)) {
            String prefixRoleRequired[] = roleRequiredPaths.split(",");
            for (String path : prefixRoleRequired) {
                rolePatterns.add(path);
            }
        }
        return;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        String guid = null;
        String token = req.getHeader("x-access-token");
        if (token != null) {
            guid = tokenService.getGuidByToken(token.toString());
            req.setAttribute("guid", guid);
        }

        String requestURI = req.getRequestURI();
        String queryString = req.getQueryString();
        Log.i("req: {}", requestURI);
        Log.i("query: {}", queryString);
        // Log.i(req.getServletPath());
        // Log.i(req.getCharacterEncoding());
        // Log.i(req.getContentType());
        // Log.i(req.getMethod());

        if (requestURI != null && requestURI.contains("elb.check")) {
            return;
        }
        String url = req.getRequestURI().substring(req.getContextPath().length());

        boolean isIgonrePath = isInclude(url, patterns);
        boolean isRoleRequiredPath = isInclude(url, rolePatterns);
        if (isIgonrePath) {
            Log.i("Dont need check role {}", url);
            chain.doFilter(request, response);
            return;
        }

        if (guid == null || guid.length() == 0) {
            Log.i("token invalid or expired, please re-login");
            RequestDispatcher rd = req.getRequestDispatcher("/unlogin");
            rd.forward(request, response);
            return;
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setGuid(guid);
            List<RoleInfo> roles = userService.getRole(userInfo);
            if (roles != null && roles.size() > 0) {
                req.setAttribute("role", roles.get(0));
            } else if (isRoleRequiredPath) {
                RequestDispatcher rd = req.getRequestDispatcher("/norole");
                rd.forward(request, response);
                return;
            }
        }
        chain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {
        Log.d("...RoleFilter destroy...");
    }

    /**
     * 是否需要过滤
     * 
     * @param url
     * @return
     */
    private boolean isInclude(String url, List<String> patternList) {
        for (String pattern : patternList) {
            if (PathMatcherUtil.matches(pattern, url)) {
                return true;
            }
        }
        return false;
    }
}
