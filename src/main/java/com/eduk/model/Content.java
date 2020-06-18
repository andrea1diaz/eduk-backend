package com.eduk.model;

import com.eduk.model.utils.TimestampedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contents")
public class Content extends TimestampedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 200)
    private String title;

    @NotBlank
    @Size(min = 1, max = 200)
    private String extension;

    @Size(min = 5, max=2000)
    private String description;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;


    @ElementCollection
    @CollectionTable(name = "keywords")
    private List<String> keywords = new ArrayList<String>();

    @Column(name = "score", nullable = false)
    @NotNull(message = "Score cannot be empty")
    @Range(min = 0)
    private Double score;

    @Min(1)
    @Max(6)
    private int year;

    public Content(String title, String description, User user, String subject, String keywords, int year){
        this.title = title;
        this.description = description;
        this.user = user;
        this.year = year;
        // this.subject = subject;
        // this.keywords = keywords;
        this.extension = "pdf";
        this.score = 0.0;

    }
}
