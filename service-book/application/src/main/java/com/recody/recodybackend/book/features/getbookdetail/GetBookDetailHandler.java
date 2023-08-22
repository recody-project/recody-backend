package com.recody.recodybackend.book.features.getbookdetail;
/**
 * 책 상세정보를 가져옵니다.
 * - 기본적인 상세정보는 FetchBookDetail 을 통해서 가져옵니다.
 * @author motive
 */
public interface GetBookDetailHandler<R> {
    R handle(GetBookDetail query);
}
