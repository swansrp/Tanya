/**
 * Title: ShopServiceImpl.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.service.impl
 * @author sharuopeng
 * @date 2019-02-22 09:29:07
 */
package com.srct.service.tanya.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfo;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.ShopService;
import com.srct.service.tanya.product.vo.ShopInfoReqVO;
import com.srct.service.tanya.product.vo.ShopInfoRespVO;
import com.srct.service.tanya.product.vo.ShopInfoVO;
import com.srct.service.utils.BeanUtil;

/**
 * @author sharuopeng
 *
 */
@Service
public class ShopServiceImpl extends ProductServiceBaseImpl implements ShopService {

    @Override
    public QueryRespVO<ShopInfoRespVO> getShopInfo(ProductBO<QueryReqVO> shop) {
        ShopInfo shopInfoEx = new ShopInfo();
        shopInfoEx.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        if (shop.getProductId() != null) {
            shopInfoEx.setId(shop.getProductId());
        }
        PageInfo<?> pageInfo = super.buildPage(shop);
        List<ShopInfo> shopInfoList = shopInfoDao.getShopInfoSelective(shopInfoEx, pageInfo);

        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<ShopInfoRespVO>();
        super.buildRespbyReq(res, shop);
        res.setTotalPages(pageInfo.getPages());
        res.setTotalSize(pageInfo.getTotal());

        shopInfoList.forEach(shopInfo -> {
            ShopInfoRespVO shopInfoRespVO = buildShopInfoRespVO(shopInfo);
            res.getInfo().add(shopInfoRespVO);
        });
        return res;
    }

    @Override
    public QueryRespVO<ShopInfoRespVO> updateShopInfo(ProductBO<ShopInfoReqVO> shop) {
        validateUpdate(shop);
        ShopInfo shopInfo = new ShopInfo();
        BeanUtil.copyProperties(shop.getReq().getShop(), shopInfo);
        shopInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        shopInfoDao.updateShopInfo(shopInfo);

        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<ShopInfoRespVO>();
        res.getInfo().add(buildShopInfoRespVO(shopInfo));

        return res;
    }

    /**
     * @param shopInfo
     * @return
     */
    private ShopInfoRespVO buildShopInfoRespVO(ShopInfo shopInfo) {
        ShopInfoVO shopInfoVO = new ShopInfoVO();
        BeanUtil.copyProperties(shopInfo, shopInfoVO);
        ShopInfoRespVO shopInfoResp = new ShopInfoRespVO(shopInfoVO);
        return shopInfoResp;
    }

    @Override
    public QueryRespVO<ShopInfoRespVO> delShopInfo(ProductBO<ShopInfoReqVO> shop) {
        validateDelete(shop);
        ShopInfo shopInfo = shopInfoDao.getShopInfobyId(shop.getProductId());
        shopInfoDao.delShopInfo(shopInfo);
        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<ShopInfoRespVO>();
        res.getInfo().add(buildShopInfoRespVO(shopInfo));
        return res;
    }

    @Override
    protected void validateUpdate(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("salesman")) {
            throw new ServiceException("dont allow to update shop by role " + roleType);
        }
    }

    @Override
    protected void validateConfirm(ProductBO<?> req) {
        throw new ServiceException("dont support confirm shop ");
    }

    @Override
    protected void validateDelete(ProductBO<?> req) {
        String roleType = req.getCreaterRole().getRole();
        if (roleType.equals("salesman")) {
            throw new ServiceException("dont allow to delete shop by role " + roleType);
        }
    }

    @Override
    protected void validateQuery(ProductBO<?> req) {
        // TODO Auto-generated method stub

    }

}
