/**
 * Title: OrderServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-20 09:32:21
 */
package com.srct.service.tanya.product.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
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

    final static private DateFormat DF = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

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
        PageInfo<?> pageInfo = super.buildPage(order);
        List<OrderInfo> orderInfoList = orderInfoDao.getOrderInfoByExample(orderExample, pageInfo);

        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<OrderInfoRespVO>();
        super.buildRespbyReq(res, order);
        res.setPageSize(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());

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
        validateUpdate(order);
        if (order.getReq().getOrder().getId() != null) {
            FactoryInfo factoryInfo = super.getFactoryInfo(order);
            List<Integer> traderFactoryMerchantMapIdList =
                super.buildTraderFactoryMerchantMapIdList(order, factoryInfo);
            OrderInfoExample orderExample = buildOrderInfoExample(order, traderFactoryMerchantMapIdList);
            orderExample.getOredCriteria().get(0).andIdEqualTo(order.getReq().getOrder().getId());
            OrderInfo orderExsited = null;
            try {
                orderExsited = orderInfoDao.getOrderInfoByExample(orderExample).get(0);
            } catch (Exception e) {
                throw new ServiceException("cant update the order id " + order.getReq().getOrder().getId());
            }
            if (orderExsited.getFactoryConfirmAt() != null || orderExsited.getMerchantConfirmAt() != null) {
                throw new ServiceException(
                    "already confirmed order by factory " + DF.format(orderExsited.getFactoryConfirmAt())
                        + " by merchant" + DF.format(orderExsited.getMerchantConfirmAt()));
            }

        }
        OrderInfo orderInfo = new OrderInfo();
        BeanUtil.copyProperties(order.getReq().getOrder(), orderInfo);
        TraderFactoryMerchantMap map = super.getTraderFactoryMerchantMap(order);
        orderInfo.setTraderFactoryMerchantId(map.getId());

        orderInfoDao.updateOrderInfo(orderInfo);

        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<OrderInfoRespVO>();
        res.getInfo().add(buildOrderInfoRespVO(orderInfo));

        return res;
    }

    /**
     * @param orderInfo
     * @param map
     */
    private OrderInfoRespVO buildOrderInfoRespVO(OrderInfo orderInfo) {
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        BeanUtil.copyProperties(orderInfo, orderInfoVO);

        OrderInfoRespVO res = new OrderInfoRespVO();

        DiscountInfoVO discountInfoVO = super.getDiscountInfoVObyId(orderInfo.getDiscountId());
        GoodsInfoVO goodsInfoVO = super.getGoodsInfoVObyId(orderInfo.getGoodsId());
        ShopInfoVO shopInfoVO = super.getShopInfoVObyId(orderInfo.getShopId());
        TraderFactoryMerchantMap map =
            traderFactoryMerchantMapDao.getTraderFactoryMerchantMapbyId(orderInfo.getTraderFactoryMerchantId());
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

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("salesman")) {
            throw new ServiceException("dont allow to update order by role " + roleType);
        }
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("salesman") || roleType.equals("trader")) {
            throw new ServiceException("dont allow to confirm order by role " + roleType);
        }
    }

    @Override
    public QueryRespVO<OrderInfoRespVO> confirmOrderInfo(ProductBO<QueryReqVO> order) {
        validateConfirm(order);
        String roleType = order.getCreaterRole().getRole();
        UserInfo userInfo = order.getCreaterInfo();
        OrderInfo orderInfo = orderInfoDao.getOrderInfobyId(order.getProductId());
        TraderFactoryMerchantMap traderFactoryMerchantMap =
            traderFactoryMerchantMapDao.getTraderFactoryMerchantMapbyId(orderInfo.getTraderFactoryMerchantId());
        if (roleType.equals("merchant")) {
            MerchantInfo merchantInfo = merchantRoleService.getMerchantInfoByUser(userInfo);
            FactoryMerchantMap factoryMerchantMap =
                factoryMerchantMapDao.getFactoryMerchantMapbyId(traderFactoryMerchantMap.getFactoryMerchantMapId());
            if (!factoryMerchantMap.getMerchantId().equals(merchantInfo.getId())) {
                throw new ServiceException("dont allow to approve order by merchant " + merchantInfo.getId());
            } else if (!DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(orderInfo.getFactoryConfirmStatus())) {
                throw new ServiceException("merchant dont allow to approve/reject order without factory confirm");
            }
            if (order.isApproved()) {
                orderInfo.setMerchantConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            } else {
                orderInfo.setMerchantConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            }
            orderInfo.setMerchantConfirmAt(new Date());
            orderInfoDao.updateOrderInfo(orderInfo);
        } else if (roleType.equals("factory")) {
            FactoryInfo factoryInfo = factoryRoleService.getFactoryInfoByUser(userInfo);
            if (!traderFactoryMerchantMap.getFactoryId().equals(factoryInfo.getId())) {
                throw new ServiceException("dont allow to approve order by factory " + factoryInfo.getId());
            }
            if (DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(orderInfo.getMerchantConfirmStatus())) {
                if (order.isApproved() == false) {
                    throw new ServiceException("merchant dont allow to reject order when merchant has confirmed");
                }
            }
            if (order.isApproved()) {
                orderInfo.setMerchantConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            } else {
                orderInfo.setMerchantConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            }
            orderInfo.setFactoryConfirmAt(new Date());
            orderInfoDao.updateOrderInfo(orderInfo);
        }
        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<OrderInfoRespVO>();
        res.getInfo().add(buildOrderInfoRespVO(orderInfo));
        return res;
    }

}
