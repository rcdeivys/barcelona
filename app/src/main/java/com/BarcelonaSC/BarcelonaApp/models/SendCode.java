package com.BarcelonaSC.BarcelonaApp.models;

/**
 * Created by Leonardojpr on 11/13/17.
 */

public class SendCode {
    private String email;
    private String pin;

    public SendCode(String email, String pin) {
        this.email = email;
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
