package com.eduk.controller;

import com.eduk.message.request.RateForm;
import com.eduk.message.response.RateResponse;
import com.eduk.model.*;
import com.eduk.repository.ContentRepository;
import com.eduk.repository.RateRepository;
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
@RequestMapping("api/rate")
public class RateController {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RateRepository rateRepository;

    @GetMapping("/{contentId}/{email}")
    public ResponseEntity<?> getRate(@PathVariable String contentId, @PathVariable String email) {
        Long id = Long.valueOf(contentId);

        Optional<Content> content = contentRepository.findById(id);
        Optional<User> user = userRepository.findByEmail(email);
        Boolean rate = rateRepository.existsByContentAndUser(content.get(), user.get());
        RateResponse response = new RateResponse();
        if (rate){
            Rate object = rateRepository.findByContentAndUser(content.get(), user.get());
            response.setRate(object.getRate());
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/post")
    public ResponseEntity<String> postRating(@Valid @RequestBody RateForm postRateRequest){
        Rate rate;
        User user = userRepository.findByEmail(postRateRequest.getEmail()).get();
        Optional<Content> content = contentRepository.findById((postRateRequest.getContent()));
        Boolean exist = rateRepository.existsByContentAndUser(content.get(), user);
        if(exist){
            rate = rateRepository.findByContentAndUser(content.get(), user);
            content.get().updateRating(rate.getRate(), postRateRequest.getRate());
            rate.setRate(postRateRequest.getRate());
        } else {
            rate = new Rate(postRateRequest.getRate());
            rate.setUser(user);
            rate.setContent(content.get());
            content.get().addRating(postRateRequest.getRate());
            user.increasePoints(1);
            userRepository.save(user);
        }
        contentRepository.save(content.get());
        rateRepository.save(rate);
        return ResponseEntity.ok().body("");
    }
}
