/**
 * Title: UploadGoodsInfoVO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-26 11:31
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.product.vo.upload
 */
package com.srct.service.tanya.product.vo.upload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UploadGoodsInfoVO extends UploadReqVO {
    @ApiModelProperty("唯一标识码")
    private String code;
    @ApiModelProperty("药品名称")
    private String title;
    @ApiModelProperty("药品备注")
    private String comment;
}
