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
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignSalesmanMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.CampaignSalesmanMapEntityVO;
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

@Api(value = "CampaignSalesmanMap")
@RestController("tanyaCampaignSalesmanMapController")
@RequestMapping(value = "/portal/admin/tanya/campaignsalesmanmap")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class CampaignSalesmanMapController {

    @Autowired
    CampaignSalesmanMapDao campaignSalesmanMapDao;

    @ApiOperation(value = "更新CampaignSalesmanMap", notes = "传入CampaignSalesmanMap值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "CampaignSalesmanMapEntityVO", name = "vo", value = "CampaignSalesmanMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateCampaignSalesmanMap(
            @RequestBody CampaignSalesmanMapEntityVO vo) {
        CampaignSalesmanMap campaignSalesmanMap = new CampaignSalesmanMap();
        BeanUtil.copyProperties(vo, campaignSalesmanMap);
        Integer id = campaignSalesmanMapDao.updateCampaignSalesmanMap(campaignSalesmanMap).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询CampaignSalesmanMap", notes = "传入CampaignSalesmanMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "CampaignSalesmanMapEntityVO", name = "vo", value = "CampaignSalesmanMap", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignSalesmanMap>>.Resp> getCampaignSalesmanMapSelective(
            @RequestBody CampaignSalesmanMapEntityVO vo) {
        QueryRespVO<CampaignSalesmanMap> res = new QueryRespVO<>();
        CampaignSalesmanMap campaignSalesmanMap = new CampaignSalesmanMap();
        BeanUtil.copyProperties(vo, campaignSalesmanMap);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        PageInfo<CampaignSalesmanMap> campaignSalesmanMapList =
                campaignSalesmanMapDao.getCampaignSalesmanMapSelective(campaignSalesmanMap, pageInfo);
        res.getInfo().addAll(campaignSalesmanMapList.getList());
        res.buildPageInfo(campaignSalesmanMapList);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询CampaignSalesmanMap", notes = "返回id对应的CampaignSalesmanMap,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "CampaignSalesmanMap的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<CampaignSalesmanMap>>.Resp> getCampaignSalesmanMap(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<CampaignSalesmanMap> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            PageInfo<CampaignSalesmanMap> campaignSalesmanMapList = campaignSalesmanMapDao
                    .getAllCampaignSalesmanMapList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID, pageInfo);
            res.getInfo().addAll(campaignSalesmanMapList.getList());
            res.buildPageInfo(campaignSalesmanMapList);
        } else {
            res.getInfo().add(campaignSalesmanMapDao.getCampaignSalesmanMapById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除CampaignSalesmanMap", notes = "软删除主键为id的CampaignSalesmanMap")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "CampaignSalesmanMap的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delCampaignSalesmanMap(@RequestParam(value = "id") Integer id) {
        CampaignSalesmanMap campaignSalesmanMap = new CampaignSalesmanMap();
        campaignSalesmanMap.setId(id);
        campaignSalesmanMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = campaignSalesmanMapDao.updateCampaignSalesmanMap(campaignSalesmanMap).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
