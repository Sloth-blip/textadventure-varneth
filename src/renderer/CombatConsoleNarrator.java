package renderer;

import engine.events.DamageDealt;
import engine.events.EventBus;

public class CombatConsoleNarrator {

    public CombatConsoleNarrator(EventBus bus) {
        bus.subscribe(DamageDealt.class, this::onDamageDealt);
    }

    private void onDamageDealt(DamageDealt e) {
        var a = e.attacker().getName();
        var b = e.target().getName();

        if (e.skill() == null) {
            System.out.println(a + " hat " + e.amount() + " Schaden an " + b + " verursacht!");
        } else {
            System.out.println(a + " hat mit "+ e.skill() + " " + e.amount() + " Schaden an " + b + " verursacht!");
        }
    }
}
