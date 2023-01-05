package com.recody.recodybackend.drama.features.registerdramacredit;

import com.recody.recodybackend.drama.RecodyDramaApplication;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.data.people.DramaActorEntity;
import com.recody.recodybackend.drama.features.fetchdramacredit.FetchDramaCredit;
import com.recody.recodybackend.drama.features.fetchdramacredit.FetchDramaCreditHandler;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCreditResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles( "test" )
@ContextConfiguration( classes = RecodyDramaApplication.class )
class DefaultRegisterDramaCreditHandlerTest {
    
    public static final int TMDB_ID = 69740;
    
    @Autowired
    RegisterDramaCreditHandler<DramaEntity> registerDramaCreditHandler;
    
    @Autowired
    FetchDramaCreditHandler<TMDBDramaCreditResponse> fetchDramaCreditHandler;
    
    @Autowired
    DramaRepository dramaRepository;
    
    
    @BeforeEach
    void before() {
        // 드라마가 이미 저장되어 있을때,
        DramaEntity entity = DramaEntity.builder().tmdbId( TMDB_ID ).build();
        DramaEntity saved = dramaRepository.save( entity );
        System.out.println( "saved drama entity = " + saved );
    }
    @Test
    @DisplayName( "credit 정보를 가져와 저장했다면," +
                  "추가 트랜잭션에서 쿼리했을 때 actor 정보를 가져올 수 있다." )
    @Transactional
    void test01() throws InterruptedException {
        // given
        TMDBDramaCreditResponse handle = fetchDramaCreditHandler.handle( FetchDramaCredit.builder().tmdbId( TMDB_ID )
                                                                                 .locale( Locale.KOREAN )
                                                                                         .build() );
        DramaEntity registered =
                registerDramaCreditHandler.register(
                        RegisterDramaCredit.builder()
                                           .tmdbDramaId( TMDB_ID )
                                           .response( handle )
                                           .locale( Locale.KOREAN )
                                           .build() );
        
        // when
        // 저장이 완료될떄까지 기다린다.
        
        Thread.sleep( 5000 );
        
        // 현재 트랜잭션을 끝내고 추가 쿼리를 위해 새로운 트랜잭션을 시작한다.
        // 다른 쓰레드에서 저장되는 배우 정보를 반영한 쿼리를 하기 위함.
        if (TestTransaction.isActive()){
            System.out.println("트랜잭션 있음.");
            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        }
        
        DramaEntity dramaEntity = dramaRepository.findByTmdbId( registered.getTmdbId() ).orElseThrow();
    
        
        // then
        List<DramaActorEntity> actors = dramaEntity.getActors();
        System.out.println( "actors = " + actors );
        assertThat( actors ).isNotEmpty();
    }
    
}