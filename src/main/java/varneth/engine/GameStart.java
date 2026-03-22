package varneth.engine;



import java.util.ArrayList;
import java.util.List;

import varneth.systems.actors.ActorDefinition;
import varneth.systems.actors.ActorState;
import varneth.systems.actors.MainAttribute;
import varneth.systems.actors.player.Player;
import varneth.ui.consolemenus.ConsoleMenuGeneral;
import varneth.ui.enums.GameStartAction;

public class GameStart {

    static ConsoleMenuGeneral consoleMenuGeneral = new ConsoleMenuGeneral();

    public void gameStartStart(){

        while (true){
            GameStartAction choice = consoleMenuGeneral.consoleMenuGameStart();
            System.out.println(choice + " gewählt.");
            switch (choice){
                case NEWGAME -> {
                    System.out.println("Starte...");
                    var gameloop = new GameLoop();
                    Player player = new Player(
                            new ActorDefinition(
                                    "Arenn",
                                    40,
                                    10,
                                    20,
                                    5,
                                    10,
                                    2,
                                    15,
                                    5,
                                    10,
                                    1,
                                    10,
                                    2,
                                    MainAttribute.INTELLIGENCE
                            ),
                            new ActorState(
                                    50,
                                    25,
                                    1,
                                    0,
                                    new ArrayList<>(List.of())
                            )
                    );
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
