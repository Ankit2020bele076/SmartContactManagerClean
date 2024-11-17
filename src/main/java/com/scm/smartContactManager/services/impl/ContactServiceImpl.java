package com.scm.smartContactManager.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.smartContactManager.entities.Contacts;
import com.scm.smartContactManager.entities.User;
import com.scm.smartContactManager.exception.ResourceNotFoundException;
import com.scm.smartContactManager.repositories.ContactRepository;
import com.scm.smartContactManager.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepo;

    @Override
    public Contacts save(Contacts contacts) {
        String contactId = UUID.randomUUID().toString();
        contacts.setId(contactId);
        return contactRepo.save(contacts);
    }

    @Override
    public Contacts update(Contacts contacts) {
        Contacts contactOld = contactRepo.findById(contacts.getId()).orElseThrow(() -> new ResourceNotFoundException("Contact Not Found"));
        contactOld.setName(contacts.getName());
        contactOld.setEmail(contacts.getEmail());
        contactOld.setPhoneNumber(contacts.getPhoneNumber());
        contactOld.setAddress(contacts.getAddress());
        contactOld.setPicture(contacts.getPicture());
        contactOld.setFavorite(contacts.isFavorite());
        contactOld.setDescription(contacts.getDescription());
        contactOld.setWebsiteLink(contacts.getWebsiteLink());
        contactOld.setLinkedInLink(contacts.getLinkedInLink());
        contactOld.setCloudinaryImagePublicId(contacts.getCloudinaryImagePublicId());
        // contactOld.setLinks(contacts.getLinks());
        return contactRepo.save(contactOld);
    }

    @Override
    public List<Contacts> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contacts getById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
    }

    @Override
    public void delete(String id) {
        contactRepo.delete(contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found")));
    }

    @Override
    public List<Contacts> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contacts> getByUser(User user, int page, int size, String sortby, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortby).descending() : Sort.by(sortby).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contacts> searchByName(String name, int size, int page, String sortby, String direction, User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortby).descending() : Sort.by(sortby).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContaining(user, name, pageable);
    }

    @Override
    public Page<Contacts> searchByEmail(String email, int size, int page, String sortby, String direction, User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortby).descending() : Sort.by(sortby).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user, email, pageable);
    }

    @Override
    public Page<Contacts> searchByPhoneNumber(String phoneNumber, int size, int page, String sortby, String direction, User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortby).descending() : Sort.by(sortby).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumber, pageable);
    }

}
