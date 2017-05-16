package io.karmanov.watermark.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Optional;

/**
 * Possible {@link Document} topics
 */
@RequiredArgsConstructor
public enum DocumentTopic {
    BUSINESS(1), SCIENCE(2), MEDIA(3);

    private final Integer code;

    @JsonValue
    public Integer getCode() {
        return code;
    }

    public static Optional<DocumentTopic> fromCode(Integer code) {
        return EnumSet.allOf(DocumentTopic.class).stream().filter(t -> t.getCode().equals(code)).findFirst();
    }

    @JsonCreator
    public static DocumentTopic valueOf(Integer code) {
        return fromCode(code).orElse(null);
    }
}
