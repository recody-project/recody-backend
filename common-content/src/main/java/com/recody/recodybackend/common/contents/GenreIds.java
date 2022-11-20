package com.recody.recodybackend.common.contents;

import com.recody.recodybackend.common.contents.exceptions.ContentErrorType;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class GenreIds implements Iterable<GenreId> {
    
    public static final int MAX_GENRE_PERSONALIZATION = 5;
    
    @Getter
    private final List<GenreId> genreIds;
    
    public static GenreIds of(List<String> genreIds) {
        if ( Objects.isNull( genreIds ) || genreIds.isEmpty() ) {
            return new GenreIds( Collections.emptyList() );
        }
        return new GenreIds( genreIds );
    }
    
    public boolean isEmpty(){
        return genreIds.isEmpty();
    }
    
    public GenreIds(String... ids) {
        if ( ids.length == 0 ) {
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
    
    private static void requireNotOverMax(List<GenreId> genreIds) {
        if ( genreIds.size() > MAX_GENRE_PERSONALIZATION ) {
            throw ApplicationExceptions.badRequestOf( ContentErrorType.GenreCustomizationCannotOver5 );
        }
    }
    
    public List<String> getValues() {
        return this.genreIds.stream().map( GenreId::getValue ).collect( Collectors.toList() );
    }
    
    @Override
    public Iterator<GenreId> iterator() {
        return this.genreIds.iterator();
    }
    
    public int size() {
        return this.genreIds.size();
    }
    
    @Override
    public String toString() {
        return "{\"GenreIds\":{"
               + "\"genreIds\":" + genreIds
               + "}}";
    }
}
