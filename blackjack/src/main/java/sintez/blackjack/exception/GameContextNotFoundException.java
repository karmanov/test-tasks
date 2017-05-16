package sintez.blackjack.exception;

public class GameContextNotFoundException extends BlackjackException {

    public GameContextNotFoundException() {
        super("Could not execute command, game context not found");
    }
}
