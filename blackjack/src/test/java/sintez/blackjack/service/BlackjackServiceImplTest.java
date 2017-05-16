package sintez.blackjack.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sintez.blackjack.configuration.ApplicationConfiguration;
import sintez.blackjack.configuration.DatabaseConfiguration;
import sintez.blackjack.exception.GameContextNotFoundException;
import sintez.blackjack.exception.GameCouldNotBeStartedException;
import sintez.blackjack.exception.PlayerNotFoundException;
import sintez.blackjack.game.GameContext;
import sintez.blackjack.game.GameResponse;
import sintez.blackjack.game.GameResult;
import sintez.blackjack.game.card.Card;
import sintez.blackjack.game.card.Rank;
import sintez.blackjack.game.card.Suit;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApplicationConfiguration.class, DatabaseConfiguration.class})
public class BlackjackServiceImplTest {

    @Autowired
    private BlackjackService blackjackService;

    @Autowired
    private DeckService deckService;

    @Test
    @Transactional
    public void test_Deal() throws Exception {
        GameResponse gameResponse = blackjackService.deal(1, 50);
        assertNotNull(gameResponse);
        assertEquals(50, gameResponse.getBet());
        assertNotSame(gameResponse.getPlayerCards(), gameResponse.getDealersCards());
        assertTrue(blackjackService.getActiveGames().containsKey(gameResponse.getId()));
    }

    @Test(expected = PlayerNotFoundException.class)
    @Transactional
    public void test_Deal_Invalid_User() throws Exception {
        blackjackService.deal(12, 50);
    }

    @Test(expected = GameCouldNotBeStartedException.class)
    @Transactional
    public void test_Deal_Invalid_Bet() throws Exception {
        blackjackService.deal(1, 500);
    }

    @Test
    @Transactional
    public void test_Hit() throws Exception {
        GameResponse gameResponse = blackjackService.deal(1, 50);
        GameResponse hitResponse = blackjackService.hit(gameResponse.getId());
        assertEquals(50, hitResponse.getBet());
        assertNotSame(hitResponse.getPlayerCards(), hitResponse.getDealersCards());
    }

    @Test(expected = GameContextNotFoundException.class)
    @Transactional
    public void test_Invalid_Game_Id_On_Hit() throws Exception {
        blackjackService.deal(1, 50);
        blackjackService.hit("gameID");
    }

    @Test(expected = GameContextNotFoundException.class)
    @Transactional
    public void test_Invalid_Game_Id_On_Stand() throws Exception {
        blackjackService.deal(1, 50);
        blackjackService.stand("gameID");
    }

    @Test
    @Transactional
    public void test_Stand() throws Exception {
        GameResponse gameResponse = blackjackService.deal(1, 50);
        GameResponse hitResponse = blackjackService.stand(gameResponse.getId());
        assertEquals(50, hitResponse.getBet());
        assertNotSame(hitResponse.getPlayerCards(), hitResponse.getDealersCards());
    }

    @Test
    public void test_Player_Has_Blackjack() throws Exception {
        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.CLUBS, Rank.TEN));
        playerCards.add(new Card(Suit.HEARTS, Rank.ACE));

        List<Card> dealersCards = new ArrayList<Card>();
        dealersCards.add(new Card(Suit.HEARTS, Rank.QUEEN));

        GameContext gameContext = new GameContext(10, playerCards, dealersCards, null, 1);
        blackjackService.getActiveGames().put(gameContext.getId(), gameContext);
        assertEquals(GameResult.WIN, gameContext.getGameResult());
    }

    @Test
    @Transactional
    public void test_Player_Busted() throws Exception {
        List<Card> deck = deckService.getNewDeck(true);
        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.CLUBS, Rank.TEN));
        playerCards.add(new Card(Suit.SPADES, Rank.TEN));
        playerCards.add(new Card(Suit.HEARTS, Rank.ACE));

        List<Card> dealersCards = new ArrayList<Card>();
        dealersCards.add(new Card(Suit.HEARTS, Rank.QUEEN));

        GameContext gameContext = new GameContext(10, playerCards, dealersCards, deck, 1);
        blackjackService.getActiveGames().put(gameContext.getId(), gameContext);
        blackjackService.hit(gameContext.getId());
        assertEquals(GameResult.LOOSE, gameContext.getGameResult());
    }

    @Test
    public void test_Push() throws Exception {
        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        playerCards.add(new Card(Suit.HEARTS, Rank.KING));

        List<Card> dealersCards = new ArrayList<Card>();
        dealersCards.add(new Card(Suit.HEARTS, Rank.JACK));
        dealersCards.add(new Card(Suit.HEARTS, Rank.FOUR));
        dealersCards.add(new Card(Suit.HEARTS, Rank.SIX));

        GameContext gameContext = new GameContext(10, playerCards, dealersCards, null, 1);
        blackjackService.getActiveGames().put(gameContext.getId(), gameContext);
        assertEquals(GameResult.PUSH, gameContext.getGameResult());
    }

    @Test
    public void test_21_After_Hit() throws Exception {
        List<Card> deck = new ArrayList<Card>();
        deck.add(new Card(Suit.HEARTS, Rank.ACE));

        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.CLUBS, Rank.TEN));

        List<Card> dealersCards = new ArrayList<Card>();
        dealersCards.add(new Card(Suit.HEARTS, Rank.JACK));
        dealersCards.add(new Card(Suit.HEARTS, Rank.FOUR));
        dealersCards.add(new Card(Suit.HEARTS, Rank.SIX));

        GameContext gameContext = new GameContext(10, playerCards, dealersCards, deck, 1);
        blackjackService.getActiveGames().put(gameContext.getId(), gameContext);
        blackjackService.hit(gameContext.getId());
        assertEquals(GameResult.WIN, gameContext.getGameResult());
    }
}