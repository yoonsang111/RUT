package com.stream.tour.domain.contact.facade;

import com.stream.tour.domain.contact.dto.ContactRequest;
import com.stream.tour.domain.contact.entity.Contact;
import com.stream.tour.domain.contact.service.ContactService;
import com.stream.tour.domain.contact.service.GoogleSheetApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
@Transactional
@RequiredArgsConstructor
public class ContactFacade {

    private final ContactService contactService;
    private final GoogleSheetApiService googleSheetApiService;

    public void createContact(ContactRequest request) throws GeneralSecurityException, IOException {
        contactService.createContact(Contact.fromDto(request));
        googleSheetApiService.appendContact(request);
    }
}
