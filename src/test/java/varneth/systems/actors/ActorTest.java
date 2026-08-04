package varneth.systems.actors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import varneth.systems.actors.player.Player;
import varneth.systems.actors.player.PlayerState;

class ActorTest {

    @Test
    void damageCannotReduceHpBelowZero() {
        Player player = createPlayer(110, 55, 1, 0);

        player.recieveDamage(200);

        assertEquals(0, player.getCurrentHp());
        assertTrue(player.isDead());
    }

    @Test
    void restRestoresLevelAdjustedHpAndResource() {
        Player player = createPlayer(30, 1, 1, 0);

        player.takeRest();

        assertEquals(110, player.getCurrentHp());
        assertEquals(55, player.getCurrentResource());
    }

    @Test
    void levelUpPreservesCurrentHpAndResourcePercentages() {
        Player player = createPlayer(50, 55, 1, 0, 50, 50);

        player.levelUp();

        assertEquals(2, player.getLevel());
        assertEquals(150, player.getMaxHp());
        assertEquals(75, player.getCurrentHp());
        assertEquals(60, player.getMaxResource());
        assertEquals(60, player.getCurrentResource());
    }

    @Test
    void experienceCanLevelActorAndKeepsRemainder() {
        Player player = createPlayer(110, 55, 1, 0);

        int levelsGained = player.gainXp(25);

        assertEquals(2, player.getLevel());
        assertEquals(1, levelsGained);
        assertEquals(15, player.getCurrentXp());
        assertEquals(20, player.getCurrentXpThreshold());
        assertEquals(120, player.getMaxHp());
    }

    private Player createPlayer(int hp, int resource, int level, int xp) {
        return createPlayer(hp, resource, level, xp, 100, 10);
    }

    private Player createPlayer(
            int hp, int resource, int level, int xp, int baseHp, int hpPerLevel
    ) {
        ActorDefinition definition = new ActorDefinition(
                "Test Player",
                baseHp, hpPerLevel,
                50, 5,
                10, 2,
                20, 3,
                5, 1,
                10, 1,
                MainAttribute.STRENGTH
        );

        PlayerState state = new PlayerState(
                hp,
                resource,
                level,
                xp,
                new ArrayList<>(),
                new ArrayList<>(),
                0
        );

        return new Player(definition, state);
    }
}
