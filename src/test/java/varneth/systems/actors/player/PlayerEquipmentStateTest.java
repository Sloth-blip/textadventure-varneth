package varneth.systems.actors.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.MainAttribute;
import varneth.systems.items.Equipment;
import varneth.systems.items.EquipmentSlot;
import varneth.systems.items.EquipmentTemplates;
import varneth.systems.items.Item;

class PlayerEquipmentStateTest {

    @Test
    void restoresOwnedEquipmentAndItsBonusesFromState() {
        Equipment focus = EquipmentTemplates.get("earth_focus");
        Player player = createPlayer(
                new ArrayList<>(List.of(focus)),
                Map.of(EquipmentSlot.MAIN_HAND, focus)
        );

        assertSame(focus, player.getEquippedItems().get(EquipmentSlot.MAIN_HAND));
        assertEquals(22, player.getIntelligence());
        assertEquals(12, player.getWisdom());
    }

    @Test
    void rejectsEquippedItemThatIsNotOwnedOrUsesWrongSlot() {
        Equipment focus = EquipmentTemplates.get("earth_focus");

        assertThrows(
                IllegalArgumentException.class,
                () -> createPlayer(
                        new ArrayList<>(),
                        Map.of(EquipmentSlot.MAIN_HAND, focus)
                )
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> createPlayer(
                        new ArrayList<>(List.of(focus)),
                        Map.of(EquipmentSlot.BODY, focus)
                )
        );
    }

    private Player createPlayer(
            List<Item> inventory,
            Map<EquipmentSlot, Equipment> equippedItems
    ) {
        return new Player(
                new ActorDefinition(
                        "Arenn",
                        40, 10,
                        20, 5,
                        10, 2,
                        15, 5,
                        10, 1,
                        10, 2,
                        MainAttribute.INTELLIGENCE
                ),
                new PlayerState(
                        50,
                        25,
                        1,
                        0,
                        new ArrayList<>(),
                        inventory,
                        0,
                        equippedItems
                )
        );
    }
}
