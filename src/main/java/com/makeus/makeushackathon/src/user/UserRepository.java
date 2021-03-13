package com.makeus.makeushackathon.src.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    User findBySocialId(String socialId);
    User findByUserIdxAndStatus(Integer userIdx, String status);
    boolean existsBySocialIdAndStatus(String socialId, String status);
    boolean existsByUserIdxAndStatus(Integer userIdx, String status);
}
