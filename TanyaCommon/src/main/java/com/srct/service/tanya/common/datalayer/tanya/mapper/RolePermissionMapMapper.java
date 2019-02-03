package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.RolePermissionMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.RolePermissionMapExample;

@Component("tanyaRolePermissionMapMapper")
public interface RolePermissionMapMapper {

    long countByExample(RolePermissionMapExample example);

    int deleteByExample(RolePermissionMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermissionMap record);

    int insertSelective(RolePermissionMap record);

    List<RolePermissionMap> selectByExample(RolePermissionMapExample example);

    RolePermissionMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePermissionMap record, @Param("example") RolePermissionMapExample example);

    int updateByExample(@Param("record") RolePermissionMap record, @Param("example") RolePermissionMapExample example);

    int updateByPrimaryKeySelective(RolePermissionMap record);

    int updateByPrimaryKey(RolePermissionMap record);
}