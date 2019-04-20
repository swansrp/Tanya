/**
 * Title: QueryMenuVO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-9 18:45
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.user.vo
 */
package com.srct.service.tanya.user.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryMenuVO {
    private MenuVO menu;
    private List<QueryMenuVO> subMenu;

    public QueryMenuVO() {
        subMenu = new ArrayList<>();
    }
}
