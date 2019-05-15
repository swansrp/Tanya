/**
 * Title: MenuServiceImpl
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-9 18:41
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.user.service.impl
 */
package com.srct.service.tanya.user.service.impl;

import com.srct.service.tanya.common.datalayer.tanya.entity.PermissionInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.RolePermissionMap;
import com.srct.service.tanya.common.datalayer.tanya.repository.PermissionInfoDao;
import com.srct.service.tanya.common.datalayer.tanya.repository.RolePermissionMapDao;
import com.srct.service.tanya.user.service.MenuService;
import com.srct.service.tanya.user.vo.MenuVO;
import com.srct.service.tanya.user.vo.QueryMenuVO;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private PermissionInfoDao permissionInfoDao;
    @Autowired
    private RolePermissionMapDao rolePermissionMapDao;


    @Override
    public List<QueryMenuVO> queryMenu(RoleInfo role) {
        List<PermissionInfo> permissionInfoList = getPermissionList(role);
        return makeMenuTree(permissionInfoList);
    }

    private List<QueryMenuVO> makeMenuTree(List<PermissionInfo> permissionInfoList) {
        List<QueryMenuVO> res = new ArrayList<>();
        List<PermissionInfo> parentMenuList =
                permissionInfoList.stream().filter(info -> info.getParentId() == null).collect(Collectors.toList());
        parentMenuList.forEach(parentMenu -> res.add(buildMenuVO(parentMenu, permissionInfoList)));
        return res;
    }

    private QueryMenuVO buildMenuVO(PermissionInfo parentMenu, List<PermissionInfo> permissionInfoList) {
        QueryMenuVO QueryMenuVO = new QueryMenuVO();
        MenuVO menuVO = new MenuVO();
        BeanUtil.copyProperties(parentMenu, menuVO);
        QueryMenuVO.setMenu(menuVO);
        List<PermissionInfo> menuList =
                permissionInfoList.stream().filter(info -> info.getParentId().equals(parentMenu.getId()))
                        .collect(Collectors.toList());
        menuList.forEach(subMenu -> QueryMenuVO.getSubMenu().add(buildMenuVO(subMenu, permissionInfoList)));
        return QueryMenuVO;
    }

    private List<PermissionInfo> getPermissionList(RoleInfo role) {
        RolePermissionMap rolePermissionMapEx = RolePermissionMap.builder().roleId(role.getId()).build();
        List<RolePermissionMap> rolePermissionMapList =
                rolePermissionMapDao.getRolePermissionMapSelective(rolePermissionMapEx);
        List<Integer> permissionInfoIdList =
                (List<Integer>) ReflectionUtil.getFieldList(rolePermissionMapList, "perimissionId");
        List<PermissionInfo> permissionInfoList = new ArrayList<>();
        permissionInfoIdList.forEach(id -> permissionInfoList.add(permissionInfoDao.getPermissionInfoById(id)));
        return permissionInfoList;
    }
}
