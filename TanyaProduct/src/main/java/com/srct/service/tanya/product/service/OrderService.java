/**
 * Title: OrderService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-02-19 13:40:20
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.OrderInfoReqVO;
import com.srct.service.tanya.product.vo.OrderInfoRespVO;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;

import java.util.List;

/**
 * @author sharuopeng
 */
public interface OrderService {

    QueryRespVO<OrderInfoRespVO> confirmOrderInfo(ProductBO<QueryReqVO> order);

    QueryRespVO<OrderInfoRespVO> delOrderInfo(ProductBO<OrderInfoReqVO> order);

    QueryRespVO<OrderInfoRespVO> getOrderInfo(ProductBO<QueryReqVO> order);

    List<OrderInfo> summaryOrderInfo(ProductBO<QueryReqVO> req);

    QueryRespVO<OrderInfoRespVO> updateOrderInfo(ProductBO<OrderInfoReqVO> order);
}
