/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: Tanya
 * @Package: com.srct.service.tanya.portal.controller.admin.tanya 
 * @author: Sharp   
 * @date: 2019/02/12
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
import com.srct.service.tanya.common.datalayer.tanya.entity.CampaignSalesmanMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.CampaignSalesmanMapDao;
import com.srct.service.tanya.portal.vo.admin.tanya.CampaignSalesmanMapEntityVO;
import com.srct.service.utils.BeanUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
        @ApiImplicitParam(paramType = "body", dataType = "CampaignSalesmanMapEntityVO", name = "vo", value = "CampaignSalesmanMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateCampaignSalesmanMap(@RequestBody CampaignSalesmanMapEntityVO vo) {
        CampaignSalesmanMap campaignSalesmanMap = new CampaignSalesmanMap();
        BeanUtil.copyProperties(vo, campaignSalesmanMap);
        Integer id = campaignSalesmanMapDao.updateCampaignSalesmanMap(campaignSalesmanMap).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询CampaignSalesmanMap", notes = "传入CampaignSalesmanMap值,匹配不为null的域进行查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "CampaignSalesmanMapEntityVO", name = "vo", value = "CampaignSalesmanMap", required = true) })
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<List<CampaignSalesmanMap>>.Resp> getCampaignSalesmanMapSelective(
            @RequestBody CampaignSalesmanMapEntityVO vo
            ) {
        List<CampaignSalesmanMap> res = new ArrayList<>();
        CampaignSalesmanMap campaignSalesmanMap = new CampaignSalesmanMap();
        BeanUtil.copyProperties(vo, campaignSalesmanMap);
        res.addAll(campaignSalesmanMapDao.getCampaignSalesmanMapSelective(campaignSalesmanMap));
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询CampaignSalesmanMap", notes = "返回id对应的CampaignSalesmanMap,id为空返回全部")
    @ApiImplicitParams({ 
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "CampaignSalesmanMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<CampaignSalesmanMap>>.Resp> getCampaignSalesmanMap(
            @RequestParam(value = "id", required = false) Integer id
            ) {
        List<CampaignSalesmanMap> resList = new ArrayList<>();
        if (id == null) {
            resList.addAll(campaignSalesmanMapDao.getAllCampaignSalesmanMapList(DataSourceCommonConstant.DATABASE_COMMON_IGORE_VALID));
        } else {
            resList.add(campaignSalesmanMapDao.getCampaignSalesmanMapbyId(id));
        }
        return TanyaExceptionHandler.generateResponse(resList);
    }
    
    @ApiOperation(value = "软删除CampaignSalesmanMap", notes = "软删除主键为id的CampaignSalesmanMap")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "Interger", name = "id", value = "CampaignSalesmanMap的主键", required = false)})
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功"),
        @ApiResponse(code = 500, message = "服务器内部异常"),
        @ApiResponse(code = 403, message = "权限不足") })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delCampaignSalesmanMap(
            @RequestParam(value = "id", required = true) Integer id
            ) {
        CampaignSalesmanMap campaignSalesmanMap = new CampaignSalesmanMap();
        campaignSalesmanMap.setId(id);
        campaignSalesmanMap.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = campaignSalesmanMapDao.updateCampaignSalesmanMap(campaignSalesmanMap).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
