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
    private User author;

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

    public Content() {
    }

    public Content(Long id,String title, String description, User author, String subject, String keywords, int year){
        this.title = title;
        this.description = description;
        this.author = author;
        this.year = year;
        // this.subject = subject;
        this.keywords = keywords;
        this.extension = "pdf";
        this.score = 0.0;

    }

    public String getId() {
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

    public String getDescription(description) {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public User getAuthor() {
      return author;
    }

    public void setAuthor(User author) {
      this.author = author;
    }

    // public String getSubject() {
    //   return subject;
    // }

    // public void setSubject(String subject) {
    //   this.subject = subject;
    // }

    public String getKeywords() {
      return keywords;
    }

    public void setKeywords(String keywords) {
      this.keywords = keywords;
    }

    public int getYear() {
      return year;
    }

    public void setYear(int year) {
      this.year = year;
    }
}
