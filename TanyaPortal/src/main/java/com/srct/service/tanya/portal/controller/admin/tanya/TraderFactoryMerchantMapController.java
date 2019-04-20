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
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.TraderFactoryMerchantMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.TraderFactoryMerchantMapEntityVO;
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

@Api(value = "TraderFactoryMerchantMap")
@RestController("tanyaTraderFactoryMerchantMapController")
@RequestMapping(value = "/portal/admin/tanya/traderfactorymerchantmap")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class TraderFactoryMerchantMapController {

    @Autowired
    TraderFactoryMerchantMapDao traderFactoryMerchantMapDao;

    @ApiOperation(value = "更新TraderFactoryMerchantMap", notes = "传入TraderFactoryMerchantMap值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TraderFactoryMerchantMapEntityVO", name = "vo", value = "TraderFactoryMerchantMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateTraderFactoryMerchantMap(
            @RequestBody TraderFactoryMerchantMapEntityVO vo) {
        TraderFactoryMerchantMap traderFactoryMerchantMap = new TraderFactoryMerchantMap();
        BeanUtil.copyProperties(vo, traderFactoryMerchantMap);
        Integer id = traderFactoryMerchantMapDao.updateTraderFactoryMerchantMap(traderFactoryMerchantMap).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询TraderFactoryMerchantMap", notes = "传入TraderFactoryMerchantMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TraderFactoryMerchantMapEntityVO", name = "vo", value = "TraderFactoryMerchantMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<TraderFactoryMerchantMap>>.Resp> getTraderFactoryMerchantMapSelective(
            @RequestBody TraderFactoryMerchantMapEntityVO vo) {
        QueryRespVO<TraderFactoryMerchantMap> res = new QueryRespVO<>();
        TraderFactoryMerchantMap traderFactoryMerchantMap = new TraderFactoryMerchantMap();
        BeanUtil.copyProperties(vo, traderFactoryMerchantMap);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        PageInfo<TraderFactoryMerchantMap> traderFactoryMerchantMapList =
                traderFactoryMerchantMapDao.getTraderFactoryMerchantMapSelective(traderFactoryMerchantMap, pageInfo);
        res.getInfo().addAll(traderFactoryMerchantMapList.getList());
        res.buildPageInfo(traderFactoryMerchantMapList);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询TraderFactoryMerchantMap", notes = "返回id对应的TraderFactoryMerchantMap,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "TraderFactoryMerchantMap的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<TraderFactoryMerchantMap>>.Resp> getTraderFactoryMerchantMap(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<TraderFactoryMerchantMap> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            PageInfo<TraderFactoryMerchantMap> traderFactoryMerchantMapList = traderFactoryMerchantMapDao
                    .getAllTraderFactoryMerchantMapList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID,
                            pageInfo);
            res.getInfo().addAll(traderFactoryMerchantMapList.getList());
            res.buildPageInfo(traderFactoryMerchantMapList);
        } else {
            res.getInfo().add(traderFactoryMerchantMapDao.getTraderFactoryMerchantMapById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除TraderFactoryMerchantMap", notes = "软删除主键为id的TraderFactoryMerchantMap")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "TraderFactoryMerchantMap的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delTraderFactoryMerchantMap(
            @RequestParam(value = "id") Integer id) {
        TraderFactoryMerchantMap traderFactoryMerchantMap = new TraderFactoryMerchantMap();
        traderFactoryMerchantMap.setId(id);
        traderFactoryMerchantMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = traderFactoryMerchantMapDao.updateTraderFactoryMerchantMap(traderFactoryMerchantMap).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
