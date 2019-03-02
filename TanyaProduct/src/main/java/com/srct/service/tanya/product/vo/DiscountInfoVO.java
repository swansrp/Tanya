/**
 * Title: DiscountInfoVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 10:32:51
 */
package com.srct.service.tanya.product.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class DiscountInfoVO {

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endAt;
}
