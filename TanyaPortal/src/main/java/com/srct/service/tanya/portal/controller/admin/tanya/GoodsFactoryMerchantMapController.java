/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: Sharp   
 * @date: 2019/01/30
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
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.GoodsFactoryMerchantMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.GoodsFactoryMerchantMapEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "GoodsFactoryMerchantMap")
@RestController("tanyaGoodsFactoryMerchantMapController")
@RequestMapping(value = "/portal/admin/tanya/goodsfactorymerchantmap")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class GoodsFactoryMerchantMapController {

    @Autowired
    GoodsFactoryMerchantMapDao goodsFactoryMerchantMapDao;

    @ApiOperation(value = "更新GoodsFactoryMerchantMap", notes = "传入GoodsFactoryMerchantMap值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "GoodsFactoryMerchantMapEntityVO", name = "vo", value = "GoodsFactoryMerchantMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateGoodsFactoryMerchantMap(@RequestBody GoodsFactoryMerchantMapEntityVO vo) {
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        BeanUtil.copyProperties(vo, goodsFactoryMerchantMap);
        Integer id = goodsFactoryMerchantMapDao.updateGoodsFactoryMerchantMap(goodsFactoryMerchantMap).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询GoodsFactoryMerchantMap", notes = "传入GoodsFactoryMerchantMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "GoodsFactoryMerchantMapEntityVO", name = "vo", value = "GoodsFactoryMerchantMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<GoodsFactoryMerchantMap>>.Resp> getGoodsFactoryMerchantMapSelective(
            @RequestBody GoodsFactoryMerchantMapEntityVO vo
            ) {
        List<GoodsFactoryMerchantMap> res = new ArrayList<>();
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        BeanUtil.copyProperties(vo, goodsFactoryMerchantMap);
        res.addAll(goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapSelective(goodsFactoryMerchantMap));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询GoodsFactoryMerchantMap", notes = "返回id对应的GoodsFactoryMerchantMap,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "GoodsFactoryMerchantMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<GoodsFactoryMerchantMap>>.Resp> getGoodsFactoryMerchantMap(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<GoodsFactoryMerchantMap> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(goodsFactoryMerchantMapDao.getAllGoodsFactoryMerchantMapList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(goodsFactoryMerchantMapDao.getGoodsFactoryMerchantMapbyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除GoodsFactoryMerchantMap", notes = "软删除主键为id的GoodsFactoryMerchantMap")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "GoodsFactoryMerchantMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delGoodsFactoryMerchantMap(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        GoodsFactoryMerchantMap goodsFactoryMerchantMap = new GoodsFactoryMerchantMap();
        goodsFactoryMerchantMap.setId(id);
        goodsFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = goodsFactoryMerchantMapDao.updateGoodsFactoryMerchantMap(goodsFactoryMerchantMap).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
