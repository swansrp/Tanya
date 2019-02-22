/**
 * Title: DiscountInfoBO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.bo
 * @author sharuopeng
 * @date 2019-02-19 10:58:11
 */
package com.srct.service.tanya.product.bo.bakcup;

import java.util.Date;

import com.srct.service.tanya.product.bo.BaseBO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class DiscountInfoBO extends BaseBO {

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "销售活动名称")
    private String title;

    @ApiModelProperty(value = "销售活动备注")
    private String comment;

    @ApiModelProperty(value = "活动数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "活动金额")
    private Double amount;

    @ApiModelProperty(value = "有效期起始")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    private Date endAt;

    @ApiModelProperty(value = "所属工厂id")
    private Integer factoryId;
}
