package com.recody.recodybackend.drama.features.synchronizedramadetail;

import com.recody.recodybackend.drama.DramaId;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSynchronizeDramaDetailHandler implements SynchronizeDramaDetailHandler<Void> {
    
    private static final List<Locale> locales = List.of( Locale.KOREAN, Locale.ENGLISH );
    
    private final RegisterDramaDetailHandler<DramaEntity> registerDramaDetailHandler;
    
    private final RegisterDramaCreditHandler<DramaEntity> registerDramaCreditHandler;
    
    private final FetchDramaDetailHandler<TMDBDramaDetail> fetchDramaDetailHandler;
    
    private final FetchDramaCreditHandler<TMDBDramaCreditResponse> fetchDramaCreditHandler;
    private final DramaRepository dramaRepository;
    
    
    @Override
    @Transactional
    public Void handle(DramaId id) {
        String idValue = id.getValue();
        Optional<DramaEntity> optionalDrama = dramaRepository.findById( idValue );
        
        if ( optionalDrama.isEmpty() ) {
            log.warn( "드라마를 찾을 수 없습니다. {}", idValue );
            return null;
        }
        Integer tmdbId = optionalDrama.get().getTmdbId();
        for (Locale locale : locales) {
            TMDBDramaDetail detail = fetchDramaDetailHandler.handle(
                    FetchDramaDetail.builder()
                                    .locale( locale )
                                    .tmdbId( tmdbId )
                                    .build() );
            registerDramaDetailHandler.handle(
                    RegisterDramaDetail.builder()
                                       .detail( detail )
                                       .locale( locale )
                                       .build() );
            TMDBDramaCreditResponse credit = fetchDramaCreditHandler.handle(
                    FetchDramaCredit.builder()
                                    .locale( locale )
                                    .tmdbId( tmdbId )
                                    .build() );
            registerDramaCreditHandler.register(
                    RegisterDramaCredit.builder()
                                       .response( credit )
                                       .tmdbDramaId( tmdbId )
                                       .locale( locale )
                                       .build() );
            log.trace( "locale {} 에 대한 상세정보 저장 완료.", locale );
        }
        
        
        return null;
    }
}
