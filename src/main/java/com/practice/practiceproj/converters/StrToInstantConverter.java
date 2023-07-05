package com.practice.practiceproj.converters;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;

public class StrToInstantConverter implements Converter<String, Instant> {
    @Override
    public Instant convert(@NotNull String source) {
        return Instant.ofEpochMilli(Long.parseLong(source));
    }
}
