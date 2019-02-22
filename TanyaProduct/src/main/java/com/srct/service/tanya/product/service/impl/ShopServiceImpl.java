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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
        Page page = PageHelper.startPage(shop.getReq().getCurrentPage(), shop.getReq().getPageSize());
        List<ShopInfo> shopInfoList = shopInfoDao.getAllShopInfoList(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        PageInfo<ShopInfo> pageInfo = new PageInfo<ShopInfo>(shopInfoList);

        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<ShopInfoRespVO>();
        shopInfoList.forEach(shopInfo -> {
            ShopInfoRespVO shopInfoRespVO = buildShopInfoRespVO(shopInfo);
            res.getInfo().add(shopInfoRespVO);
        });
        return res;
    }

    @Override
    public QueryRespVO<ShopInfoRespVO> updateShopInfo(ProductBO<ShopInfoReqVO> shop) {
        checkRoleForShopUpdate(shop);
        ShopInfo shopInfo = new ShopInfo();
        BeanUtil.copyProperties(shop.getReq().getShop(), shopInfo);
        shopInfoDao.updateShopInfo(shopInfo);

        ShopInfoRespVO shopInfoRespVO = buildShopInfoRespVO(shopInfo);

        QueryRespVO<ShopInfoRespVO> res = new QueryRespVO<ShopInfoRespVO>();
        res.getInfo().add(shopInfoRespVO);

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

    /**
     * @param shop
     */
    private void checkRoleForShopUpdate(ProductBO<ShopInfoReqVO> bo) {
        String roleType = bo.getCreaterRole().getRole();
        if (roleType.equals("factory") || roleType.equals("trader") || roleType.equals("salesman")) {
            throw new ServiceException("dont allow to update shop by role " + roleType);
        }
    }

}
