package systems.interactables;

import systems.reward.Reward;

import java.util.List;

public class PointOfInterest {

    private final PointOfInterestDefinition def;
    private final PointOfInterestState state;

    public PointOfInterest(
            PointOfInterestDefinition def,
            PointOfInterestState state
    )
    {
        this.def = def;
        this.state = state;
    }

    /** Getter - Def **/

    public String getPOIID() {return def.getpOIID();}
    public String getName() {return def.getName();}
    public PointOfInterestType getType() {return def.getType();}
    public List<List<String>> getDialogsChunks() {return def.getDialogsChunks();}
    public List<String> getDialogChunks() {return def.getDialogChunk(isUsed());}
    public Reward getRewards() {return def.getRewards();}

    /** Getter - State **/

    public boolean isUsed() {return state.isUsed();}
    public boolean isPersistent() {return state.isPersistent();}

    /** Setter **/

    public void setPOIUsed() {state.setPOIUsed();}

}
