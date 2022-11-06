package com.recody.recodybackend.users.features.getuserinfo;

import com.recody.recodybackend.users.RecodyUserInfo;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserMapper;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetUserInfoHandler implements GetUserInfoHandler{
    
    private final RecodyUserRepository userRepository;
    private final RecodyUserMapper userMapper;
    
    @Override
    public RecodyUserInfo handle(GetUserInfo command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        RecodyUserEntity userEntity
                = userRepository.findById( userId ).orElseThrow( UserNotFoundException::new );
        RecodyUserInfo userInfo = userMapper.map( userEntity );
        log.info( "유저 정보를 반환합니다.: {}", userInfo );
        return userInfo;
    }
}
