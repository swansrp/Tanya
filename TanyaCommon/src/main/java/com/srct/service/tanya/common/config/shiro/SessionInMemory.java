/**
 * Title: SessionInMemory.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaCommon
 * @Package: com.srct.service.tanya.common.config.shiro
 * @author Sharp
 * @date 2019-02-05 21:35:46
 */
package com.srct.service.tanya.common.config.shiro;

import java.util.Date;

import org.apache.shiro.session.Session;

/**
 * @author Sharp
 *
 */
public class SessionInMemory {
    private Session session;
    private Date createTime;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
