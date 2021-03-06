/**
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 *
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya
 * @author: sharuopeng
 */
package com.srct.service.tanya.portal.controller.admin.tanya;

import com.github.pagehelper.PageInfo;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.tanya.common.config.response.TanyaExceptionHandler;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.ShopFactoryMerchantMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.ShopFactoryMerchantMapEntityVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.DBUtil;
import com.srct.service.vo.QueryRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "ShopFactoryMerchantMap")
@RestController("tanyaShopFactoryMerchantMapController")
@RequestMapping(value = "/portal/admin/tanya/shopfactorymerchantmap")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class ShopFactoryMerchantMapController {

    @Autowired
    ShopFactoryMerchantMapDao shopFactoryMerchantMapDao;

    @ApiOperation(value = "更新ShopFactoryMerchantMap", notes = "传入ShopFactoryMerchantMap值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ShopFactoryMerchantMapEntityVO", name = "vo", value = "ShopFactoryMerchantMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateShopFactoryMerchantMap(
            @RequestBody ShopFactoryMerchantMapEntityVO vo) {
        ShopFactoryMerchantMap shopFactoryMerchantMap = new ShopFactoryMerchantMap();
        BeanUtil.copyProperties(vo, shopFactoryMerchantMap);
        Integer id = shopFactoryMerchantMapDao.updateShopFactoryMerchantMap(shopFactoryMerchantMap).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询ShopFactoryMerchantMap", notes = "传入ShopFactoryMerchantMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ShopFactoryMerchantMapEntityVO", name = "vo", value = "ShopFactoryMerchantMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopFactoryMerchantMap>>.Resp> getShopFactoryMerchantMapSelective(
            @RequestBody ShopFactoryMerchantMapEntityVO vo) {
        QueryRespVO<ShopFactoryMerchantMap> res = new QueryRespVO<>();
        ShopFactoryMerchantMap shopFactoryMerchantMap = new ShopFactoryMerchantMap();
        BeanUtil.copyProperties(vo, shopFactoryMerchantMap);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        PageInfo<ShopFactoryMerchantMap> shopFactoryMerchantMapList =
                shopFactoryMerchantMapDao.getShopFactoryMerchantMapSelective(shopFactoryMerchantMap, pageInfo);
        res.getInfo().addAll(shopFactoryMerchantMapList.getList());
        res.buildPageInfo(shopFactoryMerchantMapList);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询ShopFactoryMerchantMap", notes = "返回id对应的ShopFactoryMerchantMap,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "ShopFactoryMerchantMap的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopFactoryMerchantMap>>.Resp> getShopFactoryMerchantMap(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<ShopFactoryMerchantMap> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            PageInfo<ShopFactoryMerchantMap> shopFactoryMerchantMapList = shopFactoryMerchantMapDao
                    .getAllShopFactoryMerchantMapList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID, pageInfo);
            res.getInfo().addAll(shopFactoryMerchantMapList.getList());
            res.buildPageInfo(shopFactoryMerchantMapList);
        } else {
            res.getInfo().add(shopFactoryMerchantMapDao.getShopFactoryMerchantMapById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除ShopFactoryMerchantMap", notes = "软删除主键为id的ShopFactoryMerchantMap")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "ShopFactoryMerchantMap的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delShopFactoryMerchantMap(
            @RequestParam(value = "id") Integer id) {
        ShopFactoryMerchantMap shopFactoryMerchantMap = new ShopFactoryMerchantMap();
        shopFactoryMerchantMap.setId(id);
        shopFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = shopFactoryMerchantMapDao.updateShopFactoryMerchantMap(shopFactoryMerchantMap).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
