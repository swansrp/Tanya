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
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsInfoExample;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsFactoryMerchantMapDao;
import com.srct.service.tanya.common.exception.GoodsNumberLimitException;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.DiscountService;
import com.srct.service.tanya.product.service.GoodsService;
import com.srct.service.tanya.product.vo.DiscountInfoVO;
import com.srct.service.tanya.product.vo.GoodsInfoReqVO;
import com.srct.service.tanya.product.vo.GoodsInfoRespVO;
import com.srct.service.tanya.product.vo.GoodsInfoVO;
import com.srct.service.tanya.role.service.FactoryRoleService;
import com.srct.service.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sharp
 *
 */
@Service
public class GoodsServiceImpl extends ProductServiceBaseImpl implements GoodsService {

    @Autowired
    private FactoryRoleService factoryRoleService;

    @Autowired
    private GoodsFactoryMerchantMapDao goodsFactoryMerchantMapDao;

    @Autowired
    private DiscountService discountService;

    @Override
    public QueryRespVO<GoodsInfoRespVO> updateGoodsInfo(ProductBO<GoodsInfoReqVO> req) {
        validateUpdate(req);
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(req);
        FactoryInfo factoryInfo = factoryInfoList.get(0);
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList = getGoodsFactoryMerchantMapList(factoryInfo);
        GoodsInfo goodsInfo = new GoodsInfo();
        if (goodsFactoryMerchantMapList == null
            || goodsFactoryMerchantMapList.size() < factoryMerchantMap.getGoodsNumber()) {
            BeanUtil.copyProperties(req.getReq().getGoods(), goodsInfo);
            goodsInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
            goodsInfoDao.updateGoodsInfo(goodsInfo);
            makeGoodsFactoryMerchantMapRelationShip(goodsInfo, factoryMerchantMap);
        } else {
            throw new GoodsNumberLimitException();
        }

        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buidGoodInfoRespVO(goodsInfo));
        return res;
    }

    private QueryRespVO<GoodsInfoRespVO> getGoodsInfoList(ProductBO<QueryReqVO> req, FactoryInfo factoryInfo) {
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        super.buildRespbyReq(res, req);

        List<GoodsFactoryMerchantMap> goodsFactoryMerchantMapList = getGoodsFactoryMerchantMapList(factoryInfo);
        List<Integer> goodsIdList = new ArrayList<>();
        goodsFactoryMerchantMapList.forEach(map -> goodsIdList.add(map.getGoodsId()));
        GoodsInfoExample example = new GoodsInfoExample();
        GoodsInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(goodsIdList);
        PageInfo<?> pageInfo = super.buildPage(req);
        List<GoodsInfo> goodsInfoList = goodsInfoDao.getGoodsInfoByExample(example, pageInfo);

        res.setPageSize(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());

        goodsInfoList.forEach(goodsInfo -> res.getInfo().add(buidGoodInfoRespVO(goodsInfo)));
        return res;
    }

    private void makeGoodsFactoryMerchantMapRelationShip(GoodsInfo goodsInfo, FactoryMerchantMap factoryMerchantMap) {

        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setGoodsId(goodsInfo.getId());
        goodsFactoryMerchantMap.setFactoryMetchatMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        goodsFactoryMerchantMapDao.updateGoodsFactoryMerchantMap(goodsFactoryMerchantMap);
    }

    private List<GoodsFactoryMerchantMap> getGoodsFactoryMerchantMapList(FactoryInfo factoryInfo) {
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setFactoryMetchatMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap);
    }

    private GoodsFactoryMerchantMap getGoodsFactoryMerchantMapList(FactoryInfo factoryInfo, GoodsInfo goodsInfo) {
        FactoryMerchantMap factoryMerchantMap = factoryRoleService.getFactoryMerchantMapByFactoryInfo(factoryInfo);
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setFactoryMetchatMapId(factoryMerchantMap.getId());
        goodsFactoryMerchantMap.setGoodsId(goodsInfo.getId());
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap).get(0);
    }

    @Override
    public QueryRespVO<GoodsInfoRespVO> getGoodsInfo(ProductBO<QueryReqVO> goods) {
        validateQuery(goods);
        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();

        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(goods);
        FactoryInfo factoryInfo = factoryInfoList.get(0);
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

    @Override
    public QueryRespVO<GoodsInfoRespVO> getGoodsInfoWithDiscount(ProductBO<QueryReqVO> goods) {
        QueryRespVO<GoodsInfoRespVO> res = getGoodsInfo(goods);
        List<GoodsInfoRespVO> infoList = new ArrayList<>();
        res.getInfo().forEach(info -> {
            infoList.add(info);
            List<DiscountInfo> discountList = discountService.getDiscountInfoListByGoodsId(info.getGoodsInfoVO().getId());
            discountList.forEach(discountInfo -> infoList.add(buidGoodInfoRespVO(discountInfo)));
        });
        res.setInfo(infoList);
        return res;
    }

    private GoodsInfoRespVO buidGoodInfoRespVO(GoodsInfo goodsInfo) {
        GoodsInfoRespVO goodsInfoRespVO = new GoodsInfoRespVO();
        GoodsInfoVO vo = new GoodsInfoVO();
        BeanUtil.copyProperties(goodsInfo, vo);
        BeanUtil.copyProperties(goodsInfo, goodsInfoRespVO);
        goodsInfoRespVO.setGoodsInfoVO(vo);
        goodsInfoRespVO.setUnit(1);
        return goodsInfoRespVO;
    }

    private GoodsInfoRespVO buidGoodInfoRespVO(DiscountInfo discountInfo) {
        GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfobyId(discountInfo.getGoodsId());
        GoodsInfoRespVO goodsInfoRespVO = new GoodsInfoRespVO();
        GoodsInfoVO vo = new GoodsInfoVO();
        BeanUtil.copyProperties(goodsInfo, vo);
        vo.setAmount(discountInfo.getAmount());
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
        String roleType = req.getCreaterRole().getRole();
        if (!roleType.equals("factory")) {
            throw new ServiceException("dont allow to update goods by role " + roleType);
        }
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        throw new ServiceException("dont support confirm goods ");
    }

    @Override
    public QueryRespVO<GoodsInfoRespVO> delGoodsInfo(ProductBO<GoodsInfoReqVO> goods) {
        validateDelete(goods);
        GoodsInfo goodsInfo = goodsInfoDao.getGoodsInfobyId(goods.getProductId());
        List<FactoryInfo> factoryInfoList = super.getFactoryInfoList(goods);
        FactoryInfo factoryInfo = factoryInfoList.get(0);
        GoodsFactoryMerchantMap goodsFactoryMerchantMap =
                getGoodsFactoryMerchantMapList(factoryInfo, goodsInfo);
        FactoryMerchantMap factoryMerchantMap =
                factoryMerchantMapDao.getFactoryMerchantMapbyId(goodsFactoryMerchantMap.getId());
        factoryMerchantMap.setGoodsNumber(factoryMerchantMap.getGoodsNumber() - 1);
        goodsInfoDao.delGoodsInfo(goodsInfo);

        QueryRespVO<GoodsInfoRespVO> res = new QueryRespVO<>();
        res.getInfo().add(buidGoodInfoRespVO(goodsInfo));
        return res;
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("salesman") || roleType.equals("merchant")) {
            throw new ServiceException("dont allow to delete goods by role " + roleType);
        }
    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("salesman") || roleType.equals("merchant")) {
            throw new ServiceException("dont allow to query goods by role " + roleType);
        }

    }

}
