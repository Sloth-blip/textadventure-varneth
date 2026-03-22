package varneth.application.engine.ui;

public class MapEdgeView {

    private final String fromRoomId;
    private final String toRoomId;
    private final boolean discovered;
    
    public MapEdgeView(
        String fromRoomId,
        String toRoomId,
        boolean discovered
    ) {
        this.fromRoomId = fromRoomId;
        this.toRoomId = toRoomId;
        this.discovered = discovered;
    }

    public String getFromRoomId() {return fromRoomId;}
    public String getToRoomId() {return toRoomId;}
    public boolean isDiscovered() {return discovered;}

}
