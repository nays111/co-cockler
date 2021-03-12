package com.makeus.makeushackathon.src.comment;

import com.makeus.makeushackathon.src.posting.Posting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Integer> {
    int countAllByPostingAndStatus(Posting posting, String status);
}
