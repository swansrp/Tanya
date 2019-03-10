/**
 * Title: CampaignServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-20 09:32:39
 */
package com.srct.service.tanya.product.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfoExample;
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

import cn.hutool.core.bean.BeanUtil;

/**
 * @author sharuopeng
 *
 */
@Service
public class CampaignServiceImpl extends ProductServiceBaseImpl implements CampaignService {

    @Override
    public QueryRespVO<CampaignInfoRespVO> getCampaignInfo(ProductBO<QueryReqVO> campaign) {
        List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
        List<Integer> salesmanTraderMapTraderIdList =
            super.buildSalesmanTraderMapTraderIdList(campaign, traderInfoList);
        CampaignInfoExample campaignInfoExample = buildCampaignInfoExample(campaign, salesmanTraderMapTraderIdList);
        QueryRespVO<CampaignInfoRespVO> res = buildResByExample(campaign, campaignInfoExample);
        return res;
    }

    /**
     * @param campaign
     * @param campaignInfoExample
     * @return
     */
    private QueryRespVO<CampaignInfoRespVO>
        buildResByExample(ProductBO<QueryReqVO> campaign, CampaignInfoExample campaignInfoExample) {
        PageInfo<?> pageInfo = super.buildPage(campaign);
        List<CampaignInfo> campaignInfoList = campaignInfoDao.getCampaignInfoByExample(campaignInfoExample, pageInfo);

        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<CampaignInfoRespVO>();
        super.buildRespbyReq(res, campaign);
        res.setPageSize(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());

        campaignInfoList.forEach(campaignInfo -> {
            res.getInfo().add(buildCampaignInfoRespVO(campaignInfo));
        });
        return res;
    }

    /**
     * @param campaign
     * @param salesmanTraderMapIdList
     * @return
     */
    private CampaignInfoExample
        buildCampaignInfoExample(ProductBO<?> campaign, List<Integer> salesmanTraderMapTraderIdList) {
        CampaignInfoExample campaignInfoExample =
            (CampaignInfoExample)super.makeQueryExample(campaign, CampaignInfoExample.class);
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

        campaignInfo.setGoodsId(campaign.getReq().getGoodsId());

        if (campaignInfo.getStartAt() == null && campaignInfo.getEndAt() == null) {
            super.makeDefaultPeriod(campaignInfo);
        }

        List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
        TraderInfo traderInfo = traderInfoList.get(0);
        campaignInfo.setTraderId(traderInfo.getId());

        campaignInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        campaignInfoDao.updateCampaignInfo(campaignInfo);

        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<CampaignInfoRespVO>();
        res.getInfo().add(buildCampaignInfoRespVO(campaignInfo));

        return res;
    }

    /**
     * @param campaignInfo
     * @return
     */
    private CampaignInfoRespVO buildCampaignInfoRespVO(CampaignInfo campaignInfo) {
        CampaignInfoVO campaignInfoVO = new CampaignInfoVO();
        BeanUtil.copyProperties(campaignInfo, campaignInfoVO);

        CampaignInfoRespVO res = new CampaignInfoRespVO();
        BeanUtil.copyProperties(campaignInfo, res);

        GoodsInfoVO goodsInfoVO = super.getGoodsInfoVObyId(campaignInfo.getGoodsId());
        RoleInfoVO traderInfoVO = super.getRoleInfoVO(campaignInfo.getTraderId(), "trader");
        res.setGoodsInfoVO(goodsInfoVO);
        res.setCampaignInfoVO(campaignInfoVO);
        res.setTraderInfoVO(traderInfoVO);
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
        campaignInfoDao.updateCampaignInfo(campaignInfo);
        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<CampaignInfoRespVO>();
        res.getInfo().add(buildCampaignInfoRespVO(campaignInfo));
        return res;
    }

    @Override
    public QueryRespVO<CampaignInfoRespVO> delCampaignInfo(ProductBO<CampaignInfoReqVO> campaign) {
        validateDelete(campaign);

        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfobyId(campaign.getProductId());
        campaignInfoDao.delCampaignInfo(campaignInfo);

        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<CampaignInfoRespVO>();
        res.getInfo().add(buildCampaignInfoRespVO(campaignInfo));
        return res;
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("dont allow to update campaign by role " + roleType);
        }
        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>)req;
        if (campaign.getReq().getCampaign().getId() != null) {
            List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
            List<Integer> salestraderMapTraderIdList =
                super.buildSalesmanTraderMapTraderIdList(campaign, traderInfoList);
            CampaignInfoExample campaignExample = buildCampaignInfoExample(campaign, salestraderMapTraderIdList);
            campaignExample.getOredCriteria().get(0).andIdEqualTo(campaign.getReq().getCampaign().getId());
            try {
                if (campaignInfoDao.getCampaignInfoByExample(campaignExample).get(0).getConfirmAt() != null) {
                    throw new ServiceException("alreday confirmed campaign dont allow to update ");
                }
            } catch (Exception e) {
                throw new ServiceException("cant update the campaign id " + campaign.getReq().getCampaign().getId()
                    + " reason: " + e.getMessage());
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

        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>)req;
        UserInfo userInfo = campaign.getCreaterInfo();
        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfobyId(campaign.getProductId());
        TraderInfo traderInfo = traderInfoDao.getTraderInfobyId(campaignInfo.getTraderId());

        FactoryInfo campaignFactoryInfo = traderRoleService.getFacotryInfoByTraderInfo(traderInfo);
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
        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>)req;
        List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
        List<Integer> salestraderMapTraderIdList = super.buildSalesmanTraderMapTraderIdList(campaign, traderInfoList);
        CampaignInfoExample campaignExample = buildCampaignInfoExample(campaign, salestraderMapTraderIdList);
        campaignExample.getOredCriteria().get(0).andIdEqualTo(campaign.getProductId());
        try {
            if (campaignInfoDao.getCampaignInfoByExample(campaignExample).get(0).getConfirmAt() != null) {
                throw new ServiceException("alreday confirmed campaign dont allow to delete ");
            }
        } catch (Exception e) {
            throw new ServiceException("cant delete the campaign id " + campaign.getReq().getCampaign().getId()
                + " reason: " + e.getMessage());
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
