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
public class FactoryMerchantMapEntityVO extends QueryReqVO {

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "渠道id")
    private Integer merchantId;

    @ApiModelProperty(value = "药厂id")
    private Integer factoryId;

    @ApiModelProperty(value = "注册商品数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "销售代表数量")
    private Integer traderNumber;

    @ApiModelProperty(value = "商品活动数量")
    private Integer discountNumber;

    @ApiModelProperty(value = "促销活动数量")
    private Integer campaignNumber;

    @ApiModelProperty(value = "商品-营销员绑定功能")
    private Byte goodsTraderBind;

    @ApiModelProperty(value = "商店-营销员绑定功能")
    private Byte shopTraderBind;

    @ApiModelProperty(value = "有效期起始")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    private Date endAt;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "最后登入时间")
    private Date updateAt;

    @ApiModelProperty(value = "有效性")
    private Byte valid;
}
