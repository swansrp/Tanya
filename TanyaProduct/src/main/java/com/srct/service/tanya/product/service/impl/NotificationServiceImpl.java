/**
 * Title: NotificationServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-28 18:20:08
 */
package com.srct.service.tanya.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.NotificationInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.NotificationInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.repository.NotificationInfoDao;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.NotificationService;
import com.srct.service.tanya.product.vo.NotificationInfoReqVO;
import com.srct.service.tanya.product.vo.NotificationInfoRespVO;
import com.srct.service.tanya.product.vo.NotificationInfoVO;
import com.srct.service.tanya.role.vo.RoleInfoVO;
import com.srct.service.utils.BeanUtil;

/**
 * @author sharuopeng
 *
 */
@Service
public class NotificationServiceImpl extends ProductServiceBaseImpl implements NotificationService {

    @Autowired
    private NotificationInfoDao notificationInfoDao;

    @Override
    public QueryRespVO<NotificationInfoRespVO> updateNotificationInfo(ProductBO<NotificationInfoReqVO> notification) {
        validateUpdate(notification);
        FactoryInfo factoryInfo = super.getFactoryInfo(notification);

        NotificationInfo notificationInfo = null;
        if (notification.getReq().getNotification().getId() != null) {
            notificationInfo =
                notificationInfoDao.getNotificationInfobyId(notification.getReq().getNotification().getId());
            if (notificationInfo == null || notificationInfo.getFactoryId() != factoryInfo.getId()) {
                throw new ServiceException(
                    "The notification dont belong to your factory - trader: " + notificationInfo.getId());
            }
        } else {
            notificationInfo = new NotificationInfo();
        }
        BeanUtil.copyProperties(notification.getReq().getNotification(), notificationInfo);
        notificationInfo.setFactoryId(factoryInfo.getId());
        notificationInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        notificationInfoDao.updateNotificationInfo(notificationInfo);

        QueryRespVO<NotificationInfoRespVO> res = new QueryRespVO<NotificationInfoRespVO>();
        res.setInfo(new ArrayList<>());
        NotificationInfoRespVO notificationInfoRespVO = buildNotificationInfoRespVO(notificationInfo);
        res.getInfo().add(notificationInfoRespVO);
        return res;

    }

    @Override
    public QueryRespVO<NotificationInfoRespVO> getNotificationInfo(ProductBO<QueryReqVO> notification) {
        FactoryInfo factoryInfo = super.getFactoryInfo(notification);
        NotificationInfoExample example = super.makeQueryExample(notification, NotificationInfoExample.class);
        if (notification.getProductId() != null) {
            example.getOredCriteria().get(0).andIdEqualTo(notification.getProductId());
        }
        example.getOredCriteria().get(0).andFactoryIdEqualTo(factoryInfo.getId());

        PageInfo<?> pageInfo = super.buildPage(notification);
        List<NotificationInfo> notificationInfoList =
            notificationInfoDao.getNotificationInfoByExample(example, pageInfo);

        QueryRespVO<NotificationInfoRespVO> res = new QueryRespVO<NotificationInfoRespVO>();
        super.buildRespbyReq(res, notification);
        res.setTotalPages(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());

        notificationInfoList.forEach(notificationInfo -> {
            NotificationInfoRespVO notificationInfoRespVO = buildNotificationInfoRespVO(notificationInfo);
            res.getInfo().add(notificationInfoRespVO);
        });

        return res;
    }

    /**
     * @param notificationInfo
     * @return
     */
    private NotificationInfoRespVO buildNotificationInfoRespVO(NotificationInfo notificationInfo) {
        NotificationInfoRespVO notificationInfoRespVO = new NotificationInfoRespVO();
        NotificationInfoVO vo = new NotificationInfoVO();
        BeanUtil.copyProperties(notificationInfo, vo);

        RoleInfoVO factoryInfoVO = super.getRoleInfoVO(notificationInfo.getFactoryId(), "factory");

        notificationInfoRespVO.setNotificationInfoVO(vo);
        notificationInfoRespVO.setFactoryInfoVO(factoryInfoVO);

        return notificationInfoRespVO;
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("factory")) {
            throw new ServiceException("dont allow to update notification by role " + roleType);
        }

    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        throw new ServiceException("dont support confirm notification ");

    }

}
