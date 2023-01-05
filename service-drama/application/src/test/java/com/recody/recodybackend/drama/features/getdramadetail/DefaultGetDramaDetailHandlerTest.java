package com.recody.recodybackend.drama.features.getdramadetail;

import com.recody.recodybackend.drama.DramaDetail;
import com.recody.recodybackend.drama.RecodyDramaApplication;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.features.fetchdramacredit.FetchDramaCredit;
import com.recody.recodybackend.drama.features.fetchdramacredit.FetchDramaCreditHandler;
import com.recody.recodybackend.drama.features.fetchdramadetail.FetchDramaDetail;
import com.recody.recodybackend.drama.features.fetchdramadetail.FetchDramaDetailHandler;
import com.recody.recodybackend.drama.features.registerdramacredit.RegisterDramaCredit;
import com.recody.recodybackend.drama.features.registerdramacredit.RegisterDramaCreditHandler;
import com.recody.recodybackend.drama.features.registerdramadetail.RegisterDramaDetail;
import com.recody.recodybackend.drama.features.registerdramadetail.RegisterDramaDetailHandler;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCreditResponse;
import com.recody.recodybackend.drama.tmdb.detail.TMDBDramaDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;

import javax.transaction.Transactional;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles( "test" )
@ContextConfiguration( classes = RecodyDramaApplication.class )
class DefaultGetDramaDetailHandlerTest {
    
    public static final Locale LOCALE = Locale.KOREAN;
    public static final int TMDB_ID = 69740;
    
    @Autowired
    RegisterDramaDetailHandler<DramaEntity> registerDramaDetailHandler;
    
    @Autowired
    FetchDramaDetailHandler<TMDBDramaDetail> fetchDramaDetailHandler;
    
    @Autowired
    FetchDramaCreditHandler<TMDBDramaCreditResponse> fetchDramaCreditHandler;
    
    @Autowired
    RegisterDramaCreditHandler<DramaEntity> registerDramaCreditHandler;
    
    @Autowired
    GetDramaDetailHandler<DramaDetail> getDramaDetailHandler;
    @Autowired
    DramaRepository dramaRepository;
    
    
    @BeforeEach
    void before() {
        // 드라마가 이미 저장되어 있을때,
        TMDBDramaDetail detail = fetchDramaDetailHandler.handle(
                FetchDramaDetail.builder().tmdbId( TMDB_ID ).locale( LOCALE ).build() );
        DramaEntity future = registerDramaDetailHandler.handle( RegisterDramaDetail.builder()
                                                                                   .detail( detail )
                                                                                   .locale( LOCALE )
                                                                                   .build() );
        TMDBDramaCreditResponse credit = fetchDramaCreditHandler.handle(
                FetchDramaCredit.builder().tmdbId( TMDB_ID ).locale( LOCALE ).build() );
    
        DramaEntity future2 = registerDramaCreditHandler.register(
                RegisterDramaCredit.builder().locale( LOCALE )
                                   .tmdbDramaId( TMDB_ID )
                                   .response( credit ).build() );
    }
    
    @Test
    @DisplayName( "기능 테스트" )
    @Transactional
    void test01() throws InterruptedException {
        // given
        Thread.sleep( 3000 );
    
        if ( TestTransaction.isActive()){
            System.out.println("트랜잭션 있음.");
            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        }
        
        DramaEntity dramaEntity = dramaRepository.findByTmdbId( TMDB_ID ).orElseThrow();
    
        // when
        DramaDetail detailDTO = getDramaDetailHandler.handle( GetDramaDetail.builder()
                                                                         .dramaId( dramaEntity.getId() )
                                                                      .locale( LOCALE )
                                                                         .build() );
    
        // then
        System.out.println(detailDTO);
        assertThat( detailDTO ).isNotNull();
    }
}