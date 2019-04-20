/**
 * Title: ReqOrderInfoVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 09:47:23
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.vo.QueryReqVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author sharuopeng
 */
@ApiModel(value = "订单请求", description = "订单请求详情")
@Data
public class OrderInfoReqVO extends QueryReqVO {

    private OrderInfoVO order;

    private Integer goodsId;

    private Integer discountId;

    private Integer shopId;

}
