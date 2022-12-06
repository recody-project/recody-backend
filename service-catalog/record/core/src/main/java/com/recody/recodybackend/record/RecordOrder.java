package com.recody.recodybackend.record;

import lombok.Getter;
import org.springframework.data.domain.Sort;

public class RecordOrder {
    
    /**
     * RecordEntity 의 감상일 변수명과 같아야 합니다.
     */
    public static final String APPRECIATION_DATE_COLUMN_NAME = "appreciationDate";
    /**
     * RecordEntity 의 생성시간 변수명과 같아야 합니다.
     */
    public static final String CREATED_AT_COLUMN_NAME = "createdAt";
    public static final RecordOrder AppreciationDate = new RecordOrder( APPRECIATION_DATE_COLUMN_NAME );
    public static final RecordOrder CreatedDate = new RecordOrder( CREATED_AT_COLUMN_NAME );
    public static final RecordOrder UnOrdered = new RecordOrder( null );
    @Getter
    private final String columnName;
    
    private RecordOrder(String columnName) {
        this.columnName = columnName;
    }
    
    public static RecordOrder of(String value) {
        if ( value == null || value.isEmpty() ) {
            return UnOrdered;
        }
        if ( value.equals( APPRECIATION_DATE_COLUMN_NAME ) )
            return new RecordOrder( APPRECIATION_DATE_COLUMN_NAME );
        else if ( value.equals( CREATED_AT_COLUMN_NAME ) )
            return new RecordOrder( CREATED_AT_COLUMN_NAME );
        else
            throw new RuntimeException( "올바른 Record Order 가 아닙니다." );
    }
    
    public boolean isUnOrdered() {
        return this.columnName == null || this.equals( UnOrdered );
    }
    
    /**
     * JPA, QueryDsl 에서 쿼리에 사용할 수 있는 Sort 객체로 반환합니다.
     */
    public Sort toSort() {
        // 현재는 내림차순으로만 사용
        return this.isUnOrdered()
                       ? Sort.unsorted()
                       : Sort.by( Sort.Direction.DESC, this.getColumnName() );
    }
    
    
    @Override
    public String toString() {
        return "{\"RecordOrder\":{"
               + "\"columnName\":" + ((columnName != null) ? ("\"" + columnName + "\"") : null)
               + "}}";
    }
}
