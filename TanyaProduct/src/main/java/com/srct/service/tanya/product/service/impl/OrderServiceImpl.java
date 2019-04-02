/**
 * Title: OrderServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-20 09:32:21
 */
package com.srct.service.tanya.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author sharuopeng
 */
@Service
public class OrderServiceImpl extends ProductServiceBaseImpl implements OrderService {

    final static private DateFormat DF = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    @Override
    public QueryRespVO<OrderInfoRespVO> getOrderInfo(ProductBO<QueryReqVO> order) {
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(order);
        List<Integer> traderFactoryMerchantMapIdList =
                super.buildTraderFactoryMerchantMapIdList(order, factoryInfoList);
        OrderInfoExample orderExample = buildOrderInfoExample(order, traderFactoryMerchantMapIdList);
        return buildResByExample(order, orderExample);
    }

    private OrderInfoExample buildOrderInfoExample(ProductBO<?> order, List<Integer> traderFactoryMerchantMapIdList) {
        OrderInfoExample orderExample = super.makeQueryExample(order, OrderInfoExample.class);
        OrderInfoExample.Criteria orderCriteria = orderExample.getOredCriteria().get(0);


        if (traderFactoryMerchantMapIdList.size() == 0) {
            traderFactoryMerchantMapIdList.add(0);
        }

        orderCriteria.andTraderFactoryMerchantIdIn(traderFactoryMerchantMapIdList);
        if (order.getProductId() != null) {
            orderCriteria.andIdEqualTo(order.getProductId());
        }

        if (order.getCreaterRole().getRole().equals("factory")) {
            if (order.getApproved() == null) {
                orderCriteria.andFactoryConfirmAtIsNull();
            } else if (!DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID.equals(order.getApproved())) {
                orderCriteria.andFactoryConfirmStatusEqualTo(order.getApproved());
            }
        } else if (order.getCreaterRole().getRole().equals("merchant")) {
            orderCriteria.andFactoryConfirmStatusEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            if (order.getApproved() == null) {
                orderCriteria.andMerchantConfirmAtIsNull();
            } else if (DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID.equals(order.getApproved())) {
                orderCriteria.andMerchantConfirmStatusEqualTo(order.getApproved());
            }
        }

        return orderExample;
    }

    private QueryRespVO<OrderInfoRespVO> buildResByExample(ProductBO<QueryReqVO> order, OrderInfoExample orderExample) {
        PageInfo<?> pageInfo = super.buildPage(order);
        List<OrderInfo> orderInfoList = orderInfoDao.getOrderInfoByExample(orderExample, pageInfo);

        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<>();
        super.buildRespbyReq(res, order);
        res.setPageSize(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());

        orderInfoList.forEach(orderInfo -> res.getInfo().add(buildOrderInfoRespVO(orderInfo)));
        return res;
    }

    @Override
    public QueryRespVO<OrderInfoRespVO> updateOrderInfo(ProductBO<OrderInfoReqVO> order) {
        validateUpdate(order);

        OrderInfo orderInfo = new OrderInfo();
        BeanUtil.copyProperties(order.getReq().getOrder(), orderInfo);

        if (orderInfo.getStartAt() == null && orderInfo.getEndAt() == null) {
            super.makeDefaultPeriod(orderInfo);
        }

        TraderFactoryMerchantMap map = super.getTraderFactoryMerchantMap(order);
        orderInfo.setTraderFactoryMerchantId(map.getId());
        orderInfo.setShopId(order.getReq().getShopId());
        if (order.getReq().getGoodsId() != null) {
            orderInfo.setGoodsId(order.getReq().getGoodsId());
        }
        orderInfo.setDiscountId(order.getReq().getDiscountId());
        orderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        orderInfoDao.updateOrderInfo(orderInfo);

        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildOrderInfoRespVO(orderInfo));

        return res;
    }

    private OrderInfoRespVO buildOrderInfoRespVO(OrderInfo orderInfo) {
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        BeanUtil.copyProperties(orderInfo, orderInfoVO);

        OrderInfoRespVO res = new OrderInfoRespVO();
        BeanUtil.copyProperties(orderInfo, res);

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
        RoleInfoVO merchantConfirmRoleInfoVO = super.getRoleInfoVO(orderInfo.getMerchantConfirmBy(), "merchant");
        RoleInfoVO factoryConfirmRoleInfoVO = super.getRoleInfoVO(orderInfo.getFactoryConfirmBy(), "factory");
        res.setDiscountInfoVO(discountInfoVO);
        res.setFactoryInfoVO(factoryInfoVO);
        res.setGoodsInfoVO(goodsInfoVO);
        res.setMerchantInfoVO(merchantInfoVO);
        res.setOrderInfoVO(orderInfoVO);
        res.setShopInfoVO(shopInfoVO);
        res.setTraderInfoVO(traderInfoVO);
        res.setMerchantConfirmRoleInfoVO(merchantConfirmRoleInfoVO);
        res.setFactoryConfirmRoleInfoVO(factoryConfirmRoleInfoVO);
        return res;
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
            if (DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(order.getApproved())) {
                orderInfo.setMerchantConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            } else {
                orderInfo.setMerchantConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            }
            orderInfo.setMerchantConfirmAt(new Date());
            orderInfo.setMerchantConfirmBy(super.getRoleInfoVOByReq(order).getId());
            if (order.getNumber() != null && order.getNumber() < orderInfo.getGoodsNumber()) {
                orderInfo.setMerchantConfirmNumber(order.getNumber());
            } else {
                orderInfo.setMerchantConfirmNumber(orderInfo.getGoodsNumber());
            }
            orderInfoDao.updateOrderInfo(orderInfo);
        } else if (roleType.equals("factory")) {
            FactoryInfo factoryInfo = factoryRoleService.getFactoryInfoByUser(userInfo);
            if (!traderFactoryMerchantMap.getFactoryId().equals(factoryInfo.getId())) {
                throw new ServiceException("dont allow to approve order by factory " + factoryInfo.getId());
            }
            if (DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(orderInfo.getMerchantConfirmStatus())) {
                if (!DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(order.getApproved())) {
                    throw new ServiceException("merchant dont allow to reject order when merchant has confirmed");
                }
            }
            if (order.getApproved() != null) {
                orderInfo.setFactoryConfirmStatus(order.getApproved());
            }
            orderInfo.setFactoryConfirmAt(new Date());
            orderInfo.setFactoryConfirmBy(super.getRoleInfoVOByReq(order).getId());
            orderInfoDao.updateOrderInfo(orderInfo);
        }
        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildOrderInfoRespVO(orderInfo));
        return res;
    }

    @Override
    public QueryRespVO<OrderInfoRespVO> delOrderInfo(ProductBO<OrderInfoReqVO> order) {
        validateDelete(order);
        OrderInfo orderInfo = orderInfoDao.getOrderInfobyId(order.getProductId());
        orderInfoDao.delOrderInfo(orderInfo);
        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildOrderInfoRespVO(orderInfo));
        return res;
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("salesman")) {
            throw new ServiceException("dont allow to delete order by role " + roleType);
        }

        ProductBO<OrderInfoReqVO> order = (ProductBO<OrderInfoReqVO>) req;
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(req);
        List<Integer> traderFactoryMerchantMapIdList =
                super.buildTraderFactoryMerchantMapIdList(order, factoryInfoList);
        OrderInfoExample orderExample = buildOrderInfoExample(order, traderFactoryMerchantMapIdList);
        orderExample.getOredCriteria().get(0).andIdEqualTo(order.getProductId());
        OrderInfo orderExsited;
        try {
            orderExsited = orderInfoDao.getOrderInfoByExample(orderExample).get(0);
        } catch (Exception e) {
            throw new ServiceException("cant delete the order id " + order.getReq().getOrder().getId());
        }
        if (orderExsited.getFactoryConfirmAt() != null) {
            throw new ServiceException("dont allow to delete already confirmed order by factory " + DF
                    .format(orderExsited.getFactoryConfirmAt()));
        } else if (orderExsited.getMerchantConfirmAt() != null) {
            throw new ServiceException("dont allow to delete already confirmed order by merchant " + DF
                    .format(orderExsited.getMerchantConfirmAt()));
        }

    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("salesman")) {
            throw new ServiceException("dont allow to query campaign by role " + roleType);
        }
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("dont allow to update order by role " + roleType);
        }

        ProductBO<OrderInfoReqVO> order = (ProductBO<OrderInfoReqVO>) req;
        if (order.getReq().getOrder().getId() != null) {
            List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(req);
            List<Integer> traderFactoryMerchantMapIdList =
                    super.buildTraderFactoryMerchantMapIdList(order, factoryInfoList);
            OrderInfoExample orderExample = buildOrderInfoExample(order, traderFactoryMerchantMapIdList);
            orderExample.getOredCriteria().get(0).andIdEqualTo(order.getReq().getOrder().getId());
            OrderInfo orderExsited;
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
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("salesman") || roleType.equals("trader")) {
            throw new ServiceException("dont allow to confirm order by role " + roleType);
        }
        if (req.getApproved() == null) {
            throw new ServiceException("approve status is null ");
        }
    }

}
