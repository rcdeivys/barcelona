package com.BarcelonaSC.BarcelonaApp.models;

/**
 * Created by Leonardojpr on 12/4/17.
 */

public class ValidateAccItem {

    private String email;
    private String pin;

    public ValidateAccItem() {
    }

    public ValidateAccItem(String email, String pin) {
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
