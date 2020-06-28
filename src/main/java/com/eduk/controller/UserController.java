package com.eduk.controller;

import com.eduk.message.request.UpdateUserForm;
import com.eduk.model.User;
import com.eduk.repository.UserRepository;

import com.eduk.security.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    AuthenticationUtils authenticationUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("")
    public ResponseEntity<?> userInfo() {

        User user = authenticationUtils.getUserObject();
        System.out.println("------------------------------");

        System.out.println(user.getPhoto_url());

        System.out.println("------------------------------");

        return ResponseEntity.ok(user);
    }

    @PatchMapping("")
    public ResponseEntity<?> partialUpdate(@Valid @RequestBody UpdateUserForm fields) {
        User user = authenticationUtils.getUserObject();

        Optional<String> firstName = fields.getFirstName();
        Optional<String> lastName = fields.getLastName();
        Optional<String> email = fields.getEmail();
        Optional<String> photo = fields.getPhoto_url();

        Optional<String> password = fields.getPassword();
        Optional<Integer> points = fields.getPoints();

        if (firstName.isPresent()) {
            user.setFirstName(firstName.get());
        }
        if (lastName.isPresent()) {
            user.setLastName(lastName.get());
        }
        if (email.isPresent()) {
            user.setEmail(email.get());
        }
        if (photo.isPresent()) {
            user.setPhoto_url(photo.get());
        }
        if (password.isPresent()) {
            user.setPassword(encoder.encode(password.get()));
        }
        if (points.isPresent()) {
            user.setPoints(points.get());
        }

        userRepository.save(user);

        return ResponseEntity.ok().body("Successfully updated the user.");
    }

}
