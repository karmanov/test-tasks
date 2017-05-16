package sintez.blackjack.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sintez.blackjack.configuration.ApplicationConfiguration;
import sintez.blackjack.configuration.DatabaseConfiguration;
import sintez.blackjack.exception.PlayerNotFoundException;
import sintez.blackjack.model.Player;
import sintez.blackjack.model.TransactionType;

import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApplicationConfiguration.class, DatabaseConfiguration.class})
public class PlayerServiceImplTest {

    private static final int BET = 10;

    @Autowired
    PlayerService playerService;

    @Test
    public void test_Find_All() {
        List<Player> players = playerService.findAll();
        assertNotNull(players);
        assertEquals(2, players.size());
    }

    @Test
    public void test_Can_Play_True() throws PlayerNotFoundException {
        List<Player> players = playerService.findAll();
        Player player = players.get(0);
        int newBet = player.getBalance() - 1;
        boolean canPlay = playerService.canPlay(player.getId(), newBet);
        assertTrue(canPlay);
    }

    @Test
    public void test_Can_Play_False() throws PlayerNotFoundException {
        List<Player> players = playerService.findAll();
        Player player = players.get(0);
        int newBet = player.getBalance() + 1;
        boolean canPlay = playerService.canPlay(player.getId(), newBet);
        assertFalse(canPlay);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void test_Can_Play_Invalid_User() throws PlayerNotFoundException {
        boolean canPlay = playerService.canPlay(150, BET);
        assertFalse(canPlay);
    }

    @Test
    public void test_Add_Funds() throws PlayerNotFoundException {
        List<Player> players = playerService.findAll();
        Player player = players.get(0);
        int currentBalance = player.getBalance();
        playerService.addFunds(player.getId(), BET, TransactionType.INCOME);
        Player player1 = playerService.find(player.getId());
        assertEquals(currentBalance + BET, player1.getBalance());
    }
}