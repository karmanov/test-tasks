package com.challenge.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Optional;

@RequiredArgsConstructor
public enum LinkResourceType {
    INTERNAL(1), EXTERNAL(2);

    @Getter
    private final Integer code;

    public static Optional<LinkResourceType> fromCode(Integer code) {
        return EnumSet.allOf(LinkResourceType.class).stream().filter(t -> t.getCode().equals(code)).findFirst();
    }

    @JsonCreator
    public static LinkResourceType valueOf(Integer code) {
        return fromCode(code).orElse(null);
    }
}
