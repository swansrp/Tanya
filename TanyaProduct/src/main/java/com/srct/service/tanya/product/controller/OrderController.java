/**
 * Title: OrderController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.controller
 * @author sharuopeng
 * @date 2019-02-18 19:34:31
 */
package com.srct.service.tanya.product.controller;

import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.exception.ServiceException;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.service.OrderService;
import com.srct.service.tanya.product.vo.OrderInfoReqVO;
import com.srct.service.tanya.product.vo.OrderInfoRespVO;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.srct.service.config.annotation.Auth.AuthType.USER;

/**
 * @author sharuopeng
 */
@Auth(role = USER)
@Api(value = "订单(药厂-渠道)", tags = "订单(药厂-渠道)")
@RestController("OrderController")
@RequestMapping(value = "/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private final static String productType = "订单";
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "审批订单", notes = "审批订单 同意或拒绝")
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "orderid", value = "订单id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Byte", name = "confirmed", value = "0拒绝 1同意", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "ordernumber", value = "批准订单数量")})
    public ResponseEntity<CommonResponse<QueryRespVO<OrderInfoRespVO>>.Resp> confirm(
            @RequestParam(value = "orderid") Integer orderId, @RequestParam(value = "confirmed") Byte confirmed,
            @RequestParam(value = "ordernumber", required = false) Integer orderNumber) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***confirmOrder***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> order = new ProductBO<>();
        order.setProductType(productType);
        order.setCreatorInfo(info);
        order.setCreatorRole(role);
        order.setProductId(orderId);
        order.setApproved(confirmed);
        order.setNumber(orderNumber);
        QueryRespVO<OrderInfoRespVO> orderInfoVO = orderService.confirmOrderInfo(order);

        return TanyaExceptionHandler.generateResponse(orderInfoVO);
    }

    @ApiOperation(value = "删除订单", notes = "非salesman等级可以删除未审核的订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "orderid", value = "订单id", required = true)})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<QueryRespVO<OrderInfoRespVO>>.Resp> del(
            @RequestParam(value = "orderid") Integer orderId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***DelOrder***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<OrderInfoReqVO> order = new ProductBO<>();
        order.setProductType(productType);
        order.setCreatorInfo(info);
        order.setCreatorRole(role);
        order.setProductId(orderId);
        QueryRespVO<OrderInfoRespVO> orderInfoVOList = orderService.delOrderInfo(order);

        return TanyaExceptionHandler.generateResponse(orderInfoVOList);
    }

    @ApiOperation(value = "获取订单", notes = "获取订单详情,无id则返回渠道订单列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "orderid", value = "订单id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "factoryid", value = "药厂id"),
            @ApiImplicitParam(paramType = "query", dataType = "Byte", name = "confirmed", value = "0拒绝 1同意 null全部 -1未操作"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "title", value = "订单标题")})
    public ResponseEntity<CommonResponse<QueryRespVO<OrderInfoRespVO>>.Resp> getOrder(@RequestBody QueryReqVO req,
            @RequestParam(value = "orderid", required = false) Integer orderId,
            @RequestParam(value = "factoryid", required = false) Integer factoryId,
            @RequestParam(value = "confirmed", required = false) Byte confirmed,
            @RequestParam(value = "title", required = false) String title) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***getOrder***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> order = new ProductBO<>();
        order.setProductType(productType);
        order.setCreatorInfo(info);
        order.setCreatorRole(role);
        order.setReq(req);
        order.setFactoryId(factoryId);
        order.setProductId(orderId);
        order.setApproved(confirmed);
        order.setTitle(title);
        QueryRespVO<OrderInfoRespVO> orderInfoVO = orderService.getOrderInfo(order);

        return TanyaExceptionHandler.generateResponse(orderInfoVO);
    }

    @ApiOperation(value = "新增/更新订单", notes = "只有trader等级可以添加订单 若传入id则为更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<OrderInfoRespVO>>.Resp> modifyOrder(
            @RequestBody OrderInfoReqVO req) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***modifyGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<OrderInfoReqVO> order = new ProductBO<>();
        order.setProductType(productType);
        order.setReq(req);
        order.setCreatorInfo(info);
        order.setCreatorRole(role);
        QueryRespVO<OrderInfoRespVO> orderInfoVO = orderService.updateOrderInfo(order);

        return TanyaExceptionHandler.generateResponse(orderInfoVO);
    }

    @ApiOperation(value = "汇总订单", notes = "汇总指定月分份订单总额")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "查询参数", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "merchantId", value = "商业渠道id"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "summaryType", value = "[amount/count]"),})
    @RequestMapping(value = "/summary", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Double>.Resp> summary(@RequestBody QueryReqVO req,
            @RequestParam(value = "merchantId", required = false) Integer merchantId,
            @RequestParam(value = "summaryType", required = false) String summaryType) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***SummaryOrder***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> order = new ProductBO<>();
        order.setReq(req);
        order.setMerchantId(merchantId);
        order.setProductType(productType);
        order.setCreatorInfo(info);
        order.setCreatorRole(role);
        List<OrderInfo> orderInfoList;
        try {
            orderInfoList = orderService.summaryOrderInfo(order);
        } catch (Exception e) {
            Log.e(e);
            return TanyaExceptionHandler.generateResponse(Double.valueOf(0));
        }
        if (StringUtils.isEmpty(summaryType) || "amount".equals(summaryType)) {
            Double total = 0.0;
            for (OrderInfo orderInfo : orderInfoList) {
                total += orderInfo.getAmount();
            }
            return TanyaExceptionHandler.generateResponse(total);
        } else if ("count".equals(summaryType)) {
            return TanyaExceptionHandler.generateResponse(Double.valueOf(orderInfoList.size()));
        } else {
            throw new ServiceException("错误的统计类型");
        }

    }

}
