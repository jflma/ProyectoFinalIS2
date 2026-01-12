package com.app.modules.post.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.modules.post.controller.dto.response.CommentDetailsDTO;
import com.app.modules.post.domain.Comment;

@Repository
public interface CommentRepositoryImp extends JpaRepository<Comment, Long> {

  @Query("SELECT new com.app.controller.dto.response.CommentDetailsDTO(p.id,u.username,p.content)"
      + "FROM Comment p JOIN p.user u JOIN p.entry e WHERE e.id = :idEntry")

  public List<CommentDetailsDTO> findCommentByEntryId(Long idEntry);

}