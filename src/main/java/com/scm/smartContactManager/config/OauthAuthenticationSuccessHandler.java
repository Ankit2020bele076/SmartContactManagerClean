package com.scm.smartContactManager.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.smartContactManager.entities.Providers;
import com.scm.smartContactManager.entities.User;
import com.scm.smartContactManager.exception.AppConstants;
import com.scm.smartContactManager.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // logger.info("OauthenticationSuccessfullHandler");
        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        var authorizedClientId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        // logger.info(authorizedClientId);
        var user = (DefaultOAuth2User)authentication.getPrincipal();

        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setEmailVerified(true);
        user1.setEnabled(true);

        if(authorizedClientId.equalsIgnoreCase("google")){
            user1.setEmail(user.getAttribute("email").toString());
            user1.setProfilePic(user.getAttribute("picture").toString());
            user1.setName(user.getAttribute("name").toString());
            user1.setProviderUserId(user.getName());
            user1.setProvider(Providers.GOOGLE);
            user1.setAbout("This account is created using google");
        }
        else if(authorizedClientId.equalsIgnoreCase("github")){
            String email = user.getAttribute("email") != null ? user.getAttribute("email").toString() : user.getAttribute("login").toString() + "@gmail.com";
            String picture = user.getAttribute("avatar_url").toString();
            String name = user.getAttribute("login").toString();
            String providerId = user.getName();

            user1.setEmail(email);
            user1.setProfilePic(picture);
            user1.setName(name);
            user1.setProviderUserId(providerId);
            user1.setProvider(Providers.GITHUB);
            user1.setAbout("This account is created using github");
        }
        User user2 = userRepo.findByEmail(user1.getEmail()).orElse(null);
        if(user2 == null){
            userRepo.save(user1);
        }
                /* 
        DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
        

        // logger.info(user.getName());
        // user.getAttributes().forEach((key,value)->{
        //     logger.info("{}->{}",key,value);
        // });
        // logger.info(user.getAttributes().toString());

        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setName(user.getAttribute("name"));
        user1.setEmail(user.getAttribute("email"));
        // user1.setPassword("password");
        // user1.setAbout("This account is logged in by google");
        user1.setProfilePic(user.getAttribute("picture"));
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProvider(Providers.GOOGLE);
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setProviderUserId(user.getName());

        User user2 = userRepo.findByEmail(user.getAttribute("email")).orElse(null);
        if(user2 != null){
            new DefaultRedirectStrategy().sendRedirect(request,response,"/user/dashboard");
        }
        else{
            userRepo.save(user1);
            String userName =  user.getAttribute("name"); // Adjust key based on your user attributes
            String email = (String) user.getAttributes().get("email");
            request.getSession().setAttribute("email", email);
            String targetUrl = "/signUpGoogle"; 
                */
            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
        // }
        
    }
    
}
