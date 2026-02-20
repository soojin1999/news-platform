package com.soojin.news_platform.repository;

import com.soojin.news_platform.domain.Keyword;
import com.soojin.news_platform.domain.KeywordStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KeywordStatRepository extends JpaRepository<KeywordStat, Long> {
    Optional<KeywordStat> findByKeywordAndTimeBucket(Keyword keyword, LocalDateTime timeBucket);
    List<KeywordStat> findByTimeBucketOrderByCountDesc(LocalDateTime timeBucket, Pageable pageable);
}
