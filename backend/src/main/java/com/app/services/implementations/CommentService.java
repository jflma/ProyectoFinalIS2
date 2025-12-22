package com.app.services.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class CommentService implements ICommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private AnswerService answerService;

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
