/**
 * Title: CampaignHistoryJob
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-16 15:31
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.cron.job
 */
package com.srct.service.tanya.cron.job;

import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.cron.service.CampaignHistoryCronTaskService;
import com.srct.service.tanya.cron.vo.CampaignHistoryVO;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CampaignHistoryJob extends ReportBaseJob {

    private static final String reportType = "促销";
    @Autowired
    private CampaignHistoryCronTaskService campaignHistoryCronTaskService;

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<FactoryInfo> factoryInfos = getValidFactoryInfoList();
        if (factoryInfos == null || factoryInfos.size() == 0) {
            return;
        }
        factoryInfos.forEach(factoryInfo -> {
            UserInfo userInfo = userInfoDao.getUserInfoById(factoryInfo.getUserId());
            String email = userInfo.getEmail();
            if (email == null) {
                return;
            }
            List<CampaignHistoryVO> campaignHistoryVOList =
                    campaignHistoryCronTaskService.getCampaignHistoryListByFactoryId(factoryInfo.getId());
            if (campaignHistoryVOList != null && campaignHistoryVOList.size() > 0) {
                super.sendEmail(email, campaignHistoryVOList, reportType, factoryInfo.getTitle());
            }
        });
    }
}
