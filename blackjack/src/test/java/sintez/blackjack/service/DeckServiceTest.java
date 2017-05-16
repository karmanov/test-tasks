package sintez.blackjack.service;

import org.junit.Test;
import sintez.blackjack.game.card.Card;

import java.util.List;

import static org.junit.Assert.*;

public class DeckServiceTest {

    @Test
    public void test_Get_New_Not_Shuffled_Deck() throws Exception {
        DeckService deckService = new DeckService();
        List<Card> newDeck = deckService.getNewDeck(false);
        assertEquals(52, newDeck.size());
    }

    @Test
    public void test_Get_New_Shuffled_Deck() throws Exception {
        DeckService deckService = new DeckService();
        List<Card> notShuffledDeck = deckService.getNewDeck(false);
        List<Card> shuffledDeck = deckService.getNewDeck(true);
        assertEquals(52, notShuffledDeck.size());
        assertEquals(52, shuffledDeck.size());
        assertNotEquals(notShuffledDeck, shuffledDeck);
    }

    @Test
    public void test_Deal_Next_Card() throws Exception {
        DeckService deckService = new DeckService();
        List<Card> newDeck = deckService.getNewDeck(true);
        Card card = deckService.dealNextCard(newDeck);
        assertNotNull(card);
        assertEquals(51, newDeck.size());
        assertFalse(newDeck.contains(card));
    }
}