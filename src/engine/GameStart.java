package engine;



import systems.actors.ActorDefintiton;
import systems.actors.ActorState;
import systems.actors.MainAttribute;
import systems.actors.player.Player;
import systems.spells.Skill;
import systems.spells.SpellTemplates;
import ui.ConsoleMenu;
import ui.ConsoleMenu.*;

import java.util.ArrayList;
import java.util.List;
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
                    Player player = new Player(
                            new ActorDefintiton(
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
                                    100,
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
                    player.addLearnedSkill(SpellTemplates.get("pebbles"));
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
