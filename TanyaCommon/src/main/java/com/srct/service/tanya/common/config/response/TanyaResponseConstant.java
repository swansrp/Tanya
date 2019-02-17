/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.config.response
 * @author: srct
 * @date: 2019/01/29
 */
package com.srct.service.tanya.common.config.response;

import org.springframework.http.HttpStatus;

import com.srct.service.config.response.CommonResponseCode;
import com.srct.service.config.response.CommonResponseConstant;

public class TanyaResponseConstant extends CommonResponseConstant {

    public static final String TAG = "TYA";

    public static final CommonResponseCode SUCCESS = new CommonResponseCode(TAG + "0000", "Success", HttpStatus.OK);

    public static final CommonResponseCode NO_SUCH_USER =
        new CommonResponseCode(TAG + "1000", "NO_SUCH_USER", HttpStatus.OK);

    public static final CommonResponseCode USER_LOCKED =
        new CommonResponseCode(TAG + "1001", "USER_LOCKED", HttpStatus.OK);

    public static final CommonResponseCode USER_NOT_IN_ROLE =
        new CommonResponseCode(TAG + "1002", "USER_DONT_HAVE_ROLE", HttpStatus.OK);

    public static final CommonResponseCode TOUCH_TRADER_NUMBER_LIMIT =
        new CommonResponseCode(TAG + "1003", "TOUCH_TRADER_NUMBER_LIMIT", HttpStatus.OK);
}