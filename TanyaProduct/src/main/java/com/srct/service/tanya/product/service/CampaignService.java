/**
 * Title: CampaignService.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author sharuopeng
 * @date 2019-02-19 13:40:41
 */
package com.srct.service.tanya.product.service;

import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.CampaignInfoReqVO;
import com.srct.service.tanya.product.vo.CampaignInfoRespVO;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;

/**
 * @author sharuopeng
 *
 */
public interface CampaignService {

    QueryRespVO<CampaignInfoRespVO> getCampaignInfo(ProductBO<QueryReqVO> campaign);

    QueryRespVO<CampaignInfoRespVO> updateCampaignInfo(ProductBO<CampaignInfoReqVO> campaign);

    QueryRespVO<CampaignInfoRespVO> confirmCampaignInfo(ProductBO<QueryReqVO> campaign);

    QueryRespVO<CampaignInfoRespVO> delCampaignInfo(ProductBO<CampaignInfoReqVO> campaign);

    QueryRespVO<CampaignInfoRespVO> bindCampaignInfoSalesman(ProductBO<CampaignInfoReqVO> campaign);

}
