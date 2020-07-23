package com.eduk.controller;

import com.eduk.message.response.ContentResponse;
import com.eduk.model.Content;
import com.eduk.model.User;
import com.eduk.model.View;
import com.eduk.repository.ContentRepository;
import com.eduk.repository.UserRepository;
import com.eduk.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/view")
public class ViewController{

    @Autowired
    ViewRepository viewRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserRepository userRepository;

    @PatchMapping("/report/{contentId}/{userId}")
    public ResponseEntity<?> report(@PathVariable String contentId, @PathVariable String userId) {
        Long id = Long.valueOf(contentId);
        Long userid = Long.valueOf(userId);
        Content content = contentRepository.findById(id).get();
        User user = userRepository.findById(userid).get();
        Optional<View> viewOptional = viewRepository.findByContentAndUser(content, user);
        if(viewOptional.isPresent()) {
            View view = viewOptional.get();
            view.report();
            viewRepository.save(view);
        }
        return ResponseEntity.ok("Done");
    }
}
