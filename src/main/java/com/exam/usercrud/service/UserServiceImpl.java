package com.exam.usercrud.service;

import com.exam.usercrud.entity.User;
import com.exam.usercrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Cập nhật thông tin từ userDataJson vào existingUser

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User createUserWithProfileImage(MultipartFile profileImage, User user) throws IOException {
        // Xử lý lưu hình ảnh và thông tin người dùng
        String profileImagePath = saveProfileImage(profileImage);
        user.setProfileImage(profileImagePath);

        return userRepository.save(user);
    }

    @Override
    public User updateUserWithProfileImage(Long userId, MultipartFile profileImage, String userDataJson) throws IOException {
        // Tìm người dùng hiện tại và cập nhật thông tin và hình ảnh profile
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Xóa hình ảnh cũ nếu có
        // deleteProfileImage(existingUser.getProfileImage());

        // Xử lý lưu hình ảnh mới và thông tin người dùng
        String profileImagePath = saveProfileImage(profileImage);
        User updatedUser = processUserData(profileImagePath, userDataJson);

        // Cập nhật thông tin người dùng
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setActive(updatedUser.isActive());
        existingUser.setLocked(updatedUser.isLocked());
        existingUser.setProfileImage(updatedUser.getProfileImage());

        return userRepository.save(existingUser);
    }

    // Xử lý lưu hình ảnh vào thư mục
    private String saveProfileImage(MultipartFile profileImage) throws IOException {
        // Implement logic to save profile image to file system
        return "profile-images/" + profileImage.getOriginalFilename();
    }

    // Xử lý thông tin người dùng từ JSON
    private User processUserData(String profileImagePath, String userDataJson) {
        // Implement logic to process user data from JSON and set profile image path
        return new User();
    }

}
