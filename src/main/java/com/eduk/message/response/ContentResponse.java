package com.eduk.message.response;

import com.eduk.model.Content;

public class ContentResponse {
    public Content content;

    public String user_name;
		
		public String profile_url;

    public ContentResponse(Content content, String username, String profile_url){
        this.content = content;
        this.user_name = username;
				this.profile_url = profile_url;
    }
}
