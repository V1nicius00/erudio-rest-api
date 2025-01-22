package dev.vini.erudio_microservice.services;

import dev.vini.erudio_microservice.model.Person;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findbyId(String id){

        logger.info("Finding one person...");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("First Name");
        person.setLastName("Last Name");
        person.setAddress("Brazil");
        person.setGender("Male");
        return person;
    }

}
