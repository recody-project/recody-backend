package com.recody.recodybackend.catalog.features.record.getrecordcontents;

import com.recody.recodybackend.record.RecordContent;

import java.util.List;


public interface GetRecordContentsHandler {
    
    /**
     * 유저가 쓴 감상평들의 작품리스트를 가져온다.
     * 결과에는 감상한 날짜가 포함된다.
     * <ul>
     *     <li> 작품의 카테고리로 필터링할 수 있다. </li>
     *     <li> 작품의 장르로 필터링할 수 있다. </li>
     * </ul>
     * @param command: userId, category, 등으로 가져올 수 있다.
     * */
    List<RecordContent> handle(GetRecordContents command);
    

}
