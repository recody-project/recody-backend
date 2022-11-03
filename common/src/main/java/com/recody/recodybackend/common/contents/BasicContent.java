package com.recody.recodybackend.common.contents;

public interface BasicContent extends Content<String>{
    
    @Override
    String getContentId();
    @Override
    BasicCategory getCategory();
}
