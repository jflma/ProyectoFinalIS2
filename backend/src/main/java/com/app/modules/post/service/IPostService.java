package com.app.modules.post.service;

import java.util.List;

import com.app.controller.dto.response.PostDetailsDTO;
import com.app.controller.dto.response.PostPreviewDTO;
import com.app.modules.post.domain.Post;

public interface IPostService {
  public Post createPost(Long idUser, String title, String content);

  public boolean index();

  public List<Post> searchWord(String query);

  public List<PostPreviewDTO> getUltimatePost();

  public PostDetailsDTO getDetailsPostById(Long idPost);

}