package com.BarcelonaSC.BarcelonaApp.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.commons.Factory;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.login.LoginActivity;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PeoplesAsync extends AsyncTask<String, Void, List<Person>> {

    private static final String TAG = PeoplesAsync.class.getSimpleName();
    private LoginActivity loginActivity;
    Person profile = null;

    public PeoplesAsync(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<Person> doInBackground(String... params) {

        List<String> nameList = new ArrayList<>();
        List<Person> connections = null;

        try {
            PeopleService peopleService = PeopleHelper.setUp(loginActivity, params[0]);
            profile = peopleService.people().get("people/me").execute();

            ListConnectionsResponse response = peopleService.people().connections()
                    .list("people/me")
                    .setRequestMaskIncludeField("person.emailAddresses,person.names,person.phoneNumbers,person.birthdays,person.genders")
                    .execute();
            //person.addresses, person.age_range, person.biographies, person.birthdays, person.bragging_rights, person.cover_photos, person.emailAddresses, person.events, person.genders, persona.im_clients, person.interests, person.locales, person.memberships, person.metadata, person.names, person.nicknames, person.occupations, person.organizations, person.phone_numbers, person.photos, person.relations, person.relationship_interests, person.relationship_statuses, person.residences, person.skills, person.taglines, person.urls

            connections = response.getConnections();

            for (Person person : connections) {
                if (!person.isEmpty()) {
                    List<Name> names = person.getNames();
                    List<EmailAddress> emailAddresses = person.getEmailAddresses();

                    if (emailAddresses != null)
                        for (EmailAddress emailAddress : emailAddresses)
                            Log.d(TAG, "email: " + emailAddress.getValue());

                    if (names != null)
                        for (Name name : names)
                            nameList.add(name.getDisplayName());

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return connections;
    }

    @Override
    protected void onPostExecute(List<Person> persons) {
        super.onPostExecute(persons);
        loginActivity.apiAuthCall(Factory.getUserFromGoogleData(loginActivity.acct, profile, loginActivity.phoneNumber));
    }
}