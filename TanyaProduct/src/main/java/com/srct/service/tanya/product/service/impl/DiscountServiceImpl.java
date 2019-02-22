/**
 * Title: DiscountServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-20 09:32:29
 */
package com.srct.service.tanya.product.service.impl;

import org.springframework.stereotype.Service;

import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.DiscountService;
import com.srct.service.tanya.product.vo.DiscountInfoReqVO;
import com.srct.service.tanya.product.vo.DiscountInfoRespVO;

/**
 * @author sharuopeng
 *
 */
@Service
public class DiscountServiceImpl extends ProductServiceBaseImpl implements DiscountService {

    @Override
    public QueryRespVO<DiscountInfoRespVO> getDiscountInfo(ProductBO<QueryReqVO> discount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QueryRespVO<DiscountInfoRespVO> updateDiscountInfo(ProductBO<DiscountInfoReqVO> discount) {
        // TODO Auto-generated method stub
        return null;
    }

}
