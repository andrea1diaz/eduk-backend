package com.eduk.model;

import com.eduk.model.utils.TimestampedEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "subjects")
@JsonIgnoreProperties(allowGetters = true)
public class Subject{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 60, message = "Subject name length must be between 1 and 60")
    private String name;

    @OneToMany(mappedBy = "subject")
    private Set<Content> contents;

    public Subject() {
    }

    public String getName() {
        return name;
    }
}
