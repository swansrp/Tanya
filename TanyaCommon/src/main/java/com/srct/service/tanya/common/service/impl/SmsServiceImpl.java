package com.srct.service.tanya.common.service.impl;

import com.srct.service.bo.sms.SendSmsReq;
import com.srct.service.service.sms.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Title: SmsServiceImpl
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2020/3/4 15:56
 * @description Project Name: Tanya
 * @Package: com.srct.service.tanya.common.service.impl
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public void sendSms(SendSmsReq sendSmsReq) {
        log.info("Dont support Send Sms");
    }
}
