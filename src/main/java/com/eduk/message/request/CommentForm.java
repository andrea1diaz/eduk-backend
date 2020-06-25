package com.eduk.message.request;

import javax.validation.constraints.Size;

public class CommentForm {

    @Size(min = 2, max = 1000)
    private String comentario;

    private String email;

    private String content_id;

    public String getComentario() {
        return this.comentario;
    }

    public String getEmail() {
        return email;
    }

    public String getContent_id() {
        return content_id;
    }
}
