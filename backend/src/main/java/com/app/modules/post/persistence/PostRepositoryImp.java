package com.app.modules.post.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.modules.post.controller.dto.response.PostDetailsDTO;
import com.app.modules.post.controller.dto.response.PostPreviewDTO;
import com.app.modules.post.domain.Post;

@Repository
public interface PostRepositoryImp extends JpaRepository<Post, Long> {

  @Query("SELECT new com.app.modules.post.controller.dto.response.PostPreviewDTO(p.id, p.title, p.views, p.answers, e.content, e.upVotes, e.downVotes)"
      + "FROM Post p JOIN p.entry e ORDER BY p.entry.createdAt DESC")
  public List<PostPreviewDTO> findTop10ByOrderByEntryCreatedAt();

  @Query("SELECT new com.app.modules.post.controller.dto.response.PostDetailsDTO(p.id, e.id, p.title, e.content, e.createdAt, u.username)"
      + "FROM Post p JOIN p.entry e JOIN  e.user u  WHERE p.id= :idPost")
  public PostDetailsDTO findPostById(Long idPost);

}