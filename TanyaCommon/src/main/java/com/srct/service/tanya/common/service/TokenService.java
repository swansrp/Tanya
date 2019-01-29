/**  
* @Title: TokenService.java 
* Copyright (c) 2019 Sharp. All rights reserved.
* @Project Name: TanyaCommon
* @Package: com.srct.service.tanya.common.service
* @author Sharp
* @date 2019-01-29 20:41:54   
*/  
package com.srct.service.tanya.common.service;

/**
 * @author Sharp
 *
 */
public interface TokenService {
    public String genToken(String guid);
    
    public String getGuidByToken(String token);
}
