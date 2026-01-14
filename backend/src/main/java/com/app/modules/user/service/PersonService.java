package com.app.modules.user.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.app.modules.user.domain.Person;
import com.app.modules.user.persistence.PersonRepositoryImp;

@Service
public class PersonService implements IPersonService {

  private PersonRepositoryImp personRepository;

  public PersonService(PersonRepositoryImp personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public Person createPerson(String firstName, String lastName, String email, LocalDate birthDay) {
    Person newPerson = Person.builder()
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .dateOfBirthDay(birthDay)
        .build();

    return personRepository.save(newPerson);

  }

}
