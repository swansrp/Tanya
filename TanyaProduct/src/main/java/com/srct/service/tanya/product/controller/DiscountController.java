/**
 * Title: DiscountController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.controller
 * @author sharuopeng
 * @date 2019-02-18 19:55:31
 */
package com.srct.service.tanya.product.controller;

import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.common.vo.QueryReqVO;
import com.srct.service.tanya.common.vo.QueryRespVO;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.DiscountService;
import com.srct.service.tanya.product.vo.DiscountInfoReqVO;
import com.srct.service.tanya.product.vo.DiscountInfoRespVO;
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
 * @author sharuopeng
 */
@Api(value = "折扣活动(药厂-渠道)")
@RestController("DiscountController")
@RequestMapping(value = "/discount")
@CrossOrigin(origins = "*")
public class DiscountController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DiscountService discountService;

    @ApiOperation(value = "新增/更新折扣活动", notes = "只有factory等级可以添加折扣活动 若传入id则为更新")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<DiscountInfoRespVO>>.Resp> modifyDiscount(
            @RequestBody DiscountInfoReqVO req) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***modifyDiscount***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<DiscountInfoReqVO> discount = new ProductBO<DiscountInfoReqVO>();
        discount.setReq(req);
        discount.setCreaterInfo(info);
        discount.setCreaterRole(role);
        QueryRespVO<DiscountInfoRespVO> discountInfoVOList = discountService.updateDiscountInfo(discount);

        return TanyaExceptionHandler.generateResponse(discountInfoVOList);
    }

    @ApiOperation(value = "获取折扣活动", notes = "获取折扣活动详情,无id则返回渠道折扣活动列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "discountid", value = "折扣活动id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "factoryid", value = "药厂id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "goodsid", value = "商品id"),
            @ApiImplicitParam(paramType = "query", dataType = "Byte", name = "confirmed", value = "0拒绝 1同意 -1全部 null未操作")})
    public ResponseEntity<CommonResponse<QueryRespVO<DiscountInfoRespVO>>.Resp> getDiscount(@RequestBody QueryReqVO req,
            @RequestParam(value = "discountid", required = false) Integer discountId,
            @RequestParam(value = "factoryid", required = false) Integer factoryId,
            @RequestParam(value = "factoryid", required = false) Integer goodsId,
            @RequestParam(value = "confirmed", required = false) Byte confirmed) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***getDiscount***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> discount = new ProductBO<QueryReqVO>();
        discount.setCreaterInfo(info);
        discount.setCreaterRole(role);
        discount.setReq(req);
        discount.setFactoryId(factoryId);
        discount.setProductId(discountId);
        discount.setGoodsId(goodsId);
        discount.setApproved(confirmed);
        QueryRespVO<DiscountInfoRespVO> discountInfoVOList = discountService.getDiscountInfo(discount);

        return TanyaExceptionHandler.generateResponse(discountInfoVOList);
    }

    @ApiOperation(value = "审批折扣活动", notes = "审批折扣活动 同意或拒绝")
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "discountid", value = "订单id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Byte", name = "confirmed", value = "0拒绝 1同意", required = true)})
    public ResponseEntity<CommonResponse<QueryRespVO<DiscountInfoRespVO>>.Resp> confirm(
            @RequestParam(value = "discountid", required = true) Integer discountId,
            @RequestParam(value = "confirmed", required = true) Byte confirmed) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***confirmDiscount***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> discount = new ProductBO<QueryReqVO>();
        discount.setProductType("discount");
        discount.setCreaterInfo(info);
        discount.setCreaterRole(role);
        discount.setProductId(discountId);
        discount.setApproved(confirmed);
        QueryRespVO<DiscountInfoRespVO> discountInfoVO = discountService.confirmDiscountInfo(discount);

        return TanyaExceptionHandler.generateResponse(discountInfoVO);
    }

    @ApiOperation(value = "删除折扣活动", notes = "只有factory等级可以删除没有确认的折扣活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "discountid", value = "折扣活动id", required = true)})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<QueryRespVO<DiscountInfoRespVO>>.Resp> del(
            @RequestParam(value = "discountid", required = true) Integer discountId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***DelDiscount***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<DiscountInfoReqVO> discount = new ProductBO<DiscountInfoReqVO>();
        discount.setCreaterInfo(info);
        discount.setCreaterRole(role);
        discount.setProductId(discountId);
        QueryRespVO<DiscountInfoRespVO> goodsInfoVOList = discountService.delDiscountInfo(discount);

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }
}
