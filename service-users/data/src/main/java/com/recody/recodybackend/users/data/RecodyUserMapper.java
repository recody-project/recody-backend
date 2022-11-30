package com.recody.recodybackend.users.data;

import com.recody.recodybackend.users.RecodyUserInfo;
import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.SocialProvider;
import com.recody.recodybackend.users.events.UserCreated;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        imports = {Role.class, SocialProvider.class} )
public interface RecodyUserMapper {
    
    RecodyUserInfo map(RecodyUserEntity entity);
    
    UserCreated toEvent(RecodyUserEntity entity);
    List<UserCreated> toEvent(List<RecodyUserEntity> entity);

}
