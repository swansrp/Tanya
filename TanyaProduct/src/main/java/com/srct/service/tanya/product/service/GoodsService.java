/**
 * Title: GoodsService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author Sharp
 * @date 2019-02-17 22:27:00
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.GoodsInfoReqVO;
import com.srct.service.tanya.product.vo.GoodsInfoRespVO;

/**
 * @author Sharp
 *
 */
public interface GoodsService {

    public QueryRespVO<GoodsInfoRespVO> updateGoodsInfo(ProductBO<GoodsInfoReqVO> goods);

    public QueryRespVO<GoodsInfoRespVO> getGoodsInfo(ProductBO<QueryReqVO> goods);

    public QueryRespVO<GoodsInfoRespVO> delGoodsInfo(ProductBO<GoodsInfoReqVO> goods);
}
