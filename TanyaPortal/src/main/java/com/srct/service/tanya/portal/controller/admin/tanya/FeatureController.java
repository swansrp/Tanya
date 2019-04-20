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
import com.srct.service.tanya.common.datalayer.tanya.entity.Feature;
import com.srct.service.tanya.common.datalayer.tanya.repository.FeatureDao;
import com.srct.service.tanya.portal.vo.admin.tanya.FeatureEntityVO;
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

@Api(value = "Feature")
@RestController("tanyaFeatureController")
@RequestMapping(value = "/portal/admin/tanya/feature")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class FeatureController {

    @Autowired
    FeatureDao featureDao;

    @ApiOperation(value = "更新Feature", notes = "传入Feature值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "FeatureEntityVO", name = "vo", value = "Feature", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateFeature(@RequestBody FeatureEntityVO vo) {
        Feature feature = new Feature();
        BeanUtil.copyProperties(vo, feature);
        Integer id = featureDao.updateFeature(feature).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询Feature", notes = "传入Feature值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "FeatureEntityVO", name = "vo", value = "Feature", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<Feature>>.Resp> getFeatureSelective(
            @RequestBody FeatureEntityVO vo) {
        QueryRespVO<Feature> res = new QueryRespVO<>();
        Feature feature = new Feature();
        BeanUtil.copyProperties(vo, feature);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        PageInfo<Feature> featureList = featureDao.getFeatureSelective(feature, pageInfo);
        res.getInfo().addAll(featureList.getList());
        res.buildPageInfo(featureList);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询Feature", notes = "返回id对应的Feature,id为空返回全部")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "Feature的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<Feature>>.Resp> getFeature(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<Feature> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            PageInfo<Feature> featureList =
                    featureDao.getAllFeatureList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID, pageInfo);
            res.getInfo().addAll(featureList.getList());
            res.buildPageInfo(featureList);
        } else {
            res.getInfo().add(featureDao.getFeatureById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除Feature", notes = "软删除主键为id的Feature")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "Feature的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delFeature(@RequestParam(value = "id") Integer id) {
        Feature feature = new Feature();
        feature.setId(id);
        feature.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = featureDao.updateFeature(feature).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
