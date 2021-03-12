package com.makeus.makeushackathon.src.posting;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface PostingRepository extends CrudRepository<Posting,Integer>{
}
