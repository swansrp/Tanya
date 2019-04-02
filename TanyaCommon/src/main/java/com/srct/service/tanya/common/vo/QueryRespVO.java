/**
 * Title: BaseResVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 13:52:13
 */
package com.srct.service.tanya.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sharuopeng
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryRespVO<T> extends QueryReqVO {

    List<T> info;
    private Integer totalPages;

    private Long totalSize;

    public QueryRespVO() {
        info = new ArrayList<>();
    }
}
