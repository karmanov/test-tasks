package io.karmanov.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageConsumingResult {

    private List<Message> messages;
    private boolean isAllMessagesConsumed;
}
