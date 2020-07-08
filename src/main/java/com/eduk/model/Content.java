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

    private String file;

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

    private int views;

    private double upvotes;

    private double downvotes;

    private double rating;

    private int nrating;

    public Content() {
    }

    public Content(String title, String description, Subject subject, List<String> keywords, int year, String file, String extension) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.subject = subject;
        this.keywords = keywords;
        this.score = 0.0;
        this.views = 0;
        this.extension = extension;
        this.file = file;
        upvotes = 0.0;
        downvotes = 0.0;
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

    public String getDescription() {
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

    public void upvoteChange(float number){ ;this.upvotes += number; }

    public void downvoteChange(float number){ this.downvotes += number; }

    public void setUpvotes(double upvotes) {
        this.upvotes = upvotes;
    }

    public void setDownvotes(double downvotes) {
        this.downvotes = downvotes;
    }

    public double getDownvotes() {
        return downvotes;
    }

    public double getUpvotes() {
        return upvotes;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void addRating(double rating) {
        this.rating = this.rating*this.nrating + rating;
        this.nrating += 1;
        this.rating /= this.nrating;
    }

    public void updateRating(double rating, double newrating) {
        this.rating = this.rating*this.nrating - rating + newrating;
        this.rating /= this.nrating;
    }

    public double getRating() {
        return rating;
    }

    public void calculateScore(){
        double total = this.upvotes + this.downvotes;
        if (total != 0) {this.score = 5*this.upvotes/(this.upvotes + this.downvotes); }
    }

    public Double getScore(){
        return this.score;
    }

     public Subject getSubject() {
       return this.subject;
     }

     public void setSubject(Subject subject) {
       this.subject = subject;
     }

     public List<String> getKeywords() {
       return keywords;
    }

    public void setKeywords(List<String> keywords) {
			this.keywords = keywords;
    }

    public int getYear() {
      return year;
    }

    public void setYear(int year) {
      this.year = year;
    }

    public String getFile() { return this.file; }

    public String getExtension() { return this.extension; }

    public void setViews(int views) { this.views = views; }

    public int getViews() { return views; }

    public void increaseViews(){ this.views = this.views + 1; }

}
