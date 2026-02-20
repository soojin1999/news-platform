package com.soojin.news_platform.repository;

import com.soojin.news_platform.domain.NewsKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsKeywordRepository extends JpaRepository<NewsKeyword, Long> {
}
