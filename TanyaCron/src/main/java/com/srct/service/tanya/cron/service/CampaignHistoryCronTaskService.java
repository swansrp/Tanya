/**
 * Title: CamgainHistoryCronTaskService
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-15 23:05
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.cron.service
 */
package com.srct.service.tanya.cron.service;

import com.srct.service.tanya.cron.vo.CampaignHistoryVO;

import java.util.List;

public interface CampaignHistoryCronTaskService {
    /**
     * get CampaignHistory List by Factory Id
     *
     * @param factoryId factory id
     * @return List<CampaignHistoryVO>
     */
    List<CampaignHistoryVO> getCampaignHistoryListByFactoryId(Integer factoryId);

}
