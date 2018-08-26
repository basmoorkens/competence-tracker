package com.basm.ct.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SubCompetence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name ="competence_id")
    @JsonIgnore
    private Competence competence;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Integer weight;

    public String getParentCompetenceName() {
        if(competence == null ) {
           return "";
        }
        return competence.getName();
    }

}
