package com.scm.smartContactManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.smartContactManager.entities.User;
import com.scm.smartContactManager.exception.Helper;
import com.scm.smartContactManager.services.UserService;

@ControllerAdvice
public class RootController {
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInfo(Model model, Authentication authentication){
        if(authentication == null){
            return;
        }
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        model.addAttribute("user", user);
    }
}
