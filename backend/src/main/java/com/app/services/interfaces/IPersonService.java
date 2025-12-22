package com.app.services.interfaces;

import java.time.LocalDate;

import com.app.domain.user.Person;

public interface IPersonService {
  public Person createPerson (String firstName, String lastName, String email, LocalDate birthDay);
}
