package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.domain.user.Person;

@Repository
public interface PersonRepositoryImp extends JpaRepository<Person,Long>{

}