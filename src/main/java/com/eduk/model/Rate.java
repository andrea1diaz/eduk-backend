package com.eduk.model;


import com.eduk.model.utils.TimestampedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rating")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public Rate(Integer rate){
        this.rate = rate;
    }
    public Rate(){}

    public Integer getRate() {
        return rate;
    }

    public Content getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
