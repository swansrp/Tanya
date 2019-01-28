/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: Sharp   
 * @date: 2019/01/28
 */
package com.srct.service.tanya.portal.controller.admin.tanya;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.FactoryMerchantMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.FactoryMerchantMapEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "FactoryMerchantMap")
@RestController("tanyaFactoryMerchantMapController")
@RequestMapping(value = "/portal/admin/tanya/factorymerchantmap")
@CrossOrigin(origins = "*")
public class FactoryMerchantMapController {

    @Autowired
    FactoryMerchantMapDao factoryMerchantMapDao;

    @ApiOperation(value = "更新FactoryMerchantMap", notes = "传入FactoryMerchantMap值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "FactoryMerchantMapEntityVO", name = "vo", value = "FactoryMerchantMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateFactoryMerchantMap(@RequestBody FactoryMerchantMapEntityVO vo) {
        FactoryMerchantMap factoryMerchantMap = new FactoryMerchantMap();
        BeanUtil.copyProperties(vo, factoryMerchantMap);
        Integer id = factoryMerchantMapDao.updateFactoryMerchantMap(factoryMerchantMap);
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询FactoryMerchantMap", notes = "传入FactoryMerchantMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "FactoryMerchantMapEntityVO", name = "vo", value = "FactoryMerchantMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<FactoryMerchantMap>>.Resp> getFactoryMerchantMapSelective(
            @RequestBody FactoryMerchantMapEntityVO vo
            ) {
        List<FactoryMerchantMap> res = new ArrayList<>();
        FactoryMerchantMap factoryMerchantMap = new FactoryMerchantMap();
        BeanUtil.copyProperties(vo, factoryMerchantMap);
        res.addAll(factoryMerchantMapDao.getFactoryMerchantMapSelective(factoryMerchantMap));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询FactoryMerchantMap", notes = "返回id对应的FactoryMerchantMap,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "FactoryMerchantMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<FactoryMerchantMap>>.Resp> getFactoryMerchantMap(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<FactoryMerchantMap> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(factoryMerchantMapDao.getAllFactoryMerchantMapList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(factoryMerchantMapDao.getFactoryMerchantMapbyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除FactoryMerchantMap", notes = "软删除主键为id的FactoryMerchantMap")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "FactoryMerchantMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delFactoryMerchantMap(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        FactoryMerchantMap factoryMerchantMap = new FactoryMerchantMap();
        factoryMerchantMap.setId(id);
        factoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = factoryMerchantMapDao.updateFactoryMerchantMap(factoryMerchantMap);
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
