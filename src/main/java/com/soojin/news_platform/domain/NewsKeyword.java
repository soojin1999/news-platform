package com.soojin.news_platform.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 뉴스와 키워드를 연결하는 엔티티
 */
@Entity
@Getter
@Setter
@Table(name = "news_keyword",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_news_keyword",
                        columnNames = {"news_id", "keyword_id"}
                )
        }
)
public class NewsKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    private LocalDateTime createdAt;
}
