package varneth.systems.magic;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CrystalCastingCostTest {

    @Test
    void wisdomScalesChargeCostAroundBaselineAndNeverBelowOne() {
        assertAll(
                () -> assertEquals(10, CrystalCastingCost.calculate(5, 5)),
                () -> assertEquals(5, CrystalCastingCost.calculate(5, 10)),
                () -> assertEquals(5, CrystalCastingCost.calculate(5, 11)),
                () -> assertEquals(4, CrystalCastingCost.calculate(5, 15)),
                () -> assertEquals(2, CrystalCastingCost.calculate(5, 25)),
                () -> assertEquals(1, CrystalCastingCost.calculate(5, 100)),
                () -> assertEquals(50, CrystalCastingCost.calculate(5, 0))
        );
    }

    @Test
    void costCalculationRejectsInvalidInputs() {
        assertAll(
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> CrystalCastingCost.calculate(0, 10)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> CrystalCastingCost.calculate(5, -1)
                )
        );
    }
}
