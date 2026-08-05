package varneth.systems.interactables;

import java.util.List;
import java.util.Optional;

import varneth.systems.riddles.RiddleDefinition;
import varneth.systems.riddles.SpellSealDefinition;
import varneth.systems.reward.Reward;

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

    public String getId() {return def.getpOIID();}
    public String getName() {return def.getName();}
    public PointOfInterestType getType() {return def.getType();}
    public List<List<String>> getDialogsChunks() {return def.getDialogsChunks();}
    public List<String> getDialogChunks() {return def.getDialogChunk(isUsed());}
    public Reward getRewards() {return def.getRewards();}
    public Optional<RiddleDefinition> getRiddle() {return def.getRiddle();}
    public Optional<SpellSealDefinition> getSpellSeal() {return def.getSpellSeal();}

    /** Getter - State **/

    public boolean isUsed() {return state.isUsed();}
    public boolean isPersistent() {return state.isPersistent();}

    /** Setter **/

    public void setPOIUsed() {state.setPOIUsed();}

}
