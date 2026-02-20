package com.soojin.news_platform.controller;

import com.soojin.news_platform.domain.KeywordStat;
import com.soojin.news_platform.dto.response.TopKeywordResponse;
import com.soojin.news_platform.service.KeywordStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final KeywordStatService keywordStatService;

    /**
     * 상위 10개 키워드 반환
     * * 개발 중 있었던 오류사항 *
     * return 데이터 타입을 entity로 받으니까 jpa n+1문제가 발생하여 정상적으로 동작하지 않음
     * => dto 클래스 만들어서 entity로 받은값을 dto로 변환하여 문제 해결
     * @param time
     * @return
     */
    @GetMapping("/top")
    public List<TopKeywordResponse> topKeywords(@RequestParam String time) {
        LocalDateTime bucket = LocalDateTime.parse(time);
        List<KeywordStat> stats = keywordStatService.getTopKeywords(bucket, 10);
        return stats.stream()
                .map(s -> new TopKeywordResponse(
                        s.getKeyword().getId(),
                        s.getKeyword().getName(),
                        s.getCount()
                ))
                .toList();
    }
}
