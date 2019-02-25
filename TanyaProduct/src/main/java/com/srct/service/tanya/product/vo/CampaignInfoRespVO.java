/**
 * Title: CampaignInfoRespVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 10:03:09
 */
package com.srct.service.tanya.product.vo;

import java.util.Date;

import com.srct.service.tanya.role.vo.RoleInfoVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class CampaignInfoRespVO {

    private CampaignInfoVO campaignInfoVO;

    @ApiModelProperty(value = "发起销售人员信息")
    private RoleInfoVO traderInfoVO;

    @ApiModelProperty(value = "促销商品信息")
    private GoodsInfoVO goodsInfoVO;

    @ApiModelProperty(value = "确认状态")
    private Byte confirmStatus;

    @ApiModelProperty(value = "确认时间")
    private Date confirmAt;
}
