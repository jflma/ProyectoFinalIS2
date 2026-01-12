package com.app.modules.user.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.modules.user.domain.ForoUser;

@Repository
public interface UserRepositoryImp extends JpaRepository<ForoUser, Long> {

  public Optional<ForoUser> findForoUserByUsername(String username);

}
