package engine;



import systems.actors.ActorDefinition;
import systems.actors.ActorState;
import systems.actors.MainAttribute;
import systems.actors.player.Player;
import ui.consolemenus.ConsoleMenuGeneral;
import ui.enums.GameStartAction;

import java.util.ArrayList;
import java.util.List;

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
