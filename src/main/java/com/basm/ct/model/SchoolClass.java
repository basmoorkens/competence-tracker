package com.basm.ct.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String schoolYear;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolClass", cascade = CascadeType.ALL)
    private List<Student> students;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolClass", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;

    public void linkUnlinkedStudents() {
        if(students != null) {
            for (Student student : students) {
                student.setSchoolClass(this);
            }
        }
    }
}
