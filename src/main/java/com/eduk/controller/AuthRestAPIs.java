package com.eduk.controller;

import com.eduk.message.request.LoginForm;
import com.eduk.message.request.RegisterForm;
import com.eduk.message.response.JwtResponse;

import com.eduk.model.*;
import com.eduk.repository.InstitutionRepository;
import com.eduk.repository.RoleRepository;
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

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;

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
    RoleRepository roleRepository;

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

        Set<String> strRoles = registerRequest.getRole();
        System.out.println(strRoles);
        Set<Role> roles = new HashSet<Role>();

        strRoles.forEach(role -> {
            switch (role) {
                case "ROLE_TEACHER":
                    Role teacherRole = roleRepository.findByName(RoleName.ROLE_TEACHER)
                            .orElseThrow(() -> new RuntimeException("Error: User Role not found"));
                    roles.add(teacherRole);

                    user.setRoles(roles);
                    userRepository.save(user);

                    Teacher teacher = new Teacher();
                    User t_user = userRepository.findByEmail(registerRequest.getEmail()).get();
                    Institution t_institution = institutionRepository.findById(Long.parseLong(registerRequest.getInstitution())).get();
                    teacher.setUser(t_user);
                    teacher.setInstitution(t_institution);
                    teacherRepository.save(teacher);

                    break;
                case "ROLE_STUDENT":
                    Role studentRole = roleRepository.findByName(RoleName.ROLE_STUDENT)
                            .orElseThrow(() -> new RuntimeException("Error: User Role not found"));
                    roles.add(studentRole);

                    user.setRoles(roles);
                    userRepository.save(user);

                    break;
            }
        });

        return ResponseEntity.ok().body("User registered successfully!");
    }

}



