package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.FactoryInfoExample;

@Component("tanyaFactoryInfoMapper")
public interface FactoryInfoMapper {

    long countByExample(FactoryInfoExample example);

    int deleteByExample(FactoryInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FactoryInfo record);

    int insertSelective(FactoryInfo record);

    List<FactoryInfo> selectByExample(FactoryInfoExample example);

    FactoryInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FactoryInfo record, @Param("example") FactoryInfoExample example);

    int updateByExample(@Param("record") FactoryInfo record, @Param("example") FactoryInfoExample example);

    int updateByPrimaryKeySelective(FactoryInfo record);

    int updateByPrimaryKey(FactoryInfo record);
}