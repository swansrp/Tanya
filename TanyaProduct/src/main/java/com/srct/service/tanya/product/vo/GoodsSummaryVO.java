/**
 * Title: GoodsSummaryVO.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-23 22:39
 * @description Project Name: Tanya
 * @Package: com.srct.service.tanya.product.vo
 */
package com.srct.service.tanya.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsSummaryVO {
    @ApiModelProperty(value = "新增数量")
    private Long newNum;
    @ApiModelProperty(value = "删除数量")
    private Long deleteNum;
}
