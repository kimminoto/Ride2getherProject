package com.ride2gether.ride2gether;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String showHome(Model model) {
        // ดึงข้อมูลผู้ใช้ที่ล็อกอินอยู่
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email);

        // เพิ่มข้อมูลผู้ใช้ไปยัง model
        model.addAttribute("user", user);

        return "home";
    }

    @GetMapping("/find-ride")
    public String findRide() {
        return "find-ride";
    }

    @GetMapping("/be-driver")
    public String beDriver() {
        return "be-driver";
    }

    @GetMapping("/track-journey")
    public String trackJourney() {
        return "track-journey";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }
}