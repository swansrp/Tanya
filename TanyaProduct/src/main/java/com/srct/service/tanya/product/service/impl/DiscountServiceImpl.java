/**
 * Title: DiscountServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-20 09:32:29
 */
package com.srct.service.tanya.product.service.impl;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.config.FeatureConstant;
import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.DiscountService;
import com.srct.service.tanya.product.vo.DiscountInfoReqVO;
import com.srct.service.tanya.product.vo.DiscountInfoRespVO;
import com.srct.service.tanya.product.vo.DiscountInfoVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.role.vo.RoleInfoVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.DateUtils;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author sharuopeng
 */
@Service
public class DiscountServiceImpl extends ProductServiceBaseImpl implements DiscountService {

    @Override
    public QueryRespVO<DiscountInfoRespVO> getDiscountInfo(ProductBO<QueryReqVO> discount) {
        validateQuery(discount);
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(discount);
        List<Integer> factoryMerchantMapIdList = super.buildFactoryMerchantMapIdList(discount, factoryInfoList);
        DiscountInfoExample discountExample = buildDiscountInfoExample(discount, factoryMerchantMapIdList);
        return buildResByExample(discount, discountExample);
    }

    @Override
    public QueryRespVO<DiscountInfoRespVO> updateDiscountInfo(ProductBO<DiscountInfoReqVO> discount) {
        validateUpdate(discount);

        DiscountInfo discountInfo = new DiscountInfo();
        BeanUtil.copyProperties(discount.getReq().getDiscount(), discountInfo);
        if (discount.getReq().getGoodsId() != null) {
            discountInfo.setGoodsId(discount.getReq().getGoodsId());
        }
        if (discountInfo.getStartAt() == null && discountInfo.getEndAt() == null) {
            super.makeDefaultPeriod(discountInfo, FeatureConstant.DISCOUNT_DEFAULT_PERIOD, "365");
        }
        discountInfo.setEndAt(DateUtils.addSeconds(DateUtils.addDate(discountInfo.getEndAt(), 1), -1));

        FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(discount);
        if (factoryMerchantMap.getCampaignNumber() == null || factoryMerchantMap.getCampaignNumber() <= 0) {
            throw new ServiceException("不允许创建商品折扣活动");
        }
        discountInfo.setFactoryMetchatMapId(factoryMerchantMap.getId());
        if (discount.getReq().getGoodsId() != null) {
            discountInfo.setGoodsId(discount.getReq().getGoodsId());
        }
        discountInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);

        discountInfoDao.updateDiscountInfo(discountInfo);

        QueryRespVO<DiscountInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildDiscountInfoRespVO(discountInfo));

        return res;

    }

    @Override
    public QueryRespVO<DiscountInfoRespVO> confirmDiscountInfo(ProductBO<QueryReqVO> discount) {
        validateConfirm(discount);

        DiscountInfo discountInfo = discountInfoDao.getDiscountInfoById(discount.getProductId());
        if (discount.getApproved().equals(DataSourceCommonConstant.DATABASE_COMMON_VALID)) {
            discountInfo.setConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        } else {
            discountInfo.setConfirmStatus(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        }
        discountInfo.setConfirmAt(new Date());
        discountInfo.setConfirmBy(super.getRoleInfoVOByReq(discount).getId());
        discountInfoDao.updateDiscountInfo(discountInfo);
        QueryRespVO<DiscountInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildDiscountInfoRespVO(discountInfo));
        return res;
    }

    @Override
    public QueryRespVO<DiscountInfoRespVO> delDiscountInfo(ProductBO<DiscountInfoReqVO> discount) {
        validateDelete(discount);
        DiscountInfo discountInfo = discountInfoDao.getDiscountInfoById(discount.getProductId());
        discountInfoDao.delDiscountInfo(discountInfo);
        QueryRespVO<DiscountInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildDiscountInfoRespVO(discountInfo));
        return res;
    }

    @Override
    public List<DiscountInfo> getDiscountInfoListByGoodsId(Integer goodsId) {
        Date now = new Date();
        DiscountInfoExample example = new DiscountInfoExample();
        DiscountInfoExample.Criteria criteria = example.createCriteria();
        criteria.andConfirmStatusEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andEndAtGreaterThan(now);
        criteria.andStartAtLessThan(now);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return discountInfoDao.getDiscountInfoByExample(example);
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("factory")) {
            throw new ServiceException("dont allow to update discount by role " + roleType);
        }
        ProductBO<DiscountInfoReqVO> discount = (ProductBO<DiscountInfoReqVO>) req;
        if (discount.getReq().getDiscount().getId() != null) {
            List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(discount);
            List<Integer> factoryMerchantMapIdList = super.buildFactoryMerchantMapIdList(discount, factoryInfoList);
            DiscountInfoExample discountExample = buildDiscountInfoExample(discount, factoryMerchantMapIdList);
            discountExample.getOredCriteria().get(0).andIdEqualTo(discount.getReq().getDiscount().getId());
            try {
                if (discountInfoDao.getDiscountInfoByExample(discountExample).get(0).getConfirmAt() != null
                        && discountInfoDao.getDiscountInfoByExample(discountExample).get(0).getConfirmStatus()
                        .equals(DataSourceCommonConstant.DATABASE_COMMON_VALID)) {
                    throw new ServiceException("already confirmed discount dont allow to update ");
                }
            } catch (Exception e) {
                throw new ServiceException(
                        "cant update the discount id " + discount.getReq().getDiscount().getId() + " reason: " + e
                                .getMessage());
            }
        }
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("merchant")) {
            throw new ServiceException("dont allow to confirm discount by role " + roleType);
        }
        if (req.getApproved() == null) {
            throw new ServiceException("approve status is null ");
        }
        ProductBO<DiscountInfoReqVO> discount = (ProductBO<DiscountInfoReqVO>) req;
        UserInfo userInfo = discount.getCreatorInfo();
        DiscountInfo discountInfo = discountInfoDao.getDiscountInfoById(discount.getProductId());
        FactoryMerchantMap factoryMerchantMap =
                factoryMerchantMapDao.getFactoryMerchantMapById(discountInfo.getFactoryMetchatMapId());

        MerchantInfo merchantInfo = merchantRoleService.getMerchantInfoByUser(userInfo);
        if (!factoryMerchantMap.getMerchantId().equals(merchantInfo.getId())) {
            throw new ServiceException("dont allow to approve discount by merchant " + merchantInfo.getId());
        }
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("factory")) {
            throw new ServiceException("dont allow to delete discount by role " + roleType);
        }
        ProductBO<DiscountInfoReqVO> discount = (ProductBO<DiscountInfoReqVO>) req;
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(discount);
        List<Integer> factoryMerchantMapIdList = super.buildFactoryMerchantMapIdList(discount, factoryInfoList);
        DiscountInfoExample discountExample = buildDiscountInfoExample(discount, factoryMerchantMapIdList);
        discountExample.getOredCriteria().get(0).andIdEqualTo(discount.getProductId());
        try {
            if (discountInfoDao.getDiscountInfoByExample(discountExample).get(0).getConfirmAt() != null
                    && discountInfoDao.getDiscountInfoByExample(discountExample).get(0).getConfirmStatus()
                    .equals(DataSourceCommonConstant.DATABASE_COMMON_VALID)) {
                throw new ServiceException("alreday confirmed discount dont allow to delete ");
            }
        } catch (Exception e) {
            throw new ServiceException(
                    "cant delete the discount id " + discount.getReq().getDiscount().getId() + " reason: " + e
                            .getMessage());
        }
    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (roleType.equals("salesman")) {
            throw new ServiceException("dont allow to query discount by role " + roleType);
        }
    }

    private DiscountInfoExample buildDiscountInfoExample(ProductBO<?> discount,
            List<Integer> factoryMerchantMapIdList) {
        DiscountInfoExample discountExample = super.makeQueryExample(discount, DiscountInfoExample.class);
        DiscountInfoExample.Criteria discountCriteria = discountExample.getOredCriteria().get(0);
        if (factoryMerchantMapIdList.size() == 0) {
            factoryMerchantMapIdList.add(0);
        }
        discountCriteria.andFactoryMetchatMapIdIn(factoryMerchantMapIdList);
        if (discount.getProductId() != null) {
            discountCriteria.andIdEqualTo(discount.getProductId());
        }

        if (DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID.equals(discount.getApproved())) {
            discountCriteria.andConfirmAtIsNull();
        } else if (discount.getApproved() != null) {
            discountCriteria.andConfirmStatusEqualTo(discount.getApproved());
        }

        if (StringUtils.isNotBlank(discount.getTitle())) {
            discountCriteria.andTitleLike("%" + discount.getTitle() + "%");
        }

        return discountExample;
    }

    private QueryRespVO<DiscountInfoRespVO> buildResByExample(ProductBO<QueryReqVO> discount,
            DiscountInfoExample discountExample) {
        PageInfo<?> pageInfo = super.buildPage(discount);
        PageInfo<DiscountInfo> discountInfoList = discountInfoDao.getDiscountInfoByExample(discountExample, pageInfo);

        QueryRespVO<DiscountInfoRespVO> res = new QueryRespVO<>();
        super.buildRespByReq(res, discount);
        res.setTotalPages(discountInfoList.getPages());
        res.setTotalSize(discountInfoList.getTotal());

        discountInfoList.getList().forEach(discountInfo -> res.getInfo().add(buildDiscountInfoRespVO(discountInfo)));
        return res;
    }

    private DiscountInfoRespVO buildDiscountInfoRespVO(DiscountInfo discountInfo) {
        DiscountInfoVO discountInfoVO = new DiscountInfoVO();
        BeanUtil.copyProperties(discountInfo, discountInfoVO);

        DiscountInfoRespVO res = new DiscountInfoRespVO();
        BeanUtil.copyProperties(discountInfo, res);

        GoodsInfoVO goodsInfoVO = super.getGoodsInfoVOById(discountInfo.getGoodsId());
        FactoryMerchantMap map = factoryMerchantMapDao.getFactoryMerchantMapById(discountInfo.getFactoryMetchatMapId());
        FactoryMerchantMap factoryMerchantMap = factoryMerchantMapDao.getFactoryMerchantMapById(map.getId());
        RoleInfoVO merchantInfoVO = super.getRoleInfoVO(factoryMerchantMap.getMerchantId(), "merchant");
        RoleInfoVO factoryInfoVO = super.getRoleInfoVO(factoryMerchantMap.getFactoryId(), "factory");
        RoleInfoVO confirmRoleInfoVO = super.getRoleInfoVO(discountInfo.getConfirmBy(), "merchant");
        res.setDiscountInfoVO(discountInfoVO);
        res.setFactoryInfoVO(factoryInfoVO);
        res.setGoodsInfoVO(goodsInfoVO);
        res.setMerchantInfoVO(merchantInfoVO);
        res.setDiscountInfoVO(discountInfoVO);
        res.setConfirmRoleInfoVO(confirmRoleInfoVO);
        return res;
    }

}
