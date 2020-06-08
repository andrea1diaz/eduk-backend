package com.eduk.message.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ForgotPasswordForm {
    @NotBlank
    @Size(max = 50, message = "Email length must be at most 50")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

