/**
 * @Title: RoleFilter.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.filter
 * @author Sharp
 * @date 2019-01-29 20:20:14
 */
package com.srct.service.tanya.common.config.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.service.TokenService;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.utils.log.Log;

@Configuration
@WebFilter(filterName = "RoleFilter", urlPatterns = "/*")
public class RoleFilter implements Filter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        Log.i("...RoleFilter init...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        String requestURI = req.getRequestURI();
        String queryString = req.getQueryString();
        Log.d("...RoleFilter doFilter...");
        Log.d("req:" + requestURI);
        Log.d("query:" + queryString);
        if (requestURI != null && requestURI.contains("elb.check")) {
            return;
        }
        String guid = null;
        Object token = req.getSession().getAttribute("AuthToken");
        if (token != null) {
            Log.i(token.toString());
            guid = tokenService.getGuidByToken(token.toString());
        }
        if (guid == null || guid.length() == 0) {
            Log.d("token invalid or expired, please re-login");
        } else {
            RequestDispatcher rd = req.getRequestDispatcher(requestURI);
            UserInfo userInfo = new UserInfo();
            userInfo.setGuid(guid);
            List<String> roles = userService.getRole(userInfo);
            req.setAttribute("role", roles);
            req.setAttribute("guid", guid);
            rd.forward(req, resp);
            return;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        Log.d("...RoleFilter destroy...");
    }
}
