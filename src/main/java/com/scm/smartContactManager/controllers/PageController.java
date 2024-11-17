package com.scm.smartContactManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.scm.smartContactManager.entities.User;
import com.scm.smartContactManager.exception.Message;
import com.scm.smartContactManager.exception.MessageType;
import com.scm.smartContactManager.forms.UserForms;
import com.scm.smartContactManager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index(){
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("name","Substring technologies");
		model.addAttribute("youtube","learn code");
		model.addAttribute("githubRepo", "");
		return "home";
	}
	
	//about route
	@GetMapping(path = "/about")
	public String aboutPage() {
		return "about";
	}
	
	//services route
	@GetMapping(path = "/services")
	public String servicesPage() {
		return "services";
	}

	@PostMapping(path = "/login")
	public String login(){
		return "login";
	}

	@GetMapping(path = "/login")
	public String login1(){
		return "login";
	}


	@GetMapping(path = "/contact")
	public String contact(){
		return "contact";
	}

	@GetMapping(path = "/register")
	public String register(Model model){
		UserForms userForm = new UserForms();
		model.addAttribute("userForm", userForm);
		return "register";
	}

	// @GetMapping(path = "/signUpGoogle")
	// public String googleRegister(Model model, HttpServletRequest request){
	// 	// model.addAttribute("Email", email);
	// 	UserGForms userForm = new UserGForms();
	// 	model.addAttribute("userForm", userForm);

	// 	return "signUpGoogle";
	// }

	// @PostMapping(path = "/do-googlesignup")
	// public String processGRegister(@Valid @ModelAttribute("userForm") UserGForms userForm, BindingResult br, HttpSession session, HttpServletRequest request){
	// 	if(br.hasErrors()){
	// 		return "signUpGoogle";
	// 	}
	// 	String email = (String) request.getSession().getAttribute("email");
	// 	Message message = Message.builder().content("Registration successfull!").type(MessageType.green).build();
	// 	session.setAttribute("message", message);
	// 	User usern = userRepo.findByEmail(email).orElse(null);

	// 	usern.setPassword(userForm.getPassword());
	// 	usern.setAbout(userForm.getAbout());
	// 	usern.setPhoneNumber(userForm.getPhoneNumber());
	// 	userRepo.save(usern);
	// 	return "signUpSuccess";
	// }
	

	//processing registeration
	@PostMapping(path="/do-register")
	public String processRegister(@Valid @ModelAttribute("userForm") UserForms userForm, BindingResult br, HttpSession session){
		
		if(br.hasErrors()){
			return "register";
		}
		User user = new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setAbout(userForm.getAbout());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setEnabled(false);
		user.setProfilePic("https://images.app.goo.gl/98oEjySxvCCwqcBA7");

		Message message = Message.builder().content("Registration successfull!").type(MessageType.green).build();
		session.setAttribute("message", message);

		User savedUser = userService.saveUser(user);

		
		// return "signUpSuccess";
		return "signUpSuccess";
	}
}
