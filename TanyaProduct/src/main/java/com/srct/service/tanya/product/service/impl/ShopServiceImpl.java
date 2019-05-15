/**
 * Title: ShopServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-22 09:29:07
 */
package com.srct.service.tanya.product.service.impl;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.config.FeatureConstant;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopTraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopTraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.ShopFactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.ShopTraderFactoryMerchantMapDao;
import com.srct.service.tanya.common.service.FeatureService;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.ShopService;
import com.srct.service.tanya.product.vo.ShopInfoReqVO;
import com.srct.service.tanya.product.vo.ShopInfoRespVO;
import com.srct.service.tanya.product.vo.ShopInfoVO;
import com.srct.service.tanya.role.service.FactoryRoleService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.ReflectionUtil;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sharuopeng
 */
@Service
public class ShopServiceImpl extends ProductServiceBaseImpl implements ShopService {

    @Autowired
    private FactoryRoleService factoryRoleService;
    @Autowired
    private ShopFactoryMerchantMapDao shopFactoryMerchantMapDao;
    @Autowired
    private ShopTraderFactoryMerchantMapDao shopTraderFactoryMerchantMapDao;
    @Autowired
    private FeatureService featureService;

    @Override
    public QueryRespVO<ShopInfoRespVO> updateShopInfo(ProductBO<ShopInfoReqVO> req) {
        validateUpdate(req);
        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<>();
        switch (req.getCreatorRole().getRole()) {
            case "factory": {
                ShopInfo shopInfo = updateShopInfoByFactory(req);
                ShopInfoRespVO shopInfoRespVO = buildShopInfoRespVO(shopInfo);
                res.getInfo().add(shopInfoRespVO);
                break;
            }
            case "merchant": {
                ShopInfo shopInfo = updateShopInfoByMerchant(req);
                ShopInfoRespVO shopInfoRespVO = buildShopInfoRespVO(shopInfo);
                res.getInfo().add(shopInfoRespVO);
                break;
            }
            default:
                break;
        }
        return res;
    }

    private ShopInfo updateShopInfoByMerchant(ProductBO<ShopInfoReqVO> req) {
        ShopInfo shopInfo = new ShopInfo();
        BeanUtil.copyProperties(req.getReq().getShop(), shopInfo);
        MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
        shopInfo.setMerchantId(merchantInfo.getId());
        shopInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        shopInfoDao.updateShopInfo(shopInfo);
        return shopInfo;
    }

    private ShopInfo updateShopInfoByFactory(ProductBO<ShopInfoReqVO> req) {
        ShopInfo shopInfo = new ShopInfo();
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(req);
        FactoryInfo factoryInfo = factoryInfoList.get(0);
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        BeanUtil.copyProperties(req.getReq().getShop(), shopInfo);
        shopInfo.setMerchantId(factoryMerchantMap.getMerchantId());
        shopInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        shopInfoDao.updateShopInfo(shopInfo);
        if (req.getReq().getShop().getId() == null) {
            makeShopFactoryMerchantMapRelationShip(shopInfo, factoryMerchantMap);
        }
        return shopInfo;
    }

    private void makeShopFactoryMerchantMapRelationShip(ShopInfo shopInfo, FactoryMerchantMap factoryMerchantMap) {
        ShopFactoryMerchantMap shopFactoryMerchantMap = new ShopFactoryMerchantMap();
        shopFactoryMerchantMap.setShopId(shopInfo.getId());
        shopFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
        shopFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        shopFactoryMerchantMapDao.updateShopFactoryMerchantMap(shopFactoryMerchantMap);
    }

    private List<ShopFactoryMerchantMap> getShopFactoryMerchantMapList(FactoryInfo factoryInfo) {
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        ShopFactoryMerchantMap shopFactoryMerchantMap = new ShopFactoryMerchantMap();
        shopFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
        shopFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return shopFactoryMerchantMapDao.getShopFactoryMerchantMapSelective(shopFactoryMerchantMap);
    }

    private ShopFactoryMerchantMap getShopFactoryMerchantMapList(FactoryInfo factoryInfo, ShopInfo shopInfo) {
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        ShopFactoryMerchantMap shopFactoryMerchantMap = new ShopFactoryMerchantMap();
        shopFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
        shopFactoryMerchantMap.setShopId(shopInfo.getId());
        shopFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return shopFactoryMerchantMapDao.getShopFactoryMerchantMapSelective(shopFactoryMerchantMap).get(0);
    }

    @Override
    public QueryRespVO<ShopInfoRespVO> getShopInfo(ProductBO<QueryReqVO> req) {
        validateQuery(req);
        String roleType = req.getCreatorRole().getRole();
        switch (roleType) {
            case "merchant":
                return getShopInfoByMerchant(req);
            case "factory":
                return getShopInfoByFactory(req);
            case "trader":
                return getShopInfoByTrader(req);
            default:
                throw new ServiceException("不允许[" + req.getCreatorRole().getComment() + "]角色查询药店");
        }
    }

    private QueryRespVO<ShopInfoRespVO> getShopInfoByTrader(ProductBO<QueryReqVO> req) {
        FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(req);
        if (DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(factoryMerchantMap.getShopTraderBind())) {
            return getShopInfoByTraderBind(req);
        } else {
            return getShopInfoByTraderFactory(req);
        }
    }

    private QueryRespVO<ShopInfoRespVO> getShopInfoByTraderFactory(ProductBO<QueryReqVO> req) {
        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<>();
        FactoryInfo factoryInfo = super.traderRoleService.getFactoryInfoByUser(req.getCreatorInfo());
        FactoryMerchantMap factoryMerchantMap =
                super.factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        List<Integer> shopIdList;
        if (featureService.getFeatureExpected(FeatureConstant.SHOP_BIND_FACTORY_MERCHANT_FEATURE, "1")) {
            List<ShopFactoryMerchantMap> shopFactoryMerchantMapList =
                    getShopFactoryMerchantMapList(factoryMerchantMap, req.getProductId());
            shopIdList = (List<Integer>) ReflectionUtil.getFieldList(shopFactoryMerchantMapList, "shopId");
        } else {
            List<ShopInfo> shopInfoList =
                    getShopInfoListByMerchant(merchantInfoDao.getMerchantInfoById(factoryMerchantMap.getMerchantId()),
                            req.getProductId());
            shopIdList = (List<Integer>) ReflectionUtil.getFieldList(shopInfoList, "id");
        }
        if (shopIdList == null || shopIdList.size() == 0) {
            return res;
        } else {
            PageInfo pageInfo = super.buildPage(req);
            PageInfo<ShopInfo> shopInfoList = getShopInfoByIdList(req, pageInfo, shopIdList);
            res.buildPageInfo(pageInfo);
            res.setTotalPages(shopInfoList.getPages());
            res.setTotalSize(shopInfoList.getTotal());
            shopInfoList.getList().forEach(shopInfo -> {
                res.getInfo().add(buildShopInfoRespVO(shopInfo));
            });
        }
        return res;
    }

    private QueryRespVO<ShopInfoRespVO> getShopInfoByTraderBind(ProductBO<QueryReqVO> req) {
        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<>();
        TraderInfo traderInfo = super.traderRoleService.getTraderInfoByUser(req.getCreatorInfo());
        TraderFactoryMerchantMap traderFactoryMerchantMap =
                super.traderRoleService.getTraderFactoryMerchantMap(req.getCreatorInfo());
        List<ShopTraderFactoryMerchantMap> shopTraderFactoryMerchantMapList =
                getShopTraderFactoryMerchantMap(traderInfo, traderFactoryMerchantMap, req.getProductId());
        List<Integer> shopIdList =
                (List<Integer>) ReflectionUtil.getFieldList(shopTraderFactoryMerchantMapList, "shopId");
        if (shopIdList == null || shopIdList.size() == 0) {
            return res;
        } else {
            PageInfo pageInfo = super.buildPage(req);
            PageInfo<ShopInfo> shopInfoList = getShopInfoByIdList(req, pageInfo, shopIdList);
            res.buildPageInfo(pageInfo);
            res.setTotalPages(shopInfoList.getPages());
            res.setTotalSize(shopInfoList.getTotal());
            shopInfoList.getList().forEach(shopInfo -> {
                res.getInfo().add(buildShopInfoRespVO(shopInfo));
            });
        }
        return res;
    }

    private List<ShopTraderFactoryMerchantMap> getShopTraderFactoryMerchantMap(TraderInfo traderInfo,
            TraderFactoryMerchantMap traderFactoryMerchantMap, Integer shopId) {
        ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap = new ShopTraderFactoryMerchantMap();
        shopTraderFactoryMerchantMap.setTraderId(traderInfo.getId());
        shopTraderFactoryMerchantMap.setTraderFactoryMerchantMapId(traderFactoryMerchantMap.getId());
        if (shopId != null) {
            shopTraderFactoryMerchantMap.setShopId(shopId);
        }
        shopTraderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return shopTraderFactoryMerchantMapDao.getShopTraderFactoryMerchantMapSelective(shopTraderFactoryMerchantMap);
    }

    private QueryRespVO<ShopInfoRespVO> getShopInfoByFactory(ProductBO<QueryReqVO> req) {
        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<>();
        FactoryInfo factoryInfo = super.factoryRoleService.getFactoryInfoByUser(req.getCreatorInfo());
        List<Integer> shopIdList;
        FactoryMerchantMap factoryMerchantMap =
                super.factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        if (featureService.getFeatureExpected(FeatureConstant.SHOP_BIND_FACTORY_MERCHANT_FEATURE, "1")) {
            List<ShopFactoryMerchantMap> shopFactoryMerchantMapList =
                    getShopFactoryMerchantMapList(factoryMerchantMap, req.getProductId());
            shopIdList = (List<Integer>) ReflectionUtil.getFieldList(shopFactoryMerchantMapList, "shopId");
        } else {
            List<ShopInfo> shopInfoList =
                    getShopInfoListByMerchant(merchantInfoDao.getMerchantInfoById(factoryMerchantMap.getMerchantId()),
                            req.getProductId());
            shopIdList = (List<Integer>) ReflectionUtil.getFieldList(shopInfoList, "id");
        }
        if (shopIdList == null || shopIdList.size() == 0) {
            return res;
        } else {
            PageInfo pageInfo = super.buildPage(req);
            PageInfo<ShopInfo> shopInfoList = getShopInfoByIdList(req, pageInfo, shopIdList);
            res.buildPageInfo(pageInfo);
            res.setTotalPages(shopInfoList.getPages());
            res.setTotalSize(shopInfoList.getTotal());
            shopInfoList.getList().forEach(shopInfo -> {
                res.getInfo().add(buildShopInfoRespVO(shopInfo));
            });
        }
        return res;
    }

    private PageInfo<ShopInfo> getShopInfoByIdList(ProductBO<QueryReqVO> req, PageInfo pageInfo,
            List<Integer> shopIdList) {
        ShopInfoExample example = new ShopInfoExample();
        ShopInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(shopIdList);
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        if (req.getTitle() != null) {
            criteria.andTitleLike("%" + req.getTitle() + "%");
        }
        return super.shopInfoDao.getShopInfoByExample(example, pageInfo);
    }

    private List<ShopInfo> getShopInfoListByMerchant(MerchantInfo merchantInfo, Integer shopId) {
        ShopInfo shopInfo = ShopInfo.builder().merchantId(merchantInfo.getId())
                .valid(DataSourceCommonConstant.DATABASE_COMMON_VALID).build();
        if (shopId != null) {
            shopInfo.setId(shopId);
        }
        return super.shopInfoDao.getShopInfoSelective(shopInfo);
    }

    private List<ShopFactoryMerchantMap> getShopFactoryMerchantMapList(FactoryMerchantMap factoryMerchantMap,
            Integer shopId) {
        ShopFactoryMerchantMap shopFactoryMerchantMapEx = new ShopFactoryMerchantMap();
        shopFactoryMerchantMapEx.setFactoryMerchantMapId(factoryMerchantMap.getId());
        if (shopId != null) {
            shopFactoryMerchantMapEx = shopFactoryMerchantMapEx.toBuilder().id(shopId).build();
        }
        shopFactoryMerchantMapEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return shopFactoryMerchantMapDao.getShopFactoryMerchantMapSelective(shopFactoryMerchantMapEx);
    }

    private QueryRespVO<ShopInfoRespVO> getShopInfoByMerchant(ProductBO<QueryReqVO> req) {
        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<>();
        MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
        ShopInfoExample shopInfoEx = new ShopInfoExample();
        ShopInfoExample.Criteria criteria = shopInfoEx.createCriteria();
        criteria.andMerchantIdEqualTo(merchantInfo.getId());
        if (req.getProductId() != null) {
            criteria.andIdEqualTo(req.getProductId());
        }
        if (req.getTitle() != null) {
            criteria.andTitleLike("%" + req.getTitle() + "%");
        }
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        PageInfo pageInfo = super.buildPage(req);
        PageInfo<ShopInfo> shopInfoList = super.shopInfoDao.getShopInfoByExample(shopInfoEx, pageInfo);
        res.buildPageInfo(pageInfo);
        res.setTotalPages(shopInfoList.getPages());
        res.setTotalSize(shopInfoList.getTotal());
        shopInfoList.getList().forEach(shopInfo -> res.getInfo().add(buildShopInfoRespVO(shopInfo)));
        return res;
    }

    @Override
    public QueryRespVO<ShopInfoRespVO> bindShopInfo(ProductBO<ShopInfoReqVO> req) {
        validateBind(req);
        String roleType = req.getCreatorRole().getRole();
        ShopInfoReqVO shopInfoReqVO = req.getReq();
        ShopInfoVO shopInfoVO = shopInfoReqVO.getShop();
        switch (roleType) {
            case "merchant":
                bindShopFactoryRelationship(req, shopInfoReqVO.getBindIdList(),
                        DataSourceCommonConstant.DATABASE_COMMON_VALID);
                bindShopFactoryRelationship(req, shopInfoReqVO.getUnbindIdList(),
                        DataSourceCommonConstant.DATABASE_COMMON_INVALID);
                break;
            case "factory":
                bindShopTraderRelationship(req, shopInfoReqVO.getBindIdList(),
                        DataSourceCommonConstant.DATABASE_COMMON_VALID);
                bindShopTraderRelationship(req, shopInfoReqVO.getUnbindIdList(),
                        DataSourceCommonConstant.DATABASE_COMMON_INVALID);
                break;
            default:
                throw new ServiceException("不允许[" + req.getCreatorRole().getComment() + "]角色绑定药店");
        }

        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<>();
        ShopInfo shopInfo = shopInfoDao.getShopInfoById(shopInfoVO.getId());
        ShopInfoRespVO shopInfoRespVO = buildShopInfoRespVO(shopInfo);
        res.getInfo().add(shopInfoRespVO);
        return res;
    }

    private void bindShopTraderRelationship(ProductBO<ShopInfoReqVO> req, List<Integer> traderIdList, Byte valid) {
        ShopInfoReqVO shopInfoReqVO = req.getReq();
        ShopInfoVO shopInfoVO = shopInfoReqVO.getShop();
        if (traderIdList != null && traderIdList.size() > 0) {
            traderIdList.forEach(traderId -> {
                TraderInfo traderInfo = super.traderInfoDao.getTraderInfoById(traderId);
                TraderFactoryMerchantMap traderFactoryMerchantMap = super.getTraderFactoryMerchantMap(req);
                List<ShopTraderFactoryMerchantMap> shopFactoryMerchantMapList =
                        getShopTraderFactoryMerchantMap(traderInfo, traderFactoryMerchantMap, shopInfoVO.getId());
                boolean notFound = true;
                for (ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap : shopFactoryMerchantMapList) {
                    if (shopTraderFactoryMerchantMap.getTraderId().equals(traderId)) {
                        notFound = false;
                        if (!shopTraderFactoryMerchantMap.getValid().equals(valid)) {
                            shopTraderFactoryMerchantMap.setValid(valid);
                            shopTraderFactoryMerchantMapDao
                                    .updateShopTraderFactoryMerchantMap(shopTraderFactoryMerchantMap);
                        }
                    }
                }
                if (notFound) {
                    ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap = new ShopTraderFactoryMerchantMap();
                    shopTraderFactoryMerchantMap.setShopId(shopInfoVO.getId());
                    shopTraderFactoryMerchantMap.setValid(valid);
                    shopTraderFactoryMerchantMap.setTraderFactoryMerchantMapId(traderFactoryMerchantMap.getId());
                    shopTraderFactoryMerchantMap.setTraderId(traderId);
                    shopTraderFactoryMerchantMapDao.updateShopTraderFactoryMerchantMap(shopTraderFactoryMerchantMap);
                }
            });
        }
    }

    private void bindShopFactoryRelationship(ProductBO<ShopInfoReqVO> req, List<Integer> factoryIdList, Byte valid) {
        ShopInfoReqVO shopInfoReqVO = req.getReq();
        ShopInfoVO shopInfoVO = shopInfoReqVO.getShop();
        if (factoryIdList != null && factoryIdList.size() > 0) {
            factoryIdList.forEach(factoryId -> {
                FactoryMerchantMap factoryMerchantMap =
                        super.getFactoryMerchantMapByMerchantIdAndFactoryId(shopInfoVO.getMerchantId(), factoryId);
                ShopFactoryMerchantMap shopFactoryMerchantMap = new ShopFactoryMerchantMap();
                shopFactoryMerchantMap.setShopId(shopInfoVO.getId());
                shopFactoryMerchantMap.setFactoryMerchantMapId(factoryMerchantMap.getId());
                List<ShopFactoryMerchantMap> shopFactoryMerchantMapList =
                        shopFactoryMerchantMapDao.getShopFactoryMerchantMapSelective(shopFactoryMerchantMap);
                if (shopFactoryMerchantMapList != null && shopFactoryMerchantMapList.size() > 0) {
                    shopFactoryMerchantMap = shopFactoryMerchantMapList.get(0);
                    if (!shopFactoryMerchantMap.getValid().equals(valid)) {
                        shopFactoryMerchantMap.setValid(valid);
                        shopFactoryMerchantMapDao.updateShopFactoryMerchantMap(shopFactoryMerchantMap);
                    }
                } else {
                    shopFactoryMerchantMap.setValid(valid);
                    shopFactoryMerchantMapDao.updateShopFactoryMerchantMap(shopFactoryMerchantMap);
                }
            });
        }
    }

    private ShopInfoRespVO buildShopInfoRespVO(ShopInfo shopInfo) {
        ShopInfoRespVO shopInfoRespVO = new ShopInfoRespVO();
        ShopInfoVO vo = new ShopInfoVO();
        BeanUtil.copyProperties(shopInfo, vo);
        BeanUtil.copyProperties(shopInfo, shopInfoRespVO);
        shopInfoRespVO.setShopInfoVO(vo);
        shopInfoRespVO.setMerchantRoleInfoVO(super.getRoleInfoVO(shopInfo.getMerchantId(), "merchant"));
        return shopInfoRespVO;
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (!roleType.equals("factory") && !roleType.equals("merchant")) {
            throw new ServiceException("不允许[" + req.getCreatorRole().getComment() + "]角色创建/更新药店");
        }
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        throw new ServiceException("不支持确认药店");
    }

    @Override
    public QueryRespVO<ShopInfoRespVO> delShopInfo(ProductBO<ShopInfoReqVO> req) {
        validateDelete(req);
        ShopInfo shopInfo = shopInfoDao.getShopInfoById(req.getProductId());
        shopInfoDao.delShopInfo(shopInfo);

        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buildShopInfoRespVO(shopInfo));
        return res;
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (roleType.equals("salesman")) {
            throw new ServiceException("不允许[" + req.getCreatorRole().getComment() + "]角色删除药店");
        }
    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        if (roleType.equals("salesman")) {
            throw new ServiceException("不允许[" + req.getCreatorRole().getComment() + "]角色查询药店");
        }
    }

    private void validateBind(ProductBO<?> req) {
        String roleType = req.getCreatorRole().getRole();
        ProductBO<ShopInfoReqVO> shop = (ProductBO<ShopInfoReqVO>) req;
        ShopInfoReqVO shopInfoReqVO = shop.getReq();
        ShopInfoVO shopInfoVO = shopInfoReqVO.getShop();
        switch (roleType) {
            case "merchant":
                MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
                if (!merchantInfo.getId().equals(shopInfoVO.getMerchantId())) {
                    throw new ServiceException(
                            "不允许[" + req.getCreatorRole().getComment() + "]角色绑定药店: " + shopInfoVO.getId());
                }
                break;
            case "factory":
                FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(req);
                if (!factoryMerchantMap.getMerchantId().equals(shopInfoVO.getMerchantId())) {
                    throw new ServiceException(
                            "不允许[" + req.getCreatorRole().getComment() + "]角色绑定药店: " + shopInfoVO.getId());
                }
                if (!DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(factoryMerchantMap.getShopTraderBind())) {
                    throw new ServiceException("没有开通factory[" + factoryMerchantMap.getFactoryId() + "]角色绑定药店权限");
                }
                break;
            default:
                throw new ServiceException(
                        "不允许[" + req.getCreatorRole().getComment() + "]角色绑定药店: " + shopInfoVO.getId());
        }
    }


    @Override
    public QueryRespVO<ShopInfoRespVO> getShopBindInfo(ProductBO<QueryReqVO> req) {
        validateQuery(req);
        String roleType = req.getCreatorRole().getRole();
        switch (roleType) {
            case "merchant":
                if (featureService.getFeatureExpected(FeatureConstant.SHOP_BIND_FACTORY_MERCHANT_FEATURE, "1")) {
                    return getShopBindInfoByMerchant(req);
                } else {
                    return getShopInfoByMerchant(req);
                }
            case "factory":
                return getShopBindInfoByFactory(req);
            default:
                throw new ServiceException("不允许[" + roleType + "]角色查看药店绑定信息");

        }

    }

    private QueryRespVO<ShopInfoRespVO> getShopBindInfoByFactory(ProductBO<QueryReqVO> req) {
        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<>();
        FactoryInfo factoryInfo = super.factoryRoleService.getFactoryInfoByUser(req.getCreatorInfo());
        FactoryMerchantMap factoryMerchantMap = super.getFactoryMerchantMap(req);
        if (!DataSourceCommonConstant.DATABASE_COMMON_VALID.equals(factoryMerchantMap.getShopTraderBind())) {
            throw new ServiceException("没有开通factory[" + factoryMerchantMap.getFactoryId() + "]角色绑定药店权限");
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
                (List<Integer>) ReflectionUtil.getFieldList(traderFactoryMerchantMapList, "id");
        if (traderFactoryMerchantMapIdList == null || traderFactoryMerchantMapIdList.size() == 0) {
            return res;
        }
        PageInfo pageInfo = super.buildPage(req);
        ShopTraderFactoryMerchantMapExample example = new ShopTraderFactoryMerchantMapExample();
        ShopTraderFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        criteria.andTraderFactoryMerchantMapIdIn(traderFactoryMerchantMapIdList);
        PageInfo<ShopTraderFactoryMerchantMap> shopTraderFactoryMerchantMapList =
                shopTraderFactoryMerchantMapDao.getShopTraderFactoryMerchantMapByExample(example, pageInfo);
        res.buildPageInfo(pageInfo);
        res.setTotalPages(shopTraderFactoryMerchantMapList.getPages());
        res.setTotalSize(shopTraderFactoryMerchantMapList.getTotal());
        shopTraderFactoryMerchantMapList.getList().forEach(shopTraderFactoryMerchantMap -> {
            ShopInfo shopInfo = shopInfoDao.getShopInfoById(shopTraderFactoryMerchantMap.getShopId());
            res.getInfo().add(buildShopInfoRespVO(shopInfo));
        });
        return res;
    }

    private QueryRespVO<ShopInfoRespVO> getShopBindInfoByMerchant(ProductBO<QueryReqVO> req) {
        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<>();
        MerchantInfo merchantInfo = super.merchantRoleService.getMerchantInfoByUser(req.getCreatorInfo());
        FactoryMerchantMap factoryMerchantMapEx = FactoryMerchantMap.builder().merchantId(merchantInfo.getId())
                .valid(DataSourceCommonConstant.DATABASE_COMMON_VALID).build();
        if (req.getFactoryId() != null) {
            factoryMerchantMapEx = factoryMerchantMapEx.toBuilder().factoryId(req.getFactoryId()).build();
        }
        List<FactoryMerchantMap> factoryMerchantMapList =
                factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMapEx);
        List<Integer> factoryMerchantMapIdList =
                (List<Integer>) ReflectionUtil.getFieldList(factoryMerchantMapList, "id");
        if (factoryMerchantMapIdList == null || 0 == factoryMerchantMapIdList.size()) {
            return res;
        }
        PageInfo pageInfo = super.buildPage(req);
        ShopFactoryMerchantMapExample example = new ShopFactoryMerchantMapExample();
        ShopFactoryMerchantMapExample.Criteria criteria = example.createCriteria();
        criteria.andValidEqualTo(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        criteria.andFactoryMerchantMapIdIn(factoryMerchantMapIdList);
        PageInfo<ShopFactoryMerchantMap> shopFactoryMerchantMapList =
                shopFactoryMerchantMapDao.getShopFactoryMerchantMapByExample(example, pageInfo);
        res.buildPageInfo(pageInfo);
        res.setTotalPages(shopFactoryMerchantMapList.getPages());
        res.setTotalSize(shopFactoryMerchantMapList.getTotal());
        shopFactoryMerchantMapList.getList().forEach(shopFactoryMerchantMap -> {
            ShopInfo shopInfo = shopInfoDao.getShopInfoById(shopFactoryMerchantMap.getShopId());
            res.getInfo().add(buildShopInfoRespVO(shopInfo));
        });
        return res;
    }

}
