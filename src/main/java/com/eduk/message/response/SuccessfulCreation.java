package com.eduk.message.response;

public class SuccessfulCreation {
    private Long id;

    private String message;

    public SuccessfulCreation(Long id, String type) {
        this.id = id;
        this.message = "Successfully created " + type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
