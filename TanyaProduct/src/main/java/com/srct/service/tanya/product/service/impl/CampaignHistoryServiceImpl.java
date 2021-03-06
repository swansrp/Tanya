/**
 * Title: CampaignHistoryServiceImpl.java Description: Copyright: Copyright (c) 2019 Company:
 * Sharp @Project Name: TanyaProduct @Package: com.srct.service.tanya.product.service.impl
 *
 * @author sharuopeng
 * @date 2019-03-04 20:49:48
 */
package com.srct.service.tanya.product.service.impl;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistory;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignHistoryExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.CampaignHistoryService;
import com.srct.service.tanya.product.vo.CampaignHistoryReqVO;
import com.srct.service.tanya.product.vo.CampaignHistoryRespVO;
import com.srct.service.tanya.product.vo.CampaignHistoryVO;
import com.srct.service.tanya.product.vo.CampaignInfoVO;
import com.srct.service.tanya.role.vo.RoleInfoVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sharuopeng
 */
@Service
public class CampaignHistoryServiceImpl extends ProductServiceBaseImpl implements CampaignHistoryService {

    @Override
    public QueryRespVO<CampaignHistoryRespVO> getCampaignHistory(ProductBO<QueryReqVO> campaignHistory) {
        validateQuery(campaignHistory);
        List<TraderInfo> traderInfoList = super.getTraderInfo(campaignHistory);
        List<Integer> traderInfoIdList = buildTraderInfoIdList(traderInfoList);
        if (campaignHistory.getTraderId() != null) {
            if (traderInfoIdList.contains(campaignHistory.getTraderId())) {
                traderInfoList.clear();
                traderInfoList.add(traderInfoDao.getTraderInfoById(campaignHistory.getTraderId()));
            } else {
                throw new ServiceException("不允许查看销售人员[" + campaignHistory.getTraderId() + "]的信息");
            }
        }
        List<Integer> salesmanInfoIdList = super.buildSalesmanTraderMapSalesmanIdList(campaignHistory, traderInfoList);

        if (campaignHistory.getSalesmanId() != null) {
            if (salesmanInfoIdList.contains(campaignHistory.getSalesmanId())) {
                salesmanInfoIdList.clear();
                salesmanInfoIdList.add(campaignHistory.getSalesmanId());
            } else {
                throw new ServiceException("不允许查看促销人员[" + campaignHistory.getSalesmanId() + "]的信息");
            }
        }

        if (campaignHistory.getCreatorRole().getRole().equals("salesman")) {
            salesmanInfoIdList.clear();
            salesmanInfoIdList.addAll(super.getSalesmanInfoIdListByUserInfo(campaignHistory.getCreatorInfo()));
        }

        List<Integer> campaignInfoIdList = super.buildCampaignInfoIdListByTraderList(campaignHistory, traderInfoList);
        CampaignHistoryExample campaignHistoryExample =
                buildCampaignHistoryExample(campaignHistory, salesmanInfoIdList, campaignInfoIdList);
        return buildResByExample(campaignHistory, campaignHistoryExample);
    }

    private List<Integer> buildTraderInfoIdList(List<TraderInfo> traderInfoList) {
        List<Integer> idList = new ArrayList<>();
        traderInfoList.forEach(traderInfo -> idList.add(traderInfo.getId()));
        return idList;
    }

    private QueryRespVO<CampaignHistoryRespVO> buildResByExample(ProductBO<QueryReqVO> history,
            CampaignHistoryExample campaignHistoryExample) {
        PageInfo<?> pageInfo = super.buildPage(history);
        PageInfo<CampaignHistory> campaignHistoryList =
                campaignHistoryDao.getCampaignHistoryByExample(campaignHistoryExample, pageInfo);

        QueryRespVO<CampaignHistoryRespVO> res = new QueryRespVO<>();
        super.buildRespByReq(res, history);
        res.setTotalPages(campaignHistoryList.getPages());
        res.setTotalSize(campaignHistoryList.getTotal());

        campaignHistoryList.getList()
                .forEach(campaignHistory -> res.getInfo().add(buildCampaignHistoryRespVO(campaignHistory)));
        return res;
    }

    private CampaignHistoryExample buildCampaignHistoryExample(ProductBO<QueryReqVO> campaignHistory,
            List<Integer> salesmanInfoIdList, List<Integer> campaignInfoIdList) {
        CampaignHistoryExample campaignHistoryExample = new CampaignHistoryExample();
        CampaignHistoryExample.Criteria criteria = campaignHistoryExample.createCriteria();

        if (salesmanInfoIdList.size() == 0) {
            salesmanInfoIdList.add(0);
        }
        criteria.andSalesmanIdIn(salesmanInfoIdList);

        if (campaignInfoIdList.size() == 0) {
            campaignInfoIdList.add(0);
        }
        criteria.andCampaignIdIn(campaignInfoIdList);

        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        if (DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID.equals(campaignHistory.getApproved())) {
            criteria.andConfirmAtIsNull();
        } else if (campaignHistory.getApproved() != null) {
            criteria.andConfirmStatusEqualTo(campaignHistory.getApproved());
        }

        if (campaignHistory.getProductId() != null) {
            criteria.andIdEqualTo(campaignHistory.getProductId());
        }
        return campaignHistoryExample;
    }

    @Override
    public QueryRespVO<CampaignHistoryRespVO> updateCampaignHistory(ProductBO<CampaignHistoryReqVO> history) {
        validateUpdate(history);
        UserInfo userInfo = history.getCreatorInfo();
        List<Integer> salesmanInfoIdList = super.getSalesmanInfoIdListByUserInfo(userInfo);
        if (history.getReq().getCampaignHistory().getId() != null) {
            CampaignHistory campaignHistory =
                    campaignHistoryDao.getCampaignHistoryById(history.getReq().getCampaignHistory().getId());
            if (!salesmanInfoIdList.contains(campaignHistory.getSalesmanId())) {
                throw new ServiceException(
                        "不允许" + "[促销员" + userInfo.getName() + "]更新促销历史:" + history.getReq().getCampaignHistory()
                                .getId());
            }
        }

        CampaignSalesmanMapExample campaignSalesmanMapExample =
                super.makeQueryExample(history, CampaignSalesmanMapExample.class);
        CampaignSalesmanMapExample.Criteria campaignSalesmanMapCriteria =
                campaignSalesmanMapExample.getOredCriteria().get(0);
        if (salesmanInfoIdList.size() == 0) {
            salesmanInfoIdList.add(0);
        }
        campaignSalesmanMapCriteria.andSalesmanIdIn(salesmanInfoIdList);
        Integer salesmanId = null;
        List<CampaignSalesmanMap> campaignSalesmanMapList =
                campaignSalesmanMapDao.getCampaignSalesmanMapByExample(campaignSalesmanMapExample);
        for (CampaignSalesmanMap map : campaignSalesmanMapList) {
            if (map.getCampaignId().equals(history.getReq().getCampaignHistory().getCampaignId())) {
                salesmanId = map.getSalesmanId();
                break;
            }
        }
        if (salesmanId == null) {
            throw new ServiceException(
                    "不允许" + "[促销员" + userInfo.getName() + "]更新促销历史:" + history.getReq().getCampaignHistory().getId());
        }
        CampaignHistory campaignHistory = new CampaignHistory();
        BeanUtil.copyProperties(history.getReq().getCampaignHistory(), campaignHistory);
        campaignHistory.setSalesmanId(salesmanId);
        campaignHistory.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        campaignHistoryDao.updateCampaignHistory(campaignHistory);

        QueryRespVO<CampaignHistoryRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildCampaignHistoryRespVO(campaignHistory));
        return res;
    }

    @Override
    public QueryRespVO<CampaignHistoryRespVO> confirmCampaignHistory(ProductBO<QueryReqVO> history, Integer rewards) {
        validateConfirm(history);
        List<TraderInfo> traderInfoList = super.getTraderInfo(history);
        // only trader allow to confirm
        TraderInfo traderInfo = traderInfoList.get(0);
        List<Integer> salesmanInfoIdList = buildSalesmanTraderMapSalesmanIdList(history, traderInfoList);

        CampaignHistory campaignHistory = campaignHistoryDao.getCampaignHistoryById(history.getProductId());
        if (!salesmanInfoIdList.contains(campaignHistory.getSalesmanId())) {
            throw new ServiceException(
                    "dont allow to approve/reject campaign history : " + campaignHistory.getId() + " by trader "
                            + traderInfo.getId());
        }

        if (history.getApproved().equals(DataSourceCommonConstant.DATABASE_COMMON_VALID)) {
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
        campaignHistory.setConfirmBy(super.getRoleInfoVOByReq(history).getId());
        campaignHistoryDao.updateCampaignHistory(campaignHistory);
        QueryRespVO<CampaignHistoryRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildCampaignHistoryRespVO(campaignHistory));
        return res;
    }

    private CampaignHistoryRespVO buildCampaignHistoryRespVO(CampaignHistory campaignHistory) {
        CampaignHistoryVO campaignHistoryVO = new CampaignHistoryVO();
        BeanUtil.copyProperties(campaignHistory, campaignHistoryVO);

        CampaignHistoryRespVO res = new CampaignHistoryRespVO();
        BeanUtil.copyProperties(campaignHistory, res);

        RoleInfoVO salesmanInfoVO = super.getRoleInfoVO(campaignHistory.getSalesmanId(), "salesman");
        CampaignInfoVO campaignInfoVO = super.getCampaignInfoVOById(campaignHistory.getCampaignId());
        CampaignInfo campaignInfo = super.campaignInfoDao.getCampaignInfoById(campaignHistory.getCampaignId());
        RoleInfoVO confirmRoleInfoVO = super.getRoleInfoVO(campaignHistory.getConfirmBy(), "trader");
        RoleInfoVO setupRoleInfoVO = super.getRoleInfoVO(campaignInfo.getTraderId(), "trader");
        res.setCampaignInfoVO(campaignInfoVO);
        res.setCampaignHistoryVO(campaignHistoryVO);
        res.setSalesmanInfoVO(salesmanInfoVO);
        res.setConfirmRoleInfoVO(confirmRoleInfoVO);
        res.setSetupRoleInfoVO(setupRoleInfoVO);
        return res;
    }

    @Override
    public QueryRespVO<CampaignHistoryRespVO> delCampaignHistory(ProductBO<CampaignHistoryReqVO> campaignHistory) {
        validateDelete(campaignHistory);
        UserInfo userInfo = campaignHistory.getCreatorInfo();
        CampaignHistory history = campaignHistoryDao.getCampaignHistoryById(campaignHistory.getProductId());
        List<Integer> salesmanInfoIdList = super.getSalesmanInfoIdListByUserInfo(userInfo);
        if (!salesmanInfoIdList.contains(history.getSalesmanId())) {
            throw new ServiceException("不允许促销员[" + userInfo.getName() + "]删除促销历史: " + history.getId());
        }

        if (history.getConfirmStatus().equals(DataSourceCommonConstant.DATABASE_COMMON_VALID)) {
            throw new ServiceException("Dont allow to del the history which is approved");
        }

        campaignHistoryDao.delCampaignHistory(history);

        QueryRespVO<CampaignHistoryRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildCampaignHistoryRespVO(history));
        return res;
    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (roleType.equals("merchant")) {
            throw new ServiceException("角色[" + roleType + "]不允许查看促销历史");
        }
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("salesman")) {
            throw new ServiceException("角色[" + roleType + "]不允许更新促销历史");
        }
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("角色[" + roleType + "]不允许确认促销历史");
        }
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("salesman")) {
            throw new ServiceException("角色[" + roleType + "]不允许删除促销历史");
        }
    }
}
