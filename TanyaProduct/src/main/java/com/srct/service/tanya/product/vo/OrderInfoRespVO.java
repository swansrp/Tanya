/**
 * Title: OrderInfoVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-18 19:49:45
 */
package com.srct.service.tanya.product.vo;

import java.util.Date;

import com.srct.service.tanya.role.vo.RoleInfoVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@ApiModel(value = "订单", description = "订单详情")
@Data
public class OrderInfoRespVO {

    private OrderInfoVO orderInfoVO;

    @ApiModelProperty(value = "药厂确认时间")
    private Date factoryConfirmAt;

    @ApiModelProperty(value = "渠道确认时间")
    private Date merchantConfirmAt;

    @ApiModelProperty(value = "药店信息")
    private ShopInfoVO shopInfoVO;

    @ApiModelProperty(value = "商品信息")
    private GoodsInfoVO goodsInfoVO;

    @ApiModelProperty(value = "活动信息")
    private DiscountInfoVO discountInfoVO;

    @ApiModelProperty(value = "渠道信息")
    private RoleInfoVO merchantInfoVO;

    @ApiModelProperty(value = "药厂信息")
    private RoleInfoVO factoryInfoVO;

    @ApiModelProperty(value = "销售员信息")
    private RoleInfoVO traderInfoVO;

}