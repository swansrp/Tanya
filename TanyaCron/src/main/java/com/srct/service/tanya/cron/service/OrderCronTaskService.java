package com.srct.service.tanya.cron.service;

import com.srct.service.tanya.cron.vo.OrderInfoVO;

import java.util.List;

/**
 * Title: OrderCronTaskService
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-15 23:04
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.cron.service.impl
 */
public interface OrderCronTaskService {

    /**
     * get Order List by Factory Id
     *
     * @param factoryId factory id
     * @return List<OrderInfoVO>
     */
    List<OrderInfoVO> getOrderListByFactoryId(Integer factoryId);

    /**
     * get Order List by Merchant Id
     *
     * @param merchantId merchant id
     * @return List<OrderInfoVO>
     */
    List<OrderInfoVO> getOrderListByMerchantId(Integer merchantId);
}
