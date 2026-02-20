package com.soojin.news_platform.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 수집한 기사 원본 테이터
 * 네이버 api 에서 가져온 실제 기사
 * 분석하는 원천 데이터
 */
@Entity
@Getter
@Setter
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String source;

    private Double sentimentScore;

    private LocalDateTime publishedAt;      //네이버 api에서 받은 발행시간

    private LocalDateTime collectedAt;      //수집시간

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsKeyword> newsKeywords = new ArrayList<>();
}
