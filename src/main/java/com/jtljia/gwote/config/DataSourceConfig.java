package com.jtljia.gwote.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.jtljia.gwote.datasource.DynamicDataSource;
import com.jtljia.gwote.datasource.EnumDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.work")
    public DataSource getWorkDataSource(){
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.localhost")
    public DataSource getLocalDataSource(){
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(){
        Map<Object,Object> dataSourceMap=new HashMap<>();

        dataSourceMap.put(EnumDataSource
                .workDataSource,getWorkDataSource());
        dataSourceMap.put(EnumDataSource.localDataSource,getLocalDataSource());

        DynamicDataSource dynamicDataSource=new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSourceMap);

        dynamicDataSource.setDefaultTargetDataSource(getLocalDataSource());

        return dynamicDataSource;
    }
}
