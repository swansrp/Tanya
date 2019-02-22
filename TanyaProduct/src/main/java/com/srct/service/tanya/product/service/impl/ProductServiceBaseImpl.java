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
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMapExample;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.DiscountInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.MerchantInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.OrderInfoDao;
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
import com.srct.service.tanya.role.service.TraderRoleService;
import com.srct.service.tanya.role.vo.RoleInfoVO;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author sharuopeng
 *
 */
public class ProductServiceBaseImpl {

    @Autowired
    protected FactoryRoleService factoryRoleService;

    @Autowired
    protected TraderRoleService traderRoleService;

    @Autowired
    protected TraderFactoryMerchantMapDao traderFactoryMerchantMapDao;

    @Autowired
    protected FactoryMerchantMapDao factoryMerchantMapDao;

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

    private final static String CRITERIA_STARTAT_METHOD = "andStartAtGreaterThanOrEqualTo";

    private final static String CRITERIA_ENDAT_METHOD = "andEndAtLessThanOrEqualTo";

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

    /**
     * @param order
     * @param factoryInfo
     * @return
     */
    public List<Integer> buildTraderFactoryMerchantMapIdList(ProductBO<?> order, FactoryInfo factoryInfo) {
        TraderFactoryMerchantMapExample mapExample =
            (TraderFactoryMerchantMapExample)makeQueryExample(order, TraderFactoryMerchantMapExample.class);
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
     * @param order
     * @return
     */
    public <T> T makeQueryExample(ProductBO<?> bo, Class clazz) {
        Object example = clazz.getInterfaces();
        QueryReqVO req = bo.getReq();
        try {
            Method method = example.getClass().getMethod(CRITERIA_CREAT_METHOD);
            Object criteria = method.invoke(example);
            method = criteria.getClass().getMethod(CRITERIA_VALID_METHOD, Byte.class);
            method.invoke(criteria, DataSourceCommonConstant.DATABASE_COMMON_VALID);
            method = criteria.getClass().getMethod(CRITERIA_STARTAT_METHOD, Date.class);
            if (req.getQueryStartAt() != null)
                method.invoke(criteria, req.getQueryStartAt());
            method = criteria.getClass().getMethod(CRITERIA_ENDAT_METHOD, Date.class);
            if (req.getQueryEndAt() != null)
                method.invoke(criteria, req.getQueryEndAt());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
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
        pageInfo.setPageNum(req.getReq().getCurrentPage());
        pageInfo.setPageSize(req.getReq().getPageSize());
        return pageInfo;
    }

}
