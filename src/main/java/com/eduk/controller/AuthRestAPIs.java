package com.eduk.controller;

import com.eduk.message.request.LoginForm;
import com.eduk.message.request.RegisterForm;
import com.eduk.message.response.JwtResponse;

import com.eduk.model.*;
import com.eduk.repository.InstitutionRepository;
import com.eduk.repository.UserRepository;
import com.eduk.repository.TeacherRepository;
import com.eduk.security.jwt.JwtToken;
import com.eduk.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    private static final Logger log = LoggerFactory.getLogger(AuthRestAPIs.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    InstitutionRepository institutionRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        final String token = jwtToken.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterForm registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<String>("Email is already in use", HttpStatus.BAD_REQUEST);
        }

        // Creating user account
        User user = new User(registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()));


        Institution institution = institutionRepository.findByName(registerRequest.getInstitution()).get();
        user.setInstitution(institution);

        String strRoles = registerRequest.getRole();
        System.out.println(strRoles);

            if (strRoles.equals("ROLE_TEACHER")) {

                user.setRole(RoleName.ROLE_TEACHER);
                /*Teacher teacher = new Teacher();
                User t_user = userRepository.findByEmail(registerRequest.getEmail()).get();
                teacher.setUser(t_user);
                teacher.setInstitution(t_institution);
                teacherRepository.save(teacher);*/
            } else {
                user.setRole(RoleName.ROLE_STUDENT);
            }
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }

}

