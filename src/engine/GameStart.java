package engine;

import engine.player.PlayerState;
import systems.spells.KnownSpell;
import systems.spells.SpellTemplates;
import ui.ConsoleMenu;
import ui.ConsoleMenu.*;

import java.util.Objects;

public class GameStart {


    static ConsoleMenu consoleMenu = new ConsoleMenu();

    public void gameStartStart(){



        while (true){
            GameStartAction choice = consoleMenu.consoleMenuGameStart();
            System.out.println(choice + " gewählt.");
            switch (choice){
                case NEWGAME -> {
                    System.out.println("Starte...");
                    var gameloop = new GameLoop();
                    var player = new PlayerState(1);
                    player.addSpell(new KnownSpell(SpellTemplates.get("pebbles")));
                    gameloop.gameLoopStart(player);
                }
                case LOAD -> {
                    System.out.println("ToDo");
                }
                case SETTINGS -> System.out.println("ToDo");
            }
            if (choice == GameStartAction.END){
                break;
            }
        }
    }
}
