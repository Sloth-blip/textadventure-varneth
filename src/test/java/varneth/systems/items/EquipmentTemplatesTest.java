package varneth.systems.items;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EquipmentTemplatesTest {

    @Test
    void earthFocusHasStableIdentitySlotAndModifiers() {
        Equipment focus = EquipmentTemplates.get("earth_focus");

        assertEquals("earth_focus", focus.getId());
        assertEquals("Erdmagischer Fokus", focus.getName());
        assertEquals(EquipmentSlot.MAIN_HAND, focus.getSlot());
        assertEquals(new EquipmentModifiers(0, 2, 1), focus.getModifiers());
    }
}
