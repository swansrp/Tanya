/**
 * Title: NotificationServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-28 18:20:08
 */
package com.srct.service.tanya.product.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sharuopeng
 */
@Service
public class NotificationServiceImpl extends ProductServiceBaseImpl implements NotificationService {

    @Autowired
    private NotificationInfoDao notificationInfoDao;

    @Override
    public QueryRespVO<NotificationInfoRespVO> updateNotificationInfo(ProductBO<NotificationInfoReqVO> notification) {
        validateUpdate(notification);
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(notification);
        FactoryInfo factoryInfo = factoryInfoList.get(0);

        NotificationInfo notificationInfo;
        if (notification.getReq().getNotification().getId() != null) {
            notificationInfo =
                    notificationInfoDao.getNotificationInfobyId(notification.getReq().getNotification().getId());
            if (notificationInfo == null || !notificationInfo.getFactoryId().equals(factoryInfo.getId())) {
                throw new ServiceException("无权观看此公告");
            }
        } else {
            notificationInfo = new NotificationInfo();
        }
        BeanUtil.copyProperties(notification.getReq().getNotification(), notificationInfo);
        notificationInfo.setFactoryId(factoryInfo.getId());
        notificationInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        notificationInfoDao.updateNotificationInfo(notificationInfo);

        QueryRespVO<NotificationInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildNotificationInfoRespVO(notificationInfo));
        return res;

    }

    @Override
    public QueryRespVO<NotificationInfoRespVO> getNotificationInfo(ProductBO<QueryReqVO> notification) {
        validateQuery(notification);
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(notification);
        FactoryInfo factoryInfo = factoryInfoList.get(0);
        NotificationInfoExample example = super.makeQueryExample(notification, NotificationInfoExample.class);
        if (notification.getProductId() != null) {
            example.getOredCriteria().get(0).andIdEqualTo(notification.getProductId());
        }
        example.getOredCriteria().get(0).andFactoryIdEqualTo(factoryInfo.getId());

        PageInfo<?> pageInfo = super.buildPage(notification);
        List<NotificationInfo> notificationInfoList =
                notificationInfoDao.getNotificationInfoByExample(example, pageInfo);

        QueryRespVO<NotificationInfoRespVO> res = new QueryRespVO<>();
        super.buildRespbyReq(res, notification);
        res.setTotalPages(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());

        notificationInfoList.forEach(notificationInfo -> {
            NotificationInfoRespVO notificationInfoRespVO = buildNotificationInfoRespVO(notificationInfo);
            res.getInfo().add(notificationInfoRespVO);
        });

        return res;
    }

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

    @Override
    public QueryRespVO<NotificationInfoRespVO> delNotificationInfo(ProductBO<NotificationInfoReqVO> notification) {
        validateDelete(notification);
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(notification);
        FactoryInfo factoryInfo = factoryInfoList.get(0);
        NotificationInfo notificationInfo = notificationInfoDao.getNotificationInfobyId(notification.getProductId());
        if (!notificationInfo.getFactoryId().equals(factoryInfo.getId())) {
            throw new ServiceException(
                    "Dont allow to delete notification " + notificationInfo.getFactoryId() + " by factory "
                            + factoryInfo.getId());
        }
        notificationInfoDao.delNotificationInfo(notificationInfo);
        QueryRespVO<NotificationInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildNotificationInfoRespVO(notificationInfo));
        return res;
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("factory")) {
            throw new ServiceException("dont allow to delete notification by role " + roleType);
        }
    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("merchant") || roleType.equals("salesman")) {
            throw new ServiceException("dont allow to query notification by role " + roleType);
        }

    }

}
