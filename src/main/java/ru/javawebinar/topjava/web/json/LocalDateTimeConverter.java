package ru.javawebinar.topjava.web.json;


import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.TimeUtil;


import java.text.ParseException;

import java.time.LocalDateTime;
import java.util.Locale;

public class LocalDateTimeConverter implements Formatter<LocalDateTime>{


    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return TimeUtil.parseLocalDateTime(text);
    }

    @Override
    public String print(LocalDateTime ldt, Locale locale) {
        return TimeUtil.toString(ldt);
    }
}
