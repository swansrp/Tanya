/**
 * Title: CampaignInfoVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 10:33:09
 */
package com.srct.service.tanya.product.vo;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class CampaignInfoVO {
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "促销活动名称")
    private String title;

    @ApiModelProperty(value = "促销活动备注")
    private String comment;

    @ApiModelProperty(value = "有效期起始")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    private Date endAt;
}