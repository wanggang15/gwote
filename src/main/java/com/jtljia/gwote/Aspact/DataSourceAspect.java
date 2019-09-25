package com.jtljia.gwote.Aspact;

import com.jtljia.gwote.annotation.DataSource;
import com.jtljia.gwote.datasource.DatabaseContextHolder;
import com.jtljia.gwote.datasource.EnumDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;


/**
 * 切换数据源切换
 * @author chenjindong
 * @date 2019-02-18 18:00:15
 */
@Aspect
@Component
public class DataSourceAspect {

    private static final Logger LOGGER= LoggerFactory.getLogger(DataSourceAspect.class);


    @Pointcut("@annotation(com.jtljia.gwote.annotation.DataSource)")
    public void pointCut() {

    }

    /**
     * @author chenjindong
     * @date 2019-02-18 18:00:57
     * 环绕切面，动态切换数据源
     * @param pjp 处理连接点
     * @return
     * @throws Throwable
     */
    @Around("pointCut()")
    public Object aroundProcess(ProceedingJoinPoint pjp) throws Throwable {

        EnumDataSource dataSource = getDataSource(pjp);
        try {
            LOGGER.info("change datasource {} beginning... {}",dataSource,LocalDateTime.now());
            if(dataSource!=null) {
                DatabaseContextHolder.setDataSource(dataSource);
            }
            Object result = pjp.proceed();
            return result;
        } catch (Exception e) {
            LOGGER.error("change data source {} exception...!!! {}",dataSource,e);
            throw new RuntimeException(e);
        }
        finally {
            LOGGER.info("change datasource {} ending...{}",dataSource,LocalDateTime.now());
            DatabaseContextHolder.clearDataSource();
        }
    }

    private EnumDataSource getDataSource(JoinPoint joinPoint) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == args.length) {
                    DataSource dataSource=method.getAnnotation(DataSource.class);
                    if(dataSource!=null){
                        return dataSource.dataSourceType();
                    }
                }
            }
        }
        return null;
    }

}
