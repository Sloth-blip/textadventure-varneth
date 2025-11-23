package systems.interactables;

public class PointOfInterestState {


    private boolean used;
    private boolean persistent;




    public PointOfInterestState(boolean persistent){
        this.used = false;
        this.persistent = persistent;
    }

    public boolean isPersistent(){
        return this.persistent;
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setPOIUsed(){
        this.used = true;
    }


}
