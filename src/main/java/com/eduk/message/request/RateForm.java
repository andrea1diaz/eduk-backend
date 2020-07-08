package com.eduk.message.request;

import javax.validation.constraints.Size;


public class RateForm {

    private int rate;

    private String email;

    private int content;

    public int getRate() {
        return this.rate;
    }

    public String getEmail() {
        return email;
    }

    public int getContent() {
        return content;
    }
}
