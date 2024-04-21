package com.example.springscrt.service;

import com.example.springscrt.model.User;
import com.example.springscrt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAdditionalService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public Boolean changePassword(String oldPassword, String newPassword, String repytePassword){

        User onlineUser = userService.getCurrentUser();
        if(onlineUser!=null) {
            if (passwordEncoder.matches(oldPassword, onlineUser.getPassword()))
                onlineUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(onlineUser);
            return true;
        }
        return false;
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}