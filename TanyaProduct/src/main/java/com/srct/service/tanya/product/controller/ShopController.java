/**
 * Title: ShopController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.controller
 * @author sharuopeng
 * @date 2019-02-22 09:29:59
 */
package com.srct.service.tanya.product.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.ShopService;
import com.srct.service.tanya.product.vo.ShopInfoReqVO;
import com.srct.service.tanya.product.vo.ShopInfoRespVO;
import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author sharuopeng
 *
 */
@Api(value = "ShopController")
@RestController("ShopController")
@RequestMapping(value = "/shop")
@CrossOrigin(origins = "*")
public class ShopController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "新增/更新药店", notes = "只有merchant和admin等级可以添加药店 若传入id则为更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopInfoRespVO>>.Resp> modifyShop(@RequestBody ShopInfoReqVO req) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***modifyShop***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<ShopInfoReqVO> shop = new ProductBO<ShopInfoReqVO>();
        shop.setProductType("shop");
        shop.setReq(req);
        shop.setCreaterInfo(info);
        shop.setCreaterRole(role);
        QueryRespVO<ShopInfoRespVO> shopInfoVO = shopService.updateShopInfo(shop);

        return TanyaExceptionHandler.generateResponse(shopInfoVO);
    }

    @ApiOperation(value = "获取订单", notes = "获取订单详情,无id则返回渠道订单列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求", required = true),
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "shopid", value = "订单id",
            required = false),
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "factoryid", value = "药厂id",
            required = false)})
    public ResponseEntity<CommonResponse<QueryRespVO<ShopInfoRespVO>>.Resp> getShop(
        @RequestBody QueryReqVO req,
        @RequestParam(value = "shopid", required = false) Integer shopId,
        @RequestParam(value = "factoryid", required = false) Integer factoryId) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***getShop***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> shop = new ProductBO<QueryReqVO>();
        shop.setProductType("shop");
        shop.setCreaterInfo(info);
        shop.setCreaterRole(role);
        shop.setReq(req);
        shop.setFactoryId(factoryId);
        shop.setProductId(shopId);

        QueryRespVO<ShopInfoRespVO> shopInfoVO = shopService.getShopInfo(shop);

        return TanyaExceptionHandler.generateResponse(shopInfoVO);
    }

}
