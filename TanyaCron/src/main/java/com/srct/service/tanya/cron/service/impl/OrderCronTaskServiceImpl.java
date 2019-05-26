/**
 * Title: OrderCronTaskServiceImpl
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-15 23:06
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.cron.service.impl
 */
package com.srct.service.tanya.cron.service.impl;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.cron.service.OrderCronTaskService;
import com.srct.service.tanya.cron.vo.OrderInfoVO;
import com.srct.service.utils.DateUtils;
import com.srct.service.utils.MathUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderCronTaskServiceImpl extends CronTaskBaseSerivceImpl implements OrderCronTaskService {

    @Override
    public List<OrderInfoVO> getOrderListByFactoryId(Integer factoryId) {
        List<OrderInfoVO> resList = new ArrayList<>();
        List<TraderFactoryMerchantMap> traderFactoryMerchantMapList =
                getTraderFactoryMerchantListByFactoryId(factoryId);
        if (CollectionUtils.isEmpty(traderFactoryMerchantMapList)) {
            return null;
        }
        traderFactoryMerchantMapList.forEach(map -> {
            FactoryMerchantMap factoryMerchantMap =
                    factoryMerchantMapDao.getFactoryMerchantMapById(map.getFactoryMerchantMapId());
            MerchantInfo merchantInfo = merchantInfoDao.getMerchantInfoById(factoryMerchantMap.getMerchantId());
            TraderInfo traderInfo = traderInfoDao.getTraderInfoById(map.getTraderId());
            if (traderInfo.getUserId() == null) {
                return;
            }
            UserInfo userInfo = userInfoDao.getUserInfoById(traderInfo.getUserId());
            List<OrderInfo> orderInfos = getOrderInfoListByTraderFactoryMerchantId(map.getId());
            if (CollectionUtils.isEmpty(orderInfos)) {
                return;
            }
            orderInfos.forEach(orderInfo -> {
                OrderInfoVO res = new OrderInfoVO();
                GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfoById(orderInfo.getGoodsId());
                if (orderInfo.getDiscountId() != null) {
                    DiscountInfo discountInfo = discountInfoDao.getDiscountInfoById(orderInfo.getDiscountId());
                    Double price =
                            MathUtil.div(discountInfo.getAmount(), discountInfo.getGoodsNumber().doubleValue(), 2);
                    res.setGoodsPrice(price);
                    res.setGoodsNumber(discountInfo.getGoodsNumber() * orderInfo.getGoodsNumber());
                } else {
                    res.setGoodsPrice(goodsInfo.getAmount());
                    res.setGoodsNumber(orderInfo.getGoodsNumber());
                }
                ShopInfo shopInfo = shopInfoDao.getShopInfoById(orderInfo.getShopId());
                buildRes(res, orderInfo, merchantInfo, userInfo, traderInfo, goodsInfo, shopInfo);
                resList.add(res);
            });
        });
        return resList;

    }

    @Override
    public List<OrderInfoVO> getOrderListByMerchantId(Integer merchantId) {
        return null;
    }

    private void buildRes(OrderInfoVO res, OrderInfo orderInfo, MerchantInfo merchantInfo, UserInfo userInfo,
            TraderInfo traderInfo, GoodsInfo goodsInfo, ShopInfo shopInfo) {
        String orderId = DateUtils.dateToString(orderInfo.getCreateAt()) + traderInfo.getId() + orderInfo.getId();
        res.setOrderId(orderId);
        res.setStartAt(orderInfo.getStartAt());
        res.setTraderTitle(traderInfo.getTitle());
        res.setTraderUserName(userInfo.getName());
        res.setShopTitle(shopInfo.getTitle());
        res.setGoodsProduct(goodsInfo.getProduction());
        res.setGoodsSpec(goodsInfo.getSpec());
        res.setGoodsTitle(goodsInfo.getTitle());
        res.setFactoryConfirmStatus(super.getConfirmStatus(orderInfo.getFactoryConfirmStatus()));
        res.setMerchantConfirmStatus(super.getConfirmStatus(orderInfo.getMerchantConfirmStatus()));
        res.setMerchantConfirmNumber(orderInfo.getMerchantConfirmNumber());
        res.setMerchantTitle(merchantInfo.getTitle());

    }

    private List<OrderInfo> getOrderInfoListByTraderFactoryMerchantId(Integer id) {
        Date yesterdayBeginTime = DateUtils.yesterdayBeginTime();
        Date yesterdayEndTime = DateUtils.yesterdayEndTime();
        OrderInfoExample example = new OrderInfoExample();
        OrderInfoExample.Criteria criteria = example.createCriteria();
        criteria.andTraderFactoryMerchantIdEqualTo(id);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        criteria.andUpdateAtGreaterThanOrEqualTo(yesterdayBeginTime);
        criteria.andUpdateAtLessThanOrEqualTo(yesterdayEndTime);
        return orderInfoDao.getOrderInfoByExample(example);
    }
}
