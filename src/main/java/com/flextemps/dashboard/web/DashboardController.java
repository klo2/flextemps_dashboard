package com.flextemps.dashboard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String index() {
        return "index"; // points to src/main/resources/templates/index.html generally, but
                        // static/index.html also works with Spring Boot
    }
}
