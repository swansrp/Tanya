/**
 * Title: FactoryRoleService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service
 * @author sharuopeng
 * @date 2019-02-18 18:25:10
 */
package com.srct.service.tanya.role.service;

import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

/**
 * @author sharuopeng
 *
 */
public interface FactoryRoleService {

    public FactoryMerchantMap getFactoryMerchantMapByFactoryInfo(FactoryInfo factoryInfo);

    public FactoryInfo getFactoryInfoByUser(UserInfo userInfo);
}
