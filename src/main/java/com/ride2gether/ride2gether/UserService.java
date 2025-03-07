package com.ride2gether.ride2gether;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบผู้ใช้ที่มี ID: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        // ตรวจสอบว่าอีเมลนี้มีอยู่แล้วหรือไม่
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            logger.error("อีเมล {} ได้ลงทะเบียนไปแล้ว", user.getEmail());
            throw new RuntimeException("อีเมลนี้ได้ลงทะเบียนไปแล้ว");
        }

        // เข้ารหัสรหัสผ่านหากยังไม่ได้เข้ารหัส
        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        logger.info("กำลังบันทึกผู้ใช้ใหม่: {}", user.getEmail());
        return userRepository.save(user);
    }

    public void register(User user) {
        // ตรวจสอบความถูกต้องของข้อมูล
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RuntimeException("กรุณาระบุอีเมล");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException("กรุณาระบุรหัสผ่าน");
        }

        // เข้ารหัสรหัสผ่าน
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userRepository.save(user);
            logger.info("ลงทะเบียนผู้ใช้สำเร็จ: {}", user.getEmail());
        } catch (Exception e) {
            logger.error("เกิดข้อผิดพลาดในการลงทะเบียน: {}", e.getMessage());
            throw new RuntimeException("เกิดข้อผิดพลาดในการลงทะเบียน: " + e.getMessage());
        }
    }
}