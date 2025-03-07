package com.ride2gether.ride2gether;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        try {
            // บันทึกผู้ใช้
            userService.save(user);
            logger.info("User registered successfully: {}", user.getEmail());

            // เปลี่ยนเส้นทางไปยังหน้าล็อกอิน
            return "redirect:/login?registered";
        } catch (Exception e) {
            // บันทึกข้อผิดพลาด
            logger.error("Error registering user: {}", e.getMessage(), e);

            // เปลี่ยนเส้นทางกลับไปยังหน้าลงทะเบียนพร้อมข้อความแสดงข้อผิดพลาด
            return "redirect:/register?error";
        }//http://localhost:8080/register
    }
}