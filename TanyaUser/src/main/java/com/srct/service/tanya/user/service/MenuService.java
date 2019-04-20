package com.srct.service.tanya.user.service;

import com.srct.service.tanya.common.datalayer.tanya.entity.RoleInfo;
import com.srct.service.tanya.user.vo.QueryMenuVO;

import java.util.List;

/**
 * Title: MenuService
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-9 18:40
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.user.service
 */
public interface MenuService {
    List<QueryMenuVO> queryMenu(RoleInfo role);
}
