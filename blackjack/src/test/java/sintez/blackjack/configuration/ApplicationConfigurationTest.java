package sintez.blackjack.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ApplicationConfiguration.class, DatabaseConfiguration.class})
@WebAppConfiguration
public class ApplicationConfigurationTest {

    @Test
    public void contextLoads() {
    }

}