package com.soojin.news_platform.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 키워드의 시간대별 등장 횟수 저장
 */
@Entity
@Getter
@Setter
@Table(name ="keyword_stat",
        indexes = {
            @Index(name = "idx_keyword_time", columnList = "keyword_id, time_bucket")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_keyword_time",
                        columnNames = {"keyword_id", "time_bucket"}
                )
        })
public class KeywordStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;            //어떤 키워드의 통계인지

    @Column(name = "time_bucket", nullable = false)
    private LocalDateTime timeBucket;   //시간단위 집계

    @Column(nullable = false)
    private Long count;                 //해당 시간에 몇번 등장
}
