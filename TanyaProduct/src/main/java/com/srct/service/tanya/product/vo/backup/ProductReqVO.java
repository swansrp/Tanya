/**
 * Title: ProductReqVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 14:33:56
 */
package com.srct.service.tanya.product.vo.backup;

import java.util.Date;

import com.srct.service.tanya.common.vo.QueryReqVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class ProductReqVO<T> extends QueryReqVO {

    @ApiModelProperty(value = "有效期起始")
    private Date startAt;

    @ApiModelProperty(value = "有效期结束")
    private Date endAt;

    private T info;

}
