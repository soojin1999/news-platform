package com.soojin.news_platform.service;

import com.soojin.news_platform.domain.KeywordStat;
import com.soojin.news_platform.repository.KeywordStatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordStatService {
    private final KeywordStatRepository keywordStatRepository;
    public List<KeywordStat> getTopKeywords(LocalDateTime timeBucket, int limit) {
        return keywordStatRepository
                .findByTimeBucketOrderByCountDesc(timeBucket, (Pageable) PageRequest.of(0, limit));
    }
}
