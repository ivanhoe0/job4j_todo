package ru.job4j.todo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StringToDateTImeConverter  implements Converter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        String timeToParse = source.replaceAll(" ", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy, h:mm a");
        LocalDateTime localDateTime = LocalDateTime.parse(timeToParse, formatter);
        return localDateTime.atZone(ZoneId.systemDefault());
    }
}
