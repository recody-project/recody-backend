package com.recody.recodybackend.users.data;


import com.recody.recodybackend.users.events.UserCreated;
import com.recody.recodybackend.users.features.projection.UserEventPublisher;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

/**
 * 유저가 새로 생성되거나 수정될 때 감지하여 이벤트를 발행합니다.
 * @author motive
 */
@Slf4j
@NoArgsConstructor // 엔티티 리스너가 정의되려면 필요합니다.
@Component
public class RecodyUserEntityListener {
    
    private UserEventPublisher userEventPublisher;
    

    @Autowired
    public RecodyUserEntityListener(UserEventPublisher userEventPublisher) {
        this.userEventPublisher = userEventPublisher;
    }
    
    /**
     * Users 서비스에서 유저가 생성되는 경우 Record, Catalog 등에 유저를 동기화할 수 있도록 유저 생성 이벤트를 발행합니다.
     */
    @PostPersist
    void with(RecodyUserEntity entity) {
        UserCreated event = UserCreated.builder()
                                       .userId( entity.getUserId() )
                                       .role( entity.getRole() )
                                       .email( entity.getEmail() )
                                       .build();
        userEventPublisher.publish( event );
    }
}
