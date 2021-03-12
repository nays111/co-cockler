package com.makeus.makeushackathon.src.posting;

import com.makeus.makeushackathon.config.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="Posting")
@NoArgsConstructor
public class Posting extends BaseEntity {
    @Id
    @Column(name="posting_idx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postingIdx;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="user_idx",referencedColumnName = "user_idx")
//    private User user;

    @Column(name="posting_description")
    private String postingDescription;

    @Column(name="status")
    private String status = "ACTIVE";
}
