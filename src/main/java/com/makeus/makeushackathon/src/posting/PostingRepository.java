package com.makeus.makeushackathon.src.posting;

import com.makeus.makeushackathon.src.user.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface PostingRepository extends CrudRepository<Posting,Integer>{
    List<Posting> findAllByStatus(String status);
    List<Posting> findAllByStatusOrderByPostingIdxDesc(String status);
    Posting findAllByPostingIdxAndStatus(int postingIdx,String status);
    List<Posting> findAllByStatusAndUser(String status, User user);
}
