package com.makeus.makeushackathon.src.user;

import com.makeus.makeushackathon.config.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class User extends BaseEntity {
    @Id
    @Column(name = "user_idx", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userIdx;

    @Column(name = "nickname", nullable = false, length = 45)
    private String nickname;

    @Column(name = "social_id", nullable = false, length = 90)
    private String socialId;

//    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
//    private List<Posting> postings;
    @Column(name="status")
    private String status = "ACTIVE";

}