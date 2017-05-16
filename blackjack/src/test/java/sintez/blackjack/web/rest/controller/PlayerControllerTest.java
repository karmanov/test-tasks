package sintez.blackjack.web.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import sintez.blackjack.exception.PlayerNotFoundException;
import sintez.blackjack.model.Player;
import sintez.blackjack.model.Transaction;
import sintez.blackjack.model.TransactionType;
import sintez.blackjack.service.PlayerService;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApplicationConfiguration.class})
@WebAppConfiguration
public class PlayerControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    private MockMvc mockMvc;

    private PlayerService playerServiceMock;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        playerServiceMock = mock(PlayerService.class);
        Mockito.reset(playerServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Transactional
    public void test_Find_All_Players() throws Exception {
        List<Player> players = buildMockPlayers();

        when(playerServiceMock.findAll()).thenReturn(players);

        this.mockMvc.perform(get("/blackjack/players"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].balance", is(100)))
                .andExpect(jsonPath("$[0].moneyTransactions", hasSize(1)))
                .andExpect(jsonPath("$[0].moneyTransactions[0].playerId", is(1)))
                .andExpect(jsonPath("$[0].moneyTransactions[0].paymentType", is("INCOME")))
                .andExpect(jsonPath("$[0].moneyTransactions[0].amount", is(100)));
    }

    @Test
    @Transactional
    public void test_Find_Valid_Player_By_Id() throws Exception {
        List<Player> players = buildMockPlayers();
        when(playerServiceMock.find(1)).thenReturn(players.get(0));

        this.mockMvc.perform(get("/blackjack/players/{account}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("balance", is(100)))
                .andExpect(jsonPath("moneyTransactions", hasSize(1)))
                .andExpect(jsonPath("moneyTransactions[0].playerId", is(1)))
                .andExpect(jsonPath("moneyTransactions[0].paymentType", is("INCOME")))
                .andExpect(jsonPath("moneyTransactions[0].amount", is(100)));
    }

    @Test
    @Transactional
    public void test_Find_InValid_Player_By_Id() throws Exception {
        when(playerServiceMock.find(12L)).thenThrow(new PlayerNotFoundException(""));

        this.mockMvc.perform(get("/blackjack/players/{account}", 12L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void test_Show_Transaction_History_Valid_Player() throws Exception {
        List<Transaction> transactions = new ArrayList<Transaction>(buildMockTransactions(1L));
        when(playerServiceMock.findAllTransactionsByPlayer(1L)).thenReturn(transactions);

        this.mockMvc.perform(get("/blackjack/players/{account}/transactions", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].playerId", is(1)))
                .andExpect(jsonPath("$[0].paymentType", is("INCOME")))
                .andExpect(jsonPath("$[0].amount", is(100)));
    }

    @Test
    @Transactional
    public void test_Show_Transaction_History_InValid_Player() throws Exception {
        when(playerServiceMock.findAllTransactionsByPlayer(12L)).thenThrow(new PlayerNotFoundException(""));

        this.mockMvc.perform(get("/blackjack/players/{account}/transactions", 12L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void test_Find_All_Transactions() throws Exception {
        List<Transaction> transactions = new ArrayList<Transaction>(buildMockTransactions(1L));

        when(playerServiceMock.findAllTransactions()).thenReturn(transactions);

        this.mockMvc.perform(get("/blackjack/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].playerId", is(1)))
                .andExpect(jsonPath("$[0].paymentType", is("INCOME")))
                .andExpect(jsonPath("$[0].amount", is(100)));
    }

    private List<Player> buildMockPlayers() {
        List<Player> players = new ArrayList<Player>();

        Player player1 = new Player(100);
        player1.setId(1L);
        player1.setMoneyTransactions(buildMockTransactions(player1.getId()));
        players.add(player1);

        Player player2 = new Player(200);
        player1.setId(2L);
        player1.setBalance(200);
        players.add(player2);

        return players;
    }

    private Set<Transaction> buildMockTransactions(long playerId) {
        Set<Transaction> transactions = new HashSet<Transaction>();
        Transaction transaction = new Transaction(playerId, TransactionType.INCOME, 20);
        transactions.add(transaction);
        return transactions;
    }

}