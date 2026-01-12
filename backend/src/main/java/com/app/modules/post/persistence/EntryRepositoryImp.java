package com.app.modules.post.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.modules.post.domain.Entry;

@Repository
public interface EntryRepositoryImp extends JpaRepository<Entry, Long> {

}
