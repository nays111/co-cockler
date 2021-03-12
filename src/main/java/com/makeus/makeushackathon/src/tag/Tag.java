package com.makeus.makeushackathon.src.tag;

import com.makeus.makeushackathon.config.BaseEntity;
import com.makeus.makeushackathon.src.posting.Posting;
import com.makeus.makeushackathon.src.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="tag")
@NoArgsConstructor
@Getter
public class Tag extends BaseEntity {
    @Id
    @Column(name="tag_idx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="posting_idx",referencedColumnName = "posting_idx")
    private Posting posting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx",referencedColumnName = "user_idx")
    private User user;

    @Column(name="tag_name")
    private String tagName;

    @Column(name="status")
    private String status="ACTIVE";

    public Tag(User user, Posting posting, String tagName) {
        this.user = user;
        this.posting = posting;
        this.tagName = tagName;
    }
}
