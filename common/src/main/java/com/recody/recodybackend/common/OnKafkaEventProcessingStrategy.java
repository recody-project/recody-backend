package com.recody.recodybackend.common;

import lombok.NonNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

public class OnKafkaEventProcessingStrategy implements Condition {
    
    public static final String KAFKA = "kafka";
    
    @Override
    public boolean matches(ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        return Objects.equals( context.getEnvironment().getProperty( Recody.RECODY_EVENT_PROCESSOR_TYPE ), KAFKA );
    }
}
