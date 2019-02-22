/**
 * Title: GoodsServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsFactoryMerchantMapDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsInfoDao;
import com.srct.service.tanya.common.exception.GoodsNumberLimitException;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.GoodsService;
import com.srct.service.tanya.product.vo.GoodsInfoReqVO;
import com.srct.service.tanya.product.vo.GoodsInfoRespVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.role.service.FactoryRoleService;
import com.srct.service.tanya.role.service.TraderRoleService;
import com.srct.service.utils.BeanUtil;

/**
 * @author Sharp
 *
 */
@Service
public class GoodsServiceImpl extends ProductServiceBaseImpl implements GoodsService {

    @Autowired
    private FactoryRoleService factoryRoleService;

    @Autowired
    private TraderRoleService traderRoleService;

    @Autowired
    private GoodsInfoDao goodsInfoDao;

    @Autowired
    private GoodsFactoryMerchantMapDao goodsFactoryMerchantMapDao;

    @Override
    public QueryRespVO<GoodsInfoRespVO> updateGoodsInfo(ProductBO<GoodsInfoReqVO> req) {

        FactoryInfo factoryInfo = super.getFactoryInfo(req);
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList = getGoodsFactoryMerchantMapList(factoryInfo);
        GoodsInfo goodsInfo = new GoodsInfo();
        if (goodsFactoryMerchantMapList == null
            || goodsFactoryMerchantMapList.size() < factoryMerchantMap.getGoodsNumber()) {
            BeanUtil.copyProperties(req, goodsInfo);
            goodsInfoDao.updateGoodsInfo(goodsInfo);
            makeGoodsFactoryMerchantMapRelationShip(goodsInfo, factoryMerchantMap);
        } else {
            throw new GoodsNumberLimitException();
        }

        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<GoodsInfoRespVO>();
        res.setInfo(new ArrayList<>());
        GoodsInfoRespVO goodsInfoRespVO = buidGoodInfoRespVO(goodsInfo);
        res.getInfo().add(goodsInfoRespVO);
        return res;
    }

    /**
     * @param factoryInfo
     * @return
     */
    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoList(ProductBO<QueryReqVO> req, FactoryInfo factoryInfo) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<GoodsInfoRespVO>();
        res.setInfo(new ArrayList<>());
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList = getGoodsFactoryMerchantMapList(factoryInfo);
        List<Integer> goodsIdList = new ArrayList<>();
        goodsFactoryMerchantMapList.forEach(map -> {
            goodsIdList.add(map.getGoodsId());
        });
        GoodsInfoExample example = new GoodsInfoExample();
        GoodsInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(goodsIdList);
        Page page = PageHelper.startPage(req.getReq().getCurrentPage(), req.getReq().getPageSize());
        List<GoodsInfo> goodsInfoList = goodsInfoDao.getGoodsInfoByExample(example);
        PageInfo<GoodsInfo> pageInfo = new PageInfo<GoodsInfo>(goodsInfoList);

        res.setPageSize(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());
        res.setCurrentPage(req.getReq().getCurrentPage());
        res.setPageSize(req.getReq().getPageSize());

        goodsInfoList.forEach(goodsInfo -> {
            GoodsInfoRespVO goodsInfoRespVO = buidGoodInfoRespVO(goodsInfo);
            res.getInfo().add(goodsInfoRespVO);
        });
        // TODO Auto-generated method stub
        return res;
    }

    /**
     * @param goodsInfo
     * @param factoryInfo
     * @param factoryMerchantMap
     */
    private void makeGoodsFactoryMerchantMapRelationShip(GoodsInfo goodsInfo, FactoryMerchantMap factoryMerchantMap) {

        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setGoodsId(goodsInfo.getId());
        goodsFactoryMerchantMap.setFactoryMetchatMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        goodsFactoryMerchantMapDao.updateGoodsFactoryMerchantMap(goodsFactoryMerchantMap);
    }

    /**
     * @param factoryInfo
     * @return
     */
    private List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapList(FactoryInfo factoryInfo) {
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setFactoryMetchatMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap);
    }

    @Override
    public QueryRespVO<GoodsInfoRespVO> getGoodsInfo(ProductBO<QueryReqVO> goods) {

        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<GoodsInfoRespVO>();
        res.setInfo(new ArrayList<>());

        FactoryInfo factoryInfo = super.getFactoryInfo(goods);
        if (goods.getProductId() == null) {
            res = getGoodsInfoList(goods, factoryInfo);
        } else {

            List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList = getGoodsFactoryMerchantMapList(factoryInfo);
            for (GoodsFactoryMerchantMap relationship : goodsFactoryMerchantMapList) {
                if (relationship.getGoodsId().equals(goods.getProductId())) {
                    GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfobyId(relationship.getGoodsId());
                    GoodsInfoRespVO goodsInfoRespVO = buidGoodInfoRespVO(goodsInfo);
                    res.getInfo().add(goodsInfoRespVO);
                }

            }
        }

        return res;
    }

    /**
     * @param goodsInfo
     * @return
     */
    private GoodsInfoRespVO buidGoodInfoRespVO(GoodsInfo goodsInfo) {
        GoodsInfoRespVO goodsInfoRespVO = new GoodsInfoRespVO();
        GoodsInfoVO vo = new GoodsInfoVO();
        BeanUtil.copyProperties(goodsInfo, vo);
        BeanUtil.copyProperties(goodsInfo, goodsInfoRespVO);
        goodsInfoRespVO.setGoodsInfoVO(vo);
        return goodsInfoRespVO;
    }

}
