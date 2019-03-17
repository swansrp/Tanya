/**
 * Title: GoodsInfoRespVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-21 21:45:13
 */
package com.srct.service.tanya.product.vo;

import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class GoodsInfoRespVO {

    private GoodsInfoVO goodsInfoVO;

    private Integer unit;

    private DiscountInfoVO discountInfoVO;

}
