
package com.srct.service.tanya.portal.vo.admin.tanya;

import com.srct.service.vo.QueryReqVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
 
/**
* 实体类
* 根据需要删减无效参数
 * 
* @author: sharuopeng
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderInfoEntityVO extends QueryReqVO {

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "订单名称")
    private String title;

    @ApiModelProperty(value = "角色备注")
    private String comment;

    @ApiModelProperty(value = "流通渠道id")
    private Integer traderFactoryMerchantId;

    @ApiModelProperty(value = "营销员id")
    private Integer traderId;

    @ApiModelProperty(value = "厂商渠道id")
    private Integer factoryMerchantId;

    @ApiModelProperty(value = "目标药店")
    private Integer shopId;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "订单金额")
    private Double amount;

    @ApiModelProperty(value = "参加活动id")
    private Integer discountId;

    @ApiModelProperty(value = "有效期起始")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    private Date endAt;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "最后修改时间")
    private Date updateAt;

    @ApiModelProperty(value = "药厂确认状态")
    private Byte factoryConfirmStatus;

    @ApiModelProperty(value = "药厂确认时间")
    private Date factoryConfirmAt;

    @ApiModelProperty(value = "工厂确认者id")
    private Integer factoryConfirmBy;

    @ApiModelProperty(value = "渠道确认状态")
    private Byte merchantConfirmStatus;

    @ApiModelProperty(value = "渠道确认时间")
    private Date merchantConfirmAt;

    @ApiModelProperty(value = "渠道确认数量")
    private Integer merchantConfirmNumber;

    @ApiModelProperty(value = "渠道确认者id")
    private Integer merchantConfirmBy;

    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
