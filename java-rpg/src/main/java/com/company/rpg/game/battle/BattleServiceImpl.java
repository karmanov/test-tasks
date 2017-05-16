package com.company.rpg.game.battle;

import com.company.rpg.game.model.NPC;
import com.company.rpg.game.model.Player;
import com.company.rpg.map.locations.EmptyLocation;
import com.company.rpg.map.locations.Location;
import com.company.rpg.ui.menu.AbstractMenu;
import com.company.rpg.ui.menu.BattleMenu;
import com.company.rpg.utils.RandomUtil;

/**
 * Basic implementation of {@link BattleService}
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class BattleServiceImpl implements BattleService {

    private AbstractMenu menu;

    public BattleServiceImpl() {
        menu = new BattleMenu();
    }

    /**
     * {@inheritDoc}
     */
    public Location battle(Player hero, Location location) {
        NPC monster = (NPC) location.getLocationItem();
        int heroHealth = hero.getCurrentHealth();
        int monsterHealth = monster.getCurrentHealth();
        while (heroHealth > 0 && monsterHealth > 0) {
            printMessage(hero.getName() + "'s health: " + heroHealth + " | " + monster.getName() + "'s Health: " + monsterHealth);
            menu.showMenu();
            int selection = menu.getSelectionIndex();

            //Player turn
            if (selection == 1) {
                monsterHealth = playerAttack(hero, monster, monsterHealth);
            } else if (selection == 2) {
                printMessage("You begin charging your attack!");
                hero.increaseDamageOnOnePoint();
            }

            //Enemy turn
            if (monsterHealth > 0 && heroHealth > 0) {
                heroHealth = monsterAttack(hero, monster, heroHealth);
            }
        }
        if (heroHealth <= 0) {
            System.out.println("You are dead!");
            System.exit(0);
        } else if (monsterHealth <= 0) {
            System.out.println("--------------------------------");
            System.out.println(hero.getName() + " has defeated " + monster.getName() + "!");
            System.out.println("You earn " + monster.getExpCost() + " exp on the enemy.");
            System.out.println("--------------------------------");
            hero.addExp(monster.getExpCost());
            int x = location.getX();
            int y = location.getY();
            location = new EmptyLocation("monster's cave", x, y);
        }
        return location;
    }

    private int playerAttack(Player hero, NPC monster, int monsterHealth) {
        int monsterDodge = RandomUtil.nextInt(10) + 1;
        if (monsterDodge >= 9) { // Failed attack
            printMessage(monster.getName() + " dodged your attack!");
        } else if (monsterDodge <= 8) { // Successful attack
            int damage = hero.getDamage() + RandomUtil.nextInt(hero.getDamage()) - RandomUtil.nextInt(monster.getDefence());
            if (damage < 0) {
                damage = 0;
            }
            monsterHealth -= damage;
            printMessage(hero.getName() + " hits " + monster.getName() + " for " + damage + " damage!");

        }
        return monsterHealth;
    }

    private int monsterAttack(Player hero, NPC monster, int heroHealth) {
        int monsterDamage = monster.getDamage() + RandomUtil.nextInt(monster.getDamage()) - RandomUtil.nextInt(hero.getDefence());
        if (monsterDamage < 0) {
            monsterDamage = 0;
        }
        int monsterHitPlayer = RandomUtil.nextInt(100) + 1;
        if (monsterHitPlayer > 15) {
            heroHealth -= monsterDamage;
            printMessage(monster.getName() + " hits " + hero.getName() + " for " + monsterDamage + " damage!");
        } else if (monsterHitPlayer <= 15) {
            printMessage(monster.getName() + " misses " + hero.getName() + "!");
        }
        return heroHealth;
    }

    private void printMessage(String message) {
        System.out.println("------------------------------------");
        System.out.println(message);
        System.out.println("------------------------------------");
    }
}
