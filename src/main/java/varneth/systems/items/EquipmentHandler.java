package varneth.systems.items;

import java.util.Optional;

import varneth.engine.events.EquipmentChanged;
import varneth.engine.events.EventBus;
import varneth.systems.actors.player.Player;

public class EquipmentHandler {

    private final EventBus bus;

    public EquipmentHandler(EventBus bus) {
        this.bus = bus;
    }

    public EquipmentChanged toggle(Player player, Equipment equipment) {
        EquipmentChanged event;
        if (player.isEquipped(equipment)) {
            player.unequip(equipment.getSlot());
            event = new EquipmentChanged(
                    player.getName(),
                    equipment.getName(),
                    equipment.getSlot(),
                    false,
                    Optional.empty()
            );
        } else {
            Optional<Equipment> replaced = player.equip(equipment);
            event = new EquipmentChanged(
                    player.getName(),
                    equipment.getName(),
                    equipment.getSlot(),
                    true,
                    replaced.map(Equipment::getName)
            );
        }

        bus.publish(event);
        return event;
    }
}
