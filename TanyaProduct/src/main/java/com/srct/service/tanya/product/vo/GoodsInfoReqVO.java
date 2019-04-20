/**
 * Title: GoodsInfoReqVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-21 21:43:18
 */
package com.srct.service.tanya.product.vo;

import com.srct.service.vo.QueryReqVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author sharuopeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsInfoReqVO extends QueryReqVO {
    List<Integer> bindIdList;
    List<Integer> unbindIdList;
    private GoodsInfoVO goods;
}
