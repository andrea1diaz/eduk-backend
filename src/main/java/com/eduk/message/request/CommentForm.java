package com.eduk.message.request;

import javax.validation.constraints.Size;


public class CommentForm {

    @Size(min = 2, max = 1000)
    private String commentContent;

    private String email;

    private int content;

    public String getCommentContent() {
        return this.commentContent;
    }

    public String getEmail() {
        return email;
    }

    public int getContent() {
        return content;
    }
}
