package features;

import com.recody.recodybackend.book.RecodyBookApplication;
import com.recody.recodybackend.book.features.searchbooks.SearchBooks;
import com.recody.recodybackend.book.features.searchbooks.SearchBooksHandler;
import com.recody.recodybackend.book.features.searchbooks.dto.NaverBookSearchNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyBookApplication.class)
class DefaultNaverSearchBooksHandlerTest {
    @Autowired
    SearchBooksHandler searchBooksHandler;

    @Test
    @DisplayName("네이버 책 검색 결과 확인")
    void test01() {
        SearchBooks command = SearchBooks.builder().bookName("스타벅스").build();
        List<NaverBookSearchNode> results = searchBooksHandler.handle(command);

        for (NaverBookSearchNode result : results) {
            System.out.println(result.toString());
        }
    }

}
