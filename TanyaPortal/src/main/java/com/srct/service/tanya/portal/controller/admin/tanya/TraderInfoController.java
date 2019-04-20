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
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.TraderInfoEntityVO;
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

@Api(value = "TraderInfo")
@RestController("tanyaTraderInfoController")
@RequestMapping(value = "/portal/admin/tanya/traderinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class TraderInfoController {

    @Autowired
    TraderInfoDao traderInfoDao;

    @ApiOperation(value = "更新TraderInfo", notes = "传入TraderInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TraderInfoEntityVO", name = "vo", value = "TraderInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateTraderInfo(@RequestBody TraderInfoEntityVO vo) {
        TraderInfo traderInfo = new TraderInfo();
        BeanUtil.copyProperties(vo, traderInfo);
        Integer id = traderInfoDao.updateTraderInfo(traderInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询TraderInfo", notes = "传入TraderInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TraderInfoEntityVO", name = "vo", value = "TraderInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<TraderInfo>>.Resp> getTraderInfoSelective(
            @RequestBody TraderInfoEntityVO vo) {
        QueryRespVO<TraderInfo> res = new QueryRespVO<>();
        TraderInfo traderInfo = new TraderInfo();
        BeanUtil.copyProperties(vo, traderInfo);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        PageInfo<TraderInfo> traderInfoList = traderInfoDao.getTraderInfoSelective(traderInfo, pageInfo);
        res.getInfo().addAll(traderInfoList.getList());
        res.buildPageInfo(traderInfoList);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询TraderInfo", notes = "返回id对应的TraderInfo,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "TraderInfo的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<TraderInfo>>.Resp> getTraderInfo(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<TraderInfo> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            PageInfo<TraderInfo> traderInfoList =
                    traderInfoDao.getAllTraderInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID, pageInfo);
            res.getInfo().addAll(traderInfoList.getList());
            res.buildPageInfo(traderInfoList);
        } else {
            res.getInfo().add(traderInfoDao.getTraderInfoById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除TraderInfo", notes = "软删除主键为id的TraderInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "TraderInfo的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delTraderInfo(@RequestParam(value = "id") Integer id) {
        TraderInfo traderInfo = new TraderInfo();
        traderInfo.setId(id);
        traderInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = traderInfoDao.updateTraderInfo(traderInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
