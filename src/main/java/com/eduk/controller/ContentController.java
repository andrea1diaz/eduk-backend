package com.eduk.controller;

import com.eduk.model.Content;
import com.eduk.message.request.ContentForm;
import com.eduk.model.User;
import com.eduk.repository.ContentRepository;
import com.eduk.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.mail.iap.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
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
        System.out.println("-----------------------------------------------");
        System.out.println("In-back");
        System.out.println("-----------------------------------------------");
        System.out.println(postContentRequest.getTitle());
        System.out.println(postContentRequest.getEmail());
        System.out.println(postContentRequest.getSubject());
        User author = userRepository.findByEmail(postContentRequest.getEmail()).get();
        System.out.println(author.getFirstName());
        System.out.println("***************************************************************");
        Content content = new Content(postContentRequest.getTitle(), postContentRequest.getDescription(), author, postContentRequest.getSubject(), postContentRequest.getKeywords(), Integer.parseInt((postContentRequest.getYear())));
        contentRepository.save(content);
        return ResponseEntity.ok().body("Content posted!");
    }

}
