/**
 * Title: UserAccountLocked.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.exception
 * @author Sharp
 * @date 2019-02-09 11:17:02
 */
package com.srct.service.tanya.common.exception;

import com.srct.service.exception.ServiceException;

/**
 * @author Sharp
 *
 */
public class UserAccountLocked extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = -6775990767233708520L;

    public UserAccountLocked(String msg) {
        super(msg);
    }

}
