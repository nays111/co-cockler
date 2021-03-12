package com.makeus.makeushackathon.src.tag;

import com.makeus.makeushackathon.src.posting.Posting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag,Integer> {
    List<Tag> findAllByPostingAndStatus(Posting posting,String status);
}
