package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.UserRoleMapExample;

@Component("tanyaUserRoleMapMapper")
public interface UserRoleMapMapper {

    long countByExample(UserRoleMapExample example);

    int deleteByExample(UserRoleMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleMap record);

    int insertSelective(UserRoleMap record);

    List<UserRoleMap> selectByExample(UserRoleMapExample example);

    UserRoleMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRoleMap record, @Param("example") UserRoleMapExample example);

    int updateByExample(@Param("record") UserRoleMap record, @Param("example") UserRoleMapExample example);

    int updateByPrimaryKeySelective(UserRoleMap record);

    int updateByPrimaryKey(UserRoleMap record);
}