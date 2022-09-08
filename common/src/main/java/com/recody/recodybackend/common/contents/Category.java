package com.recody.recodybackend.common.contents;

public enum Category {
    Movie("Movie", "영화"),
    TVSeries("TV Series", "드라마"),
    Music("Music", "음악"),
    Book("Book", "책"),
    Performance("Performance", "공연 & 전시");
    
    
    private final String englishName;
    private final String koreanName;
    
    Category(String englishName, String koreanName) {
        this.englishName = englishName;
        this.koreanName = koreanName;
    }
    
    public String getEnglishName() { return englishName; }

    public String getKoreanName() { return koreanName; }
}
