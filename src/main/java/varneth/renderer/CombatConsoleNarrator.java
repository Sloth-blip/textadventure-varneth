package varneth.renderer;

import varneth.engine.events.CombatStateChanged;
import varneth.engine.events.DamageDealt;
import varneth.engine.events.EventBus;

public class CombatConsoleNarrator {

    public CombatConsoleNarrator(EventBus bus) {
        bus.subscribe(DamageDealt.class, this::onDamageDealt);
        bus.subscribe(CombatStateChanged.class, this::onCombatStateChanged);
    }

    private void onDamageDealt(DamageDealt e) {
        var a = e.attacker().getName();
        var b = e.target().getName();

        if (e.skill() == null) {
            System.out.println(a + " hat " + e.amount() + " Schaden an " + b + " verursacht!");
        } else {
            System.out.println(a + " hat mit "+ e.skill() + " " + e.amount() + " Schaden an " + b + " verursacht!");
        }

        if (e.hpAfter() == 0) {
            System.out.println(b + " besiegt!");
        }
    }

    private void onCombatStateChanged(CombatStateChanged event) {
        var snapshot = event.snapshot();
        var player = snapshot.player();

        System.out.println(
                player.name() + " HP " + player.currentHp() + "/" + player.maxHp()
                        + " | Ressource " + player.currentResource() + "/" + player.maxResource()
        );

        var livingEnemies = snapshot.enemies().stream()
                .filter(enemy -> enemy.currentHp() > 0)
                .toList();

        if (!livingEnemies.isEmpty()) {
            System.out.println("vs");
            for (var enemy : livingEnemies) {
                System.out.println(
                        enemy.name() + " HP " + enemy.currentHp() + "/" + enemy.maxHp()
                );
            }
        }
    }
}
