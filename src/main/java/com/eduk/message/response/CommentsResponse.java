package com.eduk.message.response;

public class CommentsResponse {
    public String comment;

    public String user;

    public CommentsResponse(String comment, String user){
        this.comment = comment;
        this.user = user;
    }
}
