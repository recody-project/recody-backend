package com.recody.recodybackend.common;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

public class SpringEventProcessingStrategy implements Condition {
    
    public static final String SPRING = "spring";
    
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Objects.equals( context.getEnvironment().getProperty( Recody.RECODY_EVENT_PROCESSOR_TYPE ), SPRING );
    }
}
