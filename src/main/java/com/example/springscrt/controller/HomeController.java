package com.example.springscrt.controller;

import com.example.springscrt.model.Role;
import com.example.springscrt.model.User;
import com.example.springscrt.service.RegisterService;
import com.example.springscrt.service.UserAdditionalService;
import com.example.springscrt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserAdditionalService userAdditionalService;

    @GetMapping(value = "/")
    public String getHome(){
        return "index";
    }
    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }
    @GetMapping(value = "/register")
    public String getRegisterPage() {
        return "register";
    }

    @GetMapping(value = "/profile")
    public String getProfilePage(Model model) {
        List<Role> roleByUser=userService.getRoleByUser();
        model.addAttribute("userRoles",roleByUser);
        return "profile";
    }
    @PostMapping(value = "/addUser")
    public String addUser(@RequestParam(value = "fullName")String fullName,
                          @RequestParam(value = "email")String email,
                          @RequestParam(value = "password")String password,
                          @RequestParam(value = "repytePassword")String repytePassowrd){
        Boolean b=registerService.registerUser(fullName,email,password,repytePassowrd);
        if (b == true && b != null){
            return "redirect:/login";
        }else{
            return "redirect:/register?someerror";
        }
    }
    @PostMapping(value = "/updatePassword")
    public String updatePassword(@RequestParam(value = "oldPassword")String oldPassword,
                                 @RequestParam(value = "newPassword") String newPassword,
                                 @RequestParam(value = "repytePassword") String repytePassword){
        Boolean b = userAdditionalService.changePassword(oldPassword,newPassword,repytePassword);
        if (b != null && b == true){
            return "redirect:/profile?success";
        }else{
            return "redirect:/profile?error";
        }
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin")
    public String adminPage(Model model){
        List<User> users = userAdditionalService.getUsers();
        model.addAttribute("users", users);
        return "admin";
    }
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @GetMapping(value = "/manager")
    public String managerPage(Model model){
        List<User> users = userAdditionalService.getUsers();
        model.addAttribute("users", users);
        return "manager";
    }
    @PostMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id")Long id){
        userAdditionalService.deleteUser(id);
        return "redirect:/admin";
    }
}