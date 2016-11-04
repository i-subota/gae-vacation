package com.sample.vacation.client;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.sample.vacation.users.Users;
import com.sample.vacation.users.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Iryna Subota on 03/11/2016.
 */

public class OAuth2Sample {

    private static final String APPLICATION_NAME = "vacation";
    private static final int TEST_PORT = 8100;
    private static final String ENDPOINT_ROOT = "http://localhost:8080/_ah/api";

    private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".store/oauth2_sample");

    private static FileDataStoreFactory DATA_STORAGE_FACTORY;

    protected static HttpTransport HTTP_TRANSPORT;

    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Arrays.asList(
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/userinfo.email");

    private static GoogleClientSecrets clientSecrets;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORAGE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                    new InputStreamReader(OAuth2Sample.class.getResourceAsStream("/client_secret.json")));

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Credential credential = authorize("test");

        Oauth2 oauth2 = new Oauth2.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();

        tokenInfo(oauth2, credential.getAccessToken());
        userInfo(oauth2);

        User user = getUserById(5, credential);
        System.out.println(user.toPrettyString());
    }

    private static Credential loadCredential(String email) throws Exception {
        return newFlow().loadCredential(email);
    }

    private static Credential authorize(String email) throws Exception {
        LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(TEST_PORT).build();
        return new AuthorizationCodeInstalledApp(newFlow(), localServerReceiver).authorize(email);
    }

    private static GoogleAuthorizationCodeFlow newFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(
                DATA_STORAGE_FACTORY).build();
    }

    private static void tokenInfo(Oauth2 oauth2, String accessToken) throws IOException {
        Tokeninfo tokeninfo = oauth2.tokeninfo().setAccessToken(accessToken).execute();
        System.out.println(tokeninfo.toPrettyString());
        if (!tokeninfo.getAudience().equals(clientSecrets.getDetails().getClientId())) {
            System.err.println("ERROR: audience does not match our client ID!");
        }
    }

    public static void userInfo(Oauth2 oauth2) throws IOException {
        Userinfoplus userinfo = oauth2.userinfo().get().execute();
        System.out.println(userinfo.toPrettyString());
    }

    static User getUserById(long id, Credential credential) throws IOException {
        return new Users.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setRootUrl(ENDPOINT_ROOT)
                .setApplicationName(APPLICATION_NAME)
                .build()
                .getById(String.valueOf(id))
                .execute();
    }
}
