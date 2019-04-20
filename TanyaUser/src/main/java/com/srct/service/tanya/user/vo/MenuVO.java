/**
 * Title: MenuVO
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-9 19:03
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.user.vo
 */
package com.srct.service.tanya.user.vo;

import lombok.Data;

@Data
public class MenuVO {
    private String permission;
    private String resourceType;
    private String path;
    private String iconUrl;
    private String name;
}
