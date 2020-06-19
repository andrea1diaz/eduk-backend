package com.eduk.controller;
import com.eduk.message.request.FileForm;
import com.eduk.model.File;
import com.eduk.repository.ContentRepository;
import com.eduk.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/file", consumes = {"multipart/form-data"})
public class FileController {
    @Autowired
    FileRepository fileRepository;

    @PostMapping(value = "/uploadFile")
    public ResponseEntity<String> upload(@ModelAttribute FileForm postFileRequest) throws IOException {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(postFileRequest);
        System.out.println(postFileRequest.getFile());
        //File new_file = new File(postFileRequest.getName(), postFileRequest.getFile().getBytes());
        //fileRepository.save(new_file);
        return ResponseEntity.ok().body("File created successfully!");
    }
}
