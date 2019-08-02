/**
 * Title: GoodsService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author Sharp
 * @date 2019-02-17 22:27:00
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.bo.UploadProductBO;
import com.srct.service.tanya.product.vo.GoodsInfoReqVO;
import com.srct.service.tanya.product.vo.GoodsInfoRespVO;
import com.srct.service.tanya.product.vo.GoodsSummaryVO;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;

/**
 * @author Sharp
 */
public interface GoodsService {

    QueryRespVO<GoodsInfoRespVO> bindGoodsInfo(ProductBO<GoodsInfoReqVO> req);

    QueryRespVO<GoodsInfoRespVO> delGoodsInfo(ProductBO<GoodsInfoReqVO> req);

    QueryRespVO<GoodsInfoRespVO> getGoodsBindInfo(ProductBO<QueryReqVO> req);

    QueryRespVO<GoodsInfoRespVO> getGoodsInfo(ProductBO<QueryReqVO> req);

    QueryRespVO<GoodsInfoRespVO> getGoodsInfoWithDiscount(ProductBO<QueryReqVO> req);

    GoodsSummaryVO summaryGoodsInfo(ProductBO<QueryReqVO> req);

    QueryRespVO<GoodsInfoRespVO> updateGoodsInfo(ProductBO<GoodsInfoReqVO> req);

    void uploadGoodsInfoVO(UploadProductBO req);
}
