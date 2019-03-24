/**
 * Title: productServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-20 09:31:53
 */
package com.srct.service.tanya.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.DiscountInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.MerchantInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignHistoryDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignSalesmanMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.DiscountInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.MerchantInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.OrderInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanTraderMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.ShopInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderFactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderInfoDao;
import com.srct.service.tanya.common.service.UserService;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.vo.CampaignInfoVO;
import com.srct.service.tanya.product.vo.DiscountInfoVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.product.vo.ShopInfoVO;
import com.srct.service.tanya.role.service.FactoryRoleService;
import com.srct.service.tanya.role.service.MerchantRoleService;
import com.srct.service.tanya.role.service.RoleService;
import com.srct.service.tanya.role.service.SalesmanRoleService;
import com.srct.service.tanya.role.service.TraderRoleService;
import com.srct.service.tanya.role.vo.RoleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author sharuopeng
 */
public abstract class ProductServiceBaseImpl {

    private final static int DEFAULT_PERIOD_VALUE = 1;
    private final static int DEFAULT_PERIOD_TYPE = Calendar.YEAR;
    private final static String CRITERIA_CREAT_METHOD = "createCriteria";
    private final static String CRITERIA_VALID_METHOD = "andValidEqualTo";
    private final static String CRITERIA_STARTAT_BEFORE_METHOD = "andStartAtLessThanOrEqualTo";
    private final static String CRITERIA_ENDAT_AFTER_METHOD = "andEndAtGreaterThanOrEqualTo";
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
    @Autowired
    protected SalesmanInfoDao salesmanInfoDao;
    @Autowired
    protected CampaignHistoryDao campaignHistoryDao;
    @Autowired
    protected CampaignSalesmanMapDao campaignSalesmanMapDao;
    @Autowired
    protected UserService userService;
    // private final static String CRITERIA_OR_METHOD = "or";
    // private final static String CRITERIA_ENDAT_BEFORE_METHOD = "andEndAtLessThanOrEqualTo";
    // private final static String CRITERIA_STARTAT_AFTER_METHOD = "andStartAtGreaterThanOrEqualTo";

    public List<FactoryInfo> getFactoryInfoList(ProductBO<?> bo) {
        List<FactoryInfo> facotoryInfoList = new ArrayList<>();
        UserInfo userInfo = bo.getCreaterInfo();
        String roleType = bo.getCreaterRole().getRole();
        if (roleType.equals("trader")) {
            facotoryInfoList.add(traderRoleService.getFactoryInfoByUser(userInfo));
        } else if (roleType.equals("factory")) {
            facotoryInfoList.add(factoryRoleService.getFactoryInfoByUser(userInfo));
        } else if (roleType.equals("merchant")) {
            if (bo.getFactoryId() != null) {
                facotoryInfoList.add(factoryInfoDao.getFactoryInfobyId(bo.getFactoryId()));
            } else {
                MerchantInfo merchantInfo = merchantRoleService.getMerchantInfoByUser(userInfo);
                facotoryInfoList.addAll(factoryRoleService.getFactoryInfoListByMerchantInfo(merchantInfo));

            }
        }
        if (facotoryInfoList == null || facotoryInfoList.size() == 0) {
            throw new ServiceException(
                    "cant get factory info for " + bo.getProductType() + " by role " + bo.getCreaterRole().getRole());
        }

        return facotoryInfoList;
    }

    public List<TraderInfo> getTraderInfo(ProductBO<?> bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        String roleType = bo.getCreaterRole().getRole();
        List<TraderInfo> traderInfoList = new ArrayList<>();
        if (roleType.equals("trader")) {
            traderInfoList.add(traderRoleService.getTraderInfoByUser(userInfo));
        } else if (roleType.equals("salesman")) {
            traderInfoList.addAll(salesmanRoleService.getTraderInfoByUser(userInfo));
        } else if (roleType.equals("factory")) {
            FactoryInfo factoryInfo = factoryRoleService.getFactoryInfoByUser(userInfo);
            traderInfoList.addAll(traderRoleService.getTraderInfoList(factoryInfo));
        } else {
            throw new ServiceException(
                    "cant get trader info for " + bo.getProductType() + " by role " + bo.getCreaterRole().getRole());
        }
        return traderInfoList;
    }

    public RoleInfoVO getRoleInfoVOByReq(ProductBO<?> bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        String roleType = bo.getCreaterRole().getRole();
        RoleService roleService = (RoleService) com.srct.service.utils.BeanUtil.getBean(roleType + "RoleServiceImpl");
        return getRoleInfoVO(roleService.getRoleIdbyUser(userInfo), roleType);
    }

    public List<SalesmanInfo> getSalesmanInfo(ProductBO<?> bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        String roleType = bo.getCreaterRole().getRole();
        List<SalesmanInfo> salesmanInfoList = new ArrayList<>();
        if (roleType.equals("trader")) {
            salesmanInfoList.addAll(traderRoleService.getSalesmanInfoListByTraderInfo(userInfo));
        } else {
            throw new ServiceException(
                    "cant get trader info for " + bo.getProductType() + " by role " + bo.getCreaterRole().getRole());
        }
        return salesmanInfoList;
    }

    public List<Integer> getSalesmanInfoIdListByUserInfo(UserInfo userInfo) {
        List<SalesmanInfo> salesmanInfoList = salesmanRoleService.getSalesmanInfoListByUser(userInfo);
        List<Integer> salesmanInfoIdList = new ArrayList<>();
        salesmanInfoList.forEach(salesmanInfo -> salesmanInfoIdList.add(salesmanInfo.getId()));
        return salesmanInfoIdList;
    }

    public TraderFactoryMerchantMap getTraderFactoryMerchantMap(ProductBO<?> bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        String roleType = bo.getCreaterRole().getRole();

        if (roleType.equals("trader")) {
            return traderRoleService.getTraderFactoryMerchantMap(userInfo);
        } else {
            throw new ServiceException(
                    "cant get trader-factory-merchantMap info for " + bo.getProductType() + " by role " + bo
                            .getCreaterRole().getRole());
        }
    }

    public FactoryMerchantMap getFactoryMerchantMap(ProductBO<?> bo) {
        UserInfo userInfo = bo.getCreaterInfo();
        String roleType = bo.getCreaterRole().getRole();

        if (roleType.equals("factory")) {
            FactoryInfo factoryInfo = factoryRoleService.getFactoryInfoByUser(userInfo);
            return factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        } else {
            throw new ServiceException(
                    "cant get trader-factory-merchantMap info for " + bo.getProductType() + " by role " + bo
                            .getCreaterRole().getRole());
        }
    }

    public List<Integer> buildTraderFactoryMerchantMapIdList(ProductBO<?> req, List<FactoryInfo> factoryInfoList) {
        TraderFactoryMerchantMapExample mapExample = makeQueryExample(req, TraderFactoryMerchantMapExample.class);
        TraderFactoryMerchantMapExample.Criteria mapCriteria = mapExample.getOredCriteria().get(0);

        List<Integer> factoryIdList = new ArrayList<>();
        factoryInfoList.forEach(factoryInfo -> {
            factoryIdList.add(factoryInfo.getId());
        });
        mapCriteria.andFactoryIdIn(factoryIdList);
        List<TraderFactoryMerchantMap> maps =
                traderFactoryMerchantMapDao.getTraderFactoryMerchantMapByExample(mapExample);
        List<Integer> traderFactoryMerchantMapIdList = new ArrayList<>();
        maps.forEach(map -> {
            traderFactoryMerchantMapIdList.add(map.getId());
        });
        return traderFactoryMerchantMapIdList;
    }

    public List<Integer> buildFactoryMerchantMapIdList(ProductBO<?> req, List<FactoryInfo> factoryInfoList) {
        FactoryMerchantMapExample mapExample = makeQueryExample(req, FactoryMerchantMapExample.class);
        FactoryMerchantMapExample.Criteria mapCriteria = mapExample.getOredCriteria().get(0);

        List<Integer> factoryInfoIdList = new ArrayList<>();
        factoryInfoList.forEach(factoryInfo -> {
            factoryInfoIdList.add(factoryInfo.getId());
        });

        mapCriteria.andFactoryIdIn(factoryInfoIdList);
        List<FactoryMerchantMap> maps = factoryMerchantMapDao.getFactoryMerchantMapByExample(mapExample);
        List<Integer> factoryMerchantMapIdList = new ArrayList<>();
        maps.forEach(map -> {
            factoryMerchantMapIdList.add(map.getId());
        });
        return factoryMerchantMapIdList;
    }

    public List<Integer> buildSalesmanTraderMapTraderIdList(ProductBO<?> req, List<TraderInfo> traderInfoList) {
        List<SalesmanTraderMap> maps = buildSalesmanTraderMapList(req, traderInfoList);
        List<Integer> salesTraderMapIdList = new ArrayList<>();
        maps.forEach(map -> {
            salesTraderMapIdList.add(map.getTraderId());
        });
        return salesTraderMapIdList;
    }

    public List<Integer> buildSalesmanTraderMapSalesmanIdList(ProductBO<?> req, List<TraderInfo> traderInfoList) {
        List<SalesmanTraderMap> maps = buildSalesmanTraderMapList(req, traderInfoList);
        List<Integer> salesTraderMapIdList = new ArrayList<>();
        maps.forEach(map -> {
            salesTraderMapIdList.add(map.getSalesmanId());
        });
        return salesTraderMapIdList;
    }

    private List<SalesmanTraderMap> buildSalesmanTraderMapList(ProductBO<?> req, List<TraderInfo> traderInfoList) {
        SalesmanTraderMapExample mapExample = makeQueryExample(req, SalesmanTraderMapExample.class);
        SalesmanTraderMapExample.Criteria mapCriteria = mapExample.getOredCriteria().get(0);
        List<Integer> traderInfoIdList = new ArrayList<>();
        traderInfoList.forEach(traderInfo -> {
            traderInfoIdList.add(traderInfo.getId());
        });
        mapCriteria.andTraderIdIn(traderInfoIdList);
        List<SalesmanTraderMap> maps = salesmanTraderMapDao.getSalesmanTraderMapByExample(mapExample);
        return maps;
    }

    public void makeDefaultPeriod(Object obj) {
        Date startAt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startAt);
        c.set(DEFAULT_PERIOD_TYPE, c.get(DEFAULT_PERIOD_TYPE) + DEFAULT_PERIOD_VALUE);
        Date endAt = c.getTime();

        Method method = null;
        try {
            method = obj.getClass().getMethod("setStartAt", Date.class);
            method.invoke(obj, startAt);

            method = obj.getClass().getMethod("setEndAt", Date.class);
            method.invoke(obj, endAt);
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public <T> T makeQueryExample(ProductBO<?> bo, Class clazz) {
        Date now = new Date();
        QueryReqVO req = bo.getReq();
        Object example = null;

        try {
            example = clazz.newInstance();
            Method method = example.getClass().getMethod(CRITERIA_CREAT_METHOD);
            Object criteria = method.invoke(example);

            method = criteria.getClass().getMethod(CRITERIA_STARTAT_BEFORE_METHOD, Date.class);
            if (req.getQueryEndAt() == null) {
                // method.invoke(criteria, now);
            } else {
                method.invoke(criteria, req.getQueryEndAt());
            }

            method = criteria.getClass().getMethod(CRITERIA_ENDAT_AFTER_METHOD, Date.class);
            if (req.getQueryStartAt() == null) {
                method.invoke(criteria, now);
            } else {
                method.invoke(criteria, req.getQueryStartAt());
            }

            method = criteria.getClass().getMethod(CRITERIA_VALID_METHOD, Byte.class);
            method.invoke(criteria, DataSourceCommonConstant.DATABASE_COMMON_VALID);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (T) example;
    }

    public DiscountInfoVO getDiscountInfoVObyId(Integer id) {
        if (id == null) {
            return null;
        }
        DiscountInfoVO discountInfoVO = new DiscountInfoVO();
        DiscountInfo discountInfo = discountInfoDao.getDiscountInfobyId(id);
        BeanUtil.copyProperties(discountInfo, discountInfoVO);
        return discountInfoVO;
    }

    public ShopInfoVO getShopInfoVObyId(Integer id) {
        if (id == null) {
            return null;
        }
        ShopInfoVO shopInfoVO = new ShopInfoVO();
        ShopInfo shopInfo = shopInfoDao.getShopInfobyId(id);
        BeanUtil.copyProperties(shopInfo, shopInfoVO);
        return shopInfoVO;
    }

    public GoodsInfoVO getGoodsInfoVObyId(Integer id) {
        if (id == null) {
            return null;
        }
        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
        GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfobyId(id);
        BeanUtil.copyProperties(goodsInfo, goodsInfoVO);
        return goodsInfoVO;
    }

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
        if (id == null) {
            return roleInfoVO;
        }
        Object targetInfo;
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
            case "salesman":
                targetInfo = salesmanInfoDao.getSalesmanInfobyId(id);
                break;
            default:
                throw new ServiceException("");
        }
        BeanUtil.copyProperties(targetInfo, roleInfoVO);
        roleInfoVO.setRoleType(roleType);
        if (roleInfoVO.getUserId() != null) {
            UserInfo user = userService.getUserbyUserId(roleInfoVO.getUserId());
            roleInfoVO.setUserName(user.getName());
            roleInfoVO.setUserComment(user.getComment());
        }
        return roleInfoVO;

    }

    public void buildRespbyReq(QueryRespVO<?> resp, ProductBO<?> req) {
        resp.setCurrentPage(req.getReq().getCurrentPage());
        resp.setPageSize(req.getReq().getPageSize());
        resp.setQueryStartAt(req.getReq().getQueryStartAt());
        resp.setQueryEndAt(req.getReq().getQueryEndAt());
    }

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

    protected abstract void validateDelete(ProductBO<?> req);

    protected abstract void validateQuery(ProductBO<?> req);

}
