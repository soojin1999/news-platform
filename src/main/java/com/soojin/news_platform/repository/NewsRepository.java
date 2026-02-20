package com.soojin.news_platform.repository;

import com.soojin.news_platform.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
