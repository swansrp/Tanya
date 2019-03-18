/**
 * Title: PermissionDetailsBO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-3-17 17:34
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.role.bo
 */
package com.srct.service.tanya.role.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PermissionDetailsBO {

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "销售员数量")
    private Integer traderNumber;

    @ApiModelProperty(value = "商品活动数量")
    private Integer discountNumber;

    @ApiModelProperty(value = "促销活动数量")
    private Integer campaignNumber;
}
