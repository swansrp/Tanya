/**
 * Title: TraderRoleService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service
 * @author sharuopeng
 * @date 2019-02-18 19:13:26
 */
package com.srct.service.tanya.role.service;

import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

/**
 * @author sharuopeng
 *
 */
public interface TraderRoleService {

    public FactoryInfo getFacotryInfoByTraderInfo(TraderInfo traderInfo);

    public FactoryInfo getFactoryInfoByUser(UserInfo userInfo);

    public TraderInfo getTraderInfoByUser(UserInfo userInfo);

    public TraderFactoryMerchantMap getTraderFactoryMerchantMap(UserInfo userInfo);

}
