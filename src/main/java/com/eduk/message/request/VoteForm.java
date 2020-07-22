package com.eduk.message.request;

import javax.validation.constraints.Size;


public class VoteForm {

    private boolean vote;

    private String email;

    private int content;

    public boolean getVote() {
        return this.vote;
    }

    public String getEmail() {
        return email;
    }

    public int getContent() {
        return content;
    }
}
