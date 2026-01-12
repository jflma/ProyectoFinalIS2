package com.app.modules.user.service;

import java.time.LocalDate;

import com.app.modules.user.domain.Person;

public interface IPersonService {
  public Person createPerson(String firstName, String lastName, String email, LocalDate birthDay);
}
