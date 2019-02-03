package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.PermissionInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.PermissionInfoExample;

@Component("tanyaPermissionInfoMapper")
public interface PermissionInfoMapper {

    long countByExample(PermissionInfoExample example);

    int deleteByExample(PermissionInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PermissionInfo record);

    int insertSelective(PermissionInfo record);

    List<PermissionInfo> selectByExample(PermissionInfoExample example);

    PermissionInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PermissionInfo record, @Param("example") PermissionInfoExample example);

    int updateByExample(@Param("record") PermissionInfo record, @Param("example") PermissionInfoExample example);

    int updateByPrimaryKeySelective(PermissionInfo record);

    int updateByPrimaryKey(PermissionInfo record);
}