package com.jtljia.gwote.util;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface ObjectToString {


    default Class[] getBasicDataTypes(){
        Class[] basicDataType = new Class[18];

        //基本数据类型--整型(包含装箱)
        basicDataType[0] = byte.class;
        basicDataType[1] = Byte.class;

        basicDataType[2] = short.class;
        basicDataType[3] = Short.class;

        basicDataType[4] = int.class;
        basicDataType[5] = Integer.class;

        basicDataType[6] = long.class;
        basicDataType[7] = Long.class;

        //基本数据类型--浮点型(包含装箱)
        basicDataType[8] = float.class;
        basicDataType[9] = Float.class;

        basicDataType[10] = double.class;
        basicDataType[11] = Double.class;

        //基本数据类型--布尔(包含装箱)
        basicDataType[12] = boolean.class;
        basicDataType[13] = Boolean.class;

        //基本数据类型--字符型(包含装箱)
        basicDataType[14] = char.class;
        basicDataType[15] = Char.class;

        return basicDataType;
    }

    default String toString(Object object){
        Map<String,String> properties = new HashMap<>();

        if (object != null){
            Class[] basicDataTypes = getBasicDataTypes();
            Field[] declaredFields = object.getClass().getDeclaredFields();

            for (Field declaredField : declaredFields) {
                Class type = declaredField.getType();
                for (Class basicDataType : basicDataTypes) {
                    if (type == basicDataType){
                        properties.put(declaredField.getName(),String.valueOf(declaredField));
                        break;
                    }
                }
            }
        }

        return String.valueOf(properties);
    }
}
