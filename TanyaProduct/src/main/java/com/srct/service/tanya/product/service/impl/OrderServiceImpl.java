/**
 * Title: OrderServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-20 09:32:21
 */
package com.srct.service.tanya.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.OrderService;
import com.srct.service.tanya.product.vo.DiscountInfoVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.product.vo.OrderInfoReqVO;
import com.srct.service.tanya.product.vo.OrderInfoRespVO;
import com.srct.service.tanya.product.vo.OrderInfoVO;
import com.srct.service.tanya.product.vo.ShopInfoVO;
import com.srct.service.tanya.role.vo.RoleInfoVO;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author sharuopeng
 *
 */
@Service
public class OrderServiceImpl extends ProductServiceBaseImpl implements OrderService {

    @Override
    public QueryRespVO<OrderInfoRespVO> getOrderInfo(ProductBO<QueryReqVO> order) {
        FactoryInfo factoryInfo = super.getFactoryInfo(order);
        List<Integer> traderFactoryMerchantMapIdList = super.buildTraderFactoryMerchantMapIdList(order, factoryInfo);
        OrderInfoExample orderExample = buildOrderInfoExample(order, traderFactoryMerchantMapIdList);
        QueryRespVO<OrderInfoRespVO> res = buildResByExample(order, orderExample);
        return res;
    }

    /**
     * @param order
     * @param mapIdList
     * @return
     */
    private OrderInfoExample buildOrderInfoExample(ProductBO<?> order, List<Integer> traderFactoryMerchantMapIdList) {
        OrderInfoExample orderExample = (OrderInfoExample)super.makeQueryExample(order, OrderInfoExample.class);
        OrderInfoExample.Criteria orderCriteria = orderExample.getOredCriteria().get(0);
        orderCriteria.andTraderFactoryMerchantIdIn(traderFactoryMerchantMapIdList);
        if (order.getProductId() != null) {
            orderCriteria.andIdEqualTo(order.getProductId());
        }
        return orderExample;
    }

    /**
     * @param order
     * @param orderExample
     * @return
     */
    private QueryRespVO<OrderInfoRespVO> buildResByExample(ProductBO<QueryReqVO> order, OrderInfoExample orderExample) {
        Page page = PageHelper.startPage(order.getReq().getCurrentPage(), order.getReq().getPageSize());
        List<OrderInfo> orderInfoList = orderInfoDao.getOrderInfoByExample(orderExample);
        PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(orderInfoList);

        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<OrderInfoRespVO>();
        res.setPageSize(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());
        res.setCurrentPage(order.getReq().getCurrentPage());
        res.setPageSize(order.getReq().getPageSize());

        orderInfoList.forEach(orderInfo -> {
            OrderInfoRespVO info = new OrderInfoRespVO();
            OrderInfoVO vo = new OrderInfoVO();
            BeanUtil.copyProperties(orderInfo, vo);
            BeanUtil.copyProperties(orderInfo, info);
            info.setOrderInfoVO(vo);
            res.getInfo().add(info);
        });
        return res;
    }

    @Override
    public QueryRespVO<OrderInfoRespVO> updateOrderInfo(ProductBO<OrderInfoReqVO> order) {

        if (order.getReq().getOrder().getId() != null) {
            FactoryInfo factoryInfo = super.getFactoryInfo(order);
            List<Integer> traderFactoryMerchantMapIdList =
                super.buildTraderFactoryMerchantMapIdList(order, factoryInfo);
            OrderInfoExample orderExample = buildOrderInfoExample(order, traderFactoryMerchantMapIdList);
            orderExample.getOredCriteria().get(0).andIdEqualTo(order.getReq().getOrder().getId());
            if (orderInfoDao.countOrderInfoByExample(orderExample) == 0) {
                throw new ServiceException("cant update the order id " + order.getReq().getOrder().getId());
            }
        }
        OrderInfo orderInfo = new OrderInfo();
        BeanUtil.copyProperties(order.getReq().getOrder(), orderInfo);
        TraderFactoryMerchantMap map = super.getTraderFactoryMerchantMap(order);
        orderInfo.setTraderFactoryMerchantId(map.getId());

        orderInfoDao.updateOrderInfo(orderInfo);

        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<OrderInfoRespVO>();
        res.getInfo().add(buildOrderInfoRespVO(orderInfo, map));

        return res;
    }

    /**
     * @param orderInfo
     * @param map
     */
    private OrderInfoRespVO buildOrderInfoRespVO(OrderInfo orderInfo, TraderFactoryMerchantMap map) {
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        BeanUtil.copyProperties(orderInfo, orderInfoVO);

        OrderInfoRespVO res = new OrderInfoRespVO();

        DiscountInfoVO discountInfoVO = super.getDiscountInfoVObyId(orderInfo.getDiscountId());
        GoodsInfoVO goodsInfoVO = super.getGoodsInfoVObyId(orderInfo.getGoodsId());
        ShopInfoVO shopInfoVO = super.getShopInfoVObyId(orderInfo.getShopId());
        FactoryMerchantMap factoryMerchantMap =
            factoryMerchantMapDao.getFactoryMerchantMapbyId(map.getFactoryMerchantMapId());
        RoleInfoVO merchantInfoVO = super.getRoleInfoVO(factoryMerchantMap.getMerchantId(), "merchant");
        RoleInfoVO factoryInfoVO = super.getRoleInfoVO(factoryMerchantMap.getFactoryId(), "factory");
        RoleInfoVO traderInfoVO = super.getRoleInfoVO(map.getTraderId(), "trader");
        res.setDiscountInfoVO(discountInfoVO);
        res.setFactoryInfoVO(factoryInfoVO);
        res.setGoodsInfoVO(goodsInfoVO);
        res.setMerchantInfoVO(merchantInfoVO);
        res.setOrderInfoVO(orderInfoVO);
        res.setShopInfoVO(shopInfoVO);
        res.setTraderInfoVO(traderInfoVO);

        return res;
    }

}
