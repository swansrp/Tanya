/**
 * Title: ShopService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-02-22 09:27:27
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.ShopInfoReqVO;
import com.srct.service.tanya.product.vo.ShopInfoRespVO;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;

/**
 * @author sharuopeng
 */
public interface ShopService {

    QueryRespVO<ShopInfoRespVO> updateShopInfo(ProductBO<ShopInfoReqVO> req);

    QueryRespVO<ShopInfoRespVO> getShopInfo(ProductBO<QueryReqVO> req);

    QueryRespVO<ShopInfoRespVO> delShopInfo(ProductBO<ShopInfoReqVO> req);

    QueryRespVO<ShopInfoRespVO> bindShopInfo(ProductBO<ShopInfoReqVO> req);

    QueryRespVO<ShopInfoRespVO> getShopBindInfo(ProductBO<QueryReqVO> req);
}
