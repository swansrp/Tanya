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
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanTraderMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.SalesmanTraderMapEntityVO;
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
            @ApiImplicitParam(paramType = "body", dataType = "SalesmanTraderMapEntityVO", name = "vo", value = "SalesmanTraderMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateSalesmanTraderMap(
            @RequestBody SalesmanTraderMapEntityVO vo) {
        SalesmanTraderMap salesmanTraderMap = new SalesmanTraderMap();
        BeanUtil.copyProperties(vo, salesmanTraderMap);
        Integer id = salesmanTraderMapDao.updateSalesmanTraderMap(salesmanTraderMap).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询SalesmanTraderMap", notes = "传入SalesmanTraderMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SalesmanTraderMapEntityVO", name = "vo", value = "SalesmanTraderMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<SalesmanTraderMap>>.Resp> getSalesmanTraderMapSelective(
            @RequestBody SalesmanTraderMapEntityVO vo) {
        QueryRespVO<SalesmanTraderMap> res = new QueryRespVO<>();
        SalesmanTraderMap salesmanTraderMap = new SalesmanTraderMap();
        BeanUtil.copyProperties(vo, salesmanTraderMap);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        PageInfo<SalesmanTraderMap> salesmanTraderMapList =
                salesmanTraderMapDao.getSalesmanTraderMapSelective(salesmanTraderMap, pageInfo);
        res.getInfo().addAll(salesmanTraderMapList.getList());
        res.buildPageInfo(salesmanTraderMapList);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询SalesmanTraderMap", notes = "返回id对应的SalesmanTraderMap,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "SalesmanTraderMap的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<SalesmanTraderMap>>.Resp> getSalesmanTraderMap(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<SalesmanTraderMap> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            PageInfo<SalesmanTraderMap> salesmanTraderMapList = salesmanTraderMapDao
                    .getAllSalesmanTraderMapList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID, pageInfo);
            res.getInfo().addAll(salesmanTraderMapList.getList());
            res.buildPageInfo(salesmanTraderMapList);
        } else {
            res.getInfo().add(salesmanTraderMapDao.getSalesmanTraderMapById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除SalesmanTraderMap", notes = "软删除主键为id的SalesmanTraderMap")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "SalesmanTraderMap的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delSalesmanTraderMap(@RequestParam(value = "id") Integer id) {
        SalesmanTraderMap salesmanTraderMap = new SalesmanTraderMap();
        salesmanTraderMap.setId(id);
        salesmanTraderMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = salesmanTraderMapDao.updateSalesmanTraderMap(salesmanTraderMap).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
