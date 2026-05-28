package dev.vini.erudio_microservice.services;

import dev.vini.erudio_microservice.data.dto.PersonDTO;
import dev.vini.erudio_microservice.exceptions.ResourceNotFoundException;
import static dev.vini.erudio_microservice.mapper.ObjectMapper.parseObject;
import static dev.vini.erudio_microservice.mapper.ObjectMapper.parseListObjects;
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
        return parseObject(entity, PersonDTO.class);
    }

    public List<PersonDTO> findByGender(String gender){
        logger.info("Finding all " + gender +  " persons...");
        return parseListObjects(personRepository.findByGenderIgnoreCase(gender), PersonDTO.class);
    }

    public List<PersonDTO> findAllPeople() {
        return parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO personDto){
        logger.info("Creating person...");
        var entity = parseObject(personDto, Person.class);
        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO personDto){
        logger.info("Updating person...");
        Person entity = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
        entity.setFirstName(personDto.getFirstName());
        entity.setLastName(personDto.getLastName());
        entity.setAddress(personDto.getAddress());
        entity.setGender(personDto.getGender());
        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("Deleting person...");
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
        personRepository.delete(entity);
    }

}
