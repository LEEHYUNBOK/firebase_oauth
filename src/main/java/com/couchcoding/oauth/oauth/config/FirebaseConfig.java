package com.couchcoding.oauth.oauth.config;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        System.out.println("Initializing Firebase.");
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/firebase.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

		FirebaseApp.initializeApp(options);
		return FirebaseAuth.getInstance(FirebaseApp.getInstance());
    }
}
