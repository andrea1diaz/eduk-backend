package com.eduk.controller;

import com.eduk.model.File;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
public class UploadFileController {
    public ResponseEntity<String> upload(@RequestParam String name, @RequestParam("file") MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);
        File new_file = new File(name, file);
        return ResponseEntity.ok().body("File created successfully!");
    }
}
