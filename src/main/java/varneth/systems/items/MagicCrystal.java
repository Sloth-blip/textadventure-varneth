package varneth.systems.items;

import varneth.systems.magic.MagicType;

public class MagicCrystal implements Item {

    private final MagicCrystalDefinition def;
    private final MagicCrystalState state;

    public MagicCrystal(MagicCrystalDefinition def, MagicCrystalState state) {
        if (state.getCurrentCharge() < 0 || state.getCurrentCharge() > def.getMaxCharge()) {
            throw new IllegalArgumentException("Crystal charge must be between zero and maximum charge");
        }
        this.def = def;
        this.state = state;
    }

    @Override
    public String getId() {return def.getId();}

    @Override
    public String getName() {return def.getName();}

    @Override
    public String getDescription() {return def.getDescription();}

    public MagicType getMagicType() {return def.getMagicType();}
    public int getCurrentCharge() {return state.getCurrentCharge();}
    public int getMaxCharge() {return def.getMaxCharge();}

    public boolean canPower(MagicType magicType, int amount) {
        return getMagicType() == magicType && amount >= 0 && getCurrentCharge() >= amount;
    }

    public boolean consumeCharge(int amount) {
        if (!canPower(getMagicType(), amount)) {
            return false;
        }
        state.setCurrentCharge(getCurrentCharge() - amount);
        return true;
    }
}
