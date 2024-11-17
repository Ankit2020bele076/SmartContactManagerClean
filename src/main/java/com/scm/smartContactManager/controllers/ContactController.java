package com.scm.smartContactManager.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.smartContactManager.entities.Contacts;
import com.scm.smartContactManager.entities.User;
import com.scm.smartContactManager.exception.AppConstants;
import com.scm.smartContactManager.exception.Helper;
import com.scm.smartContactManager.exception.Message;
import com.scm.smartContactManager.exception.MessageType;
import com.scm.smartContactManager.forms.ContactForm;
import com.scm.smartContactManager.forms.ContactSearchForm;
import com.scm.smartContactManager.services.ContactService;
import com.scm.smartContactManager.services.UserService;
import com.scm.smartContactManager.services.imageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private imageService ImageService;
 
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService; 

    @GetMapping(path = "/add")
    public String addContactView(Model model){
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "/user/add_contact";
    }

    @PostMapping(path = "/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult br,  Authentication authentication, HttpSession session){
        if(br.hasErrors()){
            session.setAttribute("message", Message.builder().content("Please correct the following errors").type(MessageType.red).build());
            return "user/add_contact";
        }
        Contacts contact = new Contacts();
        if(contactForm.getContactImage().isEmpty() == false){
            String filename = UUID.randomUUID().toString();
            String fileUrl = ImageService.uploadImage(contactForm.getContactImage(),filename);
            contact.setPicture(fileUrl);
            contact.setCloudinaryImagePublicId(filename);
        }
        
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setUser(user);
        

        contactService.save(contact);
        Message message = Message.builder().content("You have successfully added a new contact!").type(MessageType.blue).build();
        session.setAttribute("message", message);

        return "redirect:/user/contacts/add";
    }

    @GetMapping
    public String viewContacts(
        @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value="size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
        @RequestParam(value = "sortby", defaultValue = "name") String sortby, @RequestParam(value="direction",defaultValue = "asc") String direction,
        Model model, Authentication authentication){
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contacts> contacts = contactService.getByUser(user,page,size,sortby,direction);
        ContactSearchForm contactSearchForm = new ContactSearchForm();
        model.addAttribute("contactSearchForm", contactSearchForm); 
        model.addAttribute("contacts", contacts);
        return "user/contacts";
    }

    @GetMapping(path = "/search")
    public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value="size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
                                @RequestParam(value = "sortby", defaultValue = "name") String sortby,
                                @RequestParam(value="direction",defaultValue = "asc") String direction,
                                Model model, Authentication authentication){

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contacts> pageContact = null;
        if(contactSearchForm.getField().equalsIgnoreCase("name")){
            pageContact = contactService.searchByName(contactSearchForm.getKeyword(), size, page, sortby, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email")){
            pageContact = contactService.searchByEmail(contactSearchForm.getKeyword(), size, page, sortby, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("phone")){
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getKeyword(), size, page, sortby, direction, user);
        }
        model.addAttribute("contacts", pageContact);
        return "user/search";
    }

    @GetMapping(path="/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId){
        contactService.delete(contactId);
        return "redirect:/user/contacts";
    }
    
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable String contactId, Model model){
        Contacts contact = contactService.getById(contactId);

        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setDescription(contact.getDescription());
        contactForm.setAddress(contact.getAddress());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm",contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @PostMapping("/update/{contactId}")
    public String updateContact(@PathVariable String contactId,@Valid @ModelAttribute ContactForm contactForm, BindingResult br, Authentication authentication){
        if (contactForm.getContactImage().isEmpty()) {
            contactForm.setContactImage(null);
        }
        if(br.hasErrors()){
            return "user/update_contact_view";
        }
        Contacts  contacts = contactService.getById(contactId);
        if(contactForm.getContactImage() != null){
            String filename = UUID.randomUUID().toString();
            String fileUrl = ImageService.uploadImage(contactForm.getContactImage(), filename);
            contacts.setPicture(fileUrl);
            contacts.setCloudinaryImagePublicId(filename);
        }
        
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        contacts.setId(contactId);
        contacts.setName(contactForm.getName());
        contacts.setEmail(contactForm.getEmail());
        contacts.setAddress(contactForm.getAddress());
        contacts.setPhoneNumber(contactForm.getPhoneNumber());
        contacts.setDescription(contactForm.getDescription());
        contacts.setFavorite(contactForm.isFavorite());
        contacts.setWebsiteLink(contactForm.getWebsiteLink());
        contacts.setLinkedInLink(contactForm.getLinkedInLink());
        contacts.setUser(user);
        
        
        contactService.update(contacts);
        return "redirect:/user/contacts";
    }
}
