package dev.vini.erudio_microservice.services;

import dev.vini.erudio_microservice.data.dto.PersonDTO;
import dev.vini.erudio_microservice.exceptions.RequiredObjectIsNullException;
import dev.vini.erudio_microservice.models.Person;
import dev.vini.erudio_microservice.repositories.PersonRepository;
import dev.vini.erudio_microservice.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        var result = service.findById(1L);
        assertPerson(result, 1L, "Address Test1", "First Name Test1", "Last Name Test1", "Female");


    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        PersonDTO dto = input.mockDTO(1);
        when(personRepository.save(person)).thenReturn(persisted);

        var result = service.create(dto);
        assertPerson(result, 1L, "Address Test1", "First Name Test1", "Last Name Test1", "Female");

    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.create(null);
                });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        PersonDTO dto = input.mockDTO(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(persisted);

        var result = service.update(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertPerson(result, 1L, "Address Test1", "First Name Test1", "Last Name Test1", "Female");

    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(null);
                });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        service.delete(1L);
        verify(personRepository, times(1)).findById(anyLong());
        verify(personRepository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void findByGender() {
    }

    @Test
    void findAllPeople() {
        List<Person> personList = input.mockEntityList();
        when(personRepository.findAll()).thenReturn(personList);
        List<PersonDTO> people = service.findAllPeople();

        assertNotNull(people);
        assertEquals(14, people.size());

        assertPerson(people.get(1), 1L, "Address Test1", "First Name Test1", "Last Name Test1", "Female");
        assertPerson(people.get(4), 4L, "Address Test4", "First Name Test4", "Last Name Test4", "Male");
        assertPerson(people.get(11), 11L, "Address Test11", "First Name Test11", "Last Name Test11", "Female");

    }

    private void assertPerson(PersonDTO person, Long expectedId, String address, String firstName, String lastName, String gender){
        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getLinks());

        assertTrue(person.getLinks().stream()

                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/id/" + expectedId)
                        && link.getType().equals("GET")
                )
        );

        assertTrue(person.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAllPeople")
                        && link.getHref().endsWith("/api/person/v1{?gender}")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(person.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(person.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(person.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/" + expectedId)
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals(address, person.getAddress());
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(gender, person.getGender());
    }
}