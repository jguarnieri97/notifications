package ar.edu.unlam.tpi.notifications.utils;

import java.text.SimpleDateFormat;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Converter {
    
    private static ModelMapper modelMapper = new ModelMapper();
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static <T> T convertToEntity(Object object, Class<T> entityClass) {
        return modelMapper.map(object, entityClass);
    }

    public static <T> T convertToDto(Object object, Class<T> dtoClass) {
        return modelMapper.map(object, dtoClass);
    }

    public static <T> T convertToClass(Object object, Class<T> targetClass) {
        return objectMapper.convertValue(object, targetClass);
    }

}
