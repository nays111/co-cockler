package com.makeus.makeushackathon.src.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    User findBySocialId(String socialId);
    boolean existsByNicknameAndStatus(String nickname,String status);
}
