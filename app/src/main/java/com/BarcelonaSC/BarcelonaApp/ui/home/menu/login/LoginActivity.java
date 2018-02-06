package com.BarcelonaSC.BarcelonaApp.ui.home.menu.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.Factory;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.PeoplesAsync;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.google.api.services.people.v1.PeopleServiceScopes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Leonardojpr on 8/4/17.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, ResultCallback<People.LoadPeopleResult> {

    private final String TAG = LoginActivity.class.getSimpleName();

    public static final int CODE_ACCESS_DENIED = 1001;
    public static final int CODE_ACCESS_GRANTED = 1002;
    public static final int SIGN_UP_GOOGLE = 2001;
    public static final String SIGN_UP_FACEBOOK = "facebook";

    private GoogleApiClient mGoogleApiClient = null;
    private CallbackManager callbackManagerFacebook;
    public String phoneNumber = null;
    private String source = null;
    public GoogleSignInAccount acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_social);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new SocialLoginAsyncTask().execute();
    }

    private GraphRequest.GraphJSONObjectCallback facebookCallball = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            try {
                Log.d(TAG, "json facebook" + response.getJSONObject().toString());
                apiAuthCall(Factory.getUserFromFacebookData(response, phoneNumber));
            } catch (JSONException e) {
                e.printStackTrace();
                Intent intent = new Intent();
                intent.putExtra("erro_msj", Commons.getString(R.string.toast_login_social_error));
                setResult(CODE_ACCESS_DENIED, intent);

            }
        }
    };

    private void requestSuccessfullFacebook(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), facebookCallball);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,first_name,middle_name,last_name,gender,birthday,hometown");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void handleSignInResultGoogle(Intent data) {
        Intent msj_error = new Intent();
        GoogleSignInResult result = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        Log.d(TAG, "handlesSignInResultGoogle: " + result.getStatus());
        if (result != null && result.isSuccess()) {
            try {
                acct = result.getSignInAccount();
                new PeoplesAsync(this).execute(acct.getServerAuthCode());
            } catch (Exception e) {
                e.printStackTrace();
                msj_error.putExtra("erro_msj", Commons.getString(R.string.toast_login_social_error));
                setResult(CODE_ACCESS_DENIED, msj_error);
                finish();
            }
        } else {
            Log.d(TAG, result.getStatus().getStatusMessage() + " " + result.getStatus());
            msj_error.putExtra("erro_msj", Commons.getString(R.string.toast_login_social_error));
            setResult(CODE_ACCESS_DENIED, msj_error);
            finish();
        }
    }

    @Override
    public void onResult(@NonNull People.LoadPeopleResult peopleData) {
        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                Log.d(TAG, "onResult: " + count);
                for (Person uno : personBuffer) {
                    Log.d(TAG, "onResult : " + uno.getDisplayName());
                }
                // apiAuthCall(Factory.getUserFromGoogleData(acct, personBuffer.getPlayByPlay(0), phoneNumber));
            } finally {
                personBuffer.release();
            }
        } else {
            Log.e(TAG, "Error requesting people data: " + peopleData.getStatus());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SIGN_UP_GOOGLE:
                handleSignInResultGoogle(data);
                break;
            default:
                callbackManagerFacebook.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.d(TAG, "Error " + result.toString());
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    class SocialLoginAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            source = getIntent().getStringExtra("source");
            if (source.compareTo("facebook") == 0)
                facebookLogin();
            if (source.compareTo("google+") == 0)
                loginGoogle();

            return null;
        }


        void facebookLogin() {
            try {
                callbackManagerFacebook = CallbackManager.Factory.create();
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile", "user_friends", "email", "user_birthday",
                                "user_about_me", "user_hometown"));
                LoginManager.getInstance().registerCallback(callbackManagerFacebook,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                requestSuccessfullFacebook(loginResult);
                            }

                            @Override
                            public void onCancel() {
                                finish();
                                showToast("Auth Cancel");
                                finish();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                showToast(exception.getMessage());
                                if (exception instanceof FacebookAuthorizationException) {
                                    if (AccessToken.getCurrentAccessToken() != null) {
                                        LoginManager.getInstance().logOut();
                                    }
                                }
                                finish();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }
        }

        public void loginGoogle() {
            Log.d(TAG, "loginGoogle: " + Commons.getString(R.string.default_web_client_id));
            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(Commons.getString(R.string.default_web_client_id))
                    .requestServerAuthCode(Commons.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .requestScopes(new Scope(Scopes.PLUS_LOGIN),
                            new Scope(PeopleServiceScopes.CONTACTS_READONLY),
                            new Scope(PeopleServiceScopes.USER_EMAILS_READ),
                            new Scope(PeopleServiceScopes.USERINFO_EMAIL),
                            new Scope(PeopleServiceScopes.USER_PHONENUMBERS_READ))
                    .build();


            // To connect with Google Play Services and Sign In
            mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                    .enableAutoManage(LoginActivity.this, LoginActivity.this)
                    .addOnConnectionFailedListener(LoginActivity.this)
                    .addConnectionCallbacks(LoginActivity.this)
                    .addApi(com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API, signInOptions)
                    .build();

            Intent intent = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(intent, LoginActivity.SIGN_UP_GOOGLE);

        }
    }

    public void showToast(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }

    public void showToast(int msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void apiAuthCall(User user) {
        Intent intent = new Intent();
        intent.putExtra("user", user);
        setResult(CODE_ACCESS_GRANTED, intent);
        finish();
    }
}
