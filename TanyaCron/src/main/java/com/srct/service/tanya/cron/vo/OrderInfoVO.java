/**
 * Title: OrderInfoVO
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
public class OrderInfoVO {

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "订单日期")
    private Date startAt;

    @ApiModelProperty(value = "角色名称")
    private String traderTitle;

    @ApiModelProperty(value = "业务员姓名")
    private String traderUserName;

    @ApiModelProperty(value = "客户名称")
    private String shopTitle;

    @ApiModelProperty(value = "产品名称")
    private String goodsTitle;

    @ApiModelProperty(value = "产品规格")
    private String goodsSpec;

    @ApiModelProperty(value = "生产厂家")
    private String goodsProduct;

    @ApiModelProperty(value = "产品单价")
    private Double goodsPrice;

    @ApiModelProperty(value = "提交数量")
    private Integer goodsNumber;

    /**
     * 确认 拒绝 待审
     */
    @ApiModelProperty(value = "经理确认状态")
    private String factoryConfirmStatus;

    /**
     * 确认 拒绝 待审
     */
    @ApiModelProperty(value = "商业确认状态")
    private String merchantConfirmStatus;

    /**
     * 待审为空
     */
    @ApiModelProperty(value = "商业确认数量")
    private Integer merchantConfirmNumber;

    @ApiModelProperty(value = "商业名称")
    private String merchantTitle;
}
