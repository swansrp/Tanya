/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.common.config.response 
 * @author: Sharp   
 * @date: 2019/01/28
 */
package com.srct.service.tanya.common.config.response;

import org.springframework.http.HttpStatus;

import com.srct.service.config.response.CommonResponseCode;
import com.srct.service.config.response.CommonResponseConstant;

public class TanyaResponseConstant extends CommonResponseConstant {
	public static final String TAG = "TYA";

	public static final CommonResponseCode SUCCESS = new CommonResponseCode(TAG+"0000", "Success", HttpStatus.OK);

	public static final CommonResponseCode SERVER_ERROR = new CommonResponseCode(TAG+"0001", "Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

	public static final CommonResponseCode DB_ERROR = new CommonResponseCode(TAG+"0002", "DB Error", HttpStatus.SERVICE_UNAVAILABLE);

	public static final CommonResponseCode PERMISSION_DENIED_ERROR = new CommonResponseCode(TAG+"0003", "Access denied", HttpStatus.FORBIDDEN);
}