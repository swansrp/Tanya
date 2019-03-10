/**
 * Title: DiscountService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-02-19 13:40:31
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.DiscountInfoReqVO;
import com.srct.service.tanya.product.vo.DiscountInfoRespVO;

/**
 * @author sharuopeng
 *
 */
public interface DiscountService {

    /**
     * @param discount
     * @return
     */
    public QueryRespVO<DiscountInfoRespVO> getDiscountInfo(ProductBO<QueryReqVO> discount);

    /**
     * @param discount
     * @return
     */
    public QueryRespVO<DiscountInfoRespVO> updateDiscountInfo(ProductBO<DiscountInfoReqVO> discount);

    /**
     * @param discount
     * @return
     */
    public QueryRespVO<DiscountInfoRespVO> confirmDiscountInfo(ProductBO<QueryReqVO> discount);

    /**
     * @param discount
     * @return
     */
    public QueryRespVO<DiscountInfoRespVO> delDiscountInfo(ProductBO<DiscountInfoReqVO> discount);

}
