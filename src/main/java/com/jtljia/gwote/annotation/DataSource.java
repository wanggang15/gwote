package com.jtljia.gwote.annotation;


import com.jtljia.gwote.datasource.EnumDataSource;
import org.springframework.beans.factory.annotation.Required;

import java.lang.annotation.*;

/**
 * 切换数据注解的标记注解.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    @Required EnumDataSource dataSourceType();
}
