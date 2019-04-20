/**
 * Title: DiscountInfoVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 09:13:23
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.vo.QueryReqVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 */
@Data
public class DiscountInfoReqVO extends QueryReqVO {

    private DiscountInfoVO discount;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

}
