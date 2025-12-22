package com.app.services.implementations;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.post.Answer;
import com.app.domain.post.Entry;
import com.app.domain.post.Post;
import com.app.domain.user.ForoUser;
import com.app.exceptions.CreationException;
import com.app.repositories.AnswerRepositoryImp;
import com.app.services.interfaces.IAnswerService;

@Service
public class AnswerService implements IAnswerService{

  private UserService userService;
  private PostService postService;
  private EntryService entryService;
  private AnswerRepositoryImp answerRepository;

  public AnswerService (UserService userService, PostService postService, EntryService entryService, AnswerRepositoryImp answerRepository) {
    this.userService = userService;
    this.postService = postService;
    this.entryService = entryService;
    this.answerRepository = answerRepository;
  }

  @Override
  @Transactional
  public Answer createAnswer(Long postIdToReply,String content){
    try{
      String userByUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
      ForoUser user = userService.getUserByUsername(userByUsername);
      Entry entryCreated = entryService.createEntry(user, content);
      Post postToReply = postService.getPostById(postIdToReply);
  
      Answer answerCreated = new Answer();
      answerCreated.setEntry(entryCreated);
      answerCreated.setPost(postToReply);
      //crear una entry con el user de
      return answerRepository.save(answerCreated);
    }catch(Exception e){
      throw new CreationException("No se pudo crear la respuesta");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Answer getAnswerById(Long idAnswer){
    return answerRepository.findById(idAnswer)
      .orElseThrow();
  }

}