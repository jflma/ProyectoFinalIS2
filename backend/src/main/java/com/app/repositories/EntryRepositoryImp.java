package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.domain.post.Entry;

@Repository
public interface EntryRepositoryImp extends JpaRepository<Entry,Long>{

}
