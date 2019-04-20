/**
 * Title: CampaignInfoRespVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp @Project
 * Name: TanyaProduct @Package: com.srct.service.tanya.product.vo
 *
 * @author sharuopeng
 * @date 2019-02-19 10:03:09
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.tanya.role.vo.RoleInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author sharuopeng
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

    @ApiModelProperty(value = "确认者")
    private RoleInfoVO confirmRoleInfoVO;

    @ApiModelProperty(value = "绑定促销员")
    private List<RoleInfoVO> bindSalesmanList;

    @ApiModelProperty(value = "未绑定促销员")
    private List<RoleInfoVO> unbindSalesmanList;
}
