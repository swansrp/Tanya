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
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopTraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.ShopTraderFactoryMerchantMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.ShopTraderFactoryMerchantMapEntityVO;
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

@Api(value = "ShopTraderFactoryMerchantMap")
@RestController("tanyaShopTraderFactoryMerchantMapController")
@RequestMapping(value = "/portal/admin/tanya/shoptraderfactorymerchantmap")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class ShopTraderFactoryMerchantMapController {

    @Autowired
    ShopTraderFactoryMerchantMapDao shopTraderFactoryMerchantMapDao;

    @ApiOperation(value = "更新ShopTraderFactoryMerchantMap", notes = "传入ShopTraderFactoryMerchantMap值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ShopTraderFactoryMerchantMapEntityVO", name = "vo", value = "ShopTraderFactoryMerchantMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateShopTraderFactoryMerchantMap(
            @RequestBody ShopTraderFactoryMerchantMapEntityVO vo) {
        ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap = new ShopTraderFactoryMerchantMap();
        BeanUtil.copyProperties(vo, shopTraderFactoryMerchantMap);
        Integer id = shopTraderFactoryMerchantMapDao.updateShopTraderFactoryMerchantMap(shopTraderFactoryMerchantMap)
                .getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询ShopTraderFactoryMerchantMap", notes = "传入ShopTraderFactoryMerchantMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ShopTraderFactoryMerchantMapEntityVO", name = "vo", value = "ShopTraderFactoryMerchantMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopTraderFactoryMerchantMap>>.Resp> getShopTraderFactoryMerchantMapSelective(
            @RequestBody ShopTraderFactoryMerchantMapEntityVO vo) {
        QueryRespVO<ShopTraderFactoryMerchantMap> res = new QueryRespVO<>();
        ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap = new ShopTraderFactoryMerchantMap();
        BeanUtil.copyProperties(vo, shopTraderFactoryMerchantMap);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        PageInfo<ShopTraderFactoryMerchantMap> shopTraderFactoryMerchantMapList = shopTraderFactoryMerchantMapDao
                .getShopTraderFactoryMerchantMapSelective(shopTraderFactoryMerchantMap, pageInfo);
        res.getInfo().addAll(shopTraderFactoryMerchantMapList.getList());
        res.buildPageInfo(shopTraderFactoryMerchantMapList);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询ShopTraderFactoryMerchantMap", notes = "返回id对应的ShopTraderFactoryMerchantMap,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "ShopTraderFactoryMerchantMap的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopTraderFactoryMerchantMap>>.Resp> getShopTraderFactoryMerchantMap(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<ShopTraderFactoryMerchantMap> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            PageInfo<ShopTraderFactoryMerchantMap> shopTraderFactoryMerchantMapList = shopTraderFactoryMerchantMapDao
                    .getAllShopTraderFactoryMerchantMapList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID,
                            pageInfo);
            res.getInfo().addAll(shopTraderFactoryMerchantMapList.getList());
            res.buildPageInfo(shopTraderFactoryMerchantMapList);
        } else {
            res.getInfo().add(shopTraderFactoryMerchantMapDao.getShopTraderFactoryMerchantMapById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除ShopTraderFactoryMerchantMap", notes = "软删除主键为id的ShopTraderFactoryMerchantMap")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "ShopTraderFactoryMerchantMap的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delShopTraderFactoryMerchantMap(
            @RequestParam(value = "id") Integer id) {
        ShopTraderFactoryMerchantMap shopTraderFactoryMerchantMap = new ShopTraderFactoryMerchantMap();
        shopTraderFactoryMerchantMap.setId(id);
        shopTraderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = shopTraderFactoryMerchantMapDao.updateShopTraderFactoryMerchantMap(shopTraderFactoryMerchantMap)
                .getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
