package com.personal.serviceImp;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

@Service
public class DriveManager {
	@Autowired
    ResourceLoader resourceLoader;

    public Drive getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(HTTP_TRANSPORT,
                JacksonFactory.getDefaultInstance(), googleCredential(HTTP_TRANSPORT))
                .build();
    }

    public GoogleCredential googleCredential(final NetHttpTransport HTTP_TRANSPORT) throws GeneralSecurityException, IOException {
    	
//        File file = ResourceUtils.getFile("classpath:finalproject-338303-2b7d9cd9ecb7.p12");
    	Resource sourceFile = new ClassPathResource("finalproject-338303-d157f051ba61.p12");
    	InputStream inputStream = sourceFile.getInputStream();
    	
    	
        Collection<String> elenco = new ArrayList<String>();
        elenco.add("https://www.googleapis.com/auth/drive");
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setServiceAccountId("875656899490-compute@developer.gserviceaccount.com")
                .setServiceAccountScopes(elenco)
                .setServiceAccountPrivateKeyFromP12File(inputStream)
                .build();
    }
	
}
