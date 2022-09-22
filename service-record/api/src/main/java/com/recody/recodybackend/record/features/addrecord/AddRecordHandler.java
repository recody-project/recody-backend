package com.recody.recodybackend.record.features.addrecord;

/**
 * 감상평을 추가한다.
 * 감상평은 제목, 내용, 작품 ID 가 포함되어야 한다.
 * */
public interface AddRecordHandler {
    
    String handle(AddRecord command);
    

}