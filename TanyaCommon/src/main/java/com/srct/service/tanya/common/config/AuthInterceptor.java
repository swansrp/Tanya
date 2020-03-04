package com.srct.service.tanya.common.config;

import com.srct.service.config.annotation.Auth;
import com.srct.service.config.holder.ClientTypeHolder;
import com.srct.service.config.holder.TokenHolder;
import com.srct.service.config.security.AuthBaseInterceptor;
import com.srct.service.tanya.common.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Title: AuthInterceptor
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2020/3/4 15:43
 * @description Project Name: Tanya
 * @Package: com.srct.service.tanya.common.service
 */
@Component
public class AuthInterceptor extends AuthBaseInterceptor {

    @Autowired
    private AuthTokenService authTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class clazz = handlerMethod.getBeanType();
        Auth.AuthType authType = Auth.AuthType.NONE;

        if (method.isAnnotationPresent(Auth.class)) {
            Auth auth = method.getAnnotation(Auth.class);
            authType = auth.role();
        } else if (clazz.isAnnotationPresent(Auth.class)) {
            Auth auth = (Auth) clazz.getDeclaredAnnotation(Auth.class);
            authType = auth.role();
        }

        authTokenService.validate(request, response, authType);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        TokenHolder.remove();
        ClientTypeHolder.remove();
        super.postHandle(request, response, handler, modelAndView);
    }
}
