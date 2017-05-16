package sintez.blackjack.repository;

import org.springframework.data.repository.CrudRepository;
import sintez.blackjack.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {
}
