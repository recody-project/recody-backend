package com.recody.recodybackend.commonbootutils.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

@RequiredArgsConstructor
@Slf4j
public class CustomSequenceIdGenerator extends SequenceStyleGenerator {
    
    public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";
    public static final String VALUE_PREFIX_DEFAULT = "";
    private String valuePrefix;
    
    public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
    public static final String NUMBER_FORMAT_DEFAULT = "%d";
    private String numberFormat;
    
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        // id 가 주어지면 그대로 사용한다.
        Serializable id = session.getEntityPersister(null, object)
                                 .getClassMetadata().getIdentifier(object, session);
        if (id != null ) return id;
        // 새로운 id 를 시퀀스에서 생성한다.
        Serializable sequenceFromSuper = super.generate(session, object);
        String generatedId = valuePrefix + String.format(numberFormat, sequenceFromSuper);
        log.trace("generatedId: {}", generatedId);
        return generatedId;
    }
    
    
    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER, params, VALUE_PREFIX_DEFAULT);
        numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT);
    }
}
