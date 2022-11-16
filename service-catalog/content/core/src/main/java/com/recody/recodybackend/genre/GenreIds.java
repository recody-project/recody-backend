package com.recody.recodybackend.genre;

import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.exceptions.CatalogErrorType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GenreIds implements Iterable<GenreId> {
    public static final int MAX_GENRE_PERSONALIZATION = 5;
    
    @Getter
    private final List<GenreId> genreIds;
    
    public GenreIds(String... ids) {
        if (ids.length == 0){
            this.genreIds = new ArrayList<>();
        }
        else {
            ArrayList<GenreId> idList = new ArrayList<>();
            for (String id : ids) {
                GenreId idObject = GenreId.of( id );
                idList.add( idObject );
            }
            this.genreIds = idList.stream().distinct().collect( Collectors.toList() );
        }
        requireNotOverMax( this.genreIds );
    }
    
    public GenreIds(List<String> genreIds) {
        this.genreIds = genreIds.stream()
                                .distinct()
                                .map( GenreId::of )
                                .collect( Collectors.toList() );
        // distinct 처리 후의 개수로 기준.
        requireNotOverMax( this.genreIds );
    }
    
    private static void requireNotOverMax(List<GenreId> genreIds){
        if (genreIds.size() > MAX_GENRE_PERSONALIZATION){
            throw ApplicationExceptions.badRequestOf( CatalogErrorType.GenreCustomizationCannotOver5 );
        }
    }
    
    @Override
    public Iterator<GenreId> iterator() {
        return this.genreIds.iterator();
    }
    
    public int size(){
        return this.genreIds.size();
    }
}
