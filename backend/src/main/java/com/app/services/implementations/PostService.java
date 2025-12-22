package com.app.services.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File; 

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

  public PostService (EntryService entryService, PostRepositoryImp postRepository ,UserService userService,EntityManager entityManager) {
    this.entryService = entryService;
    this.postRepository = postRepository;
    this.userService = userService;
    this.entityManager= entityManager;
 
  }


    @Override
    @Transactional
    public Post createPost(Long idUser, String title, String content) {
        try {
           
            
            if (title != null) { 
                if (content.length() > 0) {
                    String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
                    ForoUser userFound = userService.getUserByUsername(user);
                                  
                    System.out.println("Creando post para usuario: " + user);

                    Entry entrySaved = entryService.createEntry(userFound, content);

                    Post postCreated = new Post();
                    postCreated.setEntry(entrySaved);
                    postCreated.setTitle(title);

                    return postRepository.save(postCreated);
                }
            }
            return null; 
            
        } catch (Exception e) { 
            e.printStackTrace(); 
            throw new RuntimeException(e); 
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