package com.app.modules.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.modules.user.domain.Person;

@Repository
public interface PersonRepositoryImp extends JpaRepository<Person, Long> {

}