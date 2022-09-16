package com.recody.recodybackend.users.features.generatenickname;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DefaultNicknameGenerator implements NicknameGenerator {
    
    private final Faker faker = new Faker();
    
    @Override
    public String randomNickname() {
        String firstName = faker.name().firstName();
        String hex = faker.random().hex();
        String pokemon = faker.pokemon().name();
        String dragonball = faker.dragonBall().character();
        StringBuilder sb = new StringBuilder();
        return sb.append(pokemon).append("_").append(hex)
                 .toString().trim().replace(" ", "");
    }
}
