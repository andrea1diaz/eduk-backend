package com.eduk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "views")
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "content_id", referencedColumnName="id"),
            @JoinColumn(name = "subject_id", referencedColumnName="subject_id")
    })
    private Content content;

    public View() {}

    public View(User user, Content content) {
        this.user = user;
        this.content = content;
    }

}
