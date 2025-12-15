package systems.rooms;

import java.util.List;

public class RoomDefinition {

    private final String roomId;
    private final String roomName;
    private final String roomDescription;


    @Override
    public String toString() {return roomName;}

    public RoomDefinition(
            String roomId,
            String roomName, String roomDescription
    )
    {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
    }

    public String getRoomId() {return roomId;}
    public String getRoomName() {return roomName;}
    public String getRoomDescription() {return roomDescription;}

}