package varneth.systems.items;

import varneth.systems.magic.MagicType;

public class MagicCrystalDefinition {

    private final String id;
    private final String name;
    private final String description;
    private final MagicType magicType;
    private final int maxCharge;

    public MagicCrystalDefinition(
            String id,
            String name,
            String description,
            MagicType magicType,
            int maxCharge
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.magicType = magicType;
        this.maxCharge = maxCharge;
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public MagicType getMagicType() {return magicType;}
    public int getMaxCharge() {return maxCharge;}
}
