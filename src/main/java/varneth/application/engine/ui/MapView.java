package varneth.application.engine.ui;

import java.util.List;

public class MapView {

    private final List<MapNodeView> nodes;
    private final List<MapEdgeView> edges;

    public MapView(
        List<MapNodeView> nodes,
        List<MapEdgeView> edges
    ) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<MapNodeView> getNodes() {return nodes;}
    public List<MapEdgeView> getEdges() {return edges;}

}
