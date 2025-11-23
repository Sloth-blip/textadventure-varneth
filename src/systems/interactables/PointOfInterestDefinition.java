package systems.interactables;

import systems.reward.Reward;

import java.util.List;

public class PointOfInterestDefinition {

    private final String pOIID;
    private final String name;
    private final List<List<String>> dialogChunks;
    private final Reward rewards;


    public PointOfInterestDefinition(
            String pOIID,
            String name,
            List<List<String>> dialogChunks,
            Reward rewards
    )
    {
        this.pOIID = pOIID;
        this.name = name;
        this.dialogChunks = dialogChunks;
        this.rewards = rewards;
    }

    public String getpOIID() {return pOIID;}
    public String getName() {return name;}
    public List<List<String>> getDialogsChunks() {return dialogChunks;}
    public List<String> getDialogChunk(boolean isUsed){
        if (!isUsed){return this.dialogChunks.getFirst();}
        else {return this.dialogChunks.getLast();}
    }
    public Reward getRewards() {return rewards;}


}
