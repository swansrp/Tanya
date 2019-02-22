/**
 * Title: DiscountInfoRespVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 09:54:26
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
public class DiscountInfoRespVO {

    private DiscountInfoVO discountInfoVO;

    @ApiModelProperty(value = "药厂信息")
    private RoleInfoVO factoryInfoVO;

    @ApiModelProperty(value = "渠道信息")
    private RoleInfoVO merchantInfoVO;

    @ApiModelProperty(value = "商品信息")
    private GoodsInfoVO goodsInfoVO;

    @ApiModelProperty(value = "确认时间")
    private Date confirmAt;
}
