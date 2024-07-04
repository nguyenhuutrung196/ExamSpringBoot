package com.exam.usercrud.controller;

import com.exam.usercrud.entity.User;
import com.exam.usercrud.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    // Lấy danh sách tất cả người dùng
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Tạo mới người dùng với hình ảnh profile
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestParam("profileImage") MultipartFile profileImage,
                                           @RequestParam("user") String userDataJson) throws IOException {
        User user = objectMapper.readValue(userDataJson, User.class);
        User createdUser = userService.createUserWithProfileImage(profileImage, user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Cập nhật người dùng với hình ảnh profile
    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId,
                                           @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
                                           @RequestParam("user") String userDataJson) throws IOException {
        User user = userService.updateUserWithProfileImage(userId, profileImage, userDataJson);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Xóa người dùng và hình ảnh profile
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Lấy thông tin người dùng bao gồm hình ảnh profile
    @GetMapping("/show/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Xử lý lưu hình ảnh profile vào thư mục trong file system
    private String saveProfileImage(MultipartFile profileImage) throws IOException {
        String uploadDir = "./profile-images/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = profileImage.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        profileImage.transferTo(filePath.toFile());

        return fileName;
    }

}
