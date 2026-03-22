package varneth.application.engine;

import java.util.ArrayList;
import java.util.List;

public class DialogSessionData {

    private String contextText;
    private List<String> dialogChunks;
    private int currentChunkIndex;
    private PoiInteractionResult poiResult;
    private String returnContextText;
    private String sourceName;

    public DialogSessionData(String contextText) {
        this.contextText = contextText;
        this.dialogChunks = new ArrayList<>();
        this.currentChunkIndex = 0;
    }

    public String getContextText() {return contextText;}
    public void setContextText(String contextText) {this.contextText = contextText;}

    public List<String> getDialogChunks() {return dialogChunks;}
    public void setDialogChunks(List<String> dialogChunks) {this.dialogChunks = dialogChunks;}

    public int getCurrentChunkIndex() {return currentChunkIndex;}
    public void setCurrentChunkIndex(int currentChunkIndex) {this.currentChunkIndex = currentChunkIndex;}

    public PoiInteractionResult getPoiResult() {return poiResult;}
    public void setPoiResult(PoiInteractionResult poiResult) {this.poiResult = poiResult;}

    public String getReturnContextText() {return returnContextText;}
    public void setReturnContextText(String returnContextText) {this.returnContextText = returnContextText;}

    public String getSourceName() {return sourceName;}
    public void setSourceName(String sourceName) {this.sourceName = sourceName;}

    public void clear() {
        this.contextText = "";
        this.dialogChunks = new ArrayList<>();
        this.currentChunkIndex = 0;
        this.poiResult = null;
        this.returnContextText = "";
        this.sourceName = null;
    }
}
