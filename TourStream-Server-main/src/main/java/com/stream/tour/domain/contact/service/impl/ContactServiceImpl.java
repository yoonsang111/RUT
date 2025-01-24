package com.stream.tour.domain.contact.service.impl;

import com.stream.tour.domain.contact.dto.ContactRequest;
import com.stream.tour.domain.contact.entity.Contact;
import com.stream.tour.domain.contact.repository.ContactRepository;
import com.stream.tour.domain.contact.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public void createContact(Contact contact) {
        contactRepository.save(contact);
    }
}
