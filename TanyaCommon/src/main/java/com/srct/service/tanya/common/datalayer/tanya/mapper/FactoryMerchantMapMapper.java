package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryMerchantMapExample;

@Component("tanyaFactoryMerchantMapMapper")
public interface FactoryMerchantMapMapper {

    long countByExample(FactoryMerchantMapExample example);

    int deleteByExample(FactoryMerchantMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FactoryMerchantMap record);

    int insertSelective(FactoryMerchantMap record);

    List<FactoryMerchantMap> selectByExample(FactoryMerchantMapExample example);

    FactoryMerchantMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FactoryMerchantMap record, @Param("example") FactoryMerchantMapExample example);

    int updateByExample(@Param("record") FactoryMerchantMap record, @Param("example") FactoryMerchantMapExample example);

    int updateByPrimaryKeySelective(FactoryMerchantMap record);

    int updateByPrimaryKey(FactoryMerchantMap record);
}