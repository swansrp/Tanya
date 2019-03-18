/**
 * Title: SalesmanRoleService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaRole
 * @Package: com.srct.service.tanya.role.service
 * @author sharuopeng
 * @date 2019-02-23 22:50:02
 */
package com.srct.service.tanya.role.service;

import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;

import java.util.List;

/**
 * @author sharuopeng
 *
 */

public interface SalesmanRoleService {

    SalesmanInfo getSalesmanInfoByUser(UserInfo userInfo);

    List<TraderInfo> getTraderInfoByUser(UserInfo userInfo);

    List<SalesmanTraderMap> getSalesmanTraderMap(UserInfo userInfo);

}
