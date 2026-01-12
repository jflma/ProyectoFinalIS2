package com.app.services.interfaces;

import com.app.domain.post.Entry;
import com.app.modules.user.domain.ForoUser;

public interface IEntryService {

  public Entry createEntry(ForoUser user, String content);

  public void addCommentToEntry(Long idEntry);

}
