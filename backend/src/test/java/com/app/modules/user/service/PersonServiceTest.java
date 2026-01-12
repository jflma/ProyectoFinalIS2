package com.app.modules.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.modules.user.domain.Person;
import com.app.modules.user.persistence.PersonRepositoryImp;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
  @Mock
  private PersonRepositoryImp personRepository;

  @InjectMocks
  private PersonService personService;

  @Test
  void testCreatePerson() {
    String firstName = "name";
    String lastName = "name2";
    String email = "email@google.com";
    LocalDate birthDay = LocalDate.now();

    Person person = new Person();
    person.setFirstName(firstName);
    person.setEmail(email);
    person.setLastName(lastName);
    person.setDateOfBirthDay(birthDay);

    when(personRepository.save(any(Person.class))).thenReturn(person);

    Person personCreated = personService.createPerson(firstName, lastName, email, birthDay);

    assertNotNull(personCreated);
    assertEquals(email, personCreated.getEmail());

  }

}
