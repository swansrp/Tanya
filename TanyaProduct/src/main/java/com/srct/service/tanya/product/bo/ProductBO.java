/**
 * Title: ProductBO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.bo
 * @author sharuopeng
 * @date 2019-02-20 09:38:57
 */
package com.srct.service.tanya.product.bo;

import com.srct.service.vo.QueryReqVO;
import lombok.Data;

/**
 * @author sharuopeng
 */
@Data
public class ProductBO<T extends QueryReqVO> extends BaseBO {
    private String productType;
    private T req;
    private Integer merchantId;
    private Integer factoryId;
    private Integer traderId;
    private Integer salesmanId;
    private Integer productId;
    private Integer goodsId;
    private Byte approved;
    private Integer number;
    private String title;
}
