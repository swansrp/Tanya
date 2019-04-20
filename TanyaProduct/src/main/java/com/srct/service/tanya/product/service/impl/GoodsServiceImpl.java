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
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsFactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsTraderFactoryMerchantMapDao;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.DiscountService;
import com.srct.service.tanya.product.service.GoodsService;
import com.srct.service.tanya.product.vo.DiscountInfoVO;
import com.srct.service.tanya.product.vo.GoodsInfoReqVO;
import com.srct.service.tanya.product.vo.GoodsInfoRespVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.role.service.FactoryRoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        }
        return res;
    }

    private GoodsInfo updateGoodsInfoByMerchant(ProductBO<GoodsInfoReqVO> req) {
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtil.copyProperties(req.getReq().getGoods(), goodsInfo);
        MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
        goodsInfo.setMerchantId(merchantInfo.getId());
        goodsInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        goodsInfoDao.updateGoodsInfo(goodsInfo);
        return goodsInfo;
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
            goodsInfoDao.updateGoodsInfo(goodsInfo);
            if (req.getReq().getGoods().getId() == null) {
                makeGoodsFactoryMerchantMapRelationShip(goodsInfo, factoryMerchantMap);
            }
        } else {
            throw new ServiceException("数量已达上限,不能再添加了");
        }
        return goodsInfo;
    }

    private void makeGoodsFactoryMerchantMapRelationShip(GoodsInfo goodsInfo, FactoryMerchantMap factoryMerchantMap) {
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setGoodsId(goodsInfo.getId());
        goodsFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        goodsFactoryMerchantMapDao.updateGoodsFactoryMerchantMap(goodsFactoryMerchantMap);
    }

    private List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapList(FactoryInfo factoryInfo) {
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap);
    }

    private GoodsFactoryMerchantMap getGoodsFactoryMerchantMapList(FactoryInfo factoryInfo, GoodsInfo goodsInfo) {
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setGoodsId(goodsInfo.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap).get(0);
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

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoByTrader(ProductBO<QueryReqVO> req) {
        FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(req);
        if (DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(factoryMerchantMap.getGoodsTraderBind())) {
            return getGoodsInfoByTraderBind(req);
        } else {
            return getGoodsInfoByTraderFactory(req);
        }
    }

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoByTraderFactory(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        FactoryInfo factoryInfo = super.traderRoleService.getFactoryInfoByUser(req.getCreatorInfo());
        FactoryMerchantMap factoryMerchantMap =
                super.factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList =
                getGoodsFactoryMerchantMapList(factoryMerchantMap, req.getProductId());
        List<Integer> goodsIdList = (List<Integer>) ReflectionUtil.getFiledList(goodsFactoryMerchantMapList, "goodsId");
        if (goodsIdList == null || goodsIdList.size() == 0) {
            return res;
        } else {
            PageInfo pageInfo = super.buildPage(req);
            PageInfo<GoodsInfo> goodsInfoList = getGoodsInfoByIdList(req, pageInfo, goodsIdList);
            res.buildPageInfo(pageInfo);
            res.setTotalPages(goodsInfoList.getPages());
            res.setTotalSize(goodsInfoList.getTotal());
            goodsInfoList.getList().forEach(goodsInfo -> {
                res.getInfo().add(buildGoodInfoRespVO(goodsInfo));
            });
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

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoByTraderBind(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        TraderInfo traderInfo = super.traderRoleService.getTraderInfoByUser(req.getCreatorInfo());
        TraderFactoryMerchantMap traderFactoryMerchantMap =
                super.traderRoleService.getTraderFactoryMerchantMap(req.getCreatorInfo());
        List<GoodsTraderFactoryMerchantMap> goodsTraderFactoryMerchantMapList =
                getGoodsTraderFactoryMerchantMap(traderInfo, traderFactoryMerchantMap, req.getProductId());
        List<Integer> goodsIdList =
                (List<Integer>) ReflectionUtil.getFiledList(goodsTraderFactoryMerchantMapList, "goodsId");
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

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoByFactory(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        FactoryInfo factoryInfo = super.factoryRoleService.getFactoryInfoByUser(req.getCreatorInfo());
        FactoryMerchantMap factoryMerchantMap =
                super.factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList =
                getGoodsFactoryMerchantMapList(factoryMerchantMap, req.getProductId());
        List<Integer> goodsIdList = (List<Integer>) ReflectionUtil.getFiledList(goodsFactoryMerchantMapList, "goodsId");
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

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoByMerchant(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
        GoodsInfoExample goodsInfoEx = new GoodsInfoExample();
        GoodsInfoExample.Criteria criteria = goodsInfoEx.createCriteria();
        criteria.andMerchantIdEqualTo(merchantInfo.getId());
        if (req.getProductId() != null) {
            criteria.andIdEqualTo(req.getProductId());
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
    public QueryRespVO<GoodsInfoRespVO> bindGoodsInfo(ProductBO<GoodsInfoReqVO> req) {
        validateBind(req);
        String roleType = req.getCreatorRole().getRole();
        GoodsInfoReqVO goodsInfoReqVO = req.getReq();
        GoodsInfoVO goodsInfoVO = goodsInfoReqVO.getGoods();
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
        GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfoById(goodsInfoVO.getId());
        GoodsInfoRespVO goodsInfoRespVO = buildGoodInfoRespVO(goodsInfo);
        res.getInfo().add(goodsInfoRespVO);
        return res;
    }

    private void bindGoodsTraderRelationship(ProductBO<GoodsInfoReqVO> req, List<Integer> traderIdList, Byte valid) {
        GoodsInfoReqVO goodsInfoReqVO = req.getReq();
        GoodsInfoVO goodsInfoVO = goodsInfoReqVO.getGoods();
        if (traderIdList != null && traderIdList.size() > 0) {
            traderIdList.forEach(traderId -> {
                TraderInfo traderInfo = super.traderInfoDao.getTraderInfoById(traderId);
                TraderFactoryMerchantMap traderFactoryMerchantMap = super.getTraderFactoryMerchantMap(req);
                List<GoodsTraderFactoryMerchantMap> goodsFactoryMerchantMapList =
                        getGoodsTraderFactoryMerchantMap(traderInfo, traderFactoryMerchantMap, goodsInfoVO.getId());
                boolean notFound = true;
                for (GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap : goodsFactoryMerchantMapList) {
                    if (goodsTraderFactoryMerchantMap.getTraderId().equals(traderId)) {
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
                    goodsTraderFactoryMerchantMap.setGoodsId(goodsInfoVO.getId());
                    goodsTraderFactoryMerchantMap.setValid(valid);
                    goodsTraderFactoryMerchantMap.setTraderFactoryMerchantMapId(traderFactoryMerchantMap.getId());
                    goodsTraderFactoryMerchantMap.setTraderId(traderId);
                    goodsTraderFactoryMerchantMapDao.updateGoodsTraderFactoryMerchantMap(goodsTraderFactoryMerchantMap);
                }
            });
        }
    }

    private void bindGoodsFactoryRelationship(ProductBO<GoodsInfoReqVO> req, List<Integer> factoryIdList, Byte valid) {
        GoodsInfoReqVO goodsInfoReqVO = req.getReq();
        GoodsInfoVO goodsInfoVO = goodsInfoReqVO.getGoods();
        if (factoryIdList != null && factoryIdList.size() > 0) {
            factoryIdList.forEach(factoryId -> {
                FactoryMerchantMap factoryMerchantMap =
                        super.getFactoryMerchantMapByMerchantIdAndFactoryId(goodsInfoVO.getMerchantId(), factoryId);
                GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
                goodsFactoryMerchantMap.setGoodsId(goodsInfoVO.getId());
                goodsFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
                List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList =
                        goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap);
                if (goodsFactoryMerchantMapList != null && goodsFactoryMerchantMapList.size() > 0) {
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
        //vo.setAmount(discountInfo.getAmount());
        BeanUtil.copyProperties(goodsInfo, goodsInfoRespVO);
        goodsInfoRespVO.setGoodsInfoVO(vo);
        goodsInfoRespVO.setUnit(discountInfo.getGoodsNumber());
        DiscountInfoVO discountInfoVO = new DiscountInfoVO();
        BeanUtil.copyProperties(discountInfo, discountInfoVO);
        goodsInfoRespVO.setDiscountInfoVO(discountInfoVO);
        return goodsInfoRespVO;
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("factory") && !roleType.equals("merchant")) {
            throw new ServiceException("不允许[" + roleType + "]角色创建/更新商品");
        }
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        throw new ServiceException("不支持确认商品");
    }

    @Override
    public QueryRespVO<GoodsInfoRespVO> delGoodsInfo(ProductBO<GoodsInfoReqVO> req) {
        validateDelete(req);
        GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfoById(req.getProductId());
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(req);
        FactoryInfo factoryInfo = factoryInfoList.get(0);
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = getGoodsFactoryMerchantMapList(factoryInfo, goodsInfo);
        FactoryMerchantMap factoryMerchantMap =
                factoryMerchantMapDao.getFactoryMerchantMapById(goodsFactoryMerchantMap.getFactoryMerchantMapId());
        factoryMerchantMap.setGoodsNumber(factoryMerchantMap.getGoodsNumber() - 1);
        goodsInfoDao.delGoodsInfo(goodsInfo);

        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildGoodInfoRespVO(goodsInfo));
        return res;
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

    private void validateBind(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        ProductBO<GoodsInfoReqVO> goods = (ProductBO<GoodsInfoReqVO>) req;
        GoodsInfoReqVO goodsInfoReqVO = goods.getReq();
        GoodsInfoVO goodsInfoVO = goodsInfoReqVO.getGoods();
        switch (roleType) {
            case "merchant":
                MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
                if (!merchantInfo.getId().equals(goodsInfoVO.getMerchantId())) {
                    throw new ServiceException("不允许[" + roleType + "]角色绑定商品: " + goodsInfoVO.getId());
                }
                break;
            case "factory":
                FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(req);
                if (!factoryMerchantMap.getMerchantId().equals(goodsInfoVO.getMerchantId())) {
                    throw new ServiceException("不允许[" + roleType + "]角色绑定商品: " + goodsInfoVO.getId());
                }
                if (!DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(factoryMerchantMap.getGoodsTraderBind())) {
                    throw new ServiceException("没有开通factory[" + factoryMerchantMap.getFactoryId() + "]角色绑定商品权限");
                }
                break;
            default:
                throw new ServiceException("不允许[" + roleType + "]角色绑定商品: " + goodsInfoVO.getId());
        }
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

    private QueryRespVO<GoodsInfoRespVO> getGoodsBindInfoByFactory(ProductBO<QueryReqVO> req) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        FactoryInfo factoryInfo = super.factoryRoleService.getFactoryInfoByUser(req.getCreatorInfo());
        FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(req);
        if (!DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(factoryMerchantMap.getGoodsTraderBind())) {
            throw new ServiceException("没有开通factory[" + factoryMerchantMap.getFactoryId() + "]角色绑定商品权限");
        }
        TraderFactoryMerchantMap TraderFactoryMerchantMapEx = new TraderFactoryMerchantMap();
        TraderFactoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        TraderFactoryMerchantMapEx.setFactoryId(factoryInfo.getId());
        TraderFactoryMerchantMapEx.setFactoryMerchantMapId(factoryMerchantMap.getId());
        if (req.getTraderId() != null) {
            TraderFactoryMerchantMapEx.setTraderId(req.getTraderId());
        }
        List<TraderFactoryMerchantMap> traderFactoryMerchantMapList =
                super.traderFactoryMerchantMapDao.getTraderFactoryMerchantMapSelective(TraderFactoryMerchantMapEx);
        List<Integer> traderFactoryMerchantMapIdList =
                (List<Integer>) ReflectionUtil.getFiledList(traderFactoryMerchantMapList, "id");
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
        FactoryMerchantMap factoryMerchantMapEx = FactoryMerchantMap.builder().merchantId(merchantInfo.getId())
                .valid(DataSourceCommonConstant.DATABASE_COMMON_VALID).build();
        if (req.getFactoryId() != null) {
            factoryMerchantMapEx = factoryMerchantMapEx.toBuilder().factoryId(req.getFactoryId()).build();
        }
        List<FactoryMerchantMap> factoryMerchantMapList =
                factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx);
        List<Integer> factoryMerchantMapIdList =
                (List<Integer>) ReflectionUtil.getFiledList(factoryMerchantMapList, "id");
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

}
