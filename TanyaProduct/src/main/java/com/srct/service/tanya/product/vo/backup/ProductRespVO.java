/**
 * Title: ProductRespVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 14:35:58
 */
package com.srct.service.tanya.product.vo.backup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sharuopeng
 */
@Data
public class ProductRespVO<T> {
    T info;
    @ApiModelProperty(value = "有效期起始")
    private Date startAt;
    @ApiModelProperty(value = "有效期结束")
    private Date endAt;
}
