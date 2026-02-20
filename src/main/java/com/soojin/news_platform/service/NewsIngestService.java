package com.soojin.news_platform.service;

import com.soojin.news_platform.domain.Keyword;
import com.soojin.news_platform.domain.KeywordStat;
import com.soojin.news_platform.domain.News;
import com.soojin.news_platform.domain.NewsKeyword;
import com.soojin.news_platform.repository.KeywordRepository;
import com.soojin.news_platform.repository.KeywordStatRepository;
import com.soojin.news_platform.repository.NewsKeywordRepository;
import com.soojin.news_platform.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsIngestService {
    private final NewsRepository newsRepository;
    private final KeywordRepository keywordRepository;
    private final NewsKeywordRepository newsKeywordRepository;
    private final KeywordStatRepository keywordStatRepository;

    public void ingestOneNews(News news, List<String> keywordNames) {
        // 0) 방어: null/중복 제거 + 공백 제거
        Set<String> normalized = normalizeKeywords(keywordNames);
        if (normalized.isEmpty()) {
            // 키워드가 없어도 뉴스는 저장할지 정책 선택 가능
            newsRepository.save(news);
            return;
        }
        // 1. news 저장
        if (news.getCollectedAt() == null) {
            news.setCollectedAt(LocalDateTime.now());    // collectedAt이 비어있으면 지금 시간 넣기 (정책)
        }
        News savedNews = newsRepository.save(news);

        // 2) timeBucket 계산 (1시간 단위)
        LocalDateTime timeBucket = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        // 3) keyword upsert + news_keyword 저장 + keyword_stat (+1)
        for (String keywordName : normalized) {
            Keyword keyword = keywordRepository.findByName(keywordName)
                    .orElseGet(() -> {
                        Keyword k = new Keyword();
                        k.setName(keywordName);
                        return keywordRepository.save(k);
                    });

            // 3) news_keyword 저장 (중복 방지 정책이 필요하면 unique 제약/체크 추가 가능)
            NewsKeyword link = new NewsKeyword();
            link.setNews(savedNews);
            link.setKeyword(keyword);
            link.setCreatedAt(LocalDateTime.now());
            newsKeywordRepository.save(link);

            // 4) keyword_stat update (+1)
            KeywordStat stat = keywordStatRepository.findByKeywordAndTimeBucket(keyword, timeBucket)
                    .orElseGet(() -> {
                        KeywordStat ks = new KeywordStat();
                        ks.setKeyword(keyword);
                        ks.setTimeBucket(timeBucket);
                        ks.setCount(0L);
                        return ks;
                    });

            stat.setCount(stat.getCount() + 1);
            keywordStatRepository.save(stat);
        }
    }

    private Set<String> normalizeKeywords(List<String> keywordNames) {
        Set<String> result = new HashSet<>();       //set은 중복 제거 list는 중복제거x
        if(keywordNames == null) return result;

        for(String raw: keywordNames) {
            if(raw == null) continue;
            String s = raw.trim();
            if(s.isEmpty()) continue;
            // 너무 긴/짧은 토큰 제거(정책)
            if (s.length() < 2 || s.length() > 20) continue;
            result.add(s);
        }
        return result;
    }
}
