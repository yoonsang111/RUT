package com.stream.tour.domain.contact.repository;

import com.stream.tour.domain.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long>{
}
