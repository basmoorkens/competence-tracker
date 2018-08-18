package com.basm.ct.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @NotBlank
    private String name;

    @NotBlank
    private String firstName;

    @ManyToOne
    @JoinColumn(name ="class_id")
    @JsonIgnore
    private SchoolClass schoolClass;
}
