package com.makeus.makeushackathon.src.user;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nickname", nullable = false, length = 45)
    private String nickname;

    @Column(name = "social_id", nullable = false, length = 90)
    private String socialId;

//    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
//    private List<Posting> postings;
}