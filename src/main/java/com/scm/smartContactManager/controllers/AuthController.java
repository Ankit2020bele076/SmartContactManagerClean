package com.scm.smartContactManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.smartContactManager.entities.User;
import com.scm.smartContactManager.exception.Message;
import com.scm.smartContactManager.exception.MessageType;
import com.scm.smartContactManager.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping(path = "/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session){
        User user = userRepo.findByEmailToken(token).orElse(null);
        if(user != null){
            user.setEnabled(true);
            user.setEmailVerified(true);
            userRepo.save(user);
            // session.setAttribute("message", Message.builder().content("Email is verified. Now you can login.").type(MessageType.green).build());
            return "success_page";
        }
        // session.setAttribute("message", Message.builder().content("Email not verified ! Token is not associated with user").type(MessageType.red).build());
        return "error_page";
    }
}
