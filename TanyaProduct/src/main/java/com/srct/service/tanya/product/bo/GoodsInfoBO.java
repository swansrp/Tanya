/**
 * Title: CreaterGoodsInfoBO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.bo
 * @author Sharp
 * @date 2019-02-17 21:23:34
 */
package com.srct.service.tanya.product.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Sharp
 *
 */
@Data
public class GoodsInfoBO extends BaseBO {

    @ApiModelProperty(value = "药品Id")
    private Integer id;

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

    @ApiModelProperty(value = "药品价格")
    private Double amount;
}
