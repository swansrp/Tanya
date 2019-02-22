/**
 * Title: ShopService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-02-22 09:27:27
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.ShopInfoReqVO;
import com.srct.service.tanya.product.vo.ShopInfoRespVO;

/**
 * @author sharuopeng
 *
 */
public interface ShopService {

    /**
     * @param order
     * @return
     */
    QueryRespVO<ShopInfoRespVO> getShopInfo(ProductBO<QueryReqVO> shop);

    /**
     * @param order
     * @return
     */
    QueryRespVO<ShopInfoRespVO> updateShopInfo(ProductBO<ShopInfoReqVO> shop);
}
