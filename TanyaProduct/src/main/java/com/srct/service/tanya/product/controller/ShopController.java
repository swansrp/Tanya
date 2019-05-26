/**
 * Title: ShopController.java Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @Project Name: TanyaProduct
 * @Package: com.srct.service.tanya.product.controller
 * @author sharuopeng
 * @date 2019-02-22 09:29:59
 */
package com.srct.service.tanya.product.controller;

import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserInfo;
import com.srct.service.tanya.product.bo.ProductBO;
import com.srct.service.tanya.product.bo.UploadProductBO;
import com.srct.service.tanya.product.service.ShopService;
import com.srct.service.tanya.product.vo.ShopInfoReqVO;
import com.srct.service.tanya.product.vo.ShopInfoRespVO;
import com.srct.service.tanya.product.vo.upload.UploadShopInfoVO;
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

import static com.srct.service.config.annotation.Auth.AuthType.USER;

/**
 * @author sharuopeng
 */
@Auth(role = USER)
@Api(value = "药店", tags = "药店")
@RestController("ShopController")
@RequestMapping(value = "/shop")
@CrossOrigin(origins = "*")
public class ShopController {

    private final static String productType = "药店";

    private final static String TEMPLATE_FILE_NAME = "shop_template.xls";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "新增/更新商店", notes = "只有factory、trader等级可以添加商店 若传入id则为更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopInfoRespVO>>.Resp> modifyShop(@RequestBody ShopInfoReqVO req) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***modifyShop***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<ShopInfoReqVO> shop = new ProductBO<>();
        shop.setProductType(productType);
        shop.setReq(req);
        shop.setCreatorInfo(info);
        shop.setCreatorRole(role);
        QueryRespVO<ShopInfoRespVO> shopInfoVOList = shopService.updateShopInfo(shop);

        return TanyaExceptionHandler.generateResponse(shopInfoVOList);
    }

    @ApiOperation(value = "获取商店", notes = "获取商店详情,无id则返回渠道商店列表")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", dataType = "QueryReqVO", name = "req", value = "基本请求"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "药店id"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "title", value = "药店名")})
    public ResponseEntity<CommonResponse<QueryRespVO<ShopInfoRespVO>>.Resp> getShop(@RequestBody QueryReqVO req,
            @RequestParam(value = "id", required = false) Integer shopId,
            @RequestParam(value = "title", required = false) String title) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***getShop***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> shop = new ProductBO<>();
        shop.setProductType(productType);
        shop.setCreatorInfo(info);
        shop.setCreatorRole(role);
        shop.setProductId(shopId);
        shop.setReq(req);
        shop.setTitle(title);
        QueryRespVO<ShopInfoRespVO> shopInfoVOList;
        shopInfoVOList = shopService.getShopInfo(shop);

        return TanyaExceptionHandler.generateResponse(shopInfoVOList);
    }

    @ApiOperation(value = "绑定商店", notes = "只有merchant/factory等级可以绑定商店")
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopInfoRespVO>>.Resp> bindShop(@RequestBody ShopInfoReqVO req) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***bindShop***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<ShopInfoReqVO> shop = new ProductBO<>();
        shop.setProductType(productType);
        shop.setReq(req);
        shop.setCreatorInfo(info);
        shop.setCreatorRole(role);
        QueryRespVO<ShopInfoRespVO> shopInfoVOList = null;
        if (req.getUnbindIdList() != null || req.getBindIdList() != null) {
            shopInfoVOList = shopService.bindShopInfo(shop);
        }
        return TanyaExceptionHandler.generateResponse(shopInfoVOList);
    }

    @ApiOperation(value = "获取绑定商店信息", notes = "只有merchant/factory等级可以获取绑定商店信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "factoryid", value = "查询药厂Id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "traderid", value = "查询销售员Id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @RequestMapping(value = "/bind", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopInfoRespVO>>.Resp> getShopBindInfo(
            @RequestParam(value = "factoryid", required = false) Integer factoryId,
            @RequestParam(value = "traderid", required = false) Integer traderId,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***QueryBindShop***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<QueryReqVO> shop = new ProductBO<>();
        shop.setProductType(productType);
        QueryReqVO req = new QueryReqVO();
        req.setCurrentPage(currentPage);
        req.setPageSize(pageSize);

        shop.setReq(req);
        shop.setCreatorInfo(info);
        shop.setCreatorRole(role);
        shop.setTraderId(traderId);
        shop.setFactoryId(factoryId);

        QueryRespVO<ShopInfoRespVO> shopInfoVOList = shopService.getShopBindInfo(shop);
        return TanyaExceptionHandler.generateResponse(shopInfoVOList);
    }

    @ApiOperation(value = "删除药店", notes = "非salesman等级可以删除药店")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "shopid", value = "药店id", required = true)})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopInfoRespVO>>.Resp> del(
            @RequestParam(value = "shopid") Integer shopId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***DelShop***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        ProductBO<ShopInfoReqVO> shop = new ProductBO<>();
        shop.setProductType(productType);
        shop.setCreatorInfo(info);
        shop.setCreatorRole(role);
        shop.setProductId(shopId);
        QueryRespVO<ShopInfoRespVO> shopInfoVOList = shopService.delShopInfo(shop);

        return TanyaExceptionHandler.generateResponse(shopInfoVOList);
    }


    @ApiOperation(value = "上传药店", notes = "只有merchant可以上传药店信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Boolean", name = "override", value = "是否覆盖", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "merchantid", value = "绑定商业渠道id", required = true)})
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(MultipartFile file, @RequestParam(value = "override") Boolean override,
            @RequestParam(value = "merchantid") Integer merchantId) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***uploadShop***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        UploadProductBO bo = new UploadProductBO();
        bo.setMerchantId(merchantId);
        bo.setFile(file);
        bo.setOverride(override);
        shopService.uploadShopInfoVO(bo);
    }

    @ApiOperation(value = "获取药店上传模板", notes = "")
    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public void template(HttpServletResponse response) {
        UserInfo info = (UserInfo) request.getAttribute("user");
        RoleInfo role = (RoleInfo) request.getAttribute("role");
        Log.i("***templateShop***");
        Log.i("guid {} role {}", info.getGuid(), role.getRole());

        try {
            response.setContentType("application/octet-stream;charset=utf-8");
            // 设置文件名在不同主流浏览器上的编码
            HttpUtil.contentDisposition(TEMPLATE_FILE_NAME, request, response);
            ServletOutputStream stream = response.getOutputStream();
            ExcelUtils.generateExcel(UploadShopInfoVO.builder().build(), stream);
            stream.flush();
            stream.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
