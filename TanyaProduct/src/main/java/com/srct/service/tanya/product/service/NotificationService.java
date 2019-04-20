/**
 * Title: NotificationService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-02-28 18:13:19
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.NotificationInfoReqVO;
import com.srct.service.tanya.product.vo.NotificationInfoRespVO;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;

/**
 * @author sharuopeng
 */
public interface NotificationService {

    QueryRespVO<NotificationInfoRespVO> updateNotificationInfo(ProductBO<NotificationInfoReqVO> notification);

    QueryRespVO<NotificationInfoRespVO> getNotificationInfo(ProductBO<QueryReqVO> notification);

    QueryRespVO<NotificationInfoRespVO> delNotificationInfo(ProductBO<NotificationInfoReqVO> notification);

}
