/**
 * Title: CronTaskBaseSerivceImpl
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-17 22:49
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.cron.service.impl
 */
package com.srct.service.tanya.cron.service.impl;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignHistoryDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.DiscountInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.MerchantInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.OrderInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.ShopInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderFactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class CronTaskBaseSerivceImpl {

    private static final String CONFIRM_STATUS_CONFIRMED = "确认";
    private static final String CONFIRM_STATUS_REJECTED = "拒绝";
    private static final String CONFIRM_STATUS_PENDING = "待审";
    @Autowired
    protected UserInfoDao userInfoDao;
    @Autowired
    protected OrderInfoDao orderInfoDao;
    @Autowired
    protected DiscountInfoDao discountInfoDao;
    @Autowired
    protected CampaignInfoDao campaignInfoDao;
    @Autowired
    protected CampaignHistoryDao campaignHistoryDao;
    @Autowired
    protected GoodsInfoDao goodsInfoDao;
    @Autowired
    protected ShopInfoDao shopInfoDao;
    @Autowired
    protected MerchantInfoDao merchantInfoDao;
    @Autowired
    protected FactoryInfoDao factoryInfoDao;
    @Autowired
    protected TraderInfoDao traderInfoDao;
    @Autowired
    protected SalesmanInfoDao salesmanInfoDao;
    @Autowired
    protected FactoryMerchantMapDao factoryMerchantMapDao;
    @Autowired
    protected TraderFactoryMerchantMapDao traderFactoryMerchantMapDao;

    protected List<TraderFactoryMerchantMap> getTraderFactoryMerchantListByFactoryId(Integer factoryId) {
        Date now = new Date();
        TraderFactoryMerchantMapExample example = new TraderFactoryMerchantMapExample();
        TraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andFactoryIdEqualTo(factoryId);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        criteria.andEndAtGreaterThanOrEqualTo(now);
        criteria.andStartAtLessThanOrEqualTo(now);
        return traderFactoryMerchantMapDao.getTraderFactoryMerchantMapByExample(example);
    }

    protected List<FactoryMerchantMap> getFactoryMerchantListByFactoryId(Integer factoryId) {
        Date now = new Date();
        FactoryMerchantMapExample example = new FactoryMerchantMapExample();
        FactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andFactoryIdEqualTo(factoryId);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        criteria.andEndAtGreaterThanOrEqualTo(now);
        criteria.andStartAtLessThanOrEqualTo(now);
        return factoryMerchantMapDao.getFactoryMerchantMapByExample(example);
    }

    protected String getConfirmStatus(Byte valid) {
        if ((DataSourceCommonConstant.DATABASE_COMMON_VALID).equals(valid)) {
            return CONFIRM_STATUS_CONFIRMED;
        } else if ((DataSourceCommonConstant.DATABASE_COMMON_INVALID).equals(valid)) {
            return CONFIRM_STATUS_REJECTED;
        } else {
            return CONFIRM_STATUS_PENDING;
        }
    }
}
