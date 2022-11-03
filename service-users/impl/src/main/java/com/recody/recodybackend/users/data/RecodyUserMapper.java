package com.recody.recodybackend.users.data;

import com.recody.recodybackend.users.RecodyUserInfo;
import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.SocialProvider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        imports = {Role.class, SocialProvider.class} )
public interface RecodyUserMapper {
    
    RecodyUserInfo map(RecodyUserEntity entity);

}
