
package com.srct.service.tanya.portal.vo.admin.tanya;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
 
/**
* 实体类
* 根据需要删减无效参数
 * 
* @author: Sharp
* @create: 2019/01/28
 **/
@Data
public class DiscountInfoEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "活动名称")
    private String title;
 
    @ApiModelProperty(value = "角色备注")
    private String comment;
 
    @ApiModelProperty(value = "药厂id")
    private Integer factoryId;
 
    @ApiModelProperty(value = "渠道商id")
    private Integer merchantId;
 
    @ApiModelProperty(value = "商品id")
    private Integer goodsId;
 
    @ApiModelProperty(value = "活动数量")
    private Integer goodsNumber;
 
    @ApiModelProperty(value = "活动金额")
    private Double amount;
 
    @ApiModelProperty(value = "有效期起始")
    private Date startAt;
 
    @ApiModelProperty(value = "有效期结束")
    private Date endAt;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "确认时间")
    private Date confirmAt;
 
    @ApiModelProperty(value = "有效性")
    private Integer valid;
}
