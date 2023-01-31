package com.recody.recodybackend.drama.features.registernetwork;

import com.recody.recodybackend.common.data.AsyncLinkingEntityManager;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.genre.DramaGenreCodeEntity;
import com.recody.recodybackend.drama.data.genre.DramaGenreEntity;
import com.recody.recodybackend.drama.data.genre.DramaGenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DramaGenreRegistrar implements AsyncLinkingEntityManager<DramaGenreEntity, DramaEntity, DramaGenreCodeEntity> {
    
    private final DramaGenreRepository genreRepository;
    
    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public DramaGenreEntity save(DramaEntity entity, DramaGenreCodeEntity entity2) {
        // 파라미터들이 null 이면 에러 발생.
        if ( Objects.isNull( entity ) || Objects.isNull( entity2 ) ) {
            log.error( "엔티티가 null 이므로 장르를 설정할 수 없음. drama: {}, genreCode: {}", entity, entity2 );
            throw new InternalServerError();
        }
        
        // 장르 정보가 드라마에 등록되어있는지 확인. 1개 또는 없음.
        Optional<DramaGenreEntity> optionalGenre =
                genreRepository.findByDramaAndGenreCode( entity, entity2 );
    
        // 등록되어 있다면 그대로 반환.
        if ( optionalGenre.isPresent() ) {
            log.trace( "이미 장르 정보가 존재하므로 그대로 반환." );
            return optionalGenre.get();
        }
        
        // 등록되어 있지 않다면 새로운 드라마 장르를 등록함.
        DramaGenreEntity newEntity = DramaGenreEntity.builder().drama( entity )
                                                 .genreCode( entity2 )
                                                 .build();
        DramaGenreEntity savedGenreEntity = genreRepository.save( newEntity );
        log.trace( "드라마에 장르 정보를 등록하였습니다. savedGenreEntity: {}", savedGenreEntity );
        return null;
    }
}
