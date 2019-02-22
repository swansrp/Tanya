/**
 * Title: CampaignServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-20 09:32:39
 */
package com.srct.service.tanya.product.service.impl;

import org.springframework.stereotype.Service;

import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.CampaignService;
import com.srct.service.tanya.product.vo.CampaignInfoReqVO;
import com.srct.service.tanya.product.vo.CampaignInfoRespVO;

/**
 * @author sharuopeng
 *
 */
@Service
public class CampaignServiceImpl extends ProductServiceBaseImpl implements CampaignService {

    @Override
    public QueryRespVO<CampaignInfoRespVO> getCampaignInfo(ProductBO<QueryReqVO> camapaign) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QueryRespVO<CampaignInfoRespVO> updateCampaignInfo(ProductBO<CampaignInfoReqVO> camapaign) {
        // TODO Auto-generated method stub
        return null;
    }

}
