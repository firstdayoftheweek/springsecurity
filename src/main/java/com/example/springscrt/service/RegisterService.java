package com.example.springscrt.service;

import com.example.springscrt.model.Role;
import com.example.springscrt.model.User;
import com.example.springscrt.repository.RoleRepository;
import com.example.springscrt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public Boolean registerUser(String fullName, String email, String password, String repytePassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            if (password.equals(repytePassword)) {
                List<Role> roles = new ArrayList<>();
                Role simpleUserRole = roleRepository.findByName("role_user");
                roles.add(simpleUserRole);
                User newUser = new User();
                newUser.setFullName(fullName);
                newUser.setEmail(email);
                newUser.setRoles(roles);
                newUser.setPassword(passwordEncoder.encode(password));
                userRepository.save(newUser);
                return  true;
            }
        }
        return false;
    }
}