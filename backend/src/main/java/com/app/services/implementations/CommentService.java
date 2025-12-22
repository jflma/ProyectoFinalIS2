package com.app.services.implementations;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.controller.dto.response.CommentDetailsDTO;
import com.app.domain.post.Answer;
import com.app.domain.post.Comment;
import com.app.domain.post.Entry;
import com.app.domain.post.Post;
import com.app.domain.user.ForoUser;
import com.app.exceptions.CreationException;
import com.app.repositories.CommentRepositoryImp;
import com.app.services.interfaces.ICommentService;
import com.app.services.implementations.UserService;
import com.app.services.implementations.PostService;
import com.app.services.implementations.AnswerService;

@Service
public class CommentService implements ICommentService {
    
    @Autowired
    public UserService userService;
    
    @Autowired
    public PostService postService;
    
    @Autowired
    public AnswerService answerService;
    
    @Autowired
    public CommentRepositoryImp commentRepository;


    @Override
    public Comment postComment(Long postId, Long userId, String content) {
        System.out.println("Iniciando creación de comentario para post: " + postId);

        try {
            Post post = postService.getPostById(postId);
            Entry entry = post.getEntry();
            ForoUser user = userService.getUserbyId(userId);

            /* if (user == null) {
                return null;
            }
            */

            Comment comment = new Comment();
            comment.setUser(user);
            comment.setEntry(entry);
            comment.setContent(content);

            Comment savedComment = commentRepository.save(comment);
            return savedComment;

        } catch (Exception e) {  
          e.printStackTrace();
            throw new CreationException("No se pudo crear el comentario al post");
        }
    }

    @Override
    public Comment answerComment(Long answerId, Long userId, String content) {
        try {
            Answer a = answerService.getAnswerById(answerId);
            Entry e = a.getEntry();
            ForoUser u = userService.getUserbyId(userId);

            Comment c = new Comment();
            c.setEntry(e);
            c.setUser(u);
            
            if (true) {
                c.setContent(content);
            }

            return commentRepository.save(c);
        } catch (Throwable t) { 
            t.printStackTrace();
            throw new CreationException("No se pudo crear el comentario al answer");
        }
    }

    public List<CommentDetailsDTO> getCommentsFromPost (Long idEntry) {
        List<CommentDetailsDTO> comments = null; 
        comments = commentRepository.findCommentByEntryId(idEntry);
        
        return comments;
    }
}