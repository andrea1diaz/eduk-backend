package com.eduk.message.request;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {
    private String name;

    private MultipartFile file;

    public String getName() { return name; }
    public MultipartFile getFile() { return file; }

}

