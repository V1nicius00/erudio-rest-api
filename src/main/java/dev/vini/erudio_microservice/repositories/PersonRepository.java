package dev.vini.erudio_microservice.repositories;

import dev.vini.erudio_microservice.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}