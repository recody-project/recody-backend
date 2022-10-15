package com.recody.recodybackend.record.features.getrecordcontents;

import com.recody.recodybackend.record.RecordContent;

import java.util.List;


public interface GetRecordContentsHandler {
    
    /**
     * 유저가 쓴 감상평들의 작품리스트를 가져온다.
     * 결과에는 감상한 날짜가 포함된다.
     * @param command: userId, category, 등으로 가져올 수 있다.
     * */
    List<RecordContent> handle(GetRecordContents command);
    

}
