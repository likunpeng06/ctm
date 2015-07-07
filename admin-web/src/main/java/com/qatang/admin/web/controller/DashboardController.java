package com.qatang.admin.web.controller;

import com.qatang.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by qatang on 14-6-5.
 */
@Controller
public class DashboardController extends BaseController {
    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
