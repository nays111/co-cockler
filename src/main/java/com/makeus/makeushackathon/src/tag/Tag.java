package com.makeus.makeushackathon.src.tag;

import com.makeus.makeushackathon.config.BaseEntity;
import com.makeus.makeushackathon.src.posting.Posting;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="tag")
@NoArgsConstructor
public class Tag extends BaseEntity {
    @Id
    @Column(name="tag_idx",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="posting_idx",referencedColumnName = "posting_idx")
    private Posting posting;

    @Column(name="tag_name")
    private String tagName;

    @Column(name="status")
    private String status;
}