package com.recody.recodybackend.catalog.data.record;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class QueryDslUtils {
    
    
    /**
     * sort 객체가 가지고 있는 칼럼 정보와 방향정보를 바탕으로 QueryDsl 쿼리에서 사용할 수 있는
     * OrderSpecifier 들을 반환한다.
     * @param sort : 정렬할 column 의 이름을 가지고있는 Sort 객체.
     * @param qEntity : 정렬할 column 가 속한 QEntity
     * @return : Q 타입 쿼리의 정렬에 사용하는 OrderSpecifier 객체
     */
    // Sort 객체를 받아 OrderSpecifier<?>[] 객체를 반환한다.
    public static OrderSpecifier<?>[] getOrderSpecifiers(Sort sort, Path<?> qEntity){
        List<OrderSpecifier<?>> orderList = new ArrayList<>();
        for (Sort.Order order : sort) {
            String property = order.getProperty();
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            orderList.add(getSortedColumn(direction, qEntity, property));
        }
        return orderList.toArray(OrderSpecifier[]::new);
    }
    
    /**
     * @param order : 방향
     * @param parent : 부모 Path. 보통 정렬할 field 가 속해있는 Q 타입 Entity 를 의미한다.
     * @param fieldName : 정렬하고자 하는 field 의 이름
     * @return 정렬할 field 의 타입을 가지는 OrderSpecifier 객체.
     */
    private static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }
    
    public static <T> JPAQuery<T> applyPageable(Pageable pageable, JPAQuery<T> query) {
        JPAQuery<T> clonedQuery = query.clone();
        return clonedQuery.limit( pageable.getPageSize() )
                    .offset( pageable.getOffset() );
    }
}
