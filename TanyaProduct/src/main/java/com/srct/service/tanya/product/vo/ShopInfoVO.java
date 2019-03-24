/**
 * Title: ShopInfoVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-21 18:49:16
 */
package com.srct.service.tanya.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 */
@Data
public class ShopInfoVO {

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "药店名称")
    private String title;

    @ApiModelProperty(value = "药店备注")
    private String comment;

}
