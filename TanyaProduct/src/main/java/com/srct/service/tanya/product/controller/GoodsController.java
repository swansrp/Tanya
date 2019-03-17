/**
 * Title: GoodsController.java Description: Copyright: Copyright (c) 2019 Company: Sharp @Project
 * Name: TanyaProduct @Package: com.srct.service.tanya.product.controller
 *
 * @author Sharp
 * @date 2019-02-17 21:14:54
 */
package com.srct.service.tanya.product.controller;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.GoodsService;
import com.srct.service.tanya.product.vo.GoodsInfoReqVO;
import com.srct.service.tanya.product.vo.GoodsInfoRespVO;
import com.srct.service.utils.log.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sharp
 */
@Api(value = "商品")
@RestController("GoodsController")
@RequestMapping(value = "/goods")
@CrossOrigin(origins = "*")
public class GoodsController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "新增/更新药品", notes = "只有factory、trader等级可以添加药品 若传入id则为更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<GoodsInfoRespVO>>.Resp> modifyGoods(
            @RequestBody GoodsInfoReqVO req) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***modifyGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<GoodsInfoReqVO> goods = new ProductBO<>();
        goods.setReq(req);
        goods.setCreaterInfo(info);
        goods.setCreaterRole(role);
        QueryRespVO<GoodsInfoRespVO> goodsInfoVOList = goodsService.updateGoodsInfo(goods);

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "获取药品", notes = "获取药品详情,无id则返回渠道药品列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    paramType = "body",
                    dataType = "QueryReqVO",
                    name = "req",
                    value = "基本请求"),
            @ApiImplicitParam(
                    paramType = "query",
                    dataType = "Interger",
                    name = "id",
                    value = "商品id"),
            @ApiImplicitParam(
                    paramType = "query",
                    dataType = "Boolean",
                    name = "withdiscount",
                    value = "是否需要活动信息")
    })
    public ResponseEntity<CommonResponse<QueryRespVO<GoodsInfoRespVO>>.Resp> getGoods(
            @RequestBody QueryReqVO req,
            @RequestParam(value = "id", required = false) Integer goodsId,
            @RequestParam(value = "withdiscount", required = false) Boolean withDiscount) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***getGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> goods = new ProductBO<>();
        goods.setProductType("goods");
        goods.setCreaterInfo(info);
        goods.setCreaterRole(role);
        goods.setProductId(goodsId);
        goods.setReq(req);
        QueryRespVO<GoodsInfoRespVO> goodsInfoVOList;
        if (withDiscount != null && withDiscount) {
            goodsInfoVOList = goodsService.getGoodsInfoWithDiscount(goods);
        } else {
            goodsInfoVOList = goodsService.getGoodsInfo(goods);
        }

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "删除商品", notes = "非salesman等级可以删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    paramType = "query",
                    dataType = "Interger",
                    name = "goodsid",
                    value = "商品id",
                    required = true)
    })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<QueryRespVO<GoodsInfoRespVO>>.Resp> del(
            @RequestParam(value = "goodsid") Integer goodsId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***DelGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<GoodsInfoReqVO> goods = new ProductBO<>();
        goods.setCreaterInfo(info);
        goods.setCreaterRole(role);
        goods.setProductId(goodsId);
        QueryRespVO<GoodsInfoRespVO> goodsInfoVOList = goodsService.delGoodsInfo(goods);

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }
}
