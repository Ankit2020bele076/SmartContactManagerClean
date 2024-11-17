package com.scm.smartContactManager.exception;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){

        if(authentication instanceof OAuth2AuthenticationToken){
            
            var oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            var clientid = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2user = (DefaultOAuth2User)authentication.getPrincipal();

            String username = "";

            if(clientid.equalsIgnoreCase("google")){
                username = oauth2user.getAttribute("email").toString();
            }
            else if(clientid.equalsIgnoreCase("github")){
                username = oauth2user.getAttribute("email") != null ? oauth2user.getAttribute("email").toString() : oauth2user.getAttribute("login").toString() + "@gmail.com";
            }
            return username;
        }else{
            return authentication.getName();
        }
    }

    public static String getLinkForEmailVerification(String emailToken){
        String link = "http://localhost:8080/auth/verify-email?token="+emailToken;
        return link;
    }
}
