package com.eduk.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
public class UploadFileController {
    public String upload(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);
        return "fileUploadView";
    }
}
