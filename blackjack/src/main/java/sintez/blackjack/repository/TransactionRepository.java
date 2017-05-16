package sintez.blackjack.repository;

import org.springframework.data.repository.CrudRepository;
import sintez.blackjack.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Iterable<Transaction> findByPlayerId(long playerId);
}
