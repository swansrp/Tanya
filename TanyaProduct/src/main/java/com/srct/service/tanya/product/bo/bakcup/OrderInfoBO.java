/**
 * Title: OrderInfoBo.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.bo
 * @author sharuopeng
 * @date 2019-02-19 10:58:39
 */
package com.srct.service.tanya.product.bo.bakcup;

import com.srct.service.tanya.product.bo.ProductBO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class OrderInfoBO extends ProductBO {

    @ApiModelProperty(value = "所属工厂id")
    private Integer factoryId;

}
