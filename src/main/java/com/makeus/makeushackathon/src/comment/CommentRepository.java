package com.makeus.makeushackathon.src.comment;

import com.makeus.makeushackathon.src.posting.Posting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Integer> {
    int countAllByPostingAndStatus(Posting posting, String status);
    List<Comment> findAllByPostingAndStatus(Posting posting, String status);
}
