/**
 * Title: CampaignHistoryReqVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-03-04 20:31:23
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.tanya.common.vo.QueryReqVO;

import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class CampaignHistoryReqVO extends QueryReqVO {

    private CampaignHistoryVO campaignHistory;

    private Integer salesmanId;

}
