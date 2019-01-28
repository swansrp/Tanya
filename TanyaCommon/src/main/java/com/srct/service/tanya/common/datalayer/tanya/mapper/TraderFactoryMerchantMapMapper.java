package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.TraderFactoryMerchantMapExample;

@Component("tanyaTraderFactoryMerchantMapMapper")
public interface TraderFactoryMerchantMapMapper {

    long countByExample(TraderFactoryMerchantMapExample example);

    int deleteByExample(TraderFactoryMerchantMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TraderFactoryMerchantMap record);

    int insertSelective(TraderFactoryMerchantMap record);

    List<TraderFactoryMerchantMap> selectByExample(TraderFactoryMerchantMapExample example);

    TraderFactoryMerchantMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TraderFactoryMerchantMap record, @Param("example") TraderFactoryMerchantMapExample example);

    int updateByExample(@Param("record") TraderFactoryMerchantMap record, @Param("example") TraderFactoryMerchantMapExample example);

    int updateByPrimaryKeySelective(TraderFactoryMerchantMap record);

    int updateByPrimaryKey(TraderFactoryMerchantMap record);
}