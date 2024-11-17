package com.scm.smartContactManager.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.smartContactManager.entities.Contacts;
import com.scm.smartContactManager.entities.User;

@Repository
public interface ContactRepository extends JpaRepository<Contacts, String> {
    Page<Contacts> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Contacts c WHERE c.user.id = :userId")
    List<Contacts> findByUserId(@Param("userId") String userId);

    Page<Contacts> findByUserAndNameContaining(User user, String name, Pageable pageable);
    Page<Contacts> findByUserAndEmailContaining(User user, String email, Pageable pageable);
    Page<Contacts> findByUserAndPhoneNumberContaining(User user, String phone, Pageable pageable);
}
