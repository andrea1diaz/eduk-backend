package com.eduk.message.response;

public class CommentsResponse {
    public String comment;

    public String user;

		public String photo_url;

    public CommentsResponse(String comment, String user, String photo_url) {
        this.comment = comment;
        this.user = user;
				this.photo_url = photo_url;
    }
}
