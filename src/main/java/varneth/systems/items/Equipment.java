package varneth.systems.items;

public class Equipment implements Item {

    private final EquipmentDefinition def;

    public Equipment(EquipmentDefinition def) {
        this.def = def;
    }

    @Override
    public String getId() {return def.getId();}

    @Override
    public String getName() {return def.getName();}

    @Override
    public String getDescription() {return def.getDescription();}

    public EquipmentSlot getSlot() {return def.getSlot();}
    public EquipmentModifiers getModifiers() {return def.getModifiers();}
}
