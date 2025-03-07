package com.ride2gether.ride2gether;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // ชื่อไฟล์ login.html ใน templates folder
    }
}