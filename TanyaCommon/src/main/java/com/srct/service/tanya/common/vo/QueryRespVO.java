/**
 * Title: BaseResVO.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.vo
 * @author sharuopeng
 * @date 2019-02-19 13:52:13
 */
package com.srct.service.tanya.common.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author sharuopeng
 *
 */
@Data
public class QueryRespVO<T> extends QueryReqVO {

    public QueryRespVO() {
        info = new ArrayList<>();
    }

    private int totalPages;

    private long totalSize;

    List<T> info;
}
