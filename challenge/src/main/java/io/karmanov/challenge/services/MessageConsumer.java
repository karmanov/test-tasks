package io.karmanov.challenge.services;

import io.karmanov.challenge.dto.MessageConsumingResult;

public interface MessageConsumer {

    MessageConsumingResult consume();
}
