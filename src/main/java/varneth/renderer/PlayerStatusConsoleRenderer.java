package varneth.renderer;

import varneth.systems.actors.player.Player;

public class PlayerStatusConsoleRenderer {

    public void render(Player player) {
        System.out.println("Status: " + player.getName());
        System.out.println(
                "Level: " + player.getLevel()
                        + " | EP: " + player.getCurrentXp()
                        + "/" + player.getCurrentXpThreshold()
        );
        System.out.println(
                "HP: " + player.getCurrentHp() + "/" + player.getMaxHp()
                        + " | Ressource: " + player.getCurrentResource()
                        + "/" + player.getMaxResource()
        );
        System.out.println(
                "Stärke: " + player.getStrength()
                        + " | Intelligenz: " + player.getIntelligence()
                        + " | Weisheit: " + player.getWisdom()
        );
        System.out.println("Hauptattribut: " + player.getMainAttribute());
        System.out.println("Gold: " + player.getGold());
    }
}
