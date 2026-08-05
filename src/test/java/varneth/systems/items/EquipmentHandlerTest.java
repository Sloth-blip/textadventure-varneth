package varneth.systems.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import varneth.engine.events.EquipmentChanged;
import varneth.engine.events.EventBus;
import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.MainAttribute;
import varneth.systems.actors.player.Player;
import varneth.systems.actors.player.PlayerState;
import varneth.systems.spells.Skill;
import varneth.systems.spells.SpellTemplates;

class EquipmentHandlerTest {

    @Test
    void togglingOwnedFocusAppliesAndRemovesBonusesAndPublishesEvents() {
        Equipment focus = EquipmentTemplates.get("earth_focus");
        Player player = createPlayer(10, focus);
        EventBus bus = new EventBus();
        List<EquipmentChanged> events = new ArrayList<>();
        bus.subscribe(EquipmentChanged.class, events::add);
        EquipmentHandler handler = new EquipmentHandler(bus);
        Skill pebbles = SpellTemplates.get("pebbles");
        int damageBefore = player.calculateDamageDealtWithSkill(pebbles);

        EquipmentChanged equipped = handler.toggle(player, focus);

        assertTrue(equipped.equipped());
        assertTrue(equipped.replacedItemName().isEmpty());
        assertSame(focus, player.getEquippedItems().get(EquipmentSlot.MAIN_HAND));
        assertEquals(12, player.getStrength());
        assertEquals(22, player.getIntelligence());
        assertEquals(12, player.getWisdom());

        assertEquals(damageBefore + 2, player.calculateDamageDealtWithSkill(pebbles));
        EquipmentChanged unequipped = handler.toggle(player, focus);

        assertFalse(unequipped.equipped());
        assertTrue(player.getEquippedItems().isEmpty());
        assertEquals(12, player.getStrength());
        assertEquals(20, player.getIntelligence());
        assertEquals(11, player.getWisdom());
        assertEquals(List.of(equipped, unequipped), events);
    }

    @Test
    void equippingSameSlotReplacesPreviousItemInsteadOfStackingBonuses() {
        Equipment focus = EquipmentTemplates.get("earth_focus");
        Equipment strengthTool = new Equipment(
                new EquipmentDefinition(
                        "strength_tool",
                        "Kraftgriff",
                        "Ein schwerer Testgriff.",
                        EquipmentSlot.MAIN_HAND,
                        new EquipmentModifiers(5, 0, 0)
                )
        );
        Player player = createPlayer(10, focus, strengthTool);
        EquipmentHandler handler = new EquipmentHandler(new EventBus());
        handler.toggle(player, focus);

        EquipmentChanged replacement = handler.toggle(player, strengthTool);

        assertEquals(Optional.of("Erdmagischer Fokus"), replacement.replacedItemName());
        assertSame(strengthTool, player.getEquippedItems().get(EquipmentSlot.MAIN_HAND));
        assertFalse(player.isEquipped(focus));
        assertEquals(17, player.getStrength());
        assertEquals(20, player.getIntelligence());
        assertEquals(11, player.getWisdom());
    }

    @Test
    void equipmentWisdomFlowsIntoCrystalCastingCost() {
        Equipment focus = EquipmentTemplates.get("earth_focus");
        Player player = createPlayer(11, focus);

        player.addItem(MagicCrystalTemplates.get("fire_crystal"));
        assertEquals(5, player.getAvailableSpells().get(0).cost());

        new EquipmentHandler(new EventBus()).toggle(player, focus);

        assertEquals(13, player.getWisdom());
        assertEquals(4, player.getAvailableSpells().get(0).cost());
    }

    @Test
    void cannotEquipItemThatPlayerDoesNotOwn() {
        Player player = createPlayer(10);
        Equipment focus = EquipmentTemplates.get("earth_focus");

        assertThrows(
                IllegalArgumentException.class,
                () -> new EquipmentHandler(new EventBus()).toggle(player, focus)
        );
    }

    private Player createPlayer(int baseWisdom, Equipment... inventory) {
        return new Player(
                new ActorDefinition(
                        "Arenn",
                        40, 10,
                        20, 5,
                        10, 2,
                        15, 5,
                        baseWisdom, 1,
                        10, 2,
                        MainAttribute.INTELLIGENCE
                ),
                new PlayerState(
                        50,
                        25,
                        1,
                        0,
                        new ArrayList<>(),
                        new ArrayList<>(List.of(inventory)),
                        0
                )
        );
    }
}
