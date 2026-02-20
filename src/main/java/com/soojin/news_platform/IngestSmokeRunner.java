package com.soojin.news_platform;

import com.soojin.news_platform.domain.News;
import com.soojin.news_platform.service.NewsIngestService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("local")
@RequiredArgsConstructor
public class IngestSmokeRunner implements CommandLineRunner {
    private final NewsIngestService newsIngestService;

    @Override
    public void run(String... args) {
        News news = new News();
        news.setTitle("테스트 뉴스 - 금리/환율/주식");
        news.setDescription("오늘 금리 인상 가능성이 거론되며 환율과 주식 시장이 변동했습니다.");
        news.setSource("SMOKE");
        news.setPublishedAt(LocalDateTime.now().minusMinutes(10));
        news.setCollectedAt(LocalDateTime.now());
        news.setSentimentScore(0.2);

        List<String> keywords = List.of("금리", "환율", "주식", "금리"); // 중복 포함(정규화 확인용)

        newsIngestService.ingestOneNews(news, keywords);

        System.out.println("[SMOKE] ingestOneNews done");
    }
}
