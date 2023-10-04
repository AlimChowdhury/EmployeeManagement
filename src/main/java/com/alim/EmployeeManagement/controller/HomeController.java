package com.alim.EmployeeManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home() {
        return "login";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "homePage";
    }

    @GetMapping("/welcome")
    public String getWelcomePage() {
        return "welcomePage";
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "adminPage";
    }

    @GetMapping("/emp")
    public String getEmployeePage() {
        return "empPage";
    }

    @GetMapping("/mgr")
    public String getManagerPage() {
        return "mgrPage";
    }

    @GetMapping("/hr")
    public String getHrPage() {
        return "hrPage";
    }

    @GetMapping("/common")
    public String getCommonPage() {
        return "commonPage";
    }

    @GetMapping("/accessDenied")
    public String getAccessDeniedPage() {
        return "accessDeniedPage";
    }
}