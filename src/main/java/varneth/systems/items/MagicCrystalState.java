package varneth.systems.items;

public class MagicCrystalState {

    private int currentCharge;

    public MagicCrystalState(int currentCharge) {
        this.currentCharge = currentCharge;
    }

    protected int getCurrentCharge() {return currentCharge;}
    protected void setCurrentCharge(int currentCharge) {this.currentCharge = currentCharge;}
}
