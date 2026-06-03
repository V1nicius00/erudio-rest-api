package dev.vini.erudio_microservice.services;

import dev.vini.erudio_microservice.controllers.PersonController;
import dev.vini.erudio_microservice.data.dto.PersonDTO;
import dev.vini.erudio_microservice.exceptions.ResourceNotFoundException;
import static dev.vini.erudio_microservice.mapper.ObjectMapper.parseObject;
import static dev.vini.erudio_microservice.mapper.ObjectMapper.parseListObjects;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import dev.vini.erudio_microservice.models.Person;
import dev.vini.erudio_microservice.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDTO findById(Long id){
        logger.info("Finding person...");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLink(dto);
        return dto;
    }

    public List<PersonDTO> findByGender(String gender){
        logger.info("Finding all " + gender +  " persons...");
        var peopleList = parseListObjects(personRepository.findByGenderIgnoreCase(gender), PersonDTO.class);
        peopleList.forEach(this::addHateoasLink);
        return peopleList;
    }

    public List<PersonDTO> findAllPeople() {
        var peopleList = parseListObjects(personRepository.findAll(), PersonDTO.class);
        peopleList.forEach(this::addHateoasLink);
        return peopleList;
    }

    public PersonDTO create(PersonDTO personDto){
        logger.info("Creating person...");
        var entity = parseObject(personDto, Person.class);
        var dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLink(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO personDto){
        logger.info("Updating person...");
        Person entity = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
        entity.setFirstName(personDto.getFirstName());
        entity.setLastName(personDto.getLastName());
        entity.setAddress(personDto.getAddress());
        entity.setGender(personDto.getGender());
        var dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLink(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting person...");
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
        personRepository.delete(entity);
    }

    private void addHateoasLink(PersonDTO dto){
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAllPeople(null)).withRel("findAllPeople").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
