/**
 * Title: DiscountController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 * 
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.controller
 * @author sharuopeng
 * @date 2019-02-18 19:55:31
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
import com.srct.service.tanya.product.service.DiscountService;
import com.srct.service.tanya.product.vo.DiscountInfoReqVO;
import com.srct.service.tanya.product.vo.DiscountInfoRespVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author sharuopeng
 *
 */
@Api(value = "DiscountController")
@RestController("DiscountController")
@RequestMapping(value = "/discount")
@CrossOrigin(origins = "*")
public class DiscountController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DiscountService discountService;

    @ApiOperation(value = "新增/更新折扣活动", notes = "只有trader等级可以添加折扣活动 若传入id则为更新")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<DiscountInfoRespVO>>.Resp>
        modifyDiscount(@RequestBody DiscountInfoReqVO vo) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***modifyGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<DiscountInfoReqVO> discount = new ProductBO<DiscountInfoReqVO>();
        BeanUtil.copyProperties(vo, discount);
        discount.setCreaterInfo(info);
        discount.setCreaterRole(role);
        QueryRespVO<DiscountInfoRespVO> discountInfoVOList = discountService.updateDiscountInfo(discount);

        return TanyaExceptionHandler.generateResponse(discountInfoVOList);
    }

    @ApiOperation(value = "获取折扣活动", notes = "获取折扣活动详情,无id则返回渠道折扣活动列表")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "discountid", value = "折扣活动id",
            required = false),
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "factoryid", value = "药厂id",
            required = false)})
    public ResponseEntity<CommonResponse<QueryRespVO<DiscountInfoRespVO>>.Resp> getDiscount(
        @RequestBody QueryReqVO req,
        @RequestParam(value = "discountid", required = false) Integer discountId,
        @RequestParam(value = "factoryid", required = false) Integer factoryId) {
        UserInfo info = (UserInfo)request.getAttribute("user");
        RoleInfo role = (RoleInfo)request.getAttribute("role");
        Log.i("***getOrder***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> discount = new ProductBO<QueryReqVO>();
        discount.setCreaterInfo(info);
        discount.setCreaterRole(role);
        discount.setReq(req);
        discount.setFactoryId(factoryId);
        discount.setProductId(discountId);
        QueryRespVO<DiscountInfoRespVO> discountInfoVOList = discountService.getDiscountInfo(discount);

        return TanyaExceptionHandler.generateResponse(discountInfoVOList);
    }
}
