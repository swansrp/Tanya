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
import com.srct.service.tanya.common.config.FeatureConstant;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.CampaignService;
import com.srct.service.tanya.product.vo.CampaignInfoReqVO;
import com.srct.service.tanya.product.vo.CampaignInfoRespVO;
import com.srct.service.tanya.product.vo.CampaignInfoVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.role.vo.RoleInfoVO;
import com.srct.service.utils.DateUtils;
import com.srct.service.utils.ReflectionUtil;
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
public class CampaignServiceImpl extends ProductServiceBaseImpl implements CampaignService {

    @Override
    public QueryRespVO<CampaignInfoRespVO> getCampaignInfo(ProductBO<QueryReqVO> campaign) {
        if (campaign.getCreatorRole().getRole().equals("salesman")) {
            List<Integer> salesmanInfoIdList = super.getSalesmanInfoIdListByUserInfo(campaign.getCreatorInfo());

            CampaignSalesmanMapExample campaignSalesmanMapExample = new CampaignSalesmanMapExample();
            CampaignSalesmanMapExample.Criteria campaignSalesmanMapCriteria =
                    campaignSalesmanMapExample.createCriteria();
            campaignSalesmanMapCriteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);

            if (salesmanInfoIdList.size() == 0) {
                salesmanInfoIdList.add(0);
            }

            campaignSalesmanMapCriteria.andSalesmanIdIn(salesmanInfoIdList);
            if (campaign.getProductId() != null) {
                campaignSalesmanMapCriteria.andCampaignIdEqualTo(campaign.getProductId());
            }
            return buildResByCampaignSalesmanMapExample(campaign, campaignSalesmanMapExample);
        } else {
            List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
            CampaignInfoExample campaignInfoExample = buildCampaignInfoExample(campaign, traderInfoList);
            return buildResByExample(campaign, campaignInfoExample);
        }
    }

    private QueryRespVO<CampaignInfoRespVO> buildResByCampaignSalesmanMapExample(ProductBO<QueryReqVO> campaign,
            CampaignSalesmanMapExample campaignSalesmanMapExample) {

        List<CampaignSalesmanMap> campaignSalesmanMapList =
                campaignSalesmanMapDao.getCampaignSalesmanMapByExample(campaignSalesmanMapExample);
        List<Integer> campaignIdList = new ArrayList<>();
        campaignSalesmanMapList.forEach(campaignSalesmanMap -> campaignIdList.add(campaignSalesmanMap.getCampaignId()));
        if (campaignIdList.size() == 0) {
            campaignIdList.add(0);
        }
        CampaignInfoExample campaignInfoExample = super.makeQueryExample(campaign, CampaignInfoExample.class);
        campaignInfoExample.getOredCriteria().get(0).andIdIn(campaignIdList);
        PageInfo<?> pageInfo = super.buildPage(campaign);
        List<CampaignInfo> campaignInfoList = campaignInfoDao.getCampaignInfoByExample(campaignInfoExample);
        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<>();
        super.buildRespByReq(res, campaign);
        res.setPageSize(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());
        campaignInfoList.forEach(campaignInfo -> res.getInfo().add(buildCampaignInfoRespVO(campaignInfo)));
        return res;
    }

    private QueryRespVO<CampaignInfoRespVO> buildResByExample(ProductBO<QueryReqVO> campaign,
            CampaignInfoExample campaignInfoExample) {
        PageInfo<?> pageInfo = super.buildPage(campaign);
        PageInfo<CampaignInfo> campaignInfoList =
                campaignInfoDao.getCampaignInfoByExample(campaignInfoExample, pageInfo);

        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<>();
        super.buildRespByReq(res, campaign);
        res.setPageSize(campaignInfoList.getPages());
        res.setTotalSize(campaignInfoList.getTotal());

        campaignInfoList.getList().forEach(campaignInfo -> {
            CampaignInfoRespVO campaignInfoRespVO = buildCampaignInfoRespVO(campaignInfo);
            if (campaign.getCreatorRole().getRole() != "salesman") {
                buildBindInfo(campaignInfoRespVO, campaignInfo);
            }
            res.getInfo().add(campaignInfoRespVO);
        });
        return res;
    }

    private void buildBindInfo(CampaignInfoRespVO campaignInfoRespVO, CampaignInfo campaignInfo) {
        campaignInfoRespVO.setBindSalesmanList(getBindSalesmanList(campaignInfo));
        campaignInfoRespVO.setUnbindSalesmanList(getUnbindSalesmanList(campaignInfo));
    }

    private CampaignInfoExample buildCampaignInfoExample(ProductBO<?> campaign, List<TraderInfo> traderInfoList) {
        CampaignInfoExample campaignInfoExample = super.makeQueryExample(campaign, CampaignInfoExample.class);
        CampaignInfoExample.Criteria campaignCriteria = campaignInfoExample.getOredCriteria().get(0);

        List<Integer> traderIdList = (List<Integer>) ReflectionUtil.getFiledList(traderInfoList, "id");

        if (traderIdList.size() == 0) {
            traderIdList.add(0);
        }

        campaignCriteria.andTraderIdIn(traderIdList);


        if (DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID.equals(campaign.getApproved())) {
            campaignCriteria.andConfirmAtIsNull();
        } else if (campaign.getApproved() != null) {
            campaignCriteria.andConfirmStatusEqualTo(campaign.getApproved());
        }

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
            super.makeDefaultPeriod(campaignInfo, FeatureConstant.CAMPAIGN_DEFAULT_PERIOD, "365");
        }
        campaignInfo.setEndAt(DateUtils.addSeconds(DateUtils.addDate(campaignInfo.getEndAt(), 1), -1));

        // only trader allow to update
        List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
        TraderInfo traderInfo = traderInfoList.get(0);
        FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(campaign);
        if (factoryMerchantMap.getCampaignNumber() == null || factoryMerchantMap.getCampaignNumber() <= 0) {
            throw new ServiceException("不允许创建商品促销活动");
        }
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

        GoodsInfoVO goodsInfoVO = super.getGoodsInfoVOById(campaignInfo.getGoodsId());
        RoleInfoVO traderInfoVO = super.getRoleInfoVO(campaignInfo.getTraderId(), "trader");
        RoleInfoVO confirmRoleInfoVO = super.getRoleInfoVO(campaignInfo.getConfirmBy(), "factory");
        res.setGoodsInfoVO(goodsInfoVO);
        res.setCampaignInfoVO(campaignInfoVO);
        res.setTraderInfoVO(traderInfoVO);
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
        salesmanCampaignMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        List<CampaignSalesmanMap> campaignSalesmanMapList =
                campaignSalesmanMapDao.getCampaignSalesmanMapSelective(salesmanCampaignMapEx);
        List<Integer> campaignSalesmanMapIdList = new ArrayList<>();
        campaignSalesmanMapList.forEach(map -> campaignSalesmanMapIdList.add(map.getId()));
        if (campaignSalesmanMapIdList.size() == 0) {
            campaignSalesmanMapIdList.add(0);
        }
        TraderInfo traderInfo = traderInfoDao.getTraderInfoById(campaignInfo.getTraderId());
        List<SalesmanInfo> salesmanInfoList = super.traderRoleService.getSalesmanInfoListByTraderInfo(traderInfo);
        List<Integer> salesmanIdList = new ArrayList<>();
        salesmanInfoList.forEach(salesmanInfo -> {
            if (!campaignSalesmanMapIdList.contains(salesmanInfo.getId())) {
                salesmanIdList.add(salesmanInfo.getId());
            }
        });

        List<RoleInfoVO> res = new ArrayList<>();
        salesmanIdList.forEach(salesmanInfoId -> res.add(super.getRoleInfoVO(salesmanInfoId, "salesman")));
        return res;
    }

    @Override
    public QueryRespVO<CampaignInfoRespVO> confirmCampaignInfo(ProductBO<QueryReqVO> campaign) {
        validateConfirm(campaign);

        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfoById(campaign.getProductId());
        if (campaign.getApproved() != null) {
            campaignInfo.setConfirmStatus(campaign.getApproved());
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

        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfoById(campaign.getProductId());
        campaignInfoDao.delCampaignInfo(campaignInfo);

        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildCampaignInfoRespVO(campaignInfo));
        return res;
    }

    @Override
    public QueryRespVO<CampaignInfoRespVO> bindCampaignInfoSalesman(ProductBO<CampaignInfoReqVO> campaign) {
        validateBind(campaign);
        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfoById(campaign.getReq().getCampaign().getId());
        bindCampaignSalesmanRelationship(campaignInfo, campaign.getReq().getBindSalesmanIdList(),
                DataSourceCommonConstant.DATABASE_COMMON_VALID);
        bindCampaignSalesmanRelationship(campaignInfo, campaign.getReq().getUnbindSalesmanIdList(),
                DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        QueryRespVO<CampaignInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildCampaignInfoRespVO(campaignInfo));
        return res;
    }

    private void bindCampaignSalesmanRelationship(CampaignInfo campaignInfo, List<Integer> bindSalesmanIdList,
            Byte valid) {
        if (bindSalesmanIdList != null && bindSalesmanIdList.size() > 0) {
            bindSalesmanIdList.forEach(id -> {
                CampaignSalesmanMap salesmanCampaignMap = new CampaignSalesmanMap();
                salesmanCampaignMap.setCampaignId(campaignInfo.getId());
                salesmanCampaignMap.setSalesmanId(id);
                List<CampaignSalesmanMap> salesmanCampaignMapList =
                        campaignSalesmanMapDao.getCampaignSalesmanMapSelective(salesmanCampaignMap);
                if (salesmanCampaignMapList != null && salesmanCampaignMapList.size() > 0) {
                    salesmanCampaignMap = salesmanCampaignMapList.get(0);
                    if (!salesmanCampaignMap.getValid().equals(valid)) {
                        salesmanCampaignMap.setValid(valid);
                        campaignSalesmanMapDao.updateCampaignSalesmanMap(salesmanCampaignMap);
                    }
                } else {
                    salesmanCampaignMap.setValid(valid);
                    campaignSalesmanMapDao.updateCampaignSalesmanMap(salesmanCampaignMap);
                }
            });
        }
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("不允许[" + roleType + "]角色修改促销活动");
        }
        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>) req;
        if (campaign.getReq().getCampaign().getId() != null) {
            List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
            CampaignInfoExample campaignExample = buildCampaignInfoExample(campaign, traderInfoList);
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
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("不允许[" + roleType + "]角色绑定促销活动");
        }
        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>) req;
        if (campaign.getReq().getCampaign().getId() != null) {
            List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
            CampaignInfoExample campaignExample = buildCampaignInfoExample(campaign, traderInfoList);
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
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("factory")) {
            throw new ServiceException("dont allow to confirm campaign by role " + roleType);
        }
        if (req.getApproved() == null) {
            throw new ServiceException("approve status is null ");
        }

        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>) req;
        UserInfo userInfo = campaign.getCreatorInfo();
        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfoById(campaign.getProductId());
        TraderInfo traderInfo = traderInfoDao.getTraderInfoById(campaignInfo.getTraderId());

        FactoryInfo campaignFactoryInfo = traderRoleService.getFactoryInfoByTraderInfo(traderInfo);
        FactoryInfo userFactoryInfo = factoryRoleService.getFactoryInfoByUser(userInfo);

        if (!userFactoryInfo.getId().equals(campaignFactoryInfo.getId())) {
            throw new ServiceException("dont allow to approve/reject campaign by factory " + userFactoryInfo.getId());
        }
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("trader")) {
            throw new ServiceException("dont allow to delete campaign by role " + roleType);
        }
        ProductBO<CampaignInfoReqVO> campaign = (ProductBO<CampaignInfoReqVO>) req;
        List<TraderInfo> traderInfoList = super.getTraderInfo(campaign);
        CampaignInfoExample campaignExample = buildCampaignInfoExample(campaign, traderInfoList);
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
        String roleType = req.getCreatorRole().getRole();
        if (roleType.equals("merchant")) {
            throw new ServiceException("dont allow to query campaign by role " + roleType);
        }
    }
}
