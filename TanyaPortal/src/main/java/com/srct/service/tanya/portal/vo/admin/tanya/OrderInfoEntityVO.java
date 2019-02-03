
package com.srct.service.tanya.portal.vo.admin.tanya;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
 
/**
* 实体类
* 根据需要删减无效参数
 * 
* @author: srct
* @create: 2019/02/03
 **/
@Data
public class OrderInfoEntityVO {
 
    @ApiModelProperty(value = "")
    private Integer id;
 
    @ApiModelProperty(value = "订单名称")
    private String title;
 
    @ApiModelProperty(value = "角色备注")
    private String comment;
 
    @ApiModelProperty(value = "发起销售员id")
    private Integer traderId;
 
    @ApiModelProperty(value = "发起药厂id")
    private Integer factoryId;
 
    @ApiModelProperty(value = "渠道id")
    private Integer merchantId;
 
    @ApiModelProperty(value = "目标药店")
    private Integer shopId;
 
    @ApiModelProperty(value = "商品id")
    private Integer goodsId;
 
    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;
 
    @ApiModelProperty(value = "订单金额")
    private Double amount;
 
    @ApiModelProperty(value = "参加活动id")
    private Integer campaignId;
 
    @ApiModelProperty(value = "有效期起始")
    private Date startAt;
 
    @ApiModelProperty(value = "有效期结束")
    private Date endAt;
 
    @ApiModelProperty(value = "创建时间")
    private Date createAt;
 
    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;
 
    @ApiModelProperty(value = "药厂确认时间")
    private Date factoryConfirmAt;
 
    @ApiModelProperty(value = "渠道确认时间")
    private Date merchantConfirmAt;
 
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
