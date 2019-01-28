package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.SalesmanTraderMapExample;

@Component("tanyaSalesmanTraderMapMapper")
public interface SalesmanTraderMapMapper {

    long countByExample(SalesmanTraderMapExample example);

    int deleteByExample(SalesmanTraderMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SalesmanTraderMap record);

    int insertSelective(SalesmanTraderMap record);

    List<SalesmanTraderMap> selectByExample(SalesmanTraderMapExample example);

    SalesmanTraderMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SalesmanTraderMap record, @Param("example") SalesmanTraderMapExample example);

    int updateByExample(@Param("record") SalesmanTraderMap record, @Param("example") SalesmanTraderMapExample example);

    int updateByPrimaryKeySelective(SalesmanTraderMap record);

    int updateByPrimaryKey(SalesmanTraderMap record);
}