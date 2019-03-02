/**
 * Title: productServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-20 09:31:53
 */
package com.srct.service.tanya.product.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.DiscountInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.MerchantInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.OrderInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanTraderMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.ShopInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderFactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderInfoDao;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.CampaignInfoVO;
import com.srct.service.tanya.product.vo.DiscountInfoVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.product.vo.ShopInfoVO;
import com.srct.service.tanya.role.service.FactoryRoleService;
import com.srct.service.tanya.role.service.MerchantRoleService;
import com.srct.service.tanya.role.service.SalesmanRoleService;
import com.srct.service.tanya.role.service.TraderRoleService;
import com.srct.service.tanya.role.vo.RoleInfoVO;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author sharuopeng
 *
 */
public abstract class ProductServiceBaseImpl {

    @Autowired
    protected MerchantRoleService merchantRoleService;

    @Autowired
    protected FactoryRoleService factoryRoleService;

    @Autowired
    protected TraderRoleService traderRoleService;

    @Autowired
    protected SalesmanRoleService salesmanRoleService;

    @Autowired
    protected TraderFactoryMerchantMapDao traderFactoryMerchantMapDao;

    @Autowired
    protected FactoryMerchantMapDao factoryMerchantMapDao;

    @Autowired
    protected SalesmanTraderMapDao salesmanTraderMapDao;

    @Autowired
    protected OrderInfoDao orderInfoDao;

    @Autowired
    protected GoodsInfoDao goodsInfoDao;

    @Autowired
    protected ShopInfoDao shopInfoDao;

    @Autowired
    protected CampaignInfoDao campaignInfoDao;

    @Autowired
    protected DiscountInfoDao discountInfoDao;

    @Autowired
    protected MerchantInfoDao merchantInfoDao;

    @Autowired
    protected FactoryInfoDao factoryInfoDao;

    @Autowired
    protected TraderInfoDao traderInfoDao;

    private final static String CRITERIA_CREAT_METHOD = "createCriteria";
    private final static String CRITERIA_VALID_METHOD = "andValidEqualTo";
    private final static String CRITERIA_STARTAT_BEFORE_METHOD = "andStartAtLessThanOrEqualTo";
    private final static String CRITERIA_ENDAT_AFTER_METHOD = "andEndAtGreaterThanOrEqualTo";
    // private final static String CRITERIA_OR_METHOD = "or";
    // private final static String CRITERIA_ENDAT_BEFORE_METHOD = "andEndAtLessThanOrEqualTo";
    // private final static String CRITERIA_STARTAT_AFTER_METHOD = "andStartAtGreaterThanOrEqualTo";

    /**
     * @param order
     * @return
     */
    public FactoryInfo getFactoryInfo(ProductBO<?> bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        String roleType = bo.getCreaterRole().getRole();
        FactoryInfo factoryInfo = null;
        if (roleType.equals("trader")) {
            factoryInfo = traderRoleService.getFactoryInfoByUser(userInfo);
        } else if (roleType.equals("factory")) {
            factoryInfo = factoryRoleService.getFactoryInfoByUser(userInfo);
        } else if (roleType.equals("merchant")) {
            if (bo.getFactoryId() != null) {
                factoryInfo = factoryInfoDao.getFactoryInfobyId(bo.getFactoryId());
            }
        }
        if (factoryInfo == null) {
            throw new ServiceException(
                "cant get factory info for " + bo.getProductType() + " by role " + bo.getCreaterRole().getRole());
        }

        return factoryInfo;
    }

    /**
     * @param order
     * @return
     */
    public TraderInfo getTraderInfo(ProductBO<?> bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        String roleType = bo.getCreaterRole().getRole();
        TraderInfo traderInfo = null;
        if (roleType.equals("trader")) {
            traderInfo = traderRoleService.getTraderInfoByUser(userInfo);
        } else if (roleType.equals("salesman")) {
            traderInfo = salesmanRoleService.getTraderInfoByUser(userInfo);
        } else {
            throw new ServiceException(
                "cant get trader info for " + bo.getProductType() + " by role " + bo.getCreaterRole().getRole());
        }

        return traderInfo;
    }

    public TraderFactoryMerchantMap getTraderFactoryMerchantMap(ProductBO<?> bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        String roleType = bo.getCreaterRole().getRole();

        if (roleType.equals("trader")) {
            return traderRoleService.getTraderFactoryMerchantMap(userInfo);
        } else {
            throw new ServiceException("cant get trader-factory-merchantMap info for " + bo.getProductType()
                + " by role " + bo.getCreaterRole().getRole());
        }
    }

    public FactoryMerchantMap getFactoryMerchantMap(ProductBO<?> bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        String roleType = bo.getCreaterRole().getRole();

        if (roleType.equals("factory")) {
            FactoryInfo factoryInfo = factoryRoleService.getFactoryInfoByUser(userInfo);
            return factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        } else {
            throw new ServiceException("cant get trader-factory-merchantMap info for " + bo.getProductType()
                + " by role " + bo.getCreaterRole().getRole());
        }
    }

    /**
     * @param order
     * @param factoryInfo
     * @return
     */
    public List<Integer> buildTraderFactoryMerchantMapIdList(ProductBO<?> req, FactoryInfo factoryInfo) {
        TraderFactoryMerchantMapExample mapExample =
            (TraderFactoryMerchantMapExample)makeQueryExample(req, TraderFactoryMerchantMapExample.class);
        TraderFactoryMerchantMapExample.Criteria mapCriteria = mapExample.getOredCriteria().get(0);
        mapCriteria.andFactoryIdEqualTo(factoryInfo.getId());
        List<TraderFactoryMerchantMap> maps =
            traderFactoryMerchantMapDao.getTraderFactoryMerchantMapByExample(mapExample);
        List<Integer> traderFactoryMerchantMapIdList = new ArrayList<>();
        maps.forEach(map -> {
            traderFactoryMerchantMapIdList.add(map.getId());
        });
        return traderFactoryMerchantMapIdList;
    }

    /**
     * @param discount
     * @param factoryInfo
     * @return
     */
    public List<Integer> buildFactoryMerchantMapIdList(ProductBO<?> req, FactoryInfo factoryInfo) {
        FactoryMerchantMapExample mapExample =
            (FactoryMerchantMapExample)makeQueryExample(req, FactoryMerchantMapExample.class);
        FactoryMerchantMapExample.Criteria mapCriteria = mapExample.getOredCriteria().get(0);
        mapCriteria.andFactoryIdEqualTo(factoryInfo.getId());
        List<FactoryMerchantMap> maps = factoryMerchantMapDao.getFactoryMerchantMapByExample(mapExample);
        List<Integer> factoryMerchantMapIdList = new ArrayList<>();
        maps.forEach(map -> {
            factoryMerchantMapIdList.add(map.getId());
        });
        return factoryMerchantMapIdList;
    }

    /**
     * @param campaign
     * @param traderInfo
     * @return
     */
    public List<Integer> buildSalesmanTraderMapTraderIdList(ProductBO<?> req, TraderInfo traderInfo) {
        SalesmanTraderMapExample mapExample =
            (SalesmanTraderMapExample)makeQueryExample(req, SalesmanTraderMapExample.class);
        SalesmanTraderMapExample.Criteria mapCriteria = mapExample.getOredCriteria().get(0);
        mapCriteria.andTraderIdEqualTo(traderInfo.getId());
        List<SalesmanTraderMap> maps = salesmanTraderMapDao.getSalesmanTraderMapByExample(mapExample);
        List<Integer> salesTraderMapIdList = new ArrayList<>();
        maps.forEach(map -> {
            salesTraderMapIdList.add(map.getTraderId());
        });
        return salesTraderMapIdList;
    }

    /**
     * @param order
     * @return
     */
    public <T> T makeQueryExample(ProductBO<?> bo, Class clazz) {
        Date now = new Date();
        QueryReqVO req = bo.getReq();
        Object example = null;

        try {
            example = clazz.newInstance();
            Method method = example.getClass().getMethod(CRITERIA_CREAT_METHOD);
            Object criteria = method.invoke(example);

            method = criteria.getClass().getMethod(CRITERIA_STARTAT_BEFORE_METHOD, Date.class);
            method.invoke(criteria, req.getQueryEndAt() == null ? now : req.getQueryEndAt());
            method = criteria.getClass().getMethod(CRITERIA_ENDAT_AFTER_METHOD, Date.class);
            method.invoke(criteria, req.getQueryStartAt() == null ? now : req.getQueryStartAt());

            method = criteria.getClass().getMethod(CRITERIA_VALID_METHOD, Byte.class);
            method.invoke(criteria, DataSourceCommonConstant.DATABASE_COMMON_VALID);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (T)example;
    }

    /**
     * @param id
     * @return
     */
    public DiscountInfoVO getDiscountInfoVObyId(Integer id) {
        if (id == null) {
            return null;
        }
        DiscountInfoVO discountInfoVO = new DiscountInfoVO();
        DiscountInfo discountInfo = discountInfoDao.getDiscountInfobyId(id);
        BeanUtil.copyProperties(discountInfo, discountInfoVO);
        return discountInfoVO;
    }

    /**
     * @param id
     * @return
     */
    public ShopInfoVO getShopInfoVObyId(Integer id) {
        if (id == null) {
            return null;
        }
        ShopInfoVO shopInfoVO = new ShopInfoVO();
        ShopInfo shopInfo = shopInfoDao.getShopInfobyId(id);
        BeanUtil.copyProperties(shopInfo, shopInfoVO);
        return shopInfoVO;
    }

    /**
     * @param id
     * @return
     */
    public GoodsInfoVO getGoodsInfoVObyId(Integer id) {
        if (id == null) {
            return null;
        }
        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
        GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfobyId(id);
        BeanUtil.copyProperties(goodsInfo, goodsInfoVO);
        return goodsInfoVO;
    }

    /**
     * @param id
     * @return
     */
    public CampaignInfoVO getCampaignInfoVObyId(Integer id) {
        if (id == null) {
            return null;
        }
        CampaignInfoVO campaignInfoVO = new CampaignInfoVO();
        CampaignInfo campaignInfo = campaignInfoDao.getCampaignInfobyId(id);
        BeanUtil.copyProperties(campaignInfo, campaignInfoVO);
        return campaignInfoVO;
    }

    public RoleInfoVO getRoleInfoVO(Integer id, String roleType) {
        RoleInfoVO roleInfoVO = new RoleInfoVO();
        Object targetInfo = null;
        switch (roleType) {
            case "merchant":
                targetInfo = merchantInfoDao.getMerchantInfobyId(id);
                break;
            case "factory":
                targetInfo = factoryInfoDao.getFactoryInfobyId(id);
                break;
            case "trader":
                targetInfo = traderInfoDao.getTraderInfobyId(id);
                break;
            default:
                throw new ServiceException("");
        }
        BeanUtil.copyProperties(targetInfo, roleInfoVO);
        roleInfoVO.setRoleType(roleType);
        return roleInfoVO;

    }

    public void buildRespbyReq(QueryRespVO<?> resp, ProductBO<?> req) {
        resp.setCurrentPage(req.getReq().getCurrentPage());
        resp.setPageSize(req.getReq().getPageSize());
        resp.setQueryStartAt(req.getReq().getQueryStartAt());
        resp.setQueryEndAt(req.getReq().getQueryEndAt());
    }

    /**
     * @param shop
     * @return
     */
    public PageInfo<?> buildPage(ProductBO<QueryReqVO> req) {
        PageInfo<?> pageInfo = new PageInfo<>();
        if (req.getReq().getCurrentPage() != null) {
            pageInfo.setPageNum(req.getReq().getCurrentPage());
        }
        if (req.getReq().getPageSize() != null) {
            pageInfo.setPageSize(req.getReq().getPageSize());
        }
        return pageInfo;
    }

    protected abstract void validateUpdate(ProductBO<?> req);

    protected abstract void validateConfirm(ProductBO<?> req);

}
