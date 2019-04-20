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
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsTraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsTraderFactoryMerchantMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.GoodsTraderFactoryMerchantMapEntityVO;
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

@Api(value = "GoodsTraderFactoryMerchantMap")
@RestController("tanyaGoodsTraderFactoryMerchantMapController")
@RequestMapping(value = "/portal/admin/tanya/goodstraderfactorymerchantmap")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class GoodsTraderFactoryMerchantMapController {

    @Autowired
    GoodsTraderFactoryMerchantMapDao goodsTraderFactoryMerchantMapDao;

    @ApiOperation(value = "更新GoodsTraderFactoryMerchantMap", notes = "传入GoodsTraderFactoryMerchantMap值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "GoodsTraderFactoryMerchantMapEntityVO", name = "vo", value = "GoodsTraderFactoryMerchantMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateGoodsTraderFactoryMerchantMap(
            @RequestBody GoodsTraderFactoryMerchantMapEntityVO vo) {
        GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap = new GoodsTraderFactoryMerchantMap();
        BeanUtil.copyProperties(vo, goodsTraderFactoryMerchantMap);
        Integer id = goodsTraderFactoryMerchantMapDao.updateGoodsTraderFactoryMerchantMap(goodsTraderFactoryMerchantMap)
                .getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询GoodsTraderFactoryMerchantMap", notes = "传入GoodsTraderFactoryMerchantMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "GoodsTraderFactoryMerchantMapEntityVO", name = "vo", value = "GoodsTraderFactoryMerchantMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<GoodsTraderFactoryMerchantMap>>.Resp> getGoodsTraderFactoryMerchantMapSelective(
            @RequestBody GoodsTraderFactoryMerchantMapEntityVO vo) {
        QueryRespVO<GoodsTraderFactoryMerchantMap> res = new QueryRespVO<>();
        GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap = new GoodsTraderFactoryMerchantMap();
        BeanUtil.copyProperties(vo, goodsTraderFactoryMerchantMap);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        PageInfo<GoodsTraderFactoryMerchantMap> goodsTraderFactoryMerchantMapList = goodsTraderFactoryMerchantMapDao
                .getGoodsTraderFactoryMerchantMapSelective(goodsTraderFactoryMerchantMap, pageInfo);
        res.getInfo().addAll(goodsTraderFactoryMerchantMapList.getList());
        res.buildPageInfo(goodsTraderFactoryMerchantMapList);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询GoodsTraderFactoryMerchantMap", notes = "返回id对应的GoodsTraderFactoryMerchantMap,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "GoodsTraderFactoryMerchantMap的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<GoodsTraderFactoryMerchantMap>>.Resp> getGoodsTraderFactoryMerchantMap(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<GoodsTraderFactoryMerchantMap> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            PageInfo<GoodsTraderFactoryMerchantMap> goodsTraderFactoryMerchantMapList = goodsTraderFactoryMerchantMapDao
                    .getAllGoodsTraderFactoryMerchantMapList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID,
                            pageInfo);
            res.getInfo().addAll(goodsTraderFactoryMerchantMapList.getList());
            res.buildPageInfo(goodsTraderFactoryMerchantMapList);
        } else {
            res.getInfo().add(goodsTraderFactoryMerchantMapDao.getGoodsTraderFactoryMerchantMapById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除GoodsTraderFactoryMerchantMap", notes = "软删除主键为id的GoodsTraderFactoryMerchantMap")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "GoodsTraderFactoryMerchantMap的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delGoodsTraderFactoryMerchantMap(
            @RequestParam(value = "id") Integer id) {
        GoodsTraderFactoryMerchantMap goodsTraderFactoryMerchantMap = new GoodsTraderFactoryMerchantMap();
        goodsTraderFactoryMerchantMap.setId(id);
        goodsTraderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId =
                goodsTraderFactoryMerchantMapDao.updateGoodsTraderFactoryMerchantMap(goodsTraderFactoryMerchantMap)
                        .getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
