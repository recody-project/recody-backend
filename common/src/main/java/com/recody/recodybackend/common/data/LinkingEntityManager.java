package com.recody.recodybackend.common.data;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ManyToMany 연관관계를 가지는 엔티티를 저장합니다. <br>
 * 연결 테이블을 가지는 저장로직을 한 곳에서 관리하기 위해 구현합니다. <br>
 * <br>
 * <b>부가 기능</b>
 * <ol>
 *     1개의 ONE 엔티티와 연결되는 OTHER 엔티티가 여러개인 경우를 위해 <br>
 *     List 로 구현된 디폴트 메서드가 있습니다.
 * </ol>
 * <br>
 * <b>엔티티 관계 예시</b>
 * <ol>
 *     ONE -< LINKING_ENTITY >- OTHER
 * </ol>
 * @param <LINKING_ENTITY> ONE 와 OTHER 엔티티를 잇는 연결 엔티티
 * @param <ONE> 엔티티의 한쪽
 * @param <OTHER> 다른 한쪽
 * @author motive
 */
public interface LinkingEntityManager<LINKING_ENTITY, ONE, OTHER> {
    
    @Transactional
    LINKING_ENTITY save(ONE one, OTHER other);
    
    @Transactional
    default List<LINKING_ENTITY> save(ONE one, List<OTHER> others) {
        return others.stream()
                       .map(other -> this.save(one, other))
                       .collect(Collectors.toList());
    }
}
