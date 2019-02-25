/**
 * Title: OrderController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.controller
 * @author sharuopeng
 * @date 2019-02-18 19:34:31
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
import com.srct.service.tanya.product.service.OrderService;
import com.srct.service.tanya.product.vo.OrderInfoReqVO;
import com.srct.service.tanya.product.vo.OrderInfoRespVO;
import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author sharuopeng
 *
 */
@Api(value = "OrderController")
@RestController("OrderController")
@RequestMapping(value = "/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "新增/更新订单", notes = "只有trader等级可以添加订单 若传入id则为更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<OrderInfoRespVO>>.Resp>
        modifyOrder(@RequestBody OrderInfoReqVO req) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***modifyGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<OrderInfoReqVO> order = new ProductBO<OrderInfoReqVO>();
        order.setProductType("order");
        order.setReq(req);
        order.setCreaterInfo(info);
        order.setCreaterRole(role);
        QueryRespVO<OrderInfoRespVO> orderInfoVO = orderService.updateOrderInfo(order);

        return TanyaExceptionHandler.generateResponse(orderInfoVO);
    }

    @ApiOperation(value = "获取订单", notes = "获取订单详情,无id则返回渠道订单列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求", required = true),
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "orderid", value = "订单id",
            required = false),
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "factoryid", value = "药厂id",
            required = false)})
    public ResponseEntity<CommonResponse<QueryRespVO<OrderInfoRespVO>>.Resp> getOrder(
        @RequestBody QueryReqVO req,
        @RequestParam(value = "orderid", required = false) Integer orderId,
        @RequestParam(value = "factoryid", required = false) Integer factoryId) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***getOrder***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> order = new ProductBO<QueryReqVO>();
        order.setProductType("order");
        order.setCreaterInfo(info);
        order.setCreaterRole(role);
        order.setReq(req);
        order.setFactoryId(factoryId);
        order.setProductId(orderId);
        QueryRespVO<OrderInfoRespVO> orderInfoVO = orderService.getOrderInfo(order);

        return TanyaExceptionHandler.generateResponse(orderInfoVO);
    }

    @ApiOperation(value = "审批订单", notes = "审批订单 同意或拒绝")
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "orderid", value = "订单id",
            required = true),
        @ApiImplicitParam(paramType = "query", dataType = "boolean", name = "confirmed", value = "true/false",
            required = true)})
    public ResponseEntity<CommonResponse<QueryRespVO<OrderInfoRespVO>>.Resp> confirm(
        @RequestParam(value = "orderid", required = true) Integer orderId,
        @RequestParam(value = "confirmed", required = true) boolean confirmed) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***confirmOrder***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> order = new ProductBO<QueryReqVO>();
        order.setProductType("order");
        order.setCreaterInfo(info);
        order.setCreaterRole(role);
        order.setProductId(orderId);
        order.setApproved(confirmed);
        QueryRespVO<OrderInfoRespVO> orderInfoVO = orderService.confirmOrderInfo(order);

        return TanyaExceptionHandler.generateResponse(orderInfoVO);
    }
}
