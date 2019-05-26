/**
 * Title: CamgainHistoryCronTaskServiceImpl
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-15 23:05
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.cron.service.impl
 */
package com.srct.service.tanya.cron.service.impl;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistory;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistoryExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.cron.service.CampaignHistoryCronTaskService;
import com.srct.service.tanya.cron.vo.CampaignHistoryVO;
import com.srct.service.utils.DateUtils;
import com.srct.service.utils.ReflectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CampaignHistoryCronTaskServiceImpl extends CronTaskBaseSerivceImpl
        implements CampaignHistoryCronTaskService {
    @Override
    public List<CampaignHistoryVO> getCampaignHistoryListByFactoryId(Integer factoryId) {
        List<CampaignHistoryVO> resList = new ArrayList<>();
        List<TraderFactoryMerchantMap> traderFactoryMerchantMapList =
                getTraderFactoryMerchantListByFactoryId(factoryId);
        if (CollectionUtils.isEmpty(traderFactoryMerchantMapList)) {
            return null;
        }
        List<Integer> traderInfoIdList =
                (List<Integer>) ReflectionUtil.getFieldList(traderFactoryMerchantMapList, "traderId");
        traderInfoIdList.forEach(traderInfoId -> {
            TraderInfo traderInfo = traderInfoDao.getTraderInfoById(traderInfoId);
            if (traderInfo.getUserId() == null) {
                return;
            }
            UserInfo traderUserInfo = userInfoDao.getUserInfoById(traderInfo.getUserId());
            List<CampaignInfo> campaignInfoList = getCampaignInfoListByTraderInfoId(traderInfoId);
            List<Integer> campaignInfoIdList = (List<Integer>) ReflectionUtil.getFieldList(campaignInfoList, "id");
            if (CollectionUtils.isEmpty(campaignInfoIdList)) {
                return;
            }
            campaignInfoIdList.forEach(campaignInfoId -> {
                CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfoById(campaignInfoId);
                GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfoById(campaignInfo.getGoodsId());
                List<CampaignHistory> campaignHistoryList = getCampaignHistoryListByCampaignInfoId(campaignInfoId);
                if (CollectionUtils.isEmpty(campaignHistoryList)) {
                    return;
                }
                campaignHistoryList.forEach(campaignHistory -> {
                    CampaignHistoryVO res = new CampaignHistoryVO();
                    SalesmanInfo salesmanInfo = salesmanInfoDao.getSalesmanInfoById(campaignHistory.getSalesmanId());
                    buildRes(res, traderInfo, traderUserInfo, goodsInfo, salesmanInfo, campaignHistory);
                    resList.add(res);
                });
            });
        });
        return resList;
    }

    private void buildRes(CampaignHistoryVO res, TraderInfo traderInfo, UserInfo traderUserInfo, GoodsInfo goodsInfo,
            SalesmanInfo salesmanInfo, CampaignHistory campaignHistory) {
        String campaignHistoryId =
                DateUtils.dateToString(campaignHistory.getCreateAt()) + salesmanInfo.getId() + campaignHistory.getId()
                        + salesmanInfo.getId();
        res.setCampaignHistoryId(campaignHistoryId);
        res.setStartAt(campaignHistory.getCreateAt());
        res.setTraderTitle(traderInfo.getTitle());
        res.setTraderUserName(traderUserInfo.getName());
        res.setSalesmanTitle(salesmanInfo.getTitle());
        res.setGoodsNumber(campaignHistory.getNumber());
        res.setGoodsProduct(goodsInfo.getProduction());
        res.setGoodsSpec(goodsInfo.getSpec());
        res.setGoodsTitle(goodsInfo.getTitle());
        res.setConfirmStatus(super.getConfirmStatus(campaignHistory.getConfirmStatus()));
    }

    private List<CampaignHistory> getCampaignHistoryListByCampaignInfoId(Integer campaignInfoId) {
        Date yesterdayBeginTime = DateUtils.yesterdayBeginTime();
        Date yesterdayEndTime = DateUtils.yesterdayEndTime();
        CampaignHistoryExample example = new CampaignHistoryExample();
        CampaignHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andCampaignIdEqualTo(campaignInfoId);
        criteria.andUpdateAtGreaterThanOrEqualTo(yesterdayBeginTime);
        criteria.andUpdateAtLessThanOrEqualTo(yesterdayEndTime);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return campaignHistoryDao.getCampaignHistoryByExample(example);
    }

    private List<CampaignInfo> getCampaignInfoListByTraderInfoId(Integer traderInfoId) {
        CampaignInfo campaignInfo =
                CampaignInfo.builder().traderId(traderInfoId).valid(DataSourceCommonConstant.DATABASE_COMMON_VALID)
                        .build();
        return campaignInfoDao.getCampaignInfoSelective(campaignInfo);
    }
}
