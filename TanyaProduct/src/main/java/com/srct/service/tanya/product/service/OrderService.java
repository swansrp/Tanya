/**
 * Title: OrderService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-02-19 13:40:20
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.OrderInfoReqVO;
import com.srct.service.tanya.product.vo.OrderInfoRespVO;

/**
 * @author sharuopeng
 *
 */
public interface OrderService {

    /**
     * @param order
     * @return
     */
    QueryRespVO<OrderInfoRespVO> getOrderInfo(ProductBO<QueryReqVO> order);

    /**
     * @param order
     * @return
     */
    QueryRespVO<OrderInfoRespVO> updateOrderInfo(ProductBO<OrderInfoReqVO> order);

}