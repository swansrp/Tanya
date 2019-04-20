/**
 * Title: CampaignHistoryService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-03-04 20:48:16
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.CampaignHistoryReqVO;
import com.srct.service.tanya.product.vo.CampaignHistoryRespVO;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;

/**
 * @author sharuopeng
 */
public interface CampaignHistoryService {

    QueryRespVO<CampaignHistoryRespVO> getCampaignHistory(ProductBO<QueryReqVO> campaignHistory);

    QueryRespVO<CampaignHistoryRespVO> updateCampaignHistory(ProductBO<CampaignHistoryReqVO> campaignHistory);

    QueryRespVO<CampaignHistoryRespVO> confirmCampaignHistory(ProductBO<QueryReqVO> campaignHistory, Integer rewards);

    QueryRespVO<CampaignHistoryRespVO> delCampaignHistory(ProductBO<CampaignHistoryReqVO> campaignHistory);
}
