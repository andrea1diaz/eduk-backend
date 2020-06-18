package com.eduk.controller;

import com.eduk.model.Content;
import com.eduk.message.request.ContentForm;
import com.eduk.model.User;
import com.eduk.repository.ContentRepository;
import com.eduk.repository.UserRepository;
import com.eduk.security.utils.AuthenticationUtils;
import com.eduk.message.response.SuccessfulCreation;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.mail.iap.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ReflectionUtils;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/content")
public class ContentController {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{contentId}")
    @JsonView(Content.class)
    public ResponseEntity<?> getContent(@PathVariable Long contentId){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Optional<Content> content = contentRepository.findById(contentId);
        //System.out.println(content);
        return ResponseEntity.ok(content.orElse(null));
    }

    @PostMapping("/post")
    public ResponseEntity<String> postContent(@Valid @RequestBody ContentForm postContentRequest){
        Content content = new Content(postContentRequest.getTitle(),postContentRequest.getDescription(),postContentRequest.getSubject(),postContentRequest.getKeywords(),postContentRequest.getYear());
        User user = AuthenticationUtils.getUserObject(postContentRequest.get());
        content.setUser(user);
        contentRepository.save(content);
        
        Field field = ReflectionUtils.findField(Content.class, "id");
        ReflectionUtils.makeAccessible(field);
        Long contentId = (Long) ReflectionUtils.getField(field, content);
        return ResponseEntity.ok().body(new SuccessfulCreation(content, "Content"));
    }

}
