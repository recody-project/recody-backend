package com.recody.recodybackend.users.features.allusers;

import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserMapper;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.events.UserCreated;
import com.recody.recodybackend.users.exceptions.AdminAuthorizationRequiredException;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultFetchAllUsersHandler implements FetchAllUsersHandler {
    
    private final RecodyUserRepository userRepository;
    private final RecodyUserMapper userMapper;
    
    @Override
    public List<UserCreated> handle(FetchAllUsers command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        RecodyUserEntity userEntity
                = userRepository.findById( userId ).orElseThrow( UserNotFoundException::new );
        if ( !userEntity.getRole().equals( Role.ROLE_ADMIN ) ){
            throw new AdminAuthorizationRequiredException();
        }
    
        List<RecodyUserEntity> allMembers = userRepository.findAllByRole( Role.ROLE_MEMBER );
        List<UserCreated> userEvents = userMapper.toEvent( allMembers );
        log.info( "all users: {}", userEvents.size() );
        return userEvents;
    }
}
