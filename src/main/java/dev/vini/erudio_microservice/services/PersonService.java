package dev.vini.erudio_microservice.services;

import dev.vini.erudio_microservice.model.Person;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    public List<Person> findAllPeople() {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i <= 8; i++){
            Person person = mockPeople(i);
            persons.add(person);
        }
        return persons;
    }

    public Person create(Person person){
        logger.info("Creating one person...");
        return person;
    }

    public Person update(Person person){
        logger.info("Updating one person...");
        return person;
    }

    private Person mockPeople(int i){
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("First Name " + i);
        person.setLastName("Last Name " + i);
        person.setAddress("Brazil " + 1);
        person.setGender("Male " + 1);
        return person;
    }
}
