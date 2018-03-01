package com.BarcelonaSC.BarcelonaApp.commons;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.models.User;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Leonardojpr on 11/14/17.
 */

public class Factory {

    public static User getUserFromFacebookData(GraphResponse response, String numberPhone) throws JSONException {

        User user = new User();
        JSONObject object = response.getJSONObject();
        String url_pic = "https://graph.facebook.com/" + object.get("id") + "/picture?type=large";
        if (numberPhone != null)
            user.setCedular(numberPhone);
        user.setSource("facebook");
        user.setFoto_redes(url_pic);
        user.setUserID_facebook(object.getString("id"));
        Log.d(Factory.class.getSimpleName(), "getUserFromFacebookData: " + object.getString("last_name"));
        user.setApellido(object.getString("last_name"));
        user.setNombre(object.getString("first_name"));
        if (object.has("first_name"))
            //   user.setApellido(object.getString("first_name"));
            if (object.has("middle_name"))
                //user.setMiddle_name(object.getString("middle_name"));
                if (object.has("last_name"))
                    user.setNombre(object.getString("last_name"));
        if (object.has("gender"))
            user.setGenero(object.getString("gender"));
        if (object.has("email"))
            user.setEmail(object.getString("email"));
        if (object.has("link"))
            //    user.setLink(object.getString("link"));
            if (object.has("birthday")) {
                user.setFecha_nacimiento((String) object.get("birthday"));
            }
        return user;
    }

    /**
     * @param acct
     * @param person
     * @param numberPhone
     * @return
     */
    public static User getUserFromGoogleData(GoogleSignInAccount acct, com.google.api.services.people.v1.model.Person person, String numberPhone) {
        User user = new User();
        if (numberPhone != null)
            user.setCedular(numberPhone);
        user.setSource("google+");
        user.setEmail(acct.getEmail().toString());
        user.setUserID_google(acct.getId());
        user.setNombre(acct.getGivenName());
        user.setApellido(acct.getFamilyName());
        user.setFoto_redes(String.valueOf(acct.getPhotoUrl()));
        if (acct.getPhotoUrl() != null)
            user.setFoto(acct.getPhotoUrl().toString());
        if (person != null) {
            if (person.getNames() != null) {
                user.setApellido(person.getNames().get(0).getGivenName());
                user.setNombre(person.getNames().get(0).getFamilyName());
            }
            if (person.getGenders() != null)
                user.setGenero(person.getGenders().get(0).getValue());
            if (person.getBiographies() != null)
                //user.setAbout_me(person.getBiographies().getPlayByPlay(0).getValue());
                if (person.getUrls() != null)
                    //  user.setLink(person.getUrls().getPlayByPlay(0).getValue());
                    if (person.getBirthdays() != null)
                        user.setFecha_nacimiento(person.getBirthdays().get(0).getText());
            if (person.getPhotos() != null)
                user.setFoto(person.getPhotos().get(0).getUrl());

          /*  Person name = person.getNames().getPlayByPlay(0).getDisplayName();
            if (name.hasFamilyName())
                user.setLast_name(name.getFamilyName());
            if (name.hasGivenName())
                user.setFirst_name(name.getGivenName());
            if (name.hasMiddleName())
                user.setMiddle_name(name.getMiddleName()); */

        }
        return user;
    }
}
