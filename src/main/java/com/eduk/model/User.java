package com.eduk.model;

import com.eduk.model.utils.TimestampedEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class User extends TimestampedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "points", nullable = false)
    @NotNull(message = "Points cannot be empty")
    @Range(min = 0)
    private Integer points;

    @NotBlank
    @Size(min = 1, max = 50, message = "First name length must be between 1 and 50")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 50, message = "Last name length must be between 1 and 50")
    private String lastName;

    @NaturalId
    @NotBlank
    @Size(max = 50, message = "Email length must be at most 50")
    @Email
    private String email;

    @NotBlank
    @JsonIgnore
    @Size(min = 6, max = 100, message = "Password length must be between 6 and 100")
    private String password;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName rolename;

    private String photo_url;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.points = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleName getRole(){
        return this.rolename;
    }

    public String getRoleName() {
        if (this.rolename == RoleName.ROLE_TEACHER) {
            return "ROLE_TEACHER";
        }
        else {
            return "ROLE_STUDENT";
        }
    }

    public void setRole(RoleName rolename) {
        this.rolename = rolename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void increasePoints(int points){ this.points += 1; }
    public long getInstitution() {
        return institution.getId();
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getPhoto_url() { return photo_url; }

    public void setPhoto_url(String photo_url) { this.photo_url = photo_url; }

}
