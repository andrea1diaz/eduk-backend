package com.eduk.controller;

import com.eduk.model.Institution;
import com.eduk.repository.InstitutionRepository;
import com.eduk.repository.SubjectRepository;
import com.eduk.repository.UserRepository;
import com.eduk.security.utils.AuthenticationUtils;
import com.eduk.message.response.SuccessfulCreation;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.mail.iap.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ReflectionUtils;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/institutions")
public class InstitutionController {
  @Autowired
  InstitutionRepository institutionRepository;

  @GetMapping("/get")
  public ResponseEntity<?> getInstitutions() {
    List<Institution> response = institutionRepository.findAll();
    return ResponseEntity.ok(response);
  }
}
