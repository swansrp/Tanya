package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanInfoExample;

@Component("tanyaSalesmanInfoMapper")
public interface SalesmanInfoMapper {

    long countByExample(SalesmanInfoExample example);

    int deleteByExample(SalesmanInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SalesmanInfo record);

    int insertSelective(SalesmanInfo record);

    List<SalesmanInfo> selectByExample(SalesmanInfoExample example);

    SalesmanInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SalesmanInfo record, @Param("example") SalesmanInfoExample example);

    int updateByExample(@Param("record") SalesmanInfo record, @Param("example") SalesmanInfoExample example);

    int updateByPrimaryKeySelective(SalesmanInfo record);

    int updateByPrimaryKey(SalesmanInfo record);
}