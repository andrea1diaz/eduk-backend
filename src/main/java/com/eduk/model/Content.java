package com.eduk.model;

import com.eduk.model.utils.TimestampedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

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

    @Column(name = "score")
    @Range(min = 0)
    private Double score;

    @Min(1)
    @Max(6)
    private int year;

    public Content() {
    }

    public Content(String title, String description, String subject, String keywords, int year) {
        this.title = title;
        this.description = description;
        this.year = year;
        // this.subject = subject;
        // this.keywords = keywords;
        this.score = 0.0;
        this.extension = "pdf";
    }

    public Content(Long id,String title, String description, User user, String subject, String keywords, int year){
        this.title = title;
        this.description = description;
        this.user = user;
        this.year = year;
        // this.subject = subject;
        // this.keywords = keywords;
        this.score = 0.0;
        this.extension = "pdf";
    }

    public Long getId() {
      return id;
    }

    public void setId(long id) {
      this.id = id;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription(String description) {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    // public String getSubject() {
    //   return subject;
    // }

    // public void setSubject(String subject) {
    //   this.subject = subject;
    // }

    // public String getKeywords() {
    //   return keywords;
    // }

    // public void setKeywords(String keywords) {
    //   this.keywords = keywords;
    // }

    public int getYear() {
      return year;
    }

    public void setYear(int year) {
      this.year = year;
    }
}
