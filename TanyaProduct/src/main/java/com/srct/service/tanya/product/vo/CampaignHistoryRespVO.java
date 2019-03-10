/**
 * Title: CampaignHistoryRespVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-03-04 20:31:03
 */
package com.srct.service.tanya.product.vo;

import java.util.Date;

import com.srct.service.tanya.role.vo.RoleInfoVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class CampaignHistoryRespVO {

    private CampaignHistoryVO campaignHistoryVO;

    @ApiModelProperty(value = "促销活动信息")
    private CampaignInfoVO campaignInfoVO;

    @ApiModelProperty(value = "促销员信息")
    private RoleInfoVO salesmanInfoVO;

    @ApiModelProperty(value = "确认状态")
    private Byte confirmStatus;

    @ApiModelProperty(value = "确认时间")
    private Date confirmAt;
}
