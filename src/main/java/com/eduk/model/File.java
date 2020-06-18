package com.eduk.model;

import com.eduk.model.utils.TimestampedEntity;
import org.hibernate.annotations.NaturalId;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "files", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @NotBlank
    private String name;

    @NotBlank
    private String bin;

    public File(String name, String binary){
        this.name = name;
        this.bin = binary;
    }
}
