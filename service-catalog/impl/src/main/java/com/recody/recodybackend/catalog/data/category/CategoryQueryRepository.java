package com.recody.recodybackend.catalog.data.category;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;

import java.util.Optional;

public interface CategoryQueryRepository {
    
    /**
     * 유저가 작품에 수정한 카테고리 내역이 있는지 확인하고 카테고리 엔티티를 반환하는 메서드. <br><br>
     * <ul>
     *     <li> Category 가 soft delete 되면 PersonalizedCategory 가 참조하는 Category 가 여전히 존재하지만
     *     참조하는 Category 를 쿼리할 수 없게 된다. 따라서 PersonalizedCategory 를 쿼리한 후 추가적으로 Category 를 쿼리하는 방식이 아닌
     *     CategoryEntity 를 바로 쿼리할 수 있게 구현한다.</li>
     * </ul>
     * <b> 쿼리 예시 </b>
     * <pre>{@code     select
     *         categoryen1_.id as id1_1_,
     *         categoryen1_.basic as basic2_1_,
     *         categoryen1_.deleted_at as deleted_3_1_,
     *         categoryen1_.icon_url as icon_url4_1_,
     *         categoryen1_.name as name5_1_,
     *         categoryen1_.user_id as user_id6_1_
     *     from
     *         category_personalization personaliz0_
     *     inner join
     *         category categoryen1_
     *             on personaliz0_.category_id=categoryen1_.id
     *     where
     *         personaliz0_.user_id=?
     *         and personaliz0_.content_id=?
     *         and (
     *             categoryen1_.deleted_at is null
     *         )} </pre>
     * @param userId 유저의 id
     * @param content content entity
     * @return 카테고리 엔티티의 Optional
     */
    Optional<CategoryEntity> findByUserIdAndContentFromJoinedPersonalizedCategory(Long userId, CatalogContentEntity content);
    
}
