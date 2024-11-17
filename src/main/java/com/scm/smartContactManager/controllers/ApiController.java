package com.scm.smartContactManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.smartContactManager.entities.Contacts;
import com.scm.smartContactManager.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    @GetMapping(path = "/contact/{contactId}")
    public Contacts getContact(@PathVariable String contactId){
        return contactService.getById(contactId);
    }
}
