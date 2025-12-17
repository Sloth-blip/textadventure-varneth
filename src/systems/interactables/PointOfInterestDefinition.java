package systems.interactables;

import systems.reward.Reward;

import java.util.List;


public class PointOfInterestDefinition {

    private final String pOIID;
    private final String name;
    private final PointOfInterestType type;
    private final List<List<String>> dialogChunks;
    private final Reward rewards;


    public PointOfInterestDefinition(
            String pOIID,
            String name,
            PointOfInterestType type,
            List<List<String>> dialogChunks,
            Reward rewards
    )
    {
        this.pOIID = pOIID;
        this.name = name;
        this.type = type;
        this.dialogChunks = dialogChunks;
        this.rewards = rewards;
    }

    protected String getpOIID() {return pOIID;}
    protected String getName() {return name;}
    protected PointOfInterestType getType() {return type;}
    protected List<List<String>> getDialogsChunks() {return dialogChunks;}
    protected List<String> getDialogChunk(boolean isUsed){
        if (!isUsed){return this.dialogChunks.getFirst();}
        else {return this.dialogChunks.getLast();}
    }
    protected Reward getRewards() {return rewards;}


}
