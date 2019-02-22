/**
 * Title: ShopInfoReqVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-21 21:48:07
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.tanya.common.vo.QueryReqVO;

import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class ShopInfoReqVO extends QueryReqVO {

    private ShopInfoVO shop;

}
