/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.config.response 
 * @author: Sharp   
 * @date: 2019/01/18
 */
package com.srct.service.tanya.common.config.response;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.utils.log.Log;

@RestControllerAdvice
public class TanyaExceptionHandler extends CommonExceptionHandler {

    /**
     * 全局异常捕捉处理
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonResponse.Resp> errorHandler(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String msg = sw.toString();
        Log.i(msg);
        CommonResponse res = new CommonResponse(TanyaResponseConstant.SERVER_ERROR, ex.getMessage());
        return res.getEntity();
    }
    
    /**
     * return response with data,if data is null,return no data message,or
     * return data
     *
     * @param data
     * @return
     */
    @ResponseBody
    public static ResponseEntity<CommonResponse.Resp> generateResponse(Object data) {
        CommonResponse res = new CommonResponse(TanyaResponseConstant.SUCCESS, data);
        return res.getEntity();
    }
}
