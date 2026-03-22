package varneth.application.engine;

public class CombatSessionData {

    private String contextText;
    private int selectedEnemyIndex;

    public CombatSessionData(String contextText) {
        this.contextText = contextText;
    }

    public String getContextText() {return contextText;}
    public int getSelectedEnemyIndex() {return selectedEnemyIndex;}

    public void setContextText(String contextText) {this.contextText = contextText;}
    public void setSelectedEnemyIndex(int selectedEnemyIndex){this.selectedEnemyIndex = selectedEnemyIndex;}
}
