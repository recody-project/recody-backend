package com.recody.recodybackend.catalog.features.changegenreoncontent;

import com.recody.recodybackend.event.GenrePersonalized;

public interface ChangeGenresOnContentHandler {
    
    /**
     * 추가, 삭제할 수 있는 장르 개수의 한계는?
     * 설정할 모든 ID 를 넣어야 한다.
     */
    GenrePersonalized handle(ChangeGenresOnContent command);



}
