package com.recody.recodybackend.insight.features.getusersrecordcount;

public interface GetUsersRecordCountHandler<R> {
    
    R handle(GetUsersRecordCount command);
}
