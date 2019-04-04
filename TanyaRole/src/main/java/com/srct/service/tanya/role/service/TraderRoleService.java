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
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

import java.util.List;

/**
 * @author sharuopeng
 *
 */
public interface TraderRoleService {

    FactoryInfo getFactoryInfoByTraderInfo(TraderInfo traderInfo);

    FactoryInfo getFactoryInfoByUser(UserInfo userInfo);

    TraderInfo getTraderInfoByUser(UserInfo userInfo);

    TraderFactoryMerchantMap getTraderFactoryMerchantMap(UserInfo userInfo);

    List<TraderInfo> getTraderInfoList(FactoryInfo factoryInfo);

    List<SalesmanInfo> getSalesmanInfoListByTraderInfo(UserInfo userInfo);

    List<SalesmanInfo> getSalesmanInfoListByTraderInfo(TraderInfo traderInfo);

}
