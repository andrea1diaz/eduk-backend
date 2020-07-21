package com.eduk.controller;

import com.eduk.message.request.VoteForm;
import com.eduk.message.response.ContentResponse;
import com.eduk.message.response.StatsResponse;
import com.eduk.model.*;
import com.eduk.message.request.ContentForm;
import com.eduk.repository.ContentRepository;
import com.eduk.repository.SubjectRepository;
import com.eduk.repository.CommentRepository;
import com.eduk.repository.UserRepository;
import com.eduk.repository.ViewRepository;
import com.eduk.repository.VoteRepository;
import com.eduk.repository.RateRepository;
import com.eduk.security.utils.AuthenticationUtils;
import com.eduk.message.response.SuccessfulCreation;
import com.eduk.message.response.RequestMessages;

import com.fasterxml.jackson.annotation.JsonView;
import com.sun.mail.iap.Response;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.support.NullValue;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ReflectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.Null;
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
    ViewRepository viewRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    RateRepository rateRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    CommentRepository commentRepository;

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

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<?> getSubjectContent(@PathVariable Long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);

        if (subject.isPresent()) {
            Optional<List<Content>> contents = contentRepository.getBySubject(subjectId);

            return ResponseEntity.ok().body(contents);
        }
        return ResponseEntity.ok().body("Done");
    }

    @GetMapping("/{contentId}/{userId}")
    public ResponseEntity<?> getContent(@PathVariable String contentId, @PathVariable String userId) {
        Long id = Long.valueOf(contentId);
        Long userid = Long.valueOf(userId);
        Content content = contentRepository.findById(id).get();
        User user = content.getUser();
        User user_view = userRepository.findById(userid).get();
        if(!viewRepository.existsByContentAndUser(content,user_view)) {
            View view = new View(user_view,content);
            content.increaseViews();
            contentRepository.save(content);
            content.calculateScore();
            viewRepository.save(view);
        }
        ContentResponse response = new ContentResponse(content, user.getFirstName() + " " + user.getLastName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<?> getRecommendations() {
        Optional<List<Content>> contents = contentRepository.getContentsAll();

        return ResponseEntity.ok().body(contents.orElse(List.of()));
    }

    @GetMapping("/stats/{userId}")
    public ResponseEntity<?> getStats(@PathVariable String userId) {
        Long id = Long.valueOf(userId);
        System.out.println("El id es: " + id);
        Optional<List<?>> views = viewRepository.getSubjectViews(id);
        List<?> gviews = new ArrayList<List>();
        if(!views.isEmpty()){
            gviews = views.get();
        }
        System.out.println("Los views son: " + views);
        Long totalViews = viewRepository.getTotalViews(id);
        Long totalComments = commentRepository.getTotalComments(id);
        Long totalVotes = voteRepository.getTotalVotes(id);
        Long totalContents = contentRepository.getTotalContents(id);
        Optional<Double> avg = rateRepository.getAvgRating(id);
        Double avgRating = 0.0;
        if(!avg.isEmpty()){
            avgRating = avg.get();
        }
        Optional<String> fav = viewRepository.getFavSubject(id);
        String favSubject = "-";
        if(!fav.isEmpty()){
            favSubject = fav.get();
        }
        StatsResponse response = new StatsResponse(gviews, totalViews, totalComments, totalVotes,
                totalContents, avgRating, favSubject);

        return ResponseEntity.ok().body(response);
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
	
		@Transactional
    @DeleteMapping("/delete/{contentId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> deleteContent(@PathVariable String contentId){
        Long id = Long.valueOf(contentId);
        Optional<Content> content = contentRepository.findById(id);
        if(content.isPresent()) {
            Content cont = content.get();
            long comments = commentRepository.deleteByContent(cont);
            long rate = rateRepository.deleteByContent(cont);
            viewRepository.deletebyContent(id);
//            List<View> views = viewRepository.findAllByContent(cont);
//            for (int i = 0; i < views.size(); i++) {
//                viewRepository.delete(views.get(i));
//            }
            long vote = voteRepository.deleteByContent(cont);
            contentRepository.deleteById(id);
        }
        return ResponseEntity.ok("Deleted");
    }
}
