package cn.hjljy.fastboot.autoconfig.config;

import cn.hjljy.fastboot.common.utils.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.zip.DataFormatException;

/**
 * @author yichaofan
 * 描述：jackson全局配置
 * 1 将Long类型转换成string类型返回，避免大整数导致前端精度丢失的问题
 * 2 将LocalDateTime全局返回时间戳（方便前端处理）并且将参数里面的时间戳转换成LocalDateTime
 */
@Configuration
@Slf4j
public class JacksonCustomizerConfig {
    /**
     * 描述:统一配置类型的转换策略
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            //将Long类型转换成string类型返回，避免大整数导致前端精度丢失的问题
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            //将LocalDateTime全局返回时间戳（方便前端处理）并且将参数里面的时间戳转换成LocalDateTime
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class, new MyLocalDateTimeDeserializer());
        };
    }

    /**
     * 描述：将LocalDateTime转换为毫秒级时间戳
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            if (value != null) {
                long timestamp = value.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                gen.writeNumber(timestamp);
            }
        }
    }

    /**
     * 描述：
     * 1 将毫秒级时间戳转换为LocalDateTime
     * 2 将字符串解析成LocalDateTime
     */
    public static class MyLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            long timestamp = p.getValueAsLong();
            if (timestamp > 0) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+8"));
            } else {
                try {
                    return LocalDateTimeUtil.stringToFormat(p.getText().trim());
                } catch (DateTimeParseException e) {
                    log.info("解析时间参数失败："+e.getMessage());
                }
                LocalDateTimeDeserializer defaultDeserializer = LocalDateTimeDeserializer.INSTANCE;
                return defaultDeserializer.deserialize(p, deserializationContext);
            }
        }
    }
}
