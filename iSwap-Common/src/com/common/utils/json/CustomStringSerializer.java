/*
 * @(#)CustomDateSerializer.java
 *
 * Copyright (C) 2005, zgcworld All right reserved.
 * see the site: http://www.zgcworld.com
 */

package com.common.utils.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * jackson 序列化时间 处理时间输出问题
 * @author zhangx
 * @since 2011-1-17 下午02:16:29
 * @name com.common.utils.json.CustomDateSerializer.java
 * @version 1.0
 */

public class CustomStringSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
                    JsonProcessingException {
        if (value == null || value.equals("")) {
             value = "(空)";
        }
        jgen.writeString(value);
    }
}
