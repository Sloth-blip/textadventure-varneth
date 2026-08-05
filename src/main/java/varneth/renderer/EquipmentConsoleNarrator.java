package varneth.renderer;

import varneth.engine.events.EquipmentChanged;
import varneth.engine.events.EventBus;

public class EquipmentConsoleNarrator {

    public EquipmentConsoleNarrator(EventBus bus) {
        bus.subscribe(EquipmentChanged.class, this::onEquipmentChanged);
    }

    private void onEquipmentChanged(EquipmentChanged event) {
        String slot = event.slot().toString();

        if (!event.equipped()) {
            System.out.println(
                    event.ownerName() + " legt „" + event.itemName()
                            + "“ ab (" + slot + ")."
            );
        } else if (event.replacedItemName().isPresent()) {
            System.out.println(
                    event.ownerName() + " ersetzt „" + event.replacedItemName().get()
                            + "“ durch „" + event.itemName() + "“ (" + slot + ")."
            );
        } else {
            System.out.println(
                    event.ownerName() + " rüstet „" + event.itemName()
                            + "“ aus (" + slot + ")."
            );
        }
    }
}
