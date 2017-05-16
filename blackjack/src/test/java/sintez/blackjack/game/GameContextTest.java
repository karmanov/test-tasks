package sintez.blackjack.game;

import org.junit.Test;
import sintez.blackjack.game.card.Card;
import sintez.blackjack.game.card.Rank;
import sintez.blackjack.game.card.Suit;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class GameContextTest {

    private GameContext gameContext;

    @Test
    public void test_Player_Has_Bust() {
        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.CLUBS, Rank.KING));
        playerCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        playerCards.add(new Card(Suit.HEARTS, Rank.DEUCE));

        List<Card> dealersCards = new ArrayList<Card>();
        dealersCards.add(new Card(Suit.HEARTS, Rank.JACK));

        gameContext = new GameContext(10, playerCards, dealersCards, null, 1);
        assertTrue("Player loose expected", gameContext.getGameResult() == GameResult.LOOSE);
    }

    @Test
    public void test_Dealer_Has_Bust() {
        List<Card> dealersCards = new ArrayList<Card>();
        dealersCards.add(new Card(Suit.CLUBS, Rank.KING));
        dealersCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        dealersCards.add(new Card(Suit.HEARTS, Rank.DEUCE));

        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.HEARTS, Rank.JACK));

        gameContext = new GameContext(10, playerCards, dealersCards, null, 1);
        assertTrue("Player win expected", gameContext.getGameResult() == GameResult.WIN);
    }

    @Test
    public void test_Dealer_Has_21() {
        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        playerCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        playerCards.add(new Card(Suit.HEARTS, Rank.ACE));

        List<Card> dealersCards = new ArrayList<Card>();
        dealersCards.add(new Card(Suit.HEARTS, Rank.JACK));
        dealersCards.add(new Card(Suit.HEARTS, Rank.ACE));

        gameContext = new GameContext(10, playerCards, dealersCards, null, 1);
        assertTrue("Player loose expected", gameContext.getGameResult() == GameResult.LOOSE);
    }

    @Test
    public void test_Player_Has_21() {
        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.CLUBS, Rank.TEN));
        playerCards.add(new Card(Suit.HEARTS, Rank.ACE));

        List<Card> dealersCards = new ArrayList<Card>();
        dealersCards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        dealersCards.add(new Card(Suit.HEARTS, Rank.FIVE));
        dealersCards.add(new Card(Suit.HEARTS, Rank.SIX));

        gameContext = new GameContext(10, playerCards, dealersCards, null, 1);
        assertTrue("Player win expected", gameContext.getGameResult() == GameResult.WIN);
    }

    @Test
    public void test_Push() {
        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        playerCards.add(new Card(Suit.HEARTS, Rank.KING));

        List<Card> dealersCards = new ArrayList<Card>();
        dealersCards.add(new Card(Suit.HEARTS, Rank.JACK));
        dealersCards.add(new Card(Suit.HEARTS, Rank.FOUR));
        dealersCards.add(new Card(Suit.HEARTS, Rank.SIX));

        gameContext = new GameContext(10, playerCards, dealersCards, null, 1);
        assertTrue("Push expected", gameContext.getGameResult() == GameResult.PUSH);
    }

    @Test
    public void test_Blackjack_Push() throws Exception {
        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        playerCards.add(new Card(Suit.HEARTS, Rank.ACE));

        List<Card> dealersCards = new ArrayList<Card>();
        dealersCards.add(new Card(Suit.HEARTS, Rank.ACE));
        dealersCards.add(new Card(Suit.HEARTS, Rank.JACK));

        gameContext = new GameContext(10, playerCards, dealersCards, null, 1);
        assertTrue("Blackjack Push expected", gameContext.getGameResult() == GameResult.PUSH);
    }
}