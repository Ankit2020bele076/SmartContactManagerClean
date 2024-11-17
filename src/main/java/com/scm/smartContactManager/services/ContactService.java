package com.scm.smartContactManager.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scm.smartContactManager.entities.Contacts;
import com.scm.smartContactManager.entities.User;


public interface ContactService {
    Contacts save(Contacts contacts);

    Contacts update(Contacts contacts);

    List<Contacts> getAll();

    Contacts getById(String id);

    void delete(String id);

    Page<Contacts> searchByName(String name, int size, int page, String sortby, String direction, User user);
    Page<Contacts> searchByEmail(String email, int size, int page, String sortby, String direction, User user);
    Page<Contacts> searchByPhoneNumber(String phoneNumber, int size, int page, String sortby, String direction, User user);

    List<Contacts> getByUserId(String userId);

    Page<Contacts> getByUser(User user, int page, int size, String sortby, String direction);
}
