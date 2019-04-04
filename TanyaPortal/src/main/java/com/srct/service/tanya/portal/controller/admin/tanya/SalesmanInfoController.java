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
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.SalesmanInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.SalesmanInfoEntityVO;
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

@Api(value = "SalesmanInfo")
@RestController("tanyaSalesmanInfoController")
@RequestMapping(value = "/portal/admin/tanya/salesmaninfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class SalesmanInfoController {

    @Autowired
    SalesmanInfoDao salesmanInfoDao;

    @ApiOperation(value = "更新SalesmanInfo", notes = "传入SalesmanInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SalesmanInfoEntityVO", name = "vo", value = "SalesmanInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateSalesmanInfo(@RequestBody SalesmanInfoEntityVO vo) {
        SalesmanInfo salesmanInfo = new SalesmanInfo();
        BeanUtil.copyProperties(vo, salesmanInfo);
        Integer id = salesmanInfoDao.updateSalesmanInfo(salesmanInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询SalesmanInfo", notes = "传入SalesmanInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SalesmanInfoEntityVO", name = "vo", value = "SalesmanInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<SalesmanInfo>>.Resp> getSalesmanInfoSelective(
            @RequestBody SalesmanInfoEntityVO vo) {
        QueryRespVO<SalesmanInfo> res = new QueryRespVO<>();
        SalesmanInfo salesmanInfo = new SalesmanInfo();
        BeanUtil.copyProperties(vo, salesmanInfo);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        res.getInfo().addAll(salesmanInfoDao.getSalesmanInfoSelective(salesmanInfo));
        res.buildPageInfo(pageInfo);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询SalesmanInfo", notes = "返回id对应的SalesmanInfo,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "SalesmanInfo的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<SalesmanInfo>>.Resp> getSalesmanInfo(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<SalesmanInfo> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            res.getInfo().addAll(salesmanInfoDao
                    .getAllSalesmanInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID, pageInfo));
        } else {
            res.getInfo().add(salesmanInfoDao.getSalesmanInfoById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除SalesmanInfo", notes = "软删除主键为id的SalesmanInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "SalesmanInfo的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delSalesmanInfo(@RequestParam(value = "id") Integer id) {
        SalesmanInfo salesmanInfo = new SalesmanInfo();
        salesmanInfo.setId(id);
        salesmanInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = salesmanInfoDao.updateSalesmanInfo(salesmanInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
