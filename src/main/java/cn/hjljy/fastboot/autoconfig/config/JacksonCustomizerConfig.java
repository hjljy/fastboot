package cn.hjljy.fastboot.autoconfig.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author yichaofan
 * 描述：jackson全局配置
 * 1 将Long类型转换成string类型返回，避免大整数导致前端精度丢失的问题
 * 2 将LocalDateTime全局返回时间戳（方便前端处理）并且将参数里面的时间戳转换成LocalDateTime
 */
@Configuration
public class JacksonCustomizerConfig {
    /**
     * 描述:统一配置类型的转换策略
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            //将Long类型转换成string类型返回，避免大整数导致前端精度丢失的问题
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            builder.serializerByType(Long.class,ToStringSerializer.instance);
            //将LocalDateTime全局返回时间戳（方便前端处理）并且将参数里面的时间戳转换成LocalDateTime
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());
        };
    }

    /**
     * 描述：将LocalDateTime转换为毫秒级时间戳
     *
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
     * 描述：将毫秒级时间戳转换为LocalDateTime
     *
     */
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            long timestamp = p.getValueAsLong();
            if (timestamp > 0) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+8"));
            } else {
                return null;
            }
        }
    }

    /**
     * description:LocalDateTime转换器，用于转换RequestParam和PathVariable参数
     * 接收毫秒级时间戳字符串——>LocalDateTime
     */
    @Bean
    public StringToLocalDateTimeConverter localDateTimeConverter1() {
        return  (source -> LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(source)), ZoneOffset.of("+8")));
    }

    interface StringToLocalDateTimeConverter extends Converter<String, LocalDateTime> {

    }
}
