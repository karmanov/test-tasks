package io.karmanov.watermark.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Optional;

/**
 * Possible document types
 */
@RequiredArgsConstructor
public enum DocumentType {
    BOOK(1), JOURNAL(2);

    private final Integer code;

    @JsonValue
    public Integer getCode() {
        return code;
    }

    public static Optional<DocumentType> fromCode(Integer code) {
        return EnumSet.allOf(DocumentType.class).stream().filter(t -> t.getCode().equals(code)).findFirst();
    }

    @JsonCreator
    public static DocumentType valueOf(Integer code) {
        return fromCode(code).orElse(null);
    }


}
