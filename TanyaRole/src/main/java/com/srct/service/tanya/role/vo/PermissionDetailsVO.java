/**
 * Title: PermissionDetailsVO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-3-17 17:52
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.role.vo
 */
package com.srct.service.tanya.role.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PermissionDetailsVO {
    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "下属销售员数量")
    private Integer traderNumber;

    @ApiModelProperty(value = "商品活动数量")
    private Integer discountNumber;

    @ApiModelProperty(value = "促销活动数量")
    private Integer campaignNumber;

    @ApiModelProperty(value = "下属工厂数量")
    private Integer factoryNumber;

    @ApiModelProperty(value = "是否签约")
    private Byte sign;
}
