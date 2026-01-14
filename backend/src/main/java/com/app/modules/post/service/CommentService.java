package com.app.modules.post.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.modules.post.controller.dto.response.CommentDetailsDTO;
import com.app.modules.post.domain.Answer;
import com.app.modules.post.domain.Comment;
import com.app.modules.post.domain.Entry;
import com.app.modules.post.domain.Post;
import com.app.modules.user.domain.ForoUser;
import com.app.exceptions.CreationException;
import com.app.modules.post.persistence.CommentRepositoryImp;
import com.app.modules.user.service.IUserService;

@Service
public class CommentService implements ICommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IAnswerService answerService;

    @Autowired
    private CommentRepositoryImp commentRepository;

    @Override
    public Comment postComment(Long postId, Long userId, String content) {
        logger.info("Iniciando creación de comentario para post: {}", postId);

        try {
            Post post = postService.getPostById(postId);
            Entry entry = post.getEntry();
            ForoUser user = userService.getUserbyId(userId);

            Comment comment = new Comment();
            comment.setUser(user);
            comment.setEntry(entry);
            comment.setContent(content);

            return commentRepository.save(comment);

        } catch (Exception e) {
            logger.error("Error al crear comentario para el post {}", postId, e);
            throw new CreationException("No se pudo crear el comentario al post");
        }
    }

    @Override
    public Comment answerComment(Long answerId, Long userId, String content) {
        try {
            Answer answer = answerService.getAnswerById(answerId);
            Entry entry = answer.getEntry();
            ForoUser user = userService.getUserbyId(userId);

            Comment comment = new Comment();
            comment.setEntry(entry);
            comment.setUser(user);
            comment.setContent(content);

            return commentRepository.save(comment);

        } catch (Exception e) {
            logger.error("Error al crear comentario para la respuesta {}", answerId, e);
            throw new CreationException("No se pudo crear el comentario al answer");
        }
    }

    @Override
    public List<CommentDetailsDTO> getCommentsFromPost(Long idEntry) {
        return commentRepository.findCommentByEntryId(idEntry);
    }
}
