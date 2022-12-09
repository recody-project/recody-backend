package com.recody.recodybackend.movie.data.overview;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Table(name = "movie_overview")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class MovieOverviewEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JoinColumn( name = "movie_id",
                 nullable = false,
                 foreignKey = @ForeignKey(name = "overview_contains_movie_id"))
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MovieEntity movie;
    
    @Lob
    private String koreanOverview;
    
    @Lob
    private String englishOverview;
    
    
    @Override
    public String toString() {
        return "{\"MovieOverviewEntity\":{"
               + "\"id\":" + id
               + ", \"koreanOverview\":" + ((koreanOverview != null) ? ("\"" + koreanOverview + "\"") : null)
               + ", \"englishOverview\":" + ((englishOverview != null) ? ("\"" + englishOverview + "\"") : null)
               + "}}";
    }
}
