package com.eduk.message.request;

import javax.validation.constraints.Size;


public class ContentForm {

    @Size(min = 3, max = 60)
    private String title;

    @Size(min = 5, max=2000)
    private String description;

    private String email;

    @Size(min = 3, max=40)
    private String subject;

    private String keywords;

    private int year;

    private String file;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getEmail() { return email; }
    public String getSubject() { return subject; }
    public String getKeywords() { return keywords;}
    public String getFile() {return file;}
    public int getYear() { return year;}


}