package com.basm.ct.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @NotBlank
    @Column(unique = true)
    private String name;

    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCompetence> subCompetences;

    public void addSubCompetence(final SubCompetence subCompetence) {
        if(subCompetences == null) {
            subCompetences = new ArrayList<>();
        }
        subCompetences.add(subCompetence);
    }

    public void linkUnlinkedSubCompetences() {
        if(subCompetences != null){
            for(SubCompetence sub : subCompetences) {
                sub.setCompetence(this);
            }
        }
    }


}
