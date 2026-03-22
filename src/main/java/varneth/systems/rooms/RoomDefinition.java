package varneth.systems.rooms;

public class RoomDefinition {

    private final String roomId;
    private final String roomName;
    private final String roomDescription;
    private final int mapX;
    private final int mapY;


    @Override
    public String toString() {return roomName;}

    public RoomDefinition(
            String roomId,
            String roomName,
            String roomDescription,
            int mapX,
            int mapY
    )
    {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    protected String getRoomId() {return roomId;}
    protected String getRoomName() {return roomName;}
    protected String getRoomDescription() {return roomDescription;}
    protected int getMapX() {return mapX;}
    protected int getMapY() {return mapY;}

}