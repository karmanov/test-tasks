package sintez.blackjack.web.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import sintez.blackjack.configuration.ApplicationConfiguration;
import sintez.blackjack.game.GameResponse;
import sintez.blackjack.game.card.Card;
import sintez.blackjack.game.card.Rank;
import sintez.blackjack.game.card.Suit;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApplicationConfiguration.class})
@WebAppConfiguration
public class BlackjackControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Transactional
    public void test_Deal() throws Exception {
        this.mockMvc.perform(get("/blackjack/deal/{account}/{bet}", 1, 50))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("bet", is(50)));
    }

    private GameResponse buildMockGameResponse() {
        List<Card> playerCards = new ArrayList<Card>();
        playerCards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        playerCards.add(new Card(Suit.DIAMONDS, Rank.KING));

        List<Card> dealerCards = new ArrayList<Card>();
        dealerCards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        dealerCards.add(new Card(Suit.DIAMONDS, Rank.DEUCE));
        GameResponse gameResponse = new GameResponse("gameID", playerCards, dealerCards, 20, 12, 50);
        return gameResponse;
    }
}