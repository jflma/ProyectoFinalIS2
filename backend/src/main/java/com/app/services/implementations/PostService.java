package com.app.services.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.controller.dto.response.PostDetailsDTO;
import com.app.controller.dto.response.PostPreviewDTO;
import com.app.domain.post.Entry;
import com.app.domain.post.Post;
import com.app.domain.user.ForoUser;
import com.app.exceptions.CreationException;
import com.app.repositories.PostRepositoryImp;
import com.app.services.interfaces.IPostService;

import jakarta.persistence.EntityManager;

@Service
public class PostService implements IPostService {

  private EntryService entryService;
  private PostRepositoryImp postRepository;
  private UserService userService;
  private EntityManager entityManager;
  private static final Logger logger = LoggerFactory.getLogger(PostService.class);

  public PostService (EntryService entryService, PostRepositoryImp postRepository ,UserService userService,EntityManager entityManager) {
    this.entryService = entryService;
    this.postRepository = postRepository;
    this.userService = userService;
    this.entityManager= entityManager;
 
  }


  @Override
  @Transactional
  public Post createPost(Long idUser, String title, String content) {
    if (title == null || title.trim().isEmpty()) { 
             logger.warn("Intento de crear post con título vacío.");
             return null; 
        }
    if (content == null || content.isEmpty()) {
          logger.warn("Intento de crear post con contenido vacío.");
          return null;
    }

    try {
        String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        ForoUser userFound = userService.getUserByUsername(user);
        
        logger.info("Creando post para usuario: {}", user);

        Entry entrySaved = entryService.createEntry(userFound, content);
        Post postCreated = new Post();
        postCreated.setEntry(entrySaved);
        postCreated.setTitle(title);

        return postRepository.save(postCreated);
        
    } catch (Exception e) {
        // Loguear error con stacktrace y lanzar excepción personalizada
        logger.error("Error al crear el post para el usuario con ID: {}", idUser, e);
        throw new CreationException("No se pudo crear el post", e); 
    }
    
  }

  public List<PostPreviewDTO> getUltimatePost() {
      return postRepository.findTop10ByOrderByEntryCreatedAt();
  }

  @Transactional(readOnly = true)
  public Post getPostById(Long idPost) {
      try {
            return postRepository.findById(idPost).orElseThrow();
      } catch (Exception e) {
          return null; 
      }
  }

  public PostDetailsDTO getDetailsPostById(Long idPost) {
      return postRepository.findPostById(idPost);
  }

  // buscador
  @Override
  public boolean index() {
      Boolean encontrado = false; 
      try {
          SearchSession searchSession = Search.session(entityManager);
          searchSession.massIndexer().startAndWait();
          encontrado = true;
      } catch (InterruptedException ie) {
          System.out.println("Error de interrupcion"); 
          encontrado = false;
      }
      return encontrado;
  }

  @Override
  public List<Post> searchWord(String query) {
      if (query != null) { 
          SearchSession searchSession = Search.session(entityManager);
          
          Set<Post> uniqueResults = new HashSet<>(searchSession.search(Post.class)
                  .where(f -> f.match()
                          .fields("title")
                          .matching(query)
                          .analyzer("multilingual")
                          .fuzzy(2)) 
                  .sort(f -> f.score())
                  .fetchHits(20)); 
          
          return new ArrayList<>(uniqueResults);
      } else {
          return null; 
      }
  }
}