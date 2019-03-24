/**
 * Title: CampaignHistoryVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo.backup
 * @author sharuopeng
 * @date 2019-03-04 20:31:35
 */
package com.srct.service.tanya.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 */
@Data
public class CampaignHistoryVO {

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "对应促销活动id")
    private Integer campaignId;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "图片地址")
    private String url;

    @ApiModelProperty(value = "促销数量")
    private Integer number;

    @ApiModelProperty(value = "获得积分")
    private Integer rewards;

}
