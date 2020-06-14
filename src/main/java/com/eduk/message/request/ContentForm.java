package com.eduk.message.request;

import com.eduk.model.User;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class ContentForm {

    @NotBlank
    @Size(min = 3, max = 60)
    private String title;

    @Size(min = 5, max=2000)
    private String description;

    @NotBlank
    private String email;

    @Size(min = 6, max=40)
    private String Subject;

    private List<String> keywords;



    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getEmail() { return email; }
    public String getSubject() { return Subject; }
    public List<String> getKeywords() { return keywords;}

    @Min(1)
    @Max(6)
    private int year;

}