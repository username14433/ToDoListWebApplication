package org.rockend.controller.secured;

import org.rockend.entity.User;
import org.rockend.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivateAdminController {

    UserServiceImpl userServiceImpl;
    @Autowired
    public PrivateAdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/admin")
    public String getManagementPage(Model model) {
        User user = userServiceImpl.getCurrentUser();
        model.addAttribute("userName", user.getName());
        return "private/admin/management-page";
    }
}
