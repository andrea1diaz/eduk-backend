package com.eduk.model;


import com.eduk.model.utils.TimestampedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "likes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean vote;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public Vote(boolean vote){
        this.vote = vote;
    }
    public Vote(){}

    public boolean getVote() {
        return vote;
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

    public void setVote(boolean vote) {
        this.vote = vote;
    }
}
