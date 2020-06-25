package com.eduk.controller;

import com.eduk.message.request.CommentForm;
import com.eduk.model.Content;
import com.eduk.model.User;
import com.eduk.model.Comment;
import com.eduk.repository.CommentRepository;
import com.eduk.repository.ContentRepository;
import com.eduk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/comments")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContentRepository contentRepository;

    @PostMapping("/post")
    public ResponseEntity<String> postComment(@Valid @RequestBody CommentForm postCommentRequest){
        Comment comment = new Comment(postCommentRequest.getComentario());
        User user = userRepository.findByEmail(postCommentRequest.getEmail()).get();
        Optional<Content> content = contentRepository.findById(Long.valueOf((postCommentRequest.getContent_id())));
        comment.setUser(user);
        comment.setContent(content.get());
        System.out.println(comment.getComentario());
        System.out.println(comment.getUser().getFirstName());
        System.out.println(comment.getContent().getTitle());
//        commentRepository.saveAll(comment);
        return ResponseEntity.ok().body("");


    }
}
