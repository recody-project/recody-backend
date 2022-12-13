package com.recody.recodybackend.book.data.book;

import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import com.recody.recodybackend.book.data.BookBaseEntity;
import com.recody.recodybackend.book.data.AuthorEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Book")
@Table(name = "book")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity extends BookBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @GenericGenerator(
            name="book_seq",
            strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50"),// high-low 최적화
                    @org.hibernate.annotations.Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "mov-"),
                    @org.hibernate.annotations.Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d") }
    )
    private String id;

    private String title;
    private String publisher;
    private String pubDate;
    private String description;

    private String imagePath;
    private String isbn;

    @OneToMany(mappedBy = "book")
    private List<BookGenreEntity> genres = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<AuthorEntity> authors = new ArrayList<>();


}
