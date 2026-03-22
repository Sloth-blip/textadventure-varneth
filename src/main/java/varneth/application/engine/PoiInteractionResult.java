package varneth.application.engine;

import java.util.List;

import varneth.systems.interactables.PointOfInterest;
import varneth.systems.reward.Reward;

public class PoiInteractionResult {

    private final PointOfInterest poi;
    private final boolean firstUse;

    public PoiInteractionResult(PointOfInterest poi, boolean firstUse){
        this.poi = poi;
        this.firstUse = firstUse;
    }

    public PointOfInterest getPoi() {return poi;}
    public boolean isFirstUse() {return firstUse;}
    public String getSourceName() {return poi.getName();}
    public Reward getReward() {return firstUse ? poi.getRewards() : null;}
    public List<String> getDialogChunks() {return poi.getDialogChunks();}
}
