/**
 * Title: NotificationService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-02-28 18:13:19
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.NotificationInfoReqVO;
import com.srct.service.tanya.product.vo.NotificationInfoRespVO;

/**
 * @author sharuopeng
 *
 */
public interface NotificationService {

    public QueryRespVO<NotificationInfoRespVO> updateNotificationInfo(ProductBO<NotificationInfoReqVO> notification);

    public QueryRespVO<NotificationInfoRespVO> getNotificationInfo(ProductBO<QueryReqVO> notification);

    public QueryRespVO<NotificationInfoRespVO> delNotificationInfo(ProductBO<NotificationInfoReqVO> notification);

}
