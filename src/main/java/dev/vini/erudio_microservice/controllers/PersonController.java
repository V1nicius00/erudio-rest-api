package dev.vini.erudio_microservice.controllers;

import dev.vini.erudio_microservice.data.dto.PersonDTO;
import dev.vini.erudio_microservice.models.Person;
import dev.vini.erudio_microservice.services.PersonService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/id/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO findById(@PathVariable(value = "id") Long id){
        return personService.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonDTO> findAllPeople(@RequestParam(value = "gender") String gender){
        if(gender != null && !gender.isBlank()){
            return personService.findByGender(gender);
        }
        return personService.findAllPeople();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO create(@RequestBody PersonDTO person){
        return personService.create(person);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO update(@RequestBody PersonDTO person){
        return personService.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        personService.delete(id);
    }
}
