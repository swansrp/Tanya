/**
 * Title: ProductBO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.bo
 * @author sharuopeng
 * @date 2019-02-20 09:38:57
 */
package com.srct.service.tanya.product.bo;

import com.srct.service.tanya.common.vo.QueryReqVO;

import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class ProductBO<T extends QueryReqVO> extends BaseBO {

    private String productType;

    private T req;

    private Integer factoryId;

    private Integer productId;

}
