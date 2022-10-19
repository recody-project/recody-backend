package com.recody.recodybackend.common.contents;

import java.util.List;

public interface BasicContentDetail extends BasicContent{
    
    <T extends Genre> List<T> getGenres();


}
