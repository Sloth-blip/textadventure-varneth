package systems.actors.interactables;

import engine.Reward;

import java.util.List;

public class PointofInterestState {

    private int pOIID;
    private String name;
    private List<String> dialogChunks;
    private boolean used;
    private boolean persistent;
    private List <Reward> rewards;

    @Override
    public String toString(){
        return this.name;
    }

    public PointofInterestState(String name, List<String> dialogChunks, boolean persistent){
        this.name = name;
        this.dialogChunks = dialogChunks;
        this.persistent = persistent;
    }

    public boolean isPersistent(){
        return this.persistent;
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setInteractableUsed(){
        this.used = true;
    }

    public List<String> getInteractableDialogChunks(){
        return dialogChunks;
    }

}
