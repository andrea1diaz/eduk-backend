package com.eduk.message.response;

import com.eduk.model.Content;

public class ContentResponse {
    public Content content;

    public String user_name;
		
    public ContentResponse(Content content, String username) {
        this.content = content;
        this.user_name = username;
    }
}
