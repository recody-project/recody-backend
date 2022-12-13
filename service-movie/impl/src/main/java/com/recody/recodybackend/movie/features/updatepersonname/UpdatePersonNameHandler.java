package com.recody.recodybackend.movie.features.updatepersonname;

public interface UpdatePersonNameHandler<T> {
    
    T handle(UpdateKoreanPersonName command);
    
}
