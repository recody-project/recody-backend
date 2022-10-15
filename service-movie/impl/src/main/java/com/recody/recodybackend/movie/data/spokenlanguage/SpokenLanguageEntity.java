package com.recody.recodybackend.movie.data.spokenlanguage;

import com.recody.recodybackend.movie.data.MovieBaseEntity;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * 특정 영화에 사용된 언어를 의미하는 엔티티입니다.<br>
 * LanguageEntity 와 MovieEntity 의 각 테이블 매핑에서 연결 테이블 역할을 합니다.
 */
@Entity(name = "spoken_language")
@Getter
@Setter
@Table(name = "spoken_language")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SpokenLanguageEntity extends MovieBaseEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "movie_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "spoken_language_contains_movie_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MovieEntity movie;
    
    @ManyToOne
    @JoinColumn(name = "iso_639_1",
                nullable = false,
                foreignKey = @ForeignKey(name = "spoken_language_contains_language_id"))
    private LanguageEntity language;
}
