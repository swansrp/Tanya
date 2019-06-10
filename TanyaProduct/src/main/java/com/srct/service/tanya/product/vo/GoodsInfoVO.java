/**
 * Title: GoodsInfoVO.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author Sharp
 * @date 2019-02-17 21:16:57
 */
package com.srct.service.tanya.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 */
@Data
public class GoodsInfoVO {

    @ApiModelProperty(value = "药品Id")
    private Integer id;

    @ApiModelProperty(value = "药品偏码")
    private String code;

    @ApiModelProperty(value = "药品名称")
    private String title;

    @ApiModelProperty(value = "药品说明")
    private String comment;

    @ApiModelProperty(value = "药品图片")
    private String photoUrl;

    @ApiModelProperty(value = "药品生产单位")
    private String production;

    @ApiModelProperty(value = "药品规格")
    private String spec;

    @ApiModelProperty(value = "药品包装")
    private String unit;

    @ApiModelProperty(value = "药品价格")
    private Double amount;

    @ApiModelProperty(value = "所属渠道id")
    private Integer merchantId;

}
