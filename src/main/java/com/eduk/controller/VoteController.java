package com.eduk.controller;

import com.eduk.message.request.VoteForm;
import com.eduk.message.response.CommentsResponse;
import com.eduk.message.response.VoteResponse;
import com.eduk.model.Comment;
import com.eduk.model.Content;
import com.eduk.model.User;
import com.eduk.model.Vote;
import com.eduk.repository.ContentRepository;
import com.eduk.repository.VoteRepository;
import com.eduk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/votes")
public class VoteController {
    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/{contentId}/{email}")
    public ResponseEntity<?> getVote(@PathVariable String contentId, @PathVariable String email) {
        Long id = Long.valueOf(contentId);

        Optional<Content> content = contentRepository.findById(id);
        Optional<User> user = userRepository.findByEmail(email);
        Boolean vote = voteRepository.existsByContentAndUser(content.get(), user.get());
        VoteResponse response = new VoteResponse();
        if (vote){
            response.setVoted(true);
            Vote object = voteRepository.findByContentAndUser(content.get(), user.get());
            //if(object.getVote())
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.ok(false);
        }
    }


    @PostMapping("/post")
    public ResponseEntity<String> postComment(@Valid @RequestBody VoteForm postVoteRequest){
        Vote vote = new Vote(postVoteRequest.getVote());
        User user = userRepository.findByEmail(postVoteRequest.getEmail()).get();
        Optional<Content> content = contentRepository.findById((postVoteRequest.getContent()));
        vote.setUser(user);
        vote.setContent(content.get());
        if(postVoteRequest.getVote()) { content.get().upvote(); } else { content.get().downvote(); }
        user.increasePoints(1);
        userRepository.save(user);
        contentRepository.save(content.get());
        voteRepository.save(vote);
        return ResponseEntity.ok().body("");
    }
}
