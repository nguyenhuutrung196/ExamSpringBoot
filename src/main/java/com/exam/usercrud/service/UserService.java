package com.exam.usercrud.service;

import com.exam.usercrud.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(Long userId, User user);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    // Tạo mới người dùng với hình ảnh profile
    User createUserWithProfileImage(MultipartFile profileImage, User user) throws IOException;

    // Cập nhật người dùng với hình ảnh profile
    User updateUserWithProfileImage(Long userId, MultipartFile profileImage, String userDataJson) throws IOException;

}
