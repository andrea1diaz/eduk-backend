package com.eduk.message.response;

public class VoteResponse {
    private boolean voted;

    private boolean sided;

    public void VoteResponse(){
        voted = false;
        sided = false;
    }

    public void setSided(boolean sided) {
        this.sided = sided;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

}
