package varneth.renderer;

import varneth.engine.events.CrystalShattered;
import varneth.engine.events.EventBus;

public class CrystalConsoleNarrator {

    public CrystalConsoleNarrator(EventBus bus) {
        bus.subscribe(CrystalShattered.class, this::onCrystalShattered);
    }

    private void onCrystalShattered(CrystalShattered event) {
        System.out.println(
                event.ownerName() + "s " + event.crystalName()
                        + " wird vollständig aufgezehrt und zerbricht!"
        );
    }
}
