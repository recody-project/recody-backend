package com.recody.recodybackend.movie.data.title;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity(name = "movie_title")
@Table(name = "movie_title")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MovieTitleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 외래키를 Movie 에 두는 것으로도 가능하나, 그러면 Title 만 가져올 수 없다.
     */
    @JoinColumn(name = "movie_id",
                nullable = false,
                unique = true,
                foreignKey = @ForeignKey(name = "title_contains_movie_id"))
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MovieEntity movie;
    
    private String koreanTitle;
    
    private String englishTitle;
    
    public void setKoreanTitle(String koreanTitle) {
        this.koreanTitle = koreanTitle;
    }
    
    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }
    
    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }
    
    @Override
    public String toString() {
        return "{\"MovieTitleEntity\":{" + ", \"koreanTitle\":" + ((koreanTitle != null) ? ("\"" + koreanTitle + "\"") : null) + ", \"englishTitle\":" + ((englishTitle != null) ? ("\"" + englishTitle + "\"") : null) + "}}";
    }
}
