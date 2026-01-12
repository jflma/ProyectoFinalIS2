package com.app.modules.post.service;

import com.app.modules.post.domain.Entry;
import com.app.modules.user.domain.ForoUser;

public interface IEntryService {

  public Entry createEntry(ForoUser user, String content);

  public void addCommentToEntry(Long idEntry);

}
