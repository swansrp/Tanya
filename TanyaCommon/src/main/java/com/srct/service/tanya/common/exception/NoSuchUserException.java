/**
 * @Title: NoSuchUserException.java Copyright (c) 2019 Sharp. All rights reserved.
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.exception
 * @author Sharp
 * @date 2019-01-30 14:47:16
 */
package com.srct.service.tanya.common.exception;

import com.srct.service.exception.ServiceException;

/**
 * @author Sharp
 *
 */
public class NoSuchUserException extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = -2168019872659571721L;

    public NoSuchUserException(String msg) {
        super(msg);
    }

}
