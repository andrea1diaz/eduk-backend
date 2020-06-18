package com.eduk.model;

import org.hibernate.annotations.NaturalId;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class File {

    @NaturalId
    @NotBlank
    private String name;

    @NotBlank
    private MultipartFile binary;

    public File(String name, MultipartFile binary){
        this.name = name;
        this.binary = binary;
    }
}
