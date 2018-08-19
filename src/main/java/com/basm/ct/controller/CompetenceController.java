package com.basm.ct.controller;

import com.basm.ct.model.Competence;
import com.basm.ct.repository.CompetenceRepository;
import com.basm.ct.repository.SubCompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CompetenceController {

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private SubCompetenceRepository subCompetenceRepository;


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/competences", method =  RequestMethod.GET, produces = "application/json")
    public List<Competence> getCompetences() {
        List<Competence> competences = new ArrayList<>();
        competenceRepository.findAll().forEach(competences::add);
        return competences;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/competences/save", method =  RequestMethod.POST, produces = "application/json")
    public Competence saveCompetence(@Valid @RequestBody final Competence competence) {
        competence.linkUnlinkedSubCompetences();
        return competenceRepository.save(competence);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/competences/{id}", method =  RequestMethod.GET, produces = "application/json")
    public Competence getCompetence (@PathVariable("id") final Long competenceId){
        return competenceRepository.findById(competenceId).get();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/competences/delete/{id}", method =  RequestMethod.POST, produces = "application/json")
    public void deleteCompetence (@PathVariable("id") final Long competenceId){
         competenceRepository.deleteById(competenceId);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/subcompetences/delete/{id}", method =  RequestMethod.POST, produces = "application/json")
    public void deleteSubCompetence (@PathVariable("id") final Long competenceId){
        subCompetenceRepository.deleteById(competenceId);
    }

}
