package com.soojin.news_platform.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 분석 단위가 되는 키워드
 */
@Entity
@Getter
@Setter
@Table(name = "keyword")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "keyword")
    private List<NewsKeyword> newsKeywords = new ArrayList<>();
}
