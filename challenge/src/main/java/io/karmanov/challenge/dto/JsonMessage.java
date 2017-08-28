package io.karmanov.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsonMessage implements Message {

    private String status;
    private String id;

    @JsonIgnore
    @Override
    public String getHash() {
        return id;
    }
}
