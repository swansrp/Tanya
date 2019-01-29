/**
 * @Title: RoleFilter.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.filter
 * @author Sharp
 * @date 2019-01-29 20:20:14
 */
package com.srct.service.tanya.common.config.filter;

import java.io.IOException;
import java.util.Map;

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

import com.srct.service.tanya.common.service.TokenService;
import com.srct.service.utils.log.Log;

@Configuration
@WebFilter(filterName = "RoleFilter", urlPatterns = "/*")
public class RoleFilter implements Filter {

    @Autowired
    TokenService tokenService;

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
        Map<String, String[]> parmMap = req.getParameterMap();
        Log.i("...RoleFilter doFilter...");
        Log.i("req:" + requestURI);
        Log.i("query:" + queryString);
        Log.ii(parmMap);
        if (requestURI != null && requestURI.contains("elb.check")) {
            return;
        }
        if (parmMap != null && parmMap.containsKey("token")) {
            String token = parmMap.get("token")[0];
            String guid = tokenService.getGuidByToken(token);
            RequestDispatcher rd = req.getRequestDispatcher("/admin" + requestURI);
            req.setAttribute("requestURI", requestURI);
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
