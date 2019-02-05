/**
 * Title: PrincipalIdNullException.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.shiro.exception
 * @author Sharp
 * @date 2019-02-05 21:37:45
 */
package com.srct.service.tanya.common.config.shiro.exception;

/**
 * @author Sharp
 *
 */
public class PrincipalIdNullException extends RuntimeException {

    private static final String MESSAGE = "Principal Id shouldn't be null!";

    public PrincipalIdNullException(Class clazz, String idMethodName) {
        super(clazz + " id field: " + idMethodName + ", value is null\n" + MESSAGE);
    }
}
