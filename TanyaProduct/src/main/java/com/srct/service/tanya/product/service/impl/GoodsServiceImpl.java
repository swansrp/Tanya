/**
 * Title: GoodsServiceImpl.java
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author Sharp
 * @date 2019-02-17 22:27:11
 */
package com.srct.service.tanya.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsFactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsInfoDao;
import com.srct.service.tanya.product.bo.GoodsInfoBO;
import com.srct.service.tanya.product.service.GoodsService;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.role.service.impl.FactoryRoleServiceImpl;
import com.srct.service.tanya.role.service.impl.TraderRoleServiceImpl;
import com.srct.service.utils.BeanUtil;

/**
 * @author Sharp
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private FactoryRoleServiceImpl factoryRoleService;

    @Autowired
    private TraderRoleServiceImpl traderRoleService;

    @Autowired
    private GoodsInfoDao goodsInfoDao;

    @Autowired
    private GoodsFactoryMerchantMapDao goodsFactoryMerchantMapDao;

    @Override
    public List<GoodsInfoVO> updateGoodsInfo(GoodsInfoBO goods) {

        UserInfo userInfo = goods.getCreaterInfo();
        String roleType = goods.getCreaterRole().getRole();
        FactoryInfo factoryInfo = null;
        if (roleType.equals("trader")) {
            factoryInfo = traderRoleService.getFactoryInfoByUser(userInfo);
        } else if (roleType.equals("factory")) {
            factoryInfo = factoryRoleService.getFactoryInfoByUser(userInfo);
        } else {
            throw new ServiceException("no permission to update goods info");
        }

        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList = getGoodsFactoryMerchantMapList(factoryInfo);
        GoodsInfo goodsInfo = new GoodsInfo();
        if (goodsFactoryMerchantMapList == null
            || goodsFactoryMerchantMapList.size() < factoryMerchantMap.getGoodsNumber()) {
            BeanUtil.copyProperties(goods, goodsInfo);
            goodsInfoDao.updateGoodsInfo(goodsInfo);
            makeGoodsFactoryMerchantMapRelationShip(goodsInfo, factoryInfo, factoryMerchantMap);
        } else {
            throw new ServiceException();
        }

        return getGoodsInfoList(factoryInfo);
    }

    /**
     * @param factoryInfo
     * @return
     */
    private List<GoodsInfoVO> getGoodsInfoList(FactoryInfo factoryInfo) {
        List<GoodsInfoVO> resList = new ArrayList<>();
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList = getGoodsFactoryMerchantMapList(factoryInfo);
        for (GoodsFactoryMerchantMap relationship : goodsFactoryMerchantMapList) {
            GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfobyId(relationship.getGoodsId());
            GoodsInfoVO res = new GoodsInfoVO();
            BeanUtil.copyProperties(goodsInfo, res);
            resList.add(res);
        }
        // TODO Auto-generated method stub
        return resList;
    }

    /**
     * @param goodsInfo
     * @param factoryInfo
     * @param factoryMerchantMap
     */
    private void makeGoodsFactoryMerchantMapRelationShip(
        GoodsInfo goodsInfo,
        FactoryInfo factoryInfo,
        FactoryMerchantMap factoryMerchantMap) {

        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setGoodsId(goodsInfo.getId());
        goodsFactoryMerchantMap.setFactoryId(factoryInfo.getId());
        goodsFactoryMerchantMap.setFactoryMetchatMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        goodsFactoryMerchantMapDao.updateGoodsFactoryMerchantMap(goodsFactoryMerchantMap);
    }

    /**
     * @param factoryInfo
     * @return
     */
    private List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapList(FactoryInfo factoryInfo) {
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setFactoryId(factoryInfo.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap);
    }

}
