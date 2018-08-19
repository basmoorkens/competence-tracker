package com.basm.ct.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @NotBlank
    private String name;

    private Date evaluationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="school_class_id")
    @JsonIgnore
    private SchoolClass schoolClass;

    @ManyToMany
    @JoinTable(
            name = "evaluation_subcompetences",
            joinColumns = {@JoinColumn(name = "evaluation_id")},
            inverseJoinColumns = {@JoinColumn(name = "subcompetence_id")}
    )
    private List<SubCompetence> subCompetences;
}
