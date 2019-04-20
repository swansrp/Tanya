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
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopInfo;
import com.srct.service.tanya.common.datalayer.tanya.repository.ShopInfoDao;
import com.srct.service.tanya.portal.vo.admin.tanya.ShopInfoEntityVO;
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

@Api(value = "ShopInfo")
@RestController("tanyaShopInfoController")
@RequestMapping(value = "/portal/admin/tanya/shopinfo")
@CrossOrigin(origins = "*")
@Profile(value = {"dev", "test"})
public class ShopInfoController {

    @Autowired
    ShopInfoDao shopInfoDao;

    @ApiOperation(value = "更新ShopInfo", notes = "传入ShopInfo值,Id为空时为插入,不为空时为更新。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ShopInfoEntityVO", name = "vo", value = "ShopInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<Integer>.Resp> updateShopInfo(@RequestBody ShopInfoEntityVO vo) {
        ShopInfo shopInfo = new ShopInfo();
        BeanUtil.copyProperties(vo, shopInfo);
        Integer id = shopInfoDao.updateShopInfo(shopInfo).getId();
        return TanyaExceptionHandler.generateResponse(id);
    }

    @ApiOperation(value = "查询ShopInfo", notes = "传入ShopInfo值,匹配不为null的域进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ShopInfoEntityVO", name = "vo", value = "ShopInfo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopInfo>>.Resp> getShopInfoSelective(
            @RequestBody ShopInfoEntityVO vo) {
        QueryRespVO<ShopInfo> res = new QueryRespVO<>();
        ShopInfo shopInfo = new ShopInfo();
        BeanUtil.copyProperties(vo, shopInfo);
        PageInfo pageInfo = DBUtil.buildPageInfo(vo);
        PageInfo<ShopInfo> shopInfoList = shopInfoDao.getShopInfoSelective(shopInfo, pageInfo);
        res.getInfo().addAll(shopInfoList.getList());
        res.buildPageInfo(shopInfoList);
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "查询ShopInfo", notes = "返回id对应的ShopInfo,id为空返回全部")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "ShopInfo的主键"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "currentPage", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页条目数量")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<QueryRespVO<ShopInfo>>.Resp> getShopInfo(
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id) {
        QueryRespVO<ShopInfo> res = new QueryRespVO<>();
        if (id == null) {
            PageInfo pageInfo = DBUtil.buildPageInfo(currentPage, pageSize);
            PageInfo<ShopInfo> shopInfoList =
                    shopInfoDao.getAllShopInfoList(DataSourceCommonConstant.DATABASE_COMMON_IGNORE_VALID, pageInfo);
            res.getInfo().addAll(shopInfoList.getList());
            res.buildPageInfo(shopInfoList);
        } else {
            res.getInfo().add(shopInfoDao.getShopInfoById(id));
        }
        return TanyaExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "软删除ShopInfo", notes = "软删除主键为id的ShopInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "ShopInfo的主键")})
    @ApiResponses({@ApiResponse(code = 200, message = "操作成功"), @ApiResponse(code = 500, message = "服务器内部异常"),
            @ApiResponse(code = 403, message = "权限不足")})
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse<Integer>.Resp> delShopInfo(@RequestParam(value = "id") Integer id) {
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(id);
        shopInfo.setValid(DataSourceCommonConstant.DATABASE_COMMON_INVALID);
        Integer delId = shopInfoDao.updateShopInfo(shopInfo).getId();
        return TanyaExceptionHandler.generateResponse(delId);
    }
}
