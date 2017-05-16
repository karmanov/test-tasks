package sintez.blackjack.web.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import sintez.blackjack.exception.GameCouldNotBeStartedException;
import sintez.blackjack.exception.PlayerNotFoundException;

@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorHandler.class);

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePlayerNotFoundException(PlayerNotFoundException ex) {
        LOGGER.error("handling 404 error on a player entry");
    }

    @ExceptionHandler(GameCouldNotBeStartedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleGameCouldNotBeStartedException(GameCouldNotBeStartedException ex) {
        LOGGER.error("handling 400 error on a deal entry");
    }

}
