/**
 * Title: OrderInfoVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 10:59:21
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
public class OrderInfoVO {
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "订单名称")
    private String title;

    @ApiModelProperty(value = "订单备注")
    private String comment;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "订单金额")
    private Double amount;

    @ApiModelProperty(value = "有效期起始")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    private Date endAt;
}
