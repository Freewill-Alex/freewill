package com.freewill.admin.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.freewill.common.constant.FormatConst;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

/**
 * @author GaoJian
 */
@Configuration
public class LocalDateTimeSerializerConfig {

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Map<Class<?>, JsonSerializer<?>> serialMap = new HashMap<>(16);
        Map<Class<?>, JsonDeserializer<?>> deserialMap = new HashMap<>(16);

        serialMap.put(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_DATE_TIME_FORMAT)));
        serialMap.put(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_DATE_FORMAT)));
        serialMap.put(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_TIME_FORMAT)));

        deserialMap.put(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_DATE_TIME_FORMAT)));
        deserialMap.put(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_DATE_FORMAT)));
        deserialMap.put(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_TIME_FORMAT)));


        return builder -> builder.serializersByType(serialMap).deserializersByType(deserialMap);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(FormatConst.DEFAULT_TIME_FORMAT)));
        om.setVisibility(ALL, NONE).setVisibility(FIELD, ANY);
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(javaTimeModule)
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module());

        return om;
    }
}