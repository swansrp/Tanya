/**
 * Title: OrderInfoJob
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-16 15:30
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.cron.job
 */
package com.srct.service.tanya.cron.job;

import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.cron.service.OrderCronTaskService;
import com.srct.service.tanya.cron.vo.OrderInfoVO;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderInfoJob extends ReportBaseJob {

    private static final String reportType = "订单";
    @Autowired
    private OrderCronTaskService orderCronTaskService;


    @Override
    protected void executeInternal(JobExecutionContext context) {

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
            List<OrderInfoVO> orderInfoVOList = orderCronTaskService.getOrderListByFactoryId(factoryInfo.getId());
            if (orderInfoVOList != null && orderInfoVOList.size() > 0) {
                super.sendEmail(email, orderInfoVOList, reportType, factoryInfo.getTitle());
            }
        });
    }


}
