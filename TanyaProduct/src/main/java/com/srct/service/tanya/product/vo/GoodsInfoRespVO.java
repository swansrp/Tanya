/**
 * Title: GoodsInfoRespVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-21 21:45:13
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.tanya.role.vo.RoleInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 */
@Data
public class GoodsInfoRespVO {

    @ApiModelProperty(value = "商品信息")
    private GoodsInfoVO goodsInfoVO;

    @ApiModelProperty(value = "商品单位数量")
    private Integer unit;

    @ApiModelProperty(value = "商品活动信息")
    private DiscountInfoVO discountInfoVO;

    @ApiModelProperty(value = "所属渠道")
    private RoleInfoVO merchantRoleInfoVO;

}
