/**
 * Title: ShopInfoRespVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-21 21:49:22
 */
package com.srct.service.tanya.product.vo;

import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class ShopInfoRespVO {

    private ShopInfoVO shopInfoVO;

    public ShopInfoRespVO() {

    }

    public ShopInfoRespVO(ShopInfoVO shopInfoVO) {
        this.shopInfoVO = shopInfoVO;
    }

}
