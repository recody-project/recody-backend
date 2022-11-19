package com.recody.recodybackend.book.features;

import com.recody.recodybackend.book.Book;
import com.recody.recodybackend.book.features.searchbooks.SearchBooks;
import com.recody.recodybackend.book.features.searchbooks.SearchBooksResult;

import java.util.List;

public interface BookSearchService {
    SearchBooksResult searchBooks(SearchBooks command);

}
