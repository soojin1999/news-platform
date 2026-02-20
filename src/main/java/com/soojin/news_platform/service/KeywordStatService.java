package com.soojin.news_platform.service;

import com.soojin.news_platform.domain.Keyword;
import com.soojin.news_platform.domain.KeywordStat;
import com.soojin.news_platform.repository.KeywordRepository;
import com.soojin.news_platform.repository.KeywordStatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 통계 전용
 */
@Service
@Transactional
@RequiredArgsConstructor
public class KeywordStatService {
    private final KeywordRepository keywordRepository;
    private final KeywordStatRepository keywordStatRepository;

    //TOP 키워드 조회
    public List<KeywordStat> getTopKeywords(LocalDateTime timeBucket, int limit) {
        return keywordStatRepository
                .findByTimeBucketOrderByCountDesc(timeBucket, PageRequest.of(0, limit));
    }

    //키워드 시간대 조회
    public List<KeywordStat> getKeywordTrend(String keywordName, LocalDateTime start, LocalDateTime end) {
        Keyword keyword = keywordRepository.findByName(keywordName)
                .orElseThrow(() -> new RuntimeException("키워드 없음"));

        return keywordStatRepository
                .findByKeywordAndTimeBucketBetweenOrderByTimeBucketAsc(keyword, start, end);
    }
}
