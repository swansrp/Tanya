/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved. @Project Name: Tanya @Package:
 * com.srct.service.tanya.common.config.response
 *
 * @author: srct
 * @date: 2019/01/29
 */
package com.srct.service.tanya.common.config.response;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.exception.ServiceException;
import com.srct.service.exception.UserNotLoginException;
import com.srct.service.tanya.common.exception.GoodsNumberLimitException;
import com.srct.service.tanya.common.exception.NoSuchUserException;
import com.srct.service.tanya.common.exception.TraderNumberLimitException;
import com.srct.service.tanya.common.exception.UserAccountLockedException;
import com.srct.service.tanya.common.exception.UserNotInRoleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class TanyaExceptionHandler {

    private static final Logger mLogger = LoggerFactory.getLogger(TanyaExceptionHandler.class);

    @ResponseBody
    public static <T> ResponseEntity<CommonResponse<T>.Resp> generateResponse(T data) {
        CommonResponse<T> res = new CommonResponse<>(TanyaResponseConstant.SUCCESS, data);
        return res.getEntity();
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonResponse<String>.Resp> errorHandler(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        int endIndex = sw.toString().indexOf("\n", 500);
        String msg = sw.toString().substring(0, endIndex);
        // mLogger.info(msg);
        CommonResponse<String> res = new CommonResponse<>(TanyaResponseConstant.SERVER_ERROR, ex.getMessage());
        return res.getEntity();
    }

    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<CommonResponse<String>.Resp> errorHandler(ServiceException ex) {
        mLogger.info("Error Information {}", ex.getMessage());
        CommonResponse<String> res = new CommonResponse<>(TanyaResponseConstant.EXCEPTION_ERROR, ex.getMessage());
        return res.getEntity();
    }

    @ResponseBody
    @ExceptionHandler(value = NoSuchUserException.class)
    public ResponseEntity<CommonResponse<String>.Resp> errorHandler(NoSuchUserException ex) {
        mLogger.info("NoSuchUserException {}", ex.getMessage());
        CommonResponse<String> res = new CommonResponse<>(TanyaResponseConstant.NO_SUCH_USER, ex.getMessage());
        return res.getEntity();
    }

    @ResponseBody
    @ExceptionHandler(value = UserNotLoginException.class)
    public ResponseEntity<CommonResponse<String>.Resp> errorHandler(UserNotLoginException ex) {
        mLogger.info("UserNotLoginException {}", ex.getMessage());
        CommonResponse<String> res = new CommonResponse<>(TanyaResponseConstant.USER_NOT_LOGIN_ERROR, ex.getMessage());
        return res.getEntity();
    }

    @ResponseBody
    @ExceptionHandler(value = UserAccountLockedException.class)
    public ResponseEntity<CommonResponse<String>.Resp> errorHandler(UserAccountLockedException ex) {
        mLogger.info("UserAccountLocked {}", ex.getMessage());
        CommonResponse<String> res = new CommonResponse<>(TanyaResponseConstant.USER_LOCKED, ex.getMessage());
        return res.getEntity();
    }

    @ResponseBody
    @ExceptionHandler(value = UserNotInRoleException.class)
    public ResponseEntity<CommonResponse<String>.Resp> errorHandler(UserNotInRoleException ex) {
        mLogger.info("UserNotInRoleException {}", ex.getMessage());
        CommonResponse<String> res = new CommonResponse<>(TanyaResponseConstant.USER_NOT_IN_ROLE, ex.getMessage());
        return res.getEntity();
    }

    @ResponseBody
    @ExceptionHandler(value = TraderNumberLimitException.class)
    public ResponseEntity<CommonResponse<String>.Resp> errorHandler(TraderNumberLimitException ex) {
        mLogger.info("UserAccountLocked {}", ex.getMessage());
        CommonResponse<String> res =
                new CommonResponse<>(TanyaResponseConstant.TOUCH_TRADER_NUMBER_LIMIT, ex.getMessage());
        return res.getEntity();
    }

    @ResponseBody
    @ExceptionHandler(value = GoodsNumberLimitException.class)
    public ResponseEntity<CommonResponse<String>.Resp> errorHandler(GoodsNumberLimitException ex) {
        mLogger.info("UserAccountLocked {}", ex.getMessage());
        CommonResponse<String> res =
                new CommonResponse<>(TanyaResponseConstant.TOUCH_GOODS_NUMBER_LIMIT, ex.getMessage());
        return res.getEntity();
    }
}
