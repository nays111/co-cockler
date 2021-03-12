package com.makeus.makeushackathon.src.posting;

import com.makeus.makeushackathon.config.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="posting")
@NoArgsConstructor
public class Posting extends BaseEntity {
    @Id
    @Column(name="posting_idx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postingIdx;

    @Column(name="posting_description",nullable = false)
    private String postingDescription;

    @Column(name="posting_emoji",nullable = false)
    private String postingEmoji;

    @Column(name="posting_thumbnail_url")
    private String postingThumbnailUrl;

    @Column(name="posting_picture1_url",nullable = true)
    private String postingPicture1Url;

    @Column(name="posting_picture2_url")
    private String getPostingPicture2Url;

    @Column(name="status")
    private String status = "ACTIVE";

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
}