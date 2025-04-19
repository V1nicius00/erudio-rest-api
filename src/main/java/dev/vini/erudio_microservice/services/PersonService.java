package dev.vini.erudio_microservice.services;

import dev.vini.erudio_microservice.exceptions.ResourceNotFoundException;
import dev.vini.erudio_microservice.models.Person;
import dev.vini.erudio_microservice.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findById(Long id){
        logger.info("Finding person...");
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
    }

    public List<Person> findByGender(String gender){
        logger.info("Finding all " + gender +  " persons...");
        return personRepository.findByGenderIgnoreCase(gender);
    }

    public List<Person> findAllPeople() {
        return personRepository.findAll();
    }

    public Person create(Person person){
        logger.info("Creating person...");
        return personRepository.save(person);
    }

    public Person update(Person person){
        logger.info("Updating person...");
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return personRepository.save(person);
    }

    public void delete(Long id){
        logger.info("Deleting person...");
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
        personRepository.delete(entity);
    }

}
