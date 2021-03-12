package com.makeus.makeushackathon.src.comment;

import com.makeus.makeushackathon.config.BaseEntity;
import com.makeus.makeushackathon.src.posting.Posting;
import com.makeus.makeushackathon.src.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="comment")
@NoArgsConstructor
@Getter
public class Comment extends BaseEntity {
    @Id
    @Column(name="comment_idx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx",referencedColumnName = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="posting_idx",referencedColumnName = "posting_idx")
    private Posting posting;

    @Column(name="comment_description")
    private String commentDescription;

    @Column(name="status")
    private String status="ACTIVE";

    public Comment(User user, Posting posting, String commentDescription) {
        this.user = user;
        this.posting = posting;
        this.commentDescription = commentDescription;
    }
}
