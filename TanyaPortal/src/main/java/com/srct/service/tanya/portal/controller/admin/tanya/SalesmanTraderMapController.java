/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: srct   
 * @date: 2019/01/29
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
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanTraderMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.SalesmanTraderMapEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "SalesmanTraderMap")
@RestController("tanyaSalesmanTraderMapController")
@RequestMapping(value = "/portal/admin/tanya/salesmantradermap")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class SalesmanTraderMapController {

    @Autowired
    SalesmanTraderMapDao salesmanTraderMapDao;

    @ApiOperation(value = "更新SalesmanTraderMap", notes = "传入SalesmanTraderMap值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "SalesmanTraderMapEntityVO", name = "vo", value = "SalesmanTraderMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateSalesmanTraderMap(@RequestBody SalesmanTraderMapEntityVO vo) {
        SalesmanTraderMap salesmanTraderMap = new SalesmanTraderMap();
        BeanUtil.copyProperties(vo, salesmanTraderMap);
        Integer id = salesmanTraderMapDao.updateSalesmanTraderMap(salesmanTraderMap);
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询SalesmanTraderMap", notes = "传入SalesmanTraderMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "SalesmanTraderMapEntityVO", name = "vo", value = "SalesmanTraderMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<SalesmanTraderMap>>.Resp> getSalesmanTraderMapSelective(
            @RequestBody SalesmanTraderMapEntityVO vo
            ) {
        List<SalesmanTraderMap> res = new ArrayList<>();
        SalesmanTraderMap salesmanTraderMap = new SalesmanTraderMap();
        BeanUtil.copyProperties(vo, salesmanTraderMap);
        res.addAll(salesmanTraderMapDao.getSalesmanTraderMapSelective(salesmanTraderMap));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询SalesmanTraderMap", notes = "返回id对应的SalesmanTraderMap,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "SalesmanTraderMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<SalesmanTraderMap>>.Resp> getSalesmanTraderMap(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<SalesmanTraderMap> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(salesmanTraderMapDao.getAllSalesmanTraderMapList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(salesmanTraderMapDao.getSalesmanTraderMapbyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除SalesmanTraderMap", notes = "软删除主键为id的SalesmanTraderMap")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "SalesmanTraderMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delSalesmanTraderMap(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        SalesmanTraderMap salesmanTraderMap = new SalesmanTraderMap();
        salesmanTraderMap.setId(id);
        salesmanTraderMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = salesmanTraderMapDao.updateSalesmanTraderMap(salesmanTraderMap);
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
