/**
 * Title: CampaignHistoryServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-03-04 20:49:48
 */
package com.srct.service.tanya.product.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistory;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistoryExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.CampaignHistoryService;
import com.srct.service.tanya.product.vo.CampaignHistoryReqVO;
import com.srct.service.tanya.product.vo.CampaignHistoryRespVO;
import com.srct.service.tanya.product.vo.CampaignHistoryVO;
import com.srct.service.tanya.product.vo.CampaignInfoVO;
import com.srct.service.tanya.role.vo.RoleInfoVO;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author sharuopeng
 *
 */
@Service
public class CampaignHistoryServiceImpl extends ProductServiceBaseImpl implements CampaignHistoryService {

    @Override
    public QueryRespVO<CampaignHistoryRespVO> getCampaignHistory(ProductBO<QueryReqVO> campaignHistory) {
        validateQuery(campaignHistory);
        List<TraderInfo> traderInfoList = super.getTraderInfo(campaignHistory);
        List<Integer> salesmanInfoIdList = buildSalesmanTraderMapSalesmanIdList(campaignHistory, traderInfoList);

        if (campaignHistory.getSalesmanId() != null) {
            if (salesmanInfoIdList.contains(campaignHistory.getSalesmanId())) {
                salesmanInfoIdList.clear();
                salesmanInfoIdList.add(campaignHistory.getSalesmanId());
            } else {
                throw new ServiceException("dont allow to query salesmanId " + campaignHistory.getSalesmanId());
            }
        }
        CampaignHistoryExample campaignHistoryExample =
            buildCampaignHistoryExample(campaignHistory, salesmanInfoIdList);
        QueryRespVO<CampaignHistoryRespVO> res = buildResByExample(campaignHistory, campaignHistoryExample);

        return res;
    }

    /**
     * @param campaignHistory
     * @param campaignHistoryExample
     * @return
     */
    private QueryRespVO<CampaignHistoryRespVO>
        buildResByExample(ProductBO<QueryReqVO> history, CampaignHistoryExample campaignHistoryExample) {
        PageInfo<?> pageInfo = super.buildPage(history);
        List<CampaignHistory> campaignHistoryList =
            campaignHistoryDao.getCampaignHistoryByExample(campaignHistoryExample, pageInfo);

        QueryRespVO<CampaignHistoryRespVO> res = new QueryRespVO<CampaignHistoryRespVO>();
        super.buildRespbyReq(res, history);
        res.setPageSize(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());

        campaignHistoryList.forEach(campaignHistory -> {
            res.getInfo().add(buildCampaignHistoryRespVO(campaignHistory));
        });
        return res;
    }

    /**
     * @param campaignHistory
     * @param salesmanInfoIdList
     * @return
     */
    private CampaignHistoryExample
        buildCampaignHistoryExample(ProductBO<QueryReqVO> campaignHistory, List<Integer> salesmanInfoIdList) {
        CampaignHistoryExample campaignHistoryExample = new CampaignHistoryExample();
        CampaignHistoryExample.Criteria criteria = campaignHistoryExample.createCriteria();
        criteria.andSalesmanIdIn(salesmanInfoIdList);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        if (campaignHistory.getApproved() != null) {
            if (campaignHistory.getApproved()) {
                criteria.andConfirmStatusEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            } else {
                criteria.andConfirmStatusEqualTo(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            }
        }
        if (campaignHistory.getProductId() != null) {
            criteria.andIdEqualTo(campaignHistory.getProductId());
        }
        return campaignHistoryExample;
    }

    @Override
    public QueryRespVO<CampaignHistoryRespVO> updateCampaignHistory(ProductBO<CampaignHistoryReqVO> history) {
        validateUpdate(history);
        UserInfo userInfo = history.getCreaterInfo();
        SalesmanInfo salesmanInfo = salesmanRoleService.getSalesmanInfoByUser(userInfo);

        if (history.getReq().getCampaignHistory().getId() != null) {
            CampaignHistory campaignHistory =
                campaignHistoryDao.getCampaignHistorybyId(history.getReq().getCampaignHistory().getId());
            if (campaignHistory.getSalesmanId() != salesmanInfo.getId()) {
                throw new ServiceException("Dont allow to update the history: "
                    + history.getReq().getCampaignHistory().getId() + " by salesman: " + salesmanInfo.getId());
            }
        }

        CampaignHistory campaignHistory = new CampaignHistory();
        BeanUtil.copyProperties(history.getReq().getCampaignHistory(), campaignHistory);
        campaignHistory.setSalesmanId(salesmanInfo.getId());
        campaignHistory.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        campaignHistoryDao.updateCampaignHistory(campaignHistory);

        QueryRespVO<CampaignHistoryRespVO> res = new QueryRespVO<CampaignHistoryRespVO>();
        res.getInfo().add(buildCampaignHistoryRespVO(campaignHistory));
        return res;
    }

    @Override
    public QueryRespVO<CampaignHistoryRespVO> confirmCampaignHistory(ProductBO<QueryReqVO> history, Integer rewards) {
        validateConfirm(history);
        List<TraderInfo> traderInfoList = super.getTraderInfo(history);
        TraderInfo traderInfo = traderInfoList.get(0);
        List<Integer> salesmanInfoIdList = buildSalesmanTraderMapSalesmanIdList(history, traderInfoList);

        CampaignHistory campaignHistory = campaignHistoryDao.getCampaignHistorybyId(history.getProductId());
        if (!salesmanInfoIdList.contains(campaignHistory.getSalesmanId())) {
            throw new ServiceException("dont allow to approve/reject campaign history : " + campaignHistory.getId()
                + " by trader " + traderInfo.getId());
        }

        if (history.getApproved()) {
            campaignHistory.setConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            if (rewards != null) {
                campaignHistory.setRewards(rewards);
            } else {
                campaignHistory.setRewards(0);
            }
        } else {
            campaignHistory.setConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            campaignHistory.setRewards(0);
        }
        campaignHistory.setConfirmAt(new Date());
        campaignHistoryDao.updateCampaignHistory(campaignHistory);
        QueryRespVO<CampaignHistoryRespVO> res = new QueryRespVO<CampaignHistoryRespVO>();
        res.getInfo().add(buildCampaignHistoryRespVO(campaignHistory));
        return res;
    }

    private CampaignHistoryRespVO buildCampaignHistoryRespVO(CampaignHistory campaignHistory) {
        CampaignHistoryVO campaignHistoryVO = new CampaignHistoryVO();
        BeanUtil.copyProperties(campaignHistory, campaignHistoryVO);

        CampaignHistoryRespVO res = new CampaignHistoryRespVO();
        BeanUtil.copyProperties(campaignHistory, res);

        RoleInfoVO salesmanInfoVO = super.getRoleInfoVO(campaignHistory.getSalesmanId(), "salesman");
        CampaignInfoVO campaignInfoVO = super.getCampaignInfoVObyId(campaignHistory.getCampaignId());
        res.setCampaignInfoVO(campaignInfoVO);
        res.setCampaignHistoryVO(campaignHistoryVO);
        res.setSalesmanInfoVO(salesmanInfoVO);
        return res;
    }

    @Override
    public QueryRespVO<CampaignHistoryRespVO> delCampaignHistory(ProductBO<CampaignHistoryReqVO> campaignHistory) {
        validateDelete(campaignHistory);
        UserInfo userInfo = campaignHistory.getCreaterInfo();
        SalesmanInfo salesmanInfo = salesmanRoleService.getSalesmanInfoByUser(userInfo);
        CampaignHistory history = campaignHistoryDao.getCampaignHistorybyId(campaignHistory.getProductId());
        if (history.getSalesmanId() != salesmanInfo.getId()) {
            throw new ServiceException(
                "Dont allow to del the history: " + history.getId() + " by salesman: " + salesmanInfo.getId());
        }

        if (history.getConfirmStatus().equals(DataSourceCommonConstant.DATABASE_COMMON_VALID)) {
            throw new ServiceException("Dont allow to del the history which is approved");
        }

        campaignHistoryDao.delCampaignHistory(history);

        QueryRespVO<CampaignHistoryRespVO> res = new QueryRespVO<CampaignHistoryRespVO>();
        res.getInfo().add(buildCampaignHistoryRespVO(history));
        return res;
    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("dont allow to confirm campaign history by role " + roleType);
        }
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("salesman")) {
            throw new ServiceException("dont allow to report campaign history by role " + roleType);
        }

    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("dont allow to confirm campaign history by role " + roleType);
        }

    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("salesman")) {
            throw new ServiceException("dont allow to delete campaign history by role " + roleType);
        }
    }

}
