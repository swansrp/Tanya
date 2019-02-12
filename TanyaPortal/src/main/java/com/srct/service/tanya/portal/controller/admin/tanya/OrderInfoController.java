/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: Sharp   
 * @date: 2019/02/12
 */
package com.srct.service.tanya.portal.controller.admin.tanya;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.OrderInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.OrderInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.OrderInfoEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "OrderInfo")
@RestController("tanyaOrderInfoController")
@RequestMapping(value = "/portal/admin/tanya/orderinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class OrderInfoController {

    @Autowired
    OrderInfoDao orderInfoDao;

    @ApiOperation(value = "更新OrderInfo", notes = "传入OrderInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "OrderInfoEntityVO", name = "vo", value = "OrderInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateOrderInfo(@RequestBody OrderInfoEntityVO vo) {
        OrderInfo orderInfo = new OrderInfo();
        BeanUtil.copyProperties(vo, orderInfo);
        Integer id = orderInfoDao.updateOrderInfo(orderInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询OrderInfo", notes = "传入OrderInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "OrderInfoEntityVO", name = "vo", value = "OrderInfo", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<OrderInfo>>.Resp> getOrderInfoSelective(
            @RequestBody OrderInfoEntityVO vo
            ) {
        List<OrderInfo> res = new ArrayList<>();
        OrderInfo orderInfo = new OrderInfo();
        BeanUtil.copyProperties(vo, orderInfo);
        res.addAll(orderInfoDao.getOrderInfoSelective(orderInfo));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询OrderInfo", notes = "返回id对应的OrderInfo,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "OrderInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<OrderInfo>>.Resp> getOrderInfo(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<OrderInfo> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(orderInfoDao.getAllOrderInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(orderInfoDao.getOrderInfobyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除OrderInfo", notes = "软删除主键为id的OrderInfo")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "OrderInfo的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delOrderInfo(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(id);
        orderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = orderInfoDao.updateOrderInfo(orderInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
