package com.jtljia.gwote.datasource;


/**
 * @author chenjindong
 * @date 2019-02-18 17:48:35
 */
public class DatabaseContextHolder {

    private static final ThreadLocal<EnumDataSource> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSource(EnumDataSource type){
        CONTEXT_HOLDER.set(type);
    }

    public static EnumDataSource getDataSource(){
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSource(){
        CONTEXT_HOLDER.remove();
    }

}
