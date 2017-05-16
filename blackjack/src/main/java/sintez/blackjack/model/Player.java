package sintez.blackjack.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playerId")
    private Set<Transaction> moneyTransactions;

    public Player() {
    }

    public Player(int balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Set<Transaction> getMoneyTransactions() {
        return moneyTransactions;
    }

    public void setMoneyTransactions(Set<Transaction> moneyTransactions) {
        this.moneyTransactions = moneyTransactions;
    }
}
