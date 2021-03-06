/**
 * Title: GoodsController.java Description: Copyright: Copyright (c) 2019 Company: Sharp @Project
 * Name: TanyaProduct @Package: com.srct.service.tanya.product.controller
 *
 * @author Sharp
 * @date 2019-02-17 21:14:54
 */
package com.srct.service.tanya.product.controller;

import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.bo.UploadProductBO;
import com.srct.service.tanya.product.service.GoodsService;
import com.srct.service.tanya.product.vo.GoodsInfoReqVO;
import com.srct.service.tanya.product.vo.GoodsInfoRespVO;
import com.srct.service.tanya.product.vo.GoodsSummaryVO;
import com.srct.service.tanya.product.vo.upload.UploadGoodsInfoVO;
import com.srct.service.utils.ExcelUtils;
import com.srct.service.utils.HttpUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.vo.QueryReqVO;
import com.srct.service.vo.QueryRespVO;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.srct.service.config.annotation.Auth.AuthType.USER;

/**
 * @author Sharp
 */
@Auth(role = USER)
@Api(value = "商品", tags = "商品")
@RestController("GoodsController")
@RequestMapping(value = "/goods")
@CrossOrigin(origins = "*")
public class GoodsController {

    private final static String productType = "商品";

    private static final String TEMPLATE_FILE_NAME = "shop_template.xls";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "绑定药品", notes = "只有merchant/factory等级可以绑定药品")
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<GoodsInfoRespVO>>.Resp> bindGoods(
            @RequestBody GoodsInfoReqVO req) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***bindGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<GoodsInfoReqVO> goods = new ProductBO<>();
        goods.setProductType(productType);
        goods.setReq(req);
        goods.setCreatorInfo(info);
        goods.setCreatorRole(role);
        QueryRespVO<GoodsInfoRespVO> goodsInfoVOList = null;
        if (req.getUnbindIdList() != null || req.getBindIdList() != null) {
            goodsInfoVOList = goodsService.bindGoodsInfo(goods);
        }
        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "删除商品", notes = "非salesman等级可以删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "goodsid", value = "商品id", required = true)})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<QueryRespVO<GoodsInfoRespVO>>.Resp> del(
            @RequestParam(value = "goodsid") Integer goodsId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***DelGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<GoodsInfoReqVO> goods = new ProductBO<>();
        goods.setProductType(productType);
        goods.setCreatorInfo(info);
        goods.setCreatorRole(role);
        goods.setProductId(goodsId);
        QueryRespVO<GoodsInfoRespVO> goodsInfoVOList = goodsService.delGoodsInfo(goods);

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "获取药品", notes = "获取药品详情,无id则返回渠道药品列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "商品id"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "title", value = "药店名"),
            @ApiImplicitParam(paramType = "query", dataType = "Boolean", name = "withdiscount", value = "是否需要活动信息"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "targetId", value = "下属id")})
    public ResponseEntity<CommonResponse<QueryRespVO<GoodsInfoRespVO>>.Resp> getGoods(@RequestBody QueryReqVO req,
            @RequestParam(value = "id", required = false) Integer goodsId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "withdiscount", required = false) Boolean withDiscount,
            @RequestParam(value = "targetId", required = false) Integer factoryId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***getGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> goods = new ProductBO<>();
        goods.setProductType(productType);
        goods.setCreatorInfo(info);
        goods.setCreatorRole(role);
        goods.setProductId(goodsId);
        goods.setReq(req);
        goods.setTitle(title);
        goods.setFactoryId(factoryId);
        QueryRespVO<GoodsInfoRespVO> goodsInfoVOList;
        if (withDiscount != null && withDiscount) {
            goodsInfoVOList = goodsService.getGoodsInfoWithDiscount(goods);
        } else {
            goodsInfoVOList = goodsService.getGoodsInfo(goods);
        }

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "获取绑定药品信息", notes = "只有merchant/factory等级可以获取绑定药品信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "int", name = "factoryId", value = "查询药厂Id"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "traderId", value = "查询销售员Id"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "每页条目数量")})
    @RequestMapping(value = "/bind", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<GoodsInfoRespVO>>.Resp> getGoodsBindInfo(
            @RequestParam(value = "factoryId", required = false) Integer factoryId,
            @RequestParam(value = "traderId", required = false) Integer traderId,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***QueryBindGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> goods = new ProductBO<>();
        goods.setProductType(productType);
        QueryReqVO req = new QueryReqVO();
        req.setCurrentPage(currentPage);
        req.setPageSize(pageSize);

        goods.setReq(req);
        goods.setCreatorInfo(info);
        goods.setCreatorRole(role);
        goods.setTraderId(traderId);
        goods.setFactoryId(factoryId);
        QueryRespVO<GoodsInfoRespVO> goodsInfoVOList = goodsService.getGoodsBindInfo(goods);
        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "新增/更新药品", notes = "只有factory、trader等级可以添加药品 若传入id则为更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<GoodsInfoRespVO>>.Resp> modifyGoods(
            @RequestBody GoodsInfoReqVO req) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***modifyGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<GoodsInfoReqVO> goods = new ProductBO<>();
        goods.setProductType(productType);
        goods.setReq(req);
        goods.setCreatorInfo(info);
        goods.setCreatorRole(role);
        QueryRespVO<GoodsInfoRespVO> goodsInfoVOList = goodsService.updateGoodsInfo(goods);

        return TanyaExceptionHandler.generateResponse(goodsInfoVOList);
    }

    @ApiOperation(value = "获取药品", notes = "获取药品详情,无id则返回渠道药品列表")
    @RequestMapping(value = "/summary", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "merchantId", value = "商业渠道id", required = true)})
    public ResponseEntity<CommonResponse<GoodsSummaryVO>.Resp> summaryGoods(@RequestBody QueryReqVO req,
            @RequestParam(value = "merchantId") Integer merchantId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***summaryGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> goods = new ProductBO<>();
        goods.setMerchantId(merchantId);
        goods.setReq(req);
        goods.setCreatorInfo(info);
        goods.setCreatorRole(role);

        GoodsSummaryVO summary = goodsService.summaryGoodsInfo(goods);
        return TanyaExceptionHandler.generateResponse(summary);
    }

    @ApiOperation(value = "获取药品上传模板", notes = "")
    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public void template(HttpServletResponse response) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***templateGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        try {
            // response.reset();
            response.setContentType("application/ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            // 设置文件名在不同主流浏览器上的编码
            HttpUtil.contentDisposition(TEMPLATE_FILE_NAME, request, response);
            ServletOutputStream stream = response.getOutputStream();
            ExcelUtils.generateExcel(UploadGoodsInfoVO.builder().build(), stream);
            stream.flush();
            stream.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "上传药品", notes = "只有merchant可以上传药品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Boolean", name = "override", value = "是否覆盖", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "merchantid", value = "绑定商业渠道id", required = true)})
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<String>.Resp> upload(MultipartFile file,
            @RequestParam(value = "override") Boolean override,
            @RequestParam(value = "merchantid") Integer merchantId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***uploadGoods***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        UploadProductBO bo = new UploadProductBO();
        bo.setMerchantId(merchantId);
        bo.setFile(file);
        bo.setOverride(override);
        goodsService.uploadGoodsInfoVO(bo);
        return TanyaExceptionHandler.generateResponse("success");
    }
}
