/**
 * Title: CampaignService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-02-19 13:40:41
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.CampaignInfoReqVO;
import com.srct.service.tanya.product.vo.CampaignInfoRespVO;

/**
 * @author sharuopeng
 *
 */
public interface CampaignService {

    /**
     * @param order
     * @return
     */
    QueryRespVO<CampaignInfoRespVO> getCampaignInfo(ProductBO<QueryReqVO> campaign);

    /**
     * @param order
     * @return
     */
    QueryRespVO<CampaignInfoRespVO> updateCampaignInfo(ProductBO<CampaignInfoReqVO> campaign);

    /**
     * @param campaign
     * @return
     */
    QueryRespVO<CampaignInfoRespVO> confirmCampaignInfo(ProductBO<QueryReqVO> campaign);

}
