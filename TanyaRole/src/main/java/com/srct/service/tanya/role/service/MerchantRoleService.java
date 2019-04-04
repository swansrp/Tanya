/**
 * Title: MerchantRoleService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service
 * @author sharuopeng
 * @date 2019-02-24 20:54:18
 */
package com.srct.service.tanya.role.service;

import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

/**
 * @author sharuopeng
 *
 */
public interface MerchantRoleService {
    MerchantInfo getMerchantInfoByUser(UserInfo userInfo);
}
