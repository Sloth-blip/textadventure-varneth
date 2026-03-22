package varneth.application.engine.ui;

public class MapNodeView {

    private final String roomId;
    private final String label;
    private final int x;
    private final int y;
    private final boolean visited;
    private final boolean current;
    private final boolean discovered;

    public MapNodeView(
        String roomId,
        String label,
        int x,
        int y,
        boolean visited,
        boolean current,
        boolean discovered
    ) {
        this.roomId = roomId;
        this.label = label;
        this.x = x;
        this.y = y;
        this.visited = visited;
        this.current = current;
        this.discovered = discovered;
    }

    public String getRoomId() {return roomId;}
    public String getLabel() {return label;}
    public int getX() {return x;}
    public int getY() {return y;}
    public boolean isVisited() {return visited;}
    public boolean isCurrent() {return current;}
    public boolean isDiscovered() {return discovered;}
}
