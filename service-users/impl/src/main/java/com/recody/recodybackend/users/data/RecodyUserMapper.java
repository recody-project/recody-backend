package com.recody.recodybackend.users.data;

import com.recody.recodybackend.users.features.login.membership.RecodyUserInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecodyUserMapper {
    
    RecodyUserInfo map(RecodyUser entity);

}
