package com.eduk.message.response;

public class VoteResponse {
    public boolean voted;

    public boolean sided;

    public void VoteResponse(){
        this.voted = false;
        this.sided = false;
    }

    public void setSided(boolean sided) {
        this.sided = sided;
    }

    public void changeVoted() {
        this.voted = !this.voted;
    }

    public boolean getSided(){
        return this.sided;
    }

    public boolean getVoted(){
        return this.voted;
    }
}
