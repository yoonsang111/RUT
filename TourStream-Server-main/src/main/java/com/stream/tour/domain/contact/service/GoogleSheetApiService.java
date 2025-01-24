package com.stream.tour.domain.contact.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.stream.tour.domain.contact.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetApiService {
    // google sheets
    @Value("${google.sheet.application-name}")
    private String APPLICATION_NAME;

    @Value("${google.sheet.id}")
    private String SHEET_ID;

    @Value("${google.sheet.name}")
    private String SHEET_NAME;

    @Value("${google.sheet.credentials}")
    private static String CREDENTIALS_FILE_PATH;
//    private static String CREDENTIALS_FILE_PATH;

    @Value("${google.sheet.credentials}")
    private String credentialsFilePath;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        ClassPathResource resource = new ClassPathResource(credentialsFilePath);
        GoogleCredential credential = GoogleCredential.fromStream(resource.getInputStream())
                .createScoped(SCOPES);

        return credential;
    }

    public void appendContact(ContactRequest request) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        List<List<Object>> values = Arrays.asList(
                Arrays.asList(request.getCompanyName(),
                        request.getRepresentativeName(),
                        request.getRequesterName(),
                        request.getPhoneNumber(),
                        request.getEmail(),
                        request.getContent()));
        ValueRange data = new ValueRange().setValues(values);

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        service.spreadsheets().values().append(SHEET_ID, SHEET_NAME, data)
                .setValueInputOption("USER_ENTERED")
                .execute();
    }
}
