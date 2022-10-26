package com.recody.recodybackend.common.exceptions;

public interface ErrorType {
    
    /**
     * 에러 타입의 이름입니다.
     * <ul>
     *     <li> 하위 구현체를 enum 으로 정의하면 이 메서드는 자동적으로 오버라이드됩니다. </li>
     * </ul>
     */
    String name();
    
    /**
     * 에러 타입의 에러코드입니다.
     * <ul>
     *     <li> 에러코드는 해당 에러타입에서 상세한 이유를 명시할 수 있습니다. </li>
     *     <li> 더 상세한 에러 메시지를 유저에게 전달하기 위해서 에러코드를 사용할 수 있습니다. </li>
     * </ul>
     */
    String getErrorCode();
}
