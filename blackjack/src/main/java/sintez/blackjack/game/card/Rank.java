package sintez.blackjack.game.card;

public enum Rank {

    ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;

    public int getValue() {
        return ordinal() + 1;
    }

}
