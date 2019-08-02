/**
 * Title: GoodsServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author Sharp
 * @date 2019-02-17 22:27:11
 */
package com.srct.service.tanya.product.service.impl;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsTraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsTraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.mapper.GoodsInfoMapper;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsFactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsTraderFactoryMerchantMapDao;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.bo.UploadProductBO;
import com.srct.service.tanya.product.service.DiscountService;
import com.srct.service.tanya.product.service.GoodsService;
import com.srct.service.tanya.product.vo.DiscountInfoVO;
import com.srct.service.tanya.product.vo.GoodsInfoReqVO;
import com.srct.service.tanya.product.vo.GoodsInfoRespVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.product.vo.GoodsSummaryVO;
import com.srct.service.tanya.product.vo.upload.UploadGoodsInfoVO;
import com.srct.service.tanya.role.service.FactoryRoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.ExcelUtils;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sharp
 */
@Service
public class GoodsServiceImpl extends ProductServiceBaseImpl implements GoodsService {
    @Autowired
    private FactoryRoleService factoryRoleService;
    @Autowired
    private GoodsFactoryMerchantMapDao goodsFactoryMerchantMapDao;
    @Autowired
    private GoodsTraderFactoryMerchantMapDao goodsTraderFactoryMerchantMapDao;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public QueryRespVO<GoodsInfoRespVO> bindGoodsInfo(ProductBO<GoodsInfoReqVO> req) {
        validateBind(req);
        String roleType = req.getCreatorRole().getRole();
        GoodsInfoReqVO goodsInfoReqVO = req.getReq();
        switch (roleType) {
            case "merchant":
                bindGoodsFactoryRelationship(req, goodsInfoReqVO.getBindIdList(),
                        DataSourceCommonConstant.DATABASE_COMMON_VALID);
                bindGoodsFactoryRelationship(req, goodsInfoReqVO.getUnbindIdList(),
                        DataSourceCommonConstant.DATABASE_COMMON_INVALID);
                break;
            case "factory":
                bindGoodsTraderRelationship(req, goodsInfoReqVO.getBindIdList(),
                        DataSourceCommonConstant.DATABASE_COMMON_VALID);
                bindGoodsTraderRelationship(req, goodsInfoReqVO.getUnbindIdList(),
                        DataSourceCommonConstant.DATABASE_COMMON_INVALID);
                break;
            default:
                throw new ServiceException("不允许[" + roleType + "]角色绑定商品");
        }

        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        /*
        GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfoById(goodsInfoId);
        GoodsInfoRespVO goodsInfoRespVO = buildGoodInfoRespVO(goodsInfo);
        res.getInfo().add(goodsInfoRespVO);
        */
        return res;
    }

    @Override
    public QueryRespVO<GoodsInfoRespVO> delGoodsInfo(ProductBO<GoodsInfoReqVO> req) {
        validateDelete(req);
        String roleType = req.getCreatorRole().getRole();
        GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfoById(req.getProductId());
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList;
        switch (roleType) {
            case "merchant":
                MerchantInfo merchantInfo = merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
                List<FactoryMerchantMap> factoryMerchantMapList =
                        super.getFactoryMerchantMapByMerchantId(merchantInfo.getId());
                List<Integer> factoryMerchantMapIdList =
                        ReflectionUtil.getFieldList(factoryMerchantMapList, "id", Integer.class);
                goodsFactoryMerchantMapList =
                        getGoodsFactoryMerchantMapListByFactoryMerchantMapIdListAndGoodsInfo(factoryMerchantMapIdList,
                                goodsInfo);
                break;
            case "factory":
                List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(req);
                FactoryInfo factoryInfo = factoryInfoList.get(0);
                goodsFactoryMerchantMapList = getGoodsFactoryMerchantMapList(factoryInfo, goodsInfo);

                break;
            default:
                throw new ServiceException("不允许[" + roleType + "]角色删除商品");
        }
        goodsFactoryMerchantMapList.forEach(goodsFactoryMerchantMap -> {
            FactoryMerchantMap factoryMerchantMap =
                    factoryMerchantMapDao.getFactoryMerchantMapById(goodsFactoryMerchantMap.getFactoryMerchantMapId());
            factoryMerchantMap.setGoodsNumber(factoryMerchantMap.getGoodsNumber() - 1);
            goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
            goodsFactoryMerchantMapDao.updateGoodsFactoryMerchantMap(goodsFactoryMerchantMap);
        });
        try {
            goodsInfoDao.delGoodsInfo(goodsInfo);
        } catch (DuplicateKeyException e) {
            goodsInfoMapper.deleteByPrimaryKey(goodsInfo.getId());
        }

        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildGoodInfoRespVO(goodsInfo));
        return res;
    }

    @Override
    public QueryRespVO<GoodsInfoRespVO> getGoodsBindInfo(ProductBO<QueryReqVO> req) {
        validateQuery(req);
        String roleType = req.getCreatorRole().getRole();
        switch (roleType) {
            case "merchant":
                return getGoodsBindInfoByMerchant(req);
            case "factory":
                return getGoodsBindInfoByFactory(req);
            default:
                throw new ServiceException("不允许[" + roleType + "]角色查看商品绑定信息");

        }
    }

    @Override
    public QueryRespVO<GoodsInfoRespVO> getGoodsInfo(ProductBO<QueryReqVO> req) {
        validateQuery(req);
        String roleType = req.getCreatorRole().getRole();
        switch (roleType) {
            case "merchant":
                return getGoodsInfoByMerchant(req);
            case "factory":
                return getGoodsInfoByFactory(req);
            case "trader":
                return getGoodsInfoByTrader(req);
            default:
                throw new ServiceException("不允许[" + roleType + "]角色查询商品");
        }
    }

    @Override
    public QueryRespVO<GoodsInfoRespVO> getGoodsInfoWithDiscount(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = getGoodsInfo(req);
        List<GoodsInfoRespVO> infoList = new ArrayList<>();
        res.getInfo().forEach(info -> {
            infoList.add(info);
            List<DiscountInfo> discountList =
                    discountService.getDiscountInfoListByGoodsId(info.getGoodsInfoVO().getId());
            discountList.forEach(discountInfo -> infoList.add(buildGoodInfoRespVO(discountInfo)));
        });
        res.setInfo(infoList);
        return res;
    }

    @Override
    public GoodsSummaryVO summaryGoodsInfo(ProductBO<QueryReqVO> req) {
        validateSummary(req);
        GoodsInfoExample example = getCreateGoodsInfoExample(req);
        Long newNum = goodsInfoDao.countGoodsInfoByExample(example);
        example = getDeleteGoodsInfoExample(req);
        Long deleteNum = goodsInfoDao.countGoodsInfoByExample(example);
        Log.i("merchantId[" + req.getMerchantId() + "] create " + newNum + " delete " + deleteNum + " from " + req
                .getReq().getQueryStartAt() + " - " + req.getReq().getQueryEndAt());
        return GoodsSummaryVO.builder().newNum(newNum).deleteNum(deleteNum).build();
    }

    @Override
    public QueryRespVO<GoodsInfoRespVO> updateGoodsInfo(ProductBO<GoodsInfoReqVO> req) {
        validateUpdate(req);
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        switch (req.getCreatorRole().getRole()) {
            case "factory": {
                GoodsInfo goodsInfo = updateGoodsInfoByFactory(req);
                GoodsInfoRespVO goodsInfoRespVO = buildGoodInfoRespVO(goodsInfo);
                res.getInfo().add(goodsInfoRespVO);
                break;
            }
            case "merchant": {
                GoodsInfo goodsInfo = updateGoodsInfoByMerchant(req);
                GoodsInfoRespVO goodsInfoRespVO = buildGoodInfoRespVO(goodsInfo);
                res.getInfo().add(goodsInfoRespVO);
                break;
            }
            default:
                break;
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadGoodsInfoVO(UploadProductBO req) {
        List<UploadGoodsInfoVO> uploadGoodsList = ExcelUtils.readFromExcel(req.getFile(), UploadGoodsInfoVO.class);
        MerchantInfo merchantInfo = super.merchantInfoDao.getMerchantInfoById(req.getMerchantId());
        if (req.getOverride() != null && req.getOverride()) {
            delGoodsInfoByMerchant(merchantInfo);
        }
        uploadGoodsList.forEach(uploadGoods -> {
            GoodsInfo goodsInfo = new GoodsInfo();
            BeanUtil.copyProperties(uploadGoods, goodsInfo);
            goodsInfo.setMerchantId(merchantInfo.getId());
            goodsInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            BigDecimal amount = new BigDecimal(goodsInfo.getAmount());
            goodsInfo.setAmount(amount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            saveGoodsInfo(goodsInfo);
        });
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        throw new ServiceException("不支持确认商品");
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (roleType.equals("salesman")) {
            throw new ServiceException("不允许[" + roleType + "]角色删除商品");
        }
    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (roleType.equals("salesman")) {
            throw new ServiceException("不允许[" + roleType + "]角色查询商品");
        }
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("factory") && !roleType.equals("merchant")) {
            throw new ServiceException("不允许[" + roleType + "]角色创建/更新商品");
        }
    }

    private void bindGoodsFactoryRelationship(ProductBO<GoodsInfoReqVO> req, List<Integer> goodsIdList, Byte valid) {
        GoodsInfoReqVO goodsInfoReqVO = req.getReq();
        FactoryInfo factoryInfo = super.factoryInfoDao.getFactoryInfoById(goodsInfoReqVO.getTargetId());
        if (!CollectionUtils.isEmpty(goodsIdList)) {
            goodsIdList.forEach(goodsId -> {
                GoodsInfo goodsInfo = super.goodsInfoDao.getGoodsInfoById(goodsId);
                FactoryMerchantMap factoryMerchantMap =
                        super.getFactoryMerchantMapByMerchantIdAndFactoryId(goodsInfo.getMerchantId(),
                                factoryInfo.getId());
                GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
                goodsFactoryMerchantMap.setGoodsId(goodsInfo.getId());
                goodsFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
                List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList =
                        goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap);
                if (!CollectionUtils.isEmpty(goodsFactoryMerchantMapList)) {
                    goodsFactoryMerchantMap = goodsFactoryMerchantMapList.get(0);
                    if (!goodsFactoryMerchantMap.getValid().equals(valid)) {
                        goodsFactoryMerchantMap.setValid(valid);
                        goodsFactoryMerchantMapDao.updateGoodsFactoryMerchantMap(goodsFactoryMerchantMap);
                    }
                } else {
                    goodsFactoryMerchantMap.setValid(valid);
                    goodsFactoryMerchantMapDao.updateGoodsFactoryMerchantMap(goodsFactoryMerchantMap);
                }
            });
        }
    }

    private void bindGoodsTraderRelationship(ProductBO<GoodsInfoReqVO> req, List<Integer> goodsIdList, Byte valid) {
        GoodsInfoReqVO goodsInfoReqVO = req.getReq();
        TraderInfo traderInfo = super.traderInfoDao.getTraderInfoById(goodsInfoReqVO.getTargetId());
        if (!CollectionUtils.isEmpty(goodsIdList)) {
            goodsIdList.forEach(goodsInfoId -> {
                GoodsInfo goodsInfo = super.goodsInfoDao.getGoodsInfoById(goodsInfoId);
                TraderFactoryMerchantMap traderFactoryMerchantMap = super.getTraderFactoryMerchantMap(req);
                List<GoodsTraderFactoryMerchantMap> goodsFactoryMerchantMapList =
                        getGoodsTraderFactoryMerchantMap(traderInfo, traderFactoryMerchantMap, goodsInfo.getId());
                boolean notFound = true;
                for (GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap : goodsFactoryMerchantMapList) {
                    if (goodsTraderFactoryMerchantMap.getTraderId().equals(traderInfo.getId())) {
                        notFound = false;
                        if (!goodsTraderFactoryMerchantMap.getValid().equals(valid)) {
                            goodsTraderFactoryMerchantMap.setValid(valid);
                            goodsTraderFactoryMerchantMapDao
                                    .updateGoodsTraderFactoryMerchantMap(goodsTraderFactoryMerchantMap);
                        }
                    }
                }
                if (notFound) {
                    GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap = new GoodsTraderFactoryMerchantMap();
                    goodsTraderFactoryMerchantMap.setGoodsId(goodsInfo.getId());
                    goodsTraderFactoryMerchantMap.setValid(valid);
                    goodsTraderFactoryMerchantMap.setTraderFactoryMerchantMapId(traderFactoryMerchantMap.getId());
                    goodsTraderFactoryMerchantMap.setTraderId(traderInfo.getId());
                    goodsTraderFactoryMerchantMapDao.updateGoodsTraderFactoryMerchantMap(goodsTraderFactoryMerchantMap);
                }
            });
        }
    }

    private GoodsInfoRespVO buildGoodInfoRespVO(GoodsInfo goodsInfo) {
        GoodsInfoRespVO goodsInfoRespVO = new GoodsInfoRespVO();
        GoodsInfoVO vo = new GoodsInfoVO();
        BeanUtil.copyProperties(goodsInfo, vo);
        BeanUtil.copyProperties(goodsInfo, goodsInfoRespVO);
        goodsInfoRespVO.setGoodsInfoVO(vo);
        goodsInfoRespVO.setUnit(1);
        goodsInfoRespVO.setMerchantRoleInfoVO(super.getRoleInfoVO(goodsInfo.getMerchantId(), "merchant"));
        return goodsInfoRespVO;
    }

    private GoodsInfoRespVO buildGoodInfoRespVO(DiscountInfo discountInfo) {
        GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfoById(discountInfo.getGoodsId());
        GoodsInfoRespVO goodsInfoRespVO = new GoodsInfoRespVO();
        GoodsInfoVO vo = new GoodsInfoVO();
        BeanUtil.copyProperties(goodsInfo, vo);
        BeanUtil.copyProperties(goodsInfo, goodsInfoRespVO);
        goodsInfoRespVO.setGoodsInfoVO(vo);
        goodsInfoRespVO.setUnit(discountInfo.getGoodsNumber());
        DiscountInfoVO discountInfoVO = new DiscountInfoVO();
        BeanUtil.copyProperties(discountInfo, discountInfoVO);
        goodsInfoRespVO.setDiscountInfoVO(discountInfoVO);
        return goodsInfoRespVO;
    }

    private void delGoodsInfoByMerchant(MerchantInfo merchantInfo) {
        List<GoodsInfo> goodsInfoList = getGoodsInfoListByMerchant(merchantInfo);
        if (!CollectionUtils.isEmpty(goodsInfoList)) {
            goodsInfoList.forEach(goodsInfo -> goodsInfoDao.delGoodsInfo(goodsInfo));
        }
    }

    private GoodsInfoExample getCreateGoodsInfoExample(ProductBO<QueryReqVO> req) {
        GoodsInfoExample example = new GoodsInfoExample();
        GoodsInfoExample.Criteria criteria = example.createCriteria();
        criteria.andMerchantIdEqualTo(req.getMerchantId());
        if (req.getReq().getQueryStartAt() != null && req.getReq().getQueryEndAt() != null) {
            criteria.andCreateAtBetween(req.getReq().getQueryStartAt(), req.getReq().getQueryEndAt());
        }
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return example;
    }

    private GoodsInfoExample getDeleteGoodsInfoExample(ProductBO<QueryReqVO> req) {
        GoodsInfoExample example = new GoodsInfoExample();
        GoodsInfoExample.Criteria criteria = example.createCriteria();
        criteria.andMerchantIdEqualTo(req.getMerchantId());
        if (req.getReq().getQueryStartAt() != null && req.getReq().getQueryEndAt() != null) {
            criteria.andCreateAtBetween(req.getReq().getQueryStartAt(), req.getReq().getQueryEndAt());
            criteria.andCreateAtGreaterThanOrEqualTo(req.getReq().getQueryStartAt());
        }
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        return example;
    }

    private QueryRespVO<GoodsInfoRespVO> getGoodsBindInfoByFactory(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        FactoryInfo factoryInfo = super.factoryRoleService.getFactoryInfoByUser(req.getCreatorInfo());
        FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(req);
        if (!DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(factoryMerchantMap.getGoodsTraderBind())) {
            throw new ServiceException("没有开通factory[" + factoryMerchantMap.getFactoryId() + "]角色绑定商品权限");
        }
        TraderFactoryMerchantMap traderFactoryMerchantMapEx = new TraderFactoryMerchantMap();
        traderFactoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        traderFactoryMerchantMapEx.setFactoryId(factoryInfo.getId());
        traderFactoryMerchantMapEx.setFactoryMerchantMapId(factoryMerchantMap.getId());
        if (req.getTraderId() != null) {
            traderFactoryMerchantMapEx.setTraderId(req.getTraderId());
        }
        List<TraderFactoryMerchantMap> traderFactoryMerchantMapList =
                super.traderFactoryMerchantMapDao.getTraderFactoryMerchantMapSelective(traderFactoryMerchantMapEx);
        List<Integer> traderFactoryMerchantMapIdList =
                ReflectionUtil.getFieldList(traderFactoryMerchantMapList, "id", Integer.class);
        if (traderFactoryMerchantMapIdList == null || traderFactoryMerchantMapIdList.size() == 0) {
            return res;
        }
        PageInfo pageInfo = super.buildPage(req);
        GoodsTraderFactoryMerchantMapExample example = new GoodsTraderFactoryMerchantMapExample();
        GoodsTraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        criteria.andTraderFactoryMerchantMapIdIn(traderFactoryMerchantMapIdList);
        PageInfo<GoodsTraderFactoryMerchantMap> goodsTraderFactoryMerchantMapList =
                goodsTraderFactoryMerchantMapDao.getGoodsTraderFactoryMerchantMapByExample(example, pageInfo);
        res.buildPageInfo(pageInfo);
        res.setTotalPages(goodsTraderFactoryMerchantMapList.getPages());
        res.setTotalSize(goodsTraderFactoryMerchantMapList.getTotal());
        goodsTraderFactoryMerchantMapList.getList().forEach(goodsTraderFactoryMerchantMap -> {
            GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfoById(goodsTraderFactoryMerchantMap.getGoodsId());
            res.getInfo().add(buildGoodInfoRespVO(goodsInfo));
        });
        return res;
    }

    private QueryRespVO<GoodsInfoRespVO> getGoodsBindInfoByMerchant(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
        List<FactoryMerchantMap> factoryMerchantMapList =
                super.getFactoryMerchantMapListByMerchantIdAndFactoryId(merchantInfo.getId(), req.getFactoryId());
        List<Integer> factoryMerchantMapIdList =
                ReflectionUtil.getFieldList(factoryMerchantMapList, "id", Integer.class);
        if (factoryMerchantMapIdList == null || 0 == factoryMerchantMapIdList.size()) {
            return res;
        }
        PageInfo pageInfo = super.buildPage(req);
        GoodsFactoryMerchantMapExample example = new GoodsFactoryMerchantMapExample();
        GoodsFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        criteria.andFactoryMerchantMapIdIn(factoryMerchantMapIdList);
        PageInfo<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList =
                goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapByExample(example, pageInfo);
        res.buildPageInfo(pageInfo);
        res.setTotalPages(goodsFactoryMerchantMapList.getPages());
        res.setTotalSize(goodsFactoryMerchantMapList.getTotal());
        goodsFactoryMerchantMapList.getList().forEach(goodsFactoryMerchantMap -> {
            GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfoById(goodsFactoryMerchantMap.getGoodsId());
            res.getInfo().add(buildGoodInfoRespVO(goodsInfo));
        });
        return res;
    }

    private List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapList(FactoryInfo factoryInfo) {
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap);
    }

    private List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapList(FactoryInfo factoryInfo, GoodsInfo goodsInfo) {
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setGoodsId(goodsInfo.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap);
    }

    private List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapList(FactoryMerchantMap factoryMerchantMap,
            Integer goodsId) {
        GoodsFactoryMerchantMap goodsFactoryMerchantMapEx = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMapEx.setFactoryMerchantMapId(factoryMerchantMap.getId());
        if (goodsId != null) {
            goodsFactoryMerchantMapEx.setGoodsId(goodsId);
        }
        goodsFactoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMapEx);
    }

    private List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapListByFactoryMerchantMapIdListAndGoodsInfo(
            List<Integer> factoryMerchantMapIdList, GoodsInfo goodsInfo) {
        GoodsFactoryMerchantMapExample goodsFactoryMerchantMapExample = new GoodsFactoryMerchantMapExample();
        GoodsFactoryMerchantMapExample.Criteria criteria = goodsFactoryMerchantMapExample.createCriteria();
        criteria.andFactoryMerchantMapIdIn(factoryMerchantMapIdList);
        criteria.andGoodsIdEqualTo(goodsInfo.getId());
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapByExample(goodsFactoryMerchantMapExample);
    }

    private List<Integer> getGoodsIdListByMerchantIdAndFactoryId(Integer merchantId, Integer factoryId) {
        FactoryMerchantMap factoryMerchantMap =
                super.getFactoryMerchantMapByMerchantIdAndFactoryId(merchantId, factoryId);
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList =
                getGoodsFactoryMerchantMapList(factoryMerchantMap, null);
        return ReflectionUtil.getFieldList(goodsFactoryMerchantMapList, "goodsId", Integer.class);
    }

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoByFactory(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        FactoryInfo factoryInfo = super.factoryRoleService.getFactoryInfoByUser(req.getCreatorInfo());
        FactoryMerchantMap factoryMerchantMap =
                super.factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList =
                getGoodsFactoryMerchantMapList(factoryMerchantMap, req.getProductId());
        List<Integer> goodsIdList = ReflectionUtil.getFieldList(goodsFactoryMerchantMapList, "goodsId", Integer.class);
        if (goodsIdList == null || goodsIdList.size() == 0) {
            return res;
        } else {
            PageInfo pageInfo = super.buildPage(req);
            PageInfo<GoodsInfo> goodsInfoList = getGoodsInfoByIdList(req, pageInfo, goodsIdList);
            res.buildPageInfo(pageInfo);
            res.setTotalPages(goodsInfoList.getPages());
            res.setTotalSize(goodsInfoList.getTotal());
            goodsInfoList.getList().forEach(goodsInfo -> res.getInfo().add(buildGoodInfoRespVO(goodsInfo)));
        }
        return res;
    }

    private PageInfo<GoodsInfo> getGoodsInfoByIdList(ProductBO<QueryReqVO> req, PageInfo pageInfo,
            List<Integer> goodsIdList) {
        GoodsInfoExample example = new GoodsInfoExample();
        GoodsInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(goodsIdList);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        if (req.getTitle() != null) {
            criteria.andTitleLike("%" + req.getTitle() + "%");
        }
        return super.goodsInfoDao.getGoodsInfoByExample(example, pageInfo);

    }

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoByMerchant(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
        GoodsInfoExample goodsInfoEx = new GoodsInfoExample();
        GoodsInfoExample.Criteria criteria = goodsInfoEx.createCriteria();
        criteria.andMerchantIdEqualTo(merchantInfo.getId());
        List<Integer> idList = new ArrayList<>();
        if (req.getFactoryId() != null) {
            List<Integer> goodsIdList =
                    getGoodsIdListByMerchantIdAndFactoryId(merchantInfo.getId(), req.getFactoryId());
            if (!CollectionUtils.isEmpty(goodsIdList)) {
                idList.addAll(goodsIdList);
            }
        }
        if (req.getProductId() != null) {
            idList.clear();
            idList.add(req.getProductId());
        }
        if (idList.size() > 0) {
            criteria.andIdIn(idList);
        }
        if (req.getTitle() != null) {
            criteria.andTitleLike("%" + req.getTitle() + "%");
        }
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        PageInfo pageInfo = super.buildPage(req);
        PageInfo<GoodsInfo> goodsInfoList = super.goodsInfoDao.getGoodsInfoByExample(goodsInfoEx, pageInfo);
        res.buildPageInfo(pageInfo);
        res.setTotalPages(goodsInfoList.getPages());
        res.setTotalSize(goodsInfoList.getTotal());
        goodsInfoList.getList().forEach(goodsInfo -> res.getInfo().add(buildGoodInfoRespVO(goodsInfo)));
        return res;
    }

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoByTrader(ProductBO<QueryReqVO> req) {
        FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(req);
        if (DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(factoryMerchantMap.getGoodsTraderBind())) {
            return getGoodsInfoByTraderBind(req);
        } else {
            return getGoodsInfoByTraderFactory(req);
        }
    }

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoByTraderBind(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        TraderInfo traderInfo = super.traderRoleService.getTraderInfoByUser(req.getCreatorInfo());
        TraderFactoryMerchantMap traderFactoryMerchantMap =
                super.traderRoleService.getTraderFactoryMerchantMap(req.getCreatorInfo());
        List<GoodsTraderFactoryMerchantMap> goodsTraderFactoryMerchantMapList =
                getGoodsTraderFactoryMerchantMap(traderInfo, traderFactoryMerchantMap, req.getProductId());
        List<Integer> goodsIdList =
                ReflectionUtil.getFieldList(goodsTraderFactoryMerchantMapList, "goodsId", Integer.class);
        if (goodsIdList == null || goodsIdList.size() == 0) {
            return res;
        } else {
            PageInfo pageInfo = super.buildPage(req);
            PageInfo<GoodsInfo> goodsInfoList = getGoodsInfoByIdList(req, pageInfo, goodsIdList);
            res.buildPageInfo(pageInfo);
            res.setTotalPages(goodsInfoList.getPages());
            res.setTotalSize(goodsInfoList.getTotal());
            goodsInfoList.getList().forEach(goodsInfo -> res.getInfo().add(buildGoodInfoRespVO(goodsInfo)));
        }
        return res;
    }

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoByTraderFactory(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        FactoryInfo factoryInfo = super.traderRoleService.getFactoryInfoByUser(req.getCreatorInfo());
        FactoryMerchantMap factoryMerchantMap =
                super.factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList =
                getGoodsFactoryMerchantMapList(factoryMerchantMap, req.getProductId());
        List<Integer> goodsIdList = ReflectionUtil.getFieldList(goodsFactoryMerchantMapList, "goodsId", Integer.class);
        if (goodsIdList == null || goodsIdList.size() == 0) {
            return res;
        } else {
            PageInfo pageInfo = super.buildPage(req);
            PageInfo<GoodsInfo> goodsInfoList = getGoodsInfoByIdList(req, pageInfo, goodsIdList);
            res.buildPageInfo(pageInfo);
            res.setTotalPages(goodsInfoList.getPages());
            res.setTotalSize(goodsInfoList.getTotal());
            goodsInfoList.getList().forEach(goodsInfo -> res.getInfo().add(buildGoodInfoRespVO(goodsInfo)));
        }
        return res;
    }

    private List<GoodsInfo> getGoodsInfoListByMerchant(MerchantInfo merchantInfo) {
        return getGoodsInfoListByMerchant(merchantInfo, null);
    }

    private List<GoodsInfo> getGoodsInfoListByMerchant(MerchantInfo merchantInfo, Integer goodsId) {
        GoodsInfo goodsInfo = GoodsInfo.builder().merchantId(merchantInfo.getId())
                .valid(DataSourceCommonConstant.DATABASE_COMMON_VALID).build();
        if (goodsId != null) {
            goodsInfo.setId(goodsId);
        }
        return super.goodsInfoDao.getGoodsInfoSelective(goodsInfo);
    }

    private List<GoodsTraderFactoryMerchantMap> getGoodsTraderFactoryMerchantMap(TraderInfo traderInfo,
            TraderFactoryMerchantMap traderFactoryMerchantMap, Integer goodsId) {
        GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap = new GoodsTraderFactoryMerchantMap();
        goodsTraderFactoryMerchantMap.setTraderId(traderInfo.getId());
        goodsTraderFactoryMerchantMap.setTraderFactoryMerchantMapId(traderFactoryMerchantMap.getId());
        if (goodsId != null) {
            goodsTraderFactoryMerchantMap.setGoodsId(goodsId);
        }
        goodsTraderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsTraderFactoryMerchantMapDao
                .getGoodsTraderFactoryMerchantMapSelective(goodsTraderFactoryMerchantMap);
    }

    private void handleGoodsInfoDuplicate(GoodsInfo goodsInfo) {
        GoodsInfo duplicateGoodsInfo =
                GoodsInfo.builder().merchantId(goodsInfo.getMerchantId()).code(goodsInfo.getCode())
                        .valid(goodsInfo.getValid()).build();
        goodsInfoMapper.deleteByPrimaryKey(super.goodsInfoDao.getGoodsInfoSelective(duplicateGoodsInfo).get(0).getId());
        goodsInfoDao.updateGoodsInfo(goodsInfo);
    }

    private void makeGoodsFactoryMerchantMapRelationShip(GoodsInfo goodsInfo, FactoryMerchantMap factoryMerchantMap) {
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setGoodsId(goodsInfo.getId());
        goodsFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        goodsFactoryMerchantMapDao.updateGoodsFactoryMerchantMap(goodsFactoryMerchantMap);
    }

    private void saveGoodsInfo(GoodsInfo goodsInfo) {
        try {
            goodsInfoDao.updateGoodsInfo(goodsInfo);
        } catch (DuplicateKeyException e) {
            handleGoodsInfoDuplicate(goodsInfo);
        }
    }

    private GoodsInfo updateGoodsInfoByFactory(ProductBO<GoodsInfoReqVO> req) {
        GoodsInfo goodsInfo = new GoodsInfo();
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(req);
        FactoryInfo factoryInfo = factoryInfoList.get(0);
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList = getGoodsFactoryMerchantMapList(factoryInfo);
        if (goodsFactoryMerchantMapList == null || goodsFactoryMerchantMapList.size() < factoryMerchantMap
                .getGoodsNumber()) {
            BeanUtil.copyProperties(req.getReq().getGoods(), goodsInfo);
            goodsInfo.setMerchantId(factoryMerchantMap.getMerchantId());
            goodsInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            saveGoodsInfo(goodsInfo);
            if (req.getReq().getGoods().getId() == null) {
                makeGoodsFactoryMerchantMapRelationShip(goodsInfo, factoryMerchantMap);
            }
        } else {
            throw new ServiceException("数量已达上限,不能再添加了");
        }
        return goodsInfo;
    }

    private GoodsInfo updateGoodsInfoByMerchant(ProductBO<GoodsInfoReqVO> req) {
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtil.copyProperties(req.getReq().getGoods(), goodsInfo);
        MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
        goodsInfo.setMerchantId(merchantInfo.getId());
        goodsInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        saveGoodsInfo(goodsInfo);
        return goodsInfo;
    }

    private void validateBind(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        ProductBO<GoodsInfoReqVO> goods = (ProductBO<GoodsInfoReqVO>) req;
        GoodsInfoReqVO goodsInfoReqVO = goods.getReq();
        switch (roleType) {
            case "merchant":
                MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
                goodsInfoReqVO.getBindIdList().forEach(id -> {
                    GoodsInfo goodsInfo = super.goodsInfoDao.getGoodsInfoById(id);
                    if (!merchantInfo.getId().equals(goodsInfo.getMerchantId())) {
                        throw new ServiceException("不允许[" + roleType + "]角色绑定商品: " + goodsInfo.getId());
                    }
                });
                goodsInfoReqVO.getUnbindIdList().forEach(id -> {
                    GoodsInfo goodsInfo = super.goodsInfoDao.getGoodsInfoById(id);
                    if (!merchantInfo.getId().equals(goodsInfo.getMerchantId())) {
                        throw new ServiceException("不允许[" + roleType + "]角色解除绑定商品: " + goodsInfo.getId());
                    }
                });

                break;
            case "factory":
                FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(req);
                goodsInfoReqVO.getBindIdList().forEach(id -> {
                    GoodsInfo goodsInfo = super.goodsInfoDao.getGoodsInfoById(id);
                    if (!factoryMerchantMap.getMerchantId().equals(goodsInfo.getMerchantId())) {
                        throw new ServiceException("不允许[" + roleType + "]角色绑定商品: " + goodsInfo.getId());
                    }
                });
                goodsInfoReqVO.getUnbindIdList().forEach(id -> {
                    GoodsInfo goodsInfo = super.goodsInfoDao.getGoodsInfoById(id);
                    if (!factoryMerchantMap.getMerchantId().equals(goodsInfo.getMerchantId())) {
                        throw new ServiceException("不允许[" + roleType + "]角色解除绑定商品: " + goodsInfo.getId());
                    }
                });
                if (!DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(factoryMerchantMap.getGoodsTraderBind())) {
                    throw new ServiceException("没有开通factory[" + factoryMerchantMap.getFactoryId() + "]角色绑定商品权限");
                }
                break;
            default:
                throw new ServiceException("不允许[" + roleType + "]角色绑定商品");
        }
    }

    private void validateSummary(ProductBO<QueryReqVO> req) {
        String roleType = req.getCreatorRole().getRole();
        if ("superAdmin".equals(roleType) || "admin".equals(roleType)) {

        } else {
            throw new ServiceException("不允许[" + roleType + "]角色查看商品汇总");
        }
    }

}
