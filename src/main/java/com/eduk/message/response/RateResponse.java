package com.eduk.message.response;

public class RateResponse {
    private int rated;

    public void RateResponse(){
        this.rated = 3;
    }

    public void setRate(int rated) {
        this.rated = rated;
    }
    public int getRate() { return rated;}
}
