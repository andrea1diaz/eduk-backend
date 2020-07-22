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
import com.sun.mail.iap.Response;
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
            response.changeVoted();
            Vote object = voteRepository.findByContentAndUser(content.get(), user.get());
            response.setSided(false);
            if(object.getVote()){
                response.setSided(true);
            }
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/post")
    public ResponseEntity<String> postVote(@Valid @RequestBody VoteForm postVoteRequest){
        Vote vote = new Vote(postVoteRequest.getVote());
        User user = userRepository.findByEmail(postVoteRequest.getEmail()).get();
        Optional<Content> content = contentRepository.findById((postVoteRequest.getContent()));
        vote.setUser(user);
        vote.setContent(content.get());
        if(postVoteRequest.getVote()) { content.get().upvoteChange(1); } else { content.get().downvoteChange(1); }
        user.increasePoints(1);
        userRepository.save(user);
        contentRepository.save(content.get());
        voteRepository.save(vote);
        return ResponseEntity.ok().body("");
    }


    @PatchMapping("/patch")
    public ResponseEntity<?> patchVote(@Valid @RequestBody VoteForm patchVoteRequest){
        Long id = Long.valueOf(patchVoteRequest.getContent());
        Optional<Content> content = contentRepository.findById(id);
        Optional<User> user = userRepository.findByEmail(patchVoteRequest.getEmail());
        Vote vote = voteRepository.findByContentAndUser(content.get(), user.get());
        vote.changeVote();
        if(vote.getVote()){
            content.get().upvoteChange(1); content.get().downvoteChange(-1);
        }
        else{
            content.get().upvoteChange(-1); content.get().downvoteChange(1);
        }
        voteRepository.save(vote);
        contentRepository.save(content.get());
        return ResponseEntity.ok("Changed");
    }

    @DeleteMapping("/delete/{contentId}/{email}")
    public ResponseEntity<?> deleteVote(@PathVariable String contentId, @PathVariable String email){
        Optional<Content> content = contentRepository.findById(Long.parseLong(contentId));
        Optional<User> user = userRepository.findByEmail(email);
        Vote vote = voteRepository.findByContentAndUser(content.get(), user.get());
        if(vote.getVote()){
            content.get().upvoteChange(-1);;
        }
        else{
            content.get().downvoteChange(-1);
        }
        voteRepository.delete(vote);
        contentRepository.save(content.get());
        return ResponseEntity.ok("Deleted");
    }

}
