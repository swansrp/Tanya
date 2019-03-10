/**
 * Title: CampaignHistoryService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-03-04 20:48:16
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.CampaignHistoryReqVO;
import com.srct.service.tanya.product.vo.CampaignHistoryRespVO;

/**
 * @author sharuopeng
 *
 */
public interface CampaignHistoryService {

    /**
     * @param campaignHistory
     * @return
     */
    public QueryRespVO<CampaignHistoryRespVO> getCampaignHistory(ProductBO<QueryReqVO> campaignHistory);

    /**
     * @param campaignHistory
     * @return
     */
    public QueryRespVO<CampaignHistoryRespVO> updateCampaignHistory(ProductBO<CampaignHistoryReqVO> campaignHistory);

    /**
     * @param campaignHistory
     * @return
     */
    public QueryRespVO<CampaignHistoryRespVO>
        confirmCampaignHistory(ProductBO<QueryReqVO> campaignHistory, Integer rewards);

    /**
     * @param campaignHistory
     * @return
     */
    public QueryRespVO<CampaignHistoryRespVO> delCampaignHistory(ProductBO<CampaignHistoryReqVO> campaignHistory);
}
