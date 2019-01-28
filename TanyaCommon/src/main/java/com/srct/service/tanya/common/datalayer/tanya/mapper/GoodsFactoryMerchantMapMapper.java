package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsFactoryMerchantMapExample;

@Component("tanyaGoodsFactoryMerchantMapMapper")
public interface GoodsFactoryMerchantMapMapper {

    long countByExample(GoodsFactoryMerchantMapExample example);

    int deleteByExample(GoodsFactoryMerchantMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsFactoryMerchantMap record);

    int insertSelective(GoodsFactoryMerchantMap record);

    List<GoodsFactoryMerchantMap> selectByExample(GoodsFactoryMerchantMapExample example);

    GoodsFactoryMerchantMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsFactoryMerchantMap record, @Param("example") GoodsFactoryMerchantMapExample example);

    int updateByExample(@Param("record") GoodsFactoryMerchantMap record, @Param("example") GoodsFactoryMerchantMapExample example);

    int updateByPrimaryKeySelective(GoodsFactoryMerchantMap record);

    int updateByPrimaryKey(GoodsFactoryMerchantMap record);
}