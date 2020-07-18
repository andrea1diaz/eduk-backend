package com.eduk.controller;

import com.eduk.message.request.VoteForm;
import com.eduk.message.response.ContentResponse;
import com.eduk.model.Content;
import com.eduk.message.request.ContentForm;
import com.eduk.model.Subject;
import com.eduk.model.User;
import com.eduk.model.Vote;
import com.eduk.repository.ContentRepository;
import com.eduk.repository.SubjectRepository;
import com.eduk.repository.UserRepository;
import com.eduk.security.utils.AuthenticationUtils;
import com.eduk.message.response.SuccessfulCreation;
import com.eduk.message.response.RequestMessages;

import com.fasterxml.jackson.annotation.JsonView;
import com.sun.mail.iap.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ReflectionUtils;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/content")
public class ContentController {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    AuthenticationUtils authenticationUtils;

    @GetMapping("")
    public ResponseEntity<?> getContentByKeywords (@RequestParam(required = false) Optional<List<String>> keywords) {
        Optional<List<Content>> contents;

        if (keywords.isPresent()) {
            if (keywords.get().isEmpty()) {
                return ResponseEntity.badRequest().body(RequestMessages.QUESTION_KEYWORD_EMPTY);
            }
            contents = contentRepository.getContentByKeywords(keywords.get());
        }
        else {
            contents = contentRepository.getContentsAll();
        }

        return ResponseEntity.ok().body(contents.orElse(List.of()));
    }

    @GetMapping("/user/{userEmail}")
    public ResponseEntity<?> getUserContent(@PathVariable String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        List<Content> contents = new ArrayList<Content>();

        if (user.isPresent()) {
            contents = contentRepository.findAllByUser(user.get());
            return ResponseEntity.ok().body(contents);
        }
        return ResponseEntity.ok().body(contents);
    }

    @GetMapping("/subject/{subjectName}")
    public ResponseEntity<?> getSubjectContent(@PathVariable String subjectName) {
        Optional<Subject> subject = subjectRepository.findByName(subjectName);
        List<Content> contents = new ArrayList<Content>();

        if (subject.isPresent()) {
            contents = contentRepository.findAllBySubject(subject.get());
            return ResponseEntity.ok().body(contents);
        }
        return ResponseEntity.ok().body(contents);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<?> getContent(@PathVariable String contentId) {
        Long id = Long.valueOf(contentId);
        Content content = contentRepository.findById(id).get();
        User user = content.getUser();
        content.increaseViews();
        content.calculateScore();
        contentRepository.save(content);
        ContentResponse response = new ContentResponse(content, user.getFirstName() + " " + user.getLastName(), user.getPhoto_url());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<?> getRecommendations() {
        Optional<List<Content>> contents = contentRepository.getContentsAll();

        return ResponseEntity.ok().body(contents.orElse(List.of()));
    }

    @PostMapping("/post")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> postContent(@Valid @RequestBody ContentForm postContentRequest) {
        String file_link = postContentRequest.getFile();
        String extension = file_link.substring(file_link.length() - 3, file_link.length());
        Optional<Subject> subject = subjectRepository.findByName(postContentRequest.getSubject());
        Content content = new Content(postContentRequest.getTitle(), postContentRequest.getDescription(), subject.get(), postContentRequest.getKeywords(), postContentRequest.getYear(), postContentRequest.getFile(), extension);
        User user = userRepository.findByEmail(postContentRequest.getEmail()).get();
        //User user = authenticationUtils.getUserObject();
        content.setUser(user);
        contentRepository.save(content);
        
       /* Field field = ReflectionUtils.findField(Content.class, "id");
        ReflectionUtils.makeAccessible(field);
        Long contentId = (Long) ReflectionUtils.getField(field, content);*/
        return ResponseEntity.ok().body(Long.toString(content.getId()));


    }

    @PatchMapping("/patch")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> patchVote(@Valid @RequestBody ContentForm patchContentRequest){
        Long id = Long.valueOf(patchContentRequest.getId());
        Optional<Content> content = contentRepository.findById(id);

        String title = patchContentRequest.getTitle();
        String description = patchContentRequest.getDescription();
        Optional<Subject> subject = subjectRepository.findByName(patchContentRequest.getSubject());
        List<String> keywords = patchContentRequest.getKeywords();
        int year = patchContentRequest.getYear();
        if (content.isPresent()) {
            Content content2 = content.get();
            content2.setTitle(title);
            content2.setDescription(description);
            if (subject.isPresent()){
                content2.setSubject(subject.get());
            }
            content2.setKeywords(keywords);
            content2.setYear(year);
        }
        contentRepository.save(content.get());
        return ResponseEntity.ok("Changed");
    }
}
