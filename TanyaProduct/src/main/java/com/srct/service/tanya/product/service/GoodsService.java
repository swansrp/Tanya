/**
 * Title: GoodsService.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service
 * @author Sharp
 * @date 2019-02-17 22:27:00
 */
package com.srct.service.tanya.product.service;

import java.util.List;

import com.srct.service.tanya.product.bo.GoodsInfoBO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;

/**
 * @author Sharp
 *
 */
public interface GoodsService {

    public List<GoodsInfoVO> updateGoodsInfo(GoodsInfoBO goods);
}
