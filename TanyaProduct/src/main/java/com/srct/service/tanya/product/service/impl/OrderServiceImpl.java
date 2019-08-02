/**
 * Title: OrderServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-20 09:32:21
 */
package com.srct.service.tanya.product.service.impl;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.config.FeatureConstant;
import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.OrderService;
import com.srct.service.tanya.product.vo.DiscountInfoVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.product.vo.OrderInfoReqVO;
import com.srct.service.tanya.product.vo.OrderInfoRespVO;
import com.srct.service.tanya.product.vo.OrderInfoVO;
import com.srct.service.tanya.product.vo.ShopInfoVO;
import com.srct.service.tanya.role.vo.RoleInfoVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.DateUtils;
import com.srct.service.utils.MathUtil;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public QueryRespVO<OrderInfoRespVO> confirmOrderInfo(ProductBO<QueryReqVO> order) {
        validateConfirm(order);
        String roleType = order.getCreatorRole().getRole();
        UserInfo userInfo = order.getCreatorInfo();
        OrderInfo orderInfo = orderInfoDao.getOrderInfoById(order.getProductId());
        FactoryMerchantMap factoryMerchantMap =
                factoryMerchantMapDao.getFactoryMerchantMapById(orderInfo.getFactoryMerchantId());
        if (("merchant").equals(roleType)) {
            MerchantInfo merchantInfo = merchantRoleService.getMerchantInfoByUser(userInfo);
            if (!factoryMerchantMap.getMerchantId().equals(merchantInfo.getId())) {
                throw new ServiceException(
                        "不允许" + order.getCreatorRole().getComment() + "[" + merchantInfo.getId() + "]审批订单[" + order
                                .getProductId() + "]");
            } else if (!DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(orderInfo.getFactoryConfirmStatus())) {
                throw new ServiceException("不允许" + order.getCreatorRole().getComment() + "审批/拒绝未经下游单位审批的订单");
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
        } else if (("factory").equals(roleType)) {
            FactoryInfo factoryInfo = factoryRoleService.getFactoryInfoByUser(userInfo);
            if (!factoryMerchantMap.getFactoryId().equals(factoryInfo.getId())) {
                throw new ServiceException(
                        "不允许" + order.getCreatorRole().getComment() + "[" + factoryInfo.getId() + "]审批订单[" + order
                                .getProductId() + "]");
            }
            if (DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(orderInfo.getMerchantConfirmStatus())) {
                if (!DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(order.getApproved())) {
                    throw new ServiceException("不允许" + order.getCreatorRole().getComment() + "拒绝上游单位已经审批的订单");
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
        OrderInfo orderInfo = orderInfoDao.getOrderInfoById(order.getProductId());
        orderInfoDao.delOrderInfo(orderInfo);
        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildOrderInfoRespVO(orderInfo));
        return res;
    }

    @Override
    public QueryRespVO<OrderInfoRespVO> getOrderInfo(ProductBO<QueryReqVO> order) {
        validateQuery(order);

        String roleType = order.getCreatorRole().getRole();
        switch (roleType) {
            case "merchant":
                return buildResByExample(order, getOrderInfoExampleByMerchant(order));
            case "factory":
                return buildResByExample(order, getOrderInfoExampleByFactory(order));
            case "trader":
                return buildResByExample(order, getOrderInfoExampleByTrader(order));
            default:
                throw new ServiceException("不允许[" + roleType + "]角色查询订单");
        }
    }

    @Override
    public List<OrderInfo> summaryOrderInfo(ProductBO<QueryReqVO> order) {
        validateQuery(order);

        String roleType = order.getCreatorRole().getRole();
        OrderInfoExample example;
        switch (roleType) {
            case "admin":
                example = getOrderInfoExampleByAdmin(order);
                break;
            case "merchant":
                example = getOrderInfoExampleByMerchant(order);
                break;
            case "factory":
                example = getOrderInfoExampleByFactory(order);
                break;
            case "trader":
                example = getOrderInfoExampleByTrader(order);
                break;
            default:
                throw new ServiceException("不允许[" + roleType + "]角色查询订单");
        }
        example.getOredCriteria().get(0)
                .andMerchantConfirmStatusEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return super.orderInfoDao.getOrderInfoByExample(example);

    }

    @Override
    public QueryRespVO<OrderInfoRespVO> updateOrderInfo(ProductBO<OrderInfoReqVO> order) {
        validateUpdate(order);

        OrderInfo orderInfo = new OrderInfo();
        BeanUtil.copyProperties(order.getReq().getOrder(), orderInfo);

        if (orderInfo.getStartAt() == null && orderInfo.getEndAt() == null) {
            super.makeDefaultPeriod(orderInfo, FeatureConstant.ORDER_DEFAULT_PERIOD, "365");
        }
        if (orderInfo.getEndAt() != null) {
            orderInfo.setEndAt(DateUtils.endTime(orderInfo.getEndAt()));
        }
        switch (order.getCreatorRole().getRole()) {
            case "trader":
                TraderInfo traderInfo = traderRoleService.getTraderInfoByUser(order.getCreatorInfo());
                TraderFactoryMerchantMap map = super.getTraderFactoryMerchantMap(order);
                orderInfo.setTraderFactoryMerchantId(map.getId());
                orderInfo.setTraderId(traderInfo.getId());
                orderInfo.setFactoryMerchantId(map.getFactoryMerchantMapId());
                break;
            case "factory":
                FactoryInfo factoryInfo = factoryRoleService.getFactoryInfoByUser(order.getCreatorInfo());
                FactoryMerchantMap factoryMerchantMap =
                        super.getFactoryMerchantMapByMerchantIdAndFactoryId(null, factoryInfo.getId());
                orderInfo.setFactoryMerchantId(factoryMerchantMap.getId());
                break;
            default:
                break;
        }

        orderInfo.setShopId(order.getReq().getShopId());
        if (order.getReq().getGoodsId() != null) {
            orderInfo.setGoodsId(order.getReq().getGoodsId());
        }
        if (order.getReq().getDiscountId() != null) {
            orderInfo.setDiscountId(order.getReq().getDiscountId());
            DiscountInfo discountInfo = super.discountInfoDao.getDiscountInfoById(order.getReq().getDiscountId());
            orderInfo.setAmount(
                    MathUtil.mul(discountInfo.getAmount(), order.getReq().getOrder().getGoodsNumber().doubleValue()));
        } else {
            GoodsInfo goodsInfo = super.goodsInfoDao.getGoodsInfoById(order.getReq().getGoodsId());
            orderInfo.setAmount(MathUtil.mul(goodsInfo.getAmount(), orderInfo.getGoodsNumber().doubleValue()));
        }
        orderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        orderInfoDao.updateOrderInfo(orderInfo);

        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildOrderInfoRespVO(orderInfo));

        return res;
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (("salesman").equals(roleType) || ("trader").equals(roleType)) {
            throw new ServiceException("dont allow to confirm order by role " + roleType);
        }
        if (req.getApproved() == null) {
            throw new ServiceException("approve status is null ");
        }
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if ("salesman".equals(roleType) || "merchant".equals(roleType)) {
            throw new ServiceException("不允许角色[" + req.getCreatorRole().getComment() + "]删除" + req.getProductType());
        }

        ProductBO<OrderInfoReqVO> order = (ProductBO<OrderInfoReqVO>) req;
        OrderInfoExample orderExample = buildOrderInfoExample(order);
        OrderInfoExample.Criteria criteria = orderExample.getOredCriteria().get(0);
        switch (roleType) {
            case "factory":
                FactoryInfo factoryInfo = factoryRoleService.getFactoryInfoByUser(order.getCreatorInfo());
                FactoryMerchantMap factoryMerchantMap =
                        super.getFactoryMerchantMapByMerchantIdAndFactoryId(null, factoryInfo.getId());
                criteria.andFactoryMerchantIdEqualTo(factoryMerchantMap.getId());
                break;
            case "trader":
                TraderInfo traderInfo = traderRoleService.getTraderInfoByUser(req.getCreatorInfo());
                criteria.andTraderIdEqualTo(traderInfo.getId());
                break;
            default:
                break;
        }
        criteria.andIdEqualTo(order.getProductId());
        OrderInfo orderExisted;
        try {
            orderExisted = orderInfoDao.getOrderInfoByExample(orderExample).get(0);
        } catch (Exception e) {
            throw new ServiceException("不能删除" + order.getProductType() + order.getReq().getOrder().getId());
        }
        if (orderExisted.getFactoryConfirmAt() != null && orderExisted.getTraderFactoryMerchantId() != null) {
            throw new ServiceException("不允许删除已经被厂商确认的订单,确认时间:" + DF.format(orderExisted.getFactoryConfirmAt()));
        } else if (orderExisted.getMerchantConfirmAt() != null) {
            throw new ServiceException("不允许删除已经被商业渠道确认的订单,确认时间:" + DF.format(orderExisted.getMerchantConfirmAt()));
        }

    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (("salesman").equals(roleType)) {
            throw new ServiceException("不允许角色 " + roleType + " 查询订单");
        } else if (("admin").equals(roleType)) {
            if (req.getMerchantId() == null) {
                throw new ServiceException("没有指定渠道ID");
            }
        }
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (("salesman").equals(roleType)) {
            throw new ServiceException(roleType + " 不允许更新订单");
        }

        ProductBO<OrderInfoReqVO> order = (ProductBO<OrderInfoReqVO>) req;
        if (order.getReq().getOrder().getId() != null) {
            OrderInfoExample orderExample = buildOrderInfoExample(order);
            OrderInfoExample.Criteria criteria = orderExample.getOredCriteria().get(0);
            switch (roleType) {
                case "factory":
                    FactoryInfo factoryInfo = factoryRoleService.getFactoryInfoByUser(order.getCreatorInfo());
                    FactoryMerchantMap factoryMerchantMap =
                            super.getFactoryMerchantMapByMerchantIdAndFactoryId(null, factoryInfo.getId());
                    criteria.andFactoryMerchantIdEqualTo(factoryMerchantMap.getId());
                    break;
                case "trader":
                    TraderInfo traderInfo = traderRoleService.getTraderInfoByUser(req.getCreatorInfo());
                    criteria.andTraderIdEqualTo(traderInfo.getId());
                    break;
                default:
                    break;
            }
            criteria.andIdEqualTo(order.getReq().getOrder().getId());
            OrderInfo orderExisted;
            try {
                orderExisted = orderInfoDao.getOrderInfoByExample(orderExample).get(0);
            } catch (Exception e) {
                throw new ServiceException("cant update the order id " + order.getReq().getOrder().getId());
            }
            if (orderExisted.getMerchantConfirmAt() != null) {
                throw new ServiceException("已经被渠道商确认" + DF.format(orderExisted.getMerchantConfirmAt()));
            }
            if (orderExisted.getFactoryConfirmAt() != null) {
                throw new ServiceException("已经被产品经理确认" + DF.format(orderExisted.getFactoryConfirmAt()));
            }
        }
    }

    private OrderInfoExample buildOrderInfoExample(ProductBO<?> order) {
        OrderInfoExample orderExample = super.makeQueryExample(order, OrderInfoExample.class);
        OrderInfoExample.Criteria orderCriteria = orderExample.getOredCriteria().get(0);

        if (order.getProductId() != null) {
            orderCriteria.andIdEqualTo(order.getProductId());
        }
        if (StringUtils.isNotBlank(order.getTitle())) {
            orderCriteria.andTitleLike("%" + order.getTitle() + "%");
        }
        orderExample.setOrderByClause("update_at DESC");
        return orderExample;
    }

    private OrderInfoRespVO buildOrderInfoRespVO(OrderInfo orderInfo) {
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        BeanUtil.copyProperties(orderInfo, orderInfoVO);

        OrderInfoRespVO res = new OrderInfoRespVO();
        BeanUtil.copyProperties(orderInfo, res);

        DiscountInfoVO discountInfoVO = super.getDiscountInfoVOById(orderInfo.getDiscountId());
        GoodsInfoVO goodsInfoVO = super.getGoodsInfoVOById(orderInfo.getGoodsId());
        ShopInfoVO shopInfoVO = super.getShopInfoVOById(orderInfo.getShopId());
        FactoryMerchantMap factoryMerchantMap =
                factoryMerchantMapDao.getFactoryMerchantMapById(orderInfo.getFactoryMerchantId());
        RoleInfoVO merchantInfoVO = super.getRoleInfoVO(factoryMerchantMap.getMerchantId(), "merchant");
        RoleInfoVO factoryInfoVO = super.getRoleInfoVO(factoryMerchantMap.getFactoryId(), "factory");
        RoleInfoVO traderInfoVO = super.getRoleInfoVO(orderInfo.getTraderId(), "trader");
        RoleInfoVO merchantConfirmRoleInfoVO = super.getRoleInfoVO(orderInfo.getMerchantConfirmBy(), "merchant");
        RoleInfoVO factoryConfirmRoleInfoVO = super.getRoleInfoVO(orderInfo.getFactoryConfirmBy(), "factory");
        if (orderInfo.getTraderFactoryMerchantId() != null) {
            res.setCreatorInfoVO(traderInfoVO);
        } else {
            res.setCreatorInfoVO(factoryInfoVO);
        }
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

    private QueryRespVO<OrderInfoRespVO> buildResByExample(ProductBO<QueryReqVO> order, OrderInfoExample orderExample) {
        PageInfo<OrderInfo> pageInfo = super.buildPage(order);
        pageInfo = orderInfoDao.getOrderInfoByExample(orderExample, pageInfo);
        QueryRespVO<OrderInfoRespVO> res = new QueryRespVO<>();
        super.buildRespByReq(res, order);
        res.setTotalPages(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());
        pageInfo.getList().forEach(orderInfo -> res.getInfo().add(buildOrderInfoRespVO(orderInfo)));
        return res;
    }

    private OrderInfoExample getOrderInfoExampleByAdmin(ProductBO<QueryReqVO> order) {
        List<FactoryMerchantMap> factoryMerchantMapList =
                super.getFactoryMerchantMapListByMerchantIdAndFactoryId(order.getMerchantId(), null);
        List<Integer> factoryMerchantMapIdList =
                ReflectionUtil.getFieldList(factoryMerchantMapList, "id", Integer.class);
        if (CollectionUtils.isEmpty(factoryMerchantMapIdList)) {
            throw new ServiceException("没有可用下属");
        }
        OrderInfoExample orderExample = buildOrderInfoExample(order);
        OrderInfoExample.Criteria orderCriteria = orderExample.getOredCriteria().get(0);
        orderCriteria.andFactoryConfirmStatusEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        if (DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID.equals(order.getApproved())) {
            orderCriteria.andMerchantConfirmAtIsNull();
        } else if (order.getApproved() != null) {
            orderCriteria.andMerchantConfirmStatusEqualTo(order.getApproved());
        }
        orderCriteria.andFactoryMerchantIdIn(factoryMerchantMapIdList);
        return orderExample;
    }

    private OrderInfoExample getOrderInfoExampleByFactory(ProductBO<QueryReqVO> order) {
        OrderInfoExample orderExample = buildOrderInfoExample(order);
        OrderInfoExample.Criteria orderCriteria = orderExample.getOredCriteria().get(0);
        if (DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID.equals(order.getApproved())) {
            orderCriteria.andFactoryConfirmAtIsNull();
        } else if (order.getApproved() != null) {
            orderCriteria.andFactoryConfirmStatusEqualTo(order.getApproved());
        }
        FactoryInfo factoryInfo = factoryRoleService.getFactoryInfoByUser(order.getCreatorInfo());
        FactoryMerchantMap factoryMerchantMap =
                super.getFactoryMerchantMapByMerchantIdAndFactoryId(null, factoryInfo.getId());
        orderCriteria.andFactoryMerchantIdEqualTo(factoryMerchantMap.getId());
        return orderExample;
    }

    private OrderInfoExample getOrderInfoExampleByMerchant(ProductBO<QueryReqVO> order) {
        MerchantInfo merchantInfo = merchantRoleService.getMerchantInfoByUser(order.getCreatorInfo());
        List<FactoryMerchantMap> factoryMerchantMapList =
                super.getFactoryMerchantMapListByMerchantIdAndFactoryId(merchantInfo.getId(), null);
        List<Integer> factoryMerchantMapIdList =
                ReflectionUtil.getFieldList(factoryMerchantMapList, "id", Integer.class);
        if (CollectionUtils.isEmpty(factoryMerchantMapIdList)) {
            throw new ServiceException("没有可用下属");
        }
        OrderInfoExample orderExample = buildOrderInfoExample(order);
        OrderInfoExample.Criteria orderCriteria = orderExample.getOredCriteria().get(0);
        orderCriteria.andFactoryConfirmStatusEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        if (DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID.equals(order.getApproved())) {
            orderCriteria.andMerchantConfirmAtIsNull();
        } else if (order.getApproved() != null) {
            orderCriteria.andMerchantConfirmStatusEqualTo(order.getApproved());
        }
        orderCriteria.andFactoryMerchantIdIn(factoryMerchantMapIdList);
        return orderExample;
    }

    private OrderInfoExample getOrderInfoExampleByTrader(ProductBO<QueryReqVO> order) {
        TraderInfo traderInfo = traderRoleService.getTraderInfoByUser(order.getCreatorInfo());
        OrderInfoExample orderExample = buildOrderInfoExample(order);
        OrderInfoExample.Criteria orderCriteria = orderExample.getOredCriteria().get(0);
        if (DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID.equals(order.getApproved())) {
            orderCriteria.andFactoryConfirmAtIsNull();
        } else if (order.getApproved() != null) {
            orderCriteria.andFactoryConfirmStatusEqualTo(order.getApproved());
        }
        orderCriteria.andTraderIdEqualTo(traderInfo.getId());
        return orderExample;
    }

}
