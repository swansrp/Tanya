/**
 * Title: DiscountService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-02-19 13:40:31
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.DiscountInfoReqVO;
import com.srct.service.tanya.product.vo.DiscountInfoRespVO;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;

import java.util.List;

/**
 * @author sharuopeng
 *
 */
public interface DiscountService {

    QueryRespVO<DiscountInfoRespVO> getDiscountInfo(ProductBO<QueryReqVO> discount);

    QueryRespVO<DiscountInfoRespVO> updateDiscountInfo(ProductBO<DiscountInfoReqVO> discount);

    QueryRespVO<DiscountInfoRespVO> confirmDiscountInfo(ProductBO<QueryReqVO> discount);

    QueryRespVO<DiscountInfoRespVO> delDiscountInfo(ProductBO<DiscountInfoReqVO> discount);

    List<DiscountInfo> getDiscountInfoListByGoodsId(Integer goodsId);

}
