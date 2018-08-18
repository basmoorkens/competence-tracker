package com.basm.ct.controller;

import com.basm.ct.model.Competence;
import com.basm.ct.model.SchoolClass;
import com.basm.ct.repository.SchoolClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SchoolClassController {

    @Autowired
    private SchoolClassRepository schoolClassRepository;


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/classes", method =  RequestMethod.GET, produces = "application/json")
    public List<SchoolClass> getSchoolClasses() {
        List<SchoolClass> schoolClasses = new ArrayList<>();
        schoolClassRepository.findAll().forEach(schoolClasses::add);
        return schoolClasses;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/classes/save", method =  RequestMethod.POST, produces = "application/json")
    public SchoolClass saveSchoolClass(@Valid @RequestBody final SchoolClass schoolClass) {
        schoolClass.linkUnlinkedStudents();
       return schoolClassRepository.save(schoolClass);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/classes/{id}", method =  RequestMethod.GET, produces = "application/json")
    public SchoolClass getSchoolClass (@PathVariable("id") final Long schoolClassId){
        return schoolClassRepository.findById(schoolClassId).get();
    }
}
