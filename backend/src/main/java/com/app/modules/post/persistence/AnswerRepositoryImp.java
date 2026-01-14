package com.app.modules.post.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.modules.post.domain.Answer;

@Repository
public interface AnswerRepositoryImp extends JpaRepository<Answer, Long> {

}