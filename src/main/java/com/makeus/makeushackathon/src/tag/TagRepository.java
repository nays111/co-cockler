package com.makeus.makeushackathon.src.tag;

import com.makeus.makeushackathon.src.posting.Posting;
import com.makeus.makeushackathon.src.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag,Integer> {
    List<Tag> findAllByPostingAndStatus(Posting posting,String status);
    List<Tag> findAllByStatus(String Status);
    List<Tag> findDistinctByUserAndStatus(User user, String status);
    List<Tag> findAllByUserAndStatus(User user, String status);
    List<Tag> findAllByUserNotAndStatusAndTagNameOrderByTagIdxDesc(User user, String status, String tagName);
    List<Tag> findAllByUserNotAndStatusOrderByTagIdxDesc(User user, String status);
}
