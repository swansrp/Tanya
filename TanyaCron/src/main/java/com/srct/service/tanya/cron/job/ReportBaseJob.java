/**
 * Title: BaseJob
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-16 23:15
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.cron.job
 */
package com.srct.service.tanya.cron.job;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.service.EmailService;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.UserInfoDao;
import com.srct.service.utils.DateUtils;
import com.srct.service.utils.ExcelUtils;
import com.srct.service.utils.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public abstract class ReportBaseJob extends QuartzJobBean {

    protected static final String EMAIL_TITLE = "药e销";

    @Autowired
    protected UserInfoDao userInfoDao;
    @Autowired
    protected EmailService emailService;
    @Autowired
    private FactoryInfoDao factoryInfoDao;

    protected void sendEmail(String email, List<?> resList, String reportType, String attachmentName) {
        if (resList == null || resList.size() == 0) {
            return;
        }
        Date yesterday = DateUtils.yesterdayBeginTime();
        String yesterdayString = DateUtils.formatDate(yesterday, "yyyyMMdd");
        String title = yesterdayString + EMAIL_TITLE + reportType + "报告";
        String fileName = "[" + attachmentName + "]" + title + ExcelUtils.EXCEL_SUFFIX;
        try {
            File f = File.createTempFile("tmp", ExcelUtils.EXCEL_SUFFIX);
            ExcelUtils.generateExcel(resList, f);
            FileSystemResource resource = new FileSystemResource(f);
            emailService.sendEmail(email, title, null, fileName, resource);
            f.deleteOnExit();
        } catch (IllegalStateException | IOException e) {
            Log.e(e);
        }
    }

    protected List<FactoryInfo> getValidFactoryInfoList() {
        Date now = new Date();
        FactoryInfoExample example = new FactoryInfoExample();
        FactoryInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStartAtLessThanOrEqualTo(now);
        criteria.andEndAtGreaterThanOrEqualTo(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        criteria.andUserIdIsNotNull();
        return factoryInfoDao.getFactoryInfoByExample(example);
    }
}
