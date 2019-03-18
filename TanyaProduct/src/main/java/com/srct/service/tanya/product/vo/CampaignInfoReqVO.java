/**
 * Title: CampaingInfoVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 09:13:08
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.tanya.common.vo.QueryReqVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author sharuopeng
 *
 */
@Data
public class CampaignInfoReqVO extends QueryReqVO {

    private CampaignInfoVO campaign;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "绑定促销员")
    private List<Integer> bindSalesmanIdList;

    @ApiModelProperty(value = "未绑定促销员")
    private List<Integer> undbindSalesmanIdList;

}
