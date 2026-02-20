package com.soojin.news_platform.dto.response;

/**
 * record는 equals/hashCode/toString 자동생성
 * 직렬화/역직렬화 유리
 * but 불변 dto처럼 set 불가
 * get은 dto.getKeywordId가 아닌 그냥 dto.keywordId로
 * @param keywordId 키워드 시퀀스
 * @param keywordName 키워드 명
 * @param count 키워드 발견 횟수
 */
public record TopKeywordResponse (
        Long keywordId,
        String keywordName,
        Long count
){}
