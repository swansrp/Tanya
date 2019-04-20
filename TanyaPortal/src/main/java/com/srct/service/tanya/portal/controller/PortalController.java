/**
 * Title: PortalController
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-4-11 15:49
 * @description Project Name: Tanya
 * Package: com.srct.service.tanya.portal.controller
 */
package com.srct.service.tanya.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
class PortalController {
    /**
     * 访问首页方式
     * 切记不能配置index.html
     */
    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
