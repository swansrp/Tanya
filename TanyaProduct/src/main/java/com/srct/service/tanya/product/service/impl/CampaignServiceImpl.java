/**
 * Title: CampaignServiceImpl.java Description: Copyright: Copyright (c) 2019 Company:
 * Sharp @Project Name: TanyaProduct @Package: com.srct.service.tanya.product.service.impl
 *
 * @author sharuopeng
 * @date 2019-02-20 09:32:39
 */
package com.srct.service.tanya.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.CampaignService;
import com.srct.service.tanya.product.vo.CampaignInfoReqVO;
import com.srct.service.tanya.product.vo.CampaignInfoRespVO;
import com.srct.service.tanya.product.vo.CampaignInfoVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.role.vo.RoleInfoVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sharuopeng
 */
@Service
public class CampaignServiceImpl extends ProductServiceBaseImpl implements CampaignService {

    @Override
    public QueryRespVO<CampaignInfoRespVO> getCampaignInfo(ProductBO<QueryReqVO> campaign) {
        if (campaign.getCreaterRole().getRole().equals("salesman")) {
            List<Integer> salesmanInfoIdList = super.getSalesmanInfoIdListByUserInfo(campaign.getCreaterInfo());

            CampaignSalesmanMapExample campaignSalesmanMapExample =
                    super.makeQueryExample(campaign, CampaignSalesmanMapExample.class);
            CampaignSalesmanMapExample.Criteria campaignSalesmanMapCriteria =
                    campaignSalesmanMapExample.getOredCriteria().get(0);

            campaignSalesmanMapCriteria.andSalesmanIdIn(salesmanInfoIdList);
            if (campaign.getProductId() != null) {
                campaignSalesmanMapCriteria.andCampaignIdEqualTo(campaign.getProductId());
            }
            return buildResByCampaignSalesmanMapExample(campaign, campaignSalesmanMapExample);
        } else {
            List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
            List<Integer> salesmanTraderMapTraderIdList =
                    super.buildSalesmanTraderMapTraderIdList(campaign, traderInfoList);
            CampaignInfoExample campaignInfoExample = buildCampaignInfoExample(campaign, salesmanTraderMapTraderIdList);
            return buildResByExample(campaign, campaignInfoExample);
        }
    }

    private QueryRespVO<CampaignInfoRespVO> buildResByCampaignSalesmanMapExample(ProductBO<QueryReqVO> campaign,
            CampaignSalesmanMapExample campaignSalesmanMapExample) {
        PageInfo<?> pageInfo = super.buildPage(campaign);
        List<CampaignSalesmanMap> campaignSalesmanMapList =
                campaignSalesmanMapDao.getCampaignSalesmanMapByExample(campaignSalesmanMapExample);
        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<>();
        super.buildRespbyReq(res, campaign);
        res.setPageSize(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());
        campaignSalesmanMapList.forEach(campaignSalesmanMap -> res.getInfo().add(buildCampaignInfoRespVO(
                campaignInfoDao.getCampaignInfobyId(campaignSalesmanMap.getCampaignId()))));
        return res;
    }

    private QueryRespVO<CampaignInfoRespVO> buildResByExample(ProductBO<QueryReqVO> campaign,
            CampaignInfoExample campaignInfoExample) {
        PageInfo<?> pageInfo = super.buildPage(campaign);
        List<CampaignInfo> campaignInfoList = campaignInfoDao.getCampaignInfoByExample(campaignInfoExample, pageInfo);

        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<>();
        super.buildRespbyReq(res, campaign);
        res.setPageSize(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());

        campaignInfoList.forEach(campaignInfo -> res.getInfo().add(buildCampaignInfoRespVO(campaignInfo)));
        return res;
    }

    private CampaignInfoExample buildCampaignInfoExample(ProductBO<?> campaign,
            List<Integer> salesmanTraderMapTraderIdList) {
        CampaignInfoExample campaignInfoExample = super.makeQueryExample(campaign, CampaignInfoExample.class);
        CampaignInfoExample.Criteria campaignCriteria = campaignInfoExample.getOredCriteria().get(0);
        campaignCriteria.andTraderIdIn(salesmanTraderMapTraderIdList);

        if (campaign.getProductId() != null) {
            campaignCriteria.andIdEqualTo(campaign.getProductId());
        }
        return campaignInfoExample;
    }

    @Override
    public QueryRespVO<CampaignInfoRespVO> updateCampaignInfo(ProductBO<CampaignInfoReqVO> campaign) {
        validateUpdate(campaign);

        CampaignInfo campaignInfo = new CampaignInfo();
        BeanUtil.copyProperties(campaign.getReq().getCampaign(), campaignInfo);
        if (campaign.getReq().getGoodsId() != null) {
            campaignInfo.setGoodsId(campaign.getReq().getGoodsId());
        }

        if (campaignInfo.getStartAt() == null && campaignInfo.getEndAt() == null) {
            super.makeDefaultPeriod(campaignInfo);
        }

        // only trader allow to update
        List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
        TraderInfo traderInfo = traderInfoList.get(0);
        campaignInfo.setTraderId(traderInfo.getId());

        campaignInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        campaignInfoDao.updateCampaignInfo(campaignInfo);

        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildCampaignInfoRespVO(campaignInfo));

        return res;
    }

    private CampaignInfoRespVO buildCampaignInfoRespVO(CampaignInfo campaignInfo) {
        CampaignInfoVO campaignInfoVO = new CampaignInfoVO();
        BeanUtil.copyProperties(campaignInfo, campaignInfoVO);

        CampaignInfoRespVO res = new CampaignInfoRespVO();
        BeanUtil.copyProperties(campaignInfo, res);

        GoodsInfoVO goodsInfoVO = super.getGoodsInfoVObyId(campaignInfo.getGoodsId());
        RoleInfoVO traderInfoVO = super.getRoleInfoVO(campaignInfo.getTraderId(), "trader");
        RoleInfoVO confirmRoleInfoVO = super.getRoleInfoVO(campaignInfo.getConfirmBy(), "factory");
        res.setGoodsInfoVO(goodsInfoVO);
        res.setCampaignInfoVO(campaignInfoVO);
        res.setTraderInfoVO(traderInfoVO);
        res.setBindSalesmanList(getBindSalesmanList(campaignInfo));
        res.setUndbindSalesmanList(getUnbindSalesmanList(campaignInfo));
        res.setConfirmRoleInfoVO(confirmRoleInfoVO);
        return res;
    }

    private List<RoleInfoVO> getBindSalesmanList(CampaignInfo campaignInfo) {
        CampaignSalesmanMap salesmanCampaignMapEx = new CampaignSalesmanMap();
        salesmanCampaignMapEx.setCampaignId(campaignInfo.getId());
        salesmanCampaignMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<CampaignSalesmanMap> campaignSalesmanMapList =
                campaignSalesmanMapDao.getCampaignSalesmanMapSelective(salesmanCampaignMapEx);
        if (campaignSalesmanMapList == null) {
            return null;
        }
        List<RoleInfoVO> res = new ArrayList<>();
        campaignSalesmanMapList.forEach(
                campaignSalesmanMap -> res.add(super.getRoleInfoVO(campaignSalesmanMap.getSalesmanId(), "salesman")));
        return res;
    }

    private List<RoleInfoVO> getUnbindSalesmanList(CampaignInfo campaignInfo) {
        CampaignSalesmanMap salesmanCampaignMapEx = new CampaignSalesmanMap();
        salesmanCampaignMapEx.setCampaignId(campaignInfo.getId());
        salesmanCampaignMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        List<CampaignSalesmanMap> campaignSalesmanMapList =
                campaignSalesmanMapDao.getCampaignSalesmanMapSelective(salesmanCampaignMapEx);
        if (campaignSalesmanMapList == null) {
            return null;
        }
        List<RoleInfoVO> res = new ArrayList<>();
        campaignSalesmanMapList.forEach(
                campaignSalesmanMap -> res.add(super.getRoleInfoVO(campaignSalesmanMap.getSalesmanId(), "salesman")));
        return res;
    }

    @Override
    public QueryRespVO<CampaignInfoRespVO> confirmCampaignInfo(ProductBO<QueryReqVO> campaign) {
        validateConfirm(campaign);

        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfobyId(campaign.getProductId());
        if (campaign.getApproved()) {
            campaignInfo.setConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        } else {
            campaignInfo.setConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        }
        campaignInfo.setConfirmAt(new Date());
        campaignInfo.setConfirmBy(getRoleInfoVOByReq(campaign).getId());
        campaignInfoDao.updateCampaignInfo(campaignInfo);
        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildCampaignInfoRespVO(campaignInfo));
        return res;
    }

    @Override
    public QueryRespVO<CampaignInfoRespVO> delCampaignInfo(ProductBO<CampaignInfoReqVO> campaign) {
        validateDelete(campaign);

        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfobyId(campaign.getProductId());
        campaignInfoDao.delCampaignInfo(campaignInfo);

        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildCampaignInfoRespVO(campaignInfo));
        return res;
    }

    @Override
    public QueryRespVO<CampaignInfoRespVO> bindCampaignInfoSalesman(ProductBO<CampaignInfoReqVO> campaign) {
        validateBind(campaign);
        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfobyId(campaign.getReq().getCampaign().getId());
        if (campaign.getReq().getBindSalesmanIdList() != null && campaign.getReq().getBindSalesmanIdList().size() > 0) {
            campaign.getReq().getBindSalesmanIdList().forEach(id -> {
                CampaignSalesmanMap salesmanCampaignMap = new CampaignSalesmanMap();
                salesmanCampaignMap.setCampaignId(campaignInfo.getId());
                salesmanCampaignMap.setSalesmanId(id);
                List<CampaignSalesmanMap> salesmanCampaignMapList =
                        campaignSalesmanMapDao.getCampaignSalesmanMapSelective(salesmanCampaignMap);
                if (salesmanCampaignMapList != null && salesmanCampaignMapList.size() > 0) {
                    salesmanCampaignMap = salesmanCampaignMapList.get(0);
                    if (salesmanCampaignMap.getValid().equals(DataSourceCommonConstant.DATABASE_COMMON_INVALID)) {
                        salesmanCampaignMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
                        campaignSalesmanMapDao.updateCampaignSalesmanMap(salesmanCampaignMap);
                    }
                } else {
                    salesmanCampaignMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
                    campaignSalesmanMapDao.updateCampaignSalesmanMap(salesmanCampaignMap);
                }
            });
        }
        if (campaign.getReq().getUndbindSalesmanIdList() != null
                && campaign.getReq().getUndbindSalesmanIdList().size() > 0) {
            campaign.getReq().getUndbindSalesmanIdList().forEach(id -> {
                CampaignSalesmanMap salesmanCampaignMap = new CampaignSalesmanMap();
                salesmanCampaignMap.setCampaignId(campaignInfo.getId());
                salesmanCampaignMap.setSalesmanId(id);
                List<CampaignSalesmanMap> salesmanCampaignMapList =
                        campaignSalesmanMapDao.getCampaignSalesmanMapSelective(salesmanCampaignMap);
                if (salesmanCampaignMapList != null && salesmanCampaignMapList.size() > 0) {
                    salesmanCampaignMap = salesmanCampaignMapList.get(0);
                    if (salesmanCampaignMap.getValid().equals(DataSourceCommonConstant.DATABASE_COMMON_VALID)) {
                        salesmanCampaignMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
                        campaignSalesmanMapDao.updateCampaignSalesmanMap(salesmanCampaignMap);
                    }
                } else {
                    salesmanCampaignMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
                    campaignSalesmanMapDao.updateCampaignSalesmanMap(salesmanCampaignMap);
                }
            });
        }

        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildCampaignInfoRespVO(campaignInfo));

        return res;
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("不允许[" + roleType + "]角色修改促销活动");
        }
        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>) req;
        if (campaign.getReq().getCampaign().getId() != null) {
            List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
            List<Integer> salesTraderMapTraderIdList =
                    super.buildSalesmanTraderMapTraderIdList(campaign, traderInfoList);
            CampaignInfoExample campaignExample = buildCampaignInfoExample(campaign, salesTraderMapTraderIdList);
            campaignExample.getOredCriteria().get(0).andIdEqualTo(campaign.getReq().getCampaign().getId());
            try {
                if (campaignInfoDao.getCampaignInfoByExample(campaignExample).get(0).getConfirmAt() != null) {
                    throw new ServiceException("已经审批不能更新");
                }
            } catch (Exception e) {
                throw new ServiceException(
                        "不能更新促销活动:" + campaign.getReq().getCampaign().getId() + " 原因为: " + e.getMessage());
            }
        }
    }

    private void validateBind(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("不允许[" + roleType + "]角色绑定促销活动");
        }
        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>) req;
        if (campaign.getReq().getCampaign().getId() != null) {
            List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
            List<Integer> salesTraderMapTraderIdList =
                    super.buildSalesmanTraderMapTraderIdList(campaign, traderInfoList);
            CampaignInfoExample campaignExample = buildCampaignInfoExample(campaign, salesTraderMapTraderIdList);
            campaignExample.getOredCriteria().get(0).andIdEqualTo(campaign.getReq().getCampaign().getId());
            try {
                if (campaignInfoDao.getCampaignInfoByExample(campaignExample).get(0).getConfirmAt() == null) {
                    throw new ServiceException("尚未审批不能绑定");
                }
            } catch (Exception e) {
                throw new ServiceException(
                        "不能绑定促销活动: " + campaign.getReq().getCampaign().getId() + " 原因为: " + e.getMessage());
            }
        }
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("factory")) {
            throw new ServiceException("dont allow to confirm campaign by role " + roleType);
        }
        if (req.getApproved() == null) {
            throw new ServiceException("approve status is null ");
        }

        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>) req;
        UserInfo userInfo = campaign.getCreaterInfo();
        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfobyId(campaign.getProductId());
        TraderInfo traderInfo = traderInfoDao.getTraderInfobyId(campaignInfo.getTraderId());

        FactoryInfo campaignFactoryInfo = traderRoleService.getFactoryInfoByTraderInfo(traderInfo);
        FactoryInfo userFactoryInfo = factoryRoleService.getFactoryInfoByUser(userInfo);

        if (!userFactoryInfo.getId().equals(campaignFactoryInfo.getId())) {
            throw new ServiceException("dont allow to approve/reject campaign by factory " + userFactoryInfo.getId());
        }
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("dont allow to delete campaign by role " + roleType);
        }
        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>) req;
        List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
        List<Integer> salestraderMapTraderIdList = super.buildSalesmanTraderMapTraderIdList(campaign, traderInfoList);
        CampaignInfoExample campaignExample = buildCampaignInfoExample(campaign, salestraderMapTraderIdList);
        campaignExample.getOredCriteria().get(0).andIdEqualTo(campaign.getProductId());
        try {
            if (campaignInfoDao.getCampaignInfoByExample(campaignExample).get(0).getConfirmAt() != null) {
                throw new ServiceException("alreday confirmed campaign dont allow to delete ");
            }
        } catch (Exception e) {
            throw new ServiceException(
                    "cant delete the campaign id " + campaign.getReq().getCampaign().getId() + " reason: " + e
                            .getMessage());
        }
    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("merchant")) {
            throw new ServiceException("dont allow to query campaign by role " + roleType);
        }
    }
}
