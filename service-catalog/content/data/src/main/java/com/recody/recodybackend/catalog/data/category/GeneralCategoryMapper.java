package com.recody.recodybackend.catalog.data.category;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Target( value = {ElementType.METHOD} )
@Retention( value = RetentionPolicy.CLASS )
public @interface GeneralCategoryMapper {

}
