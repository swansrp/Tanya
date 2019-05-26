/**
 * Title: CampaignHistoryVO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-15 23:01
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.cron.vo
 */
package com.srct.service.tanya.cron.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CampaignHistoryVO {

    @ApiModelProperty(value = "促销单号")
    private String campaignHistoryId;

    @ApiModelProperty(value = "促销日期")
    private Date startAt;

    @ApiModelProperty(value = "角色名称")
    private String traderTitle;

    @ApiModelProperty(value = "业务员姓名")
    private String traderUserName;

    @ApiModelProperty(value = "促销员名称")
    private String salesmanTitle;

    @ApiModelProperty(value = "产品名称")
    private String goodsTitle;

    @ApiModelProperty(value = "产品规格")
    private String goodsSpec;

    @ApiModelProperty(value = "生产单位")
    private String goodsProduct;

    @ApiModelProperty(value = "产品数量")
    private Integer goodsNumber;

    /**
     * 确认 拒绝 待审
     */
    @ApiModelProperty(value = "业务员确认状态")
    private String confirmStatus;
}
