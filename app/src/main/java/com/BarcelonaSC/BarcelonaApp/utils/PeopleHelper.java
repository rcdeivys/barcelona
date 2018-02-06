package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.R;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Suleiman19 on 4/4/16.
 */
public class PeopleHelper {

    private static final String TAG = PeopleHelper.class.getSimpleName();

    private static final String APPLICATION_NAME = "milloariosfc";

    private static final List<String> SCOPES =
            Arrays.asList(PeopleServiceScopes.CONTACTS_READONLY);

    public static PeopleService setUp(Context context, String serverAuthCode) throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        // Redirect URL for web based applications.
        // Can be empty too.
        String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
        String scope = "https://www.googleapis.com/auth/contacts.readonly";


        String authorizationUrl = new GoogleBrowserClientRequestUrl(context.getString(R.string.client),
                redirectUrl,
                Arrays.asList(scope))
                .build();

        Log.d(TAG, "authorizationUrl: " + authorizationUrl);
        // Exchange auth code for access token
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                httpTransport,
                jsonFactory,
                context.getString(R.string.client),
                context.getString(R.string.clientSecret),
                serverAuthCode,
                redirectUrl
        )
                .execute();

        // Then, create a GoogleCredential object using the tokens from GoogleTokenResponse
        GoogleCredential credential = new GoogleCredential.Builder()
                .setClientSecrets(context.getString(R.string.client), context.getString(R.string.clientSecret))
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .build();

        credential.setFromTokenResponse(tokenResponse);

        // credential can then be used to access Google services
        return new PeopleService.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

}