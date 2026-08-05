package varneth.systems.rooms;

import java.util.List;
import java.util.Optional;

import varneth.engine.GameState;
import varneth.engine.events.EventBus;

import varneth.systems.actors.player.Player;
import varneth.systems.interactables.PointOfInterest;
import varneth.systems.interactables.PointOfInterestType;
import varneth.systems.riddles.RiddleAttemptResult;
import varneth.systems.riddles.RiddleDefinition;
import varneth.systems.riddles.RiddleHandler;
import varneth.systems.riddles.SpellSealDefinition;
import varneth.systems.riddles.SpellSealHandler;
import varneth.systems.riddles.SpellSealResult;
import varneth.systems.reward.RewardHandler;
import varneth.systems.spells.AvailableSpell;
import varneth.ui.consolemenus.ExplorationConsoleMenu;
import varneth.ui.consolemenus.SpellConsoleMenu;
import varneth.ui.enums.ExplorationAction;

public class ExplorationPhase {

    private final RewardHandler rewardHandler;
    private final RiddleHandler riddleHandler;
    private final SpellSealHandler spellSealHandler;
    private final SpellConsoleMenu spellConsoleMenu;

    public ExplorationPhase(EventBus bus) {
        rewardHandler = new RewardHandler(bus);
        riddleHandler = new RiddleHandler();
        spellSealHandler = new SpellSealHandler(bus);
        spellConsoleMenu = new SpellConsoleMenu();
    }

    ExplorationConsoleMenu explorationConsoleMenu = new ExplorationConsoleMenu();

    public ExplorationAction explorationPhase(Room room, boolean firstVisit) {
        System.out.println(room.getRoomDescription());

        if(firstVisit) {
            explorationConsoleMenu.consoleMenuDisplayRoomDialog(room.getRoomDialogChunks());
        }

        explorationConsoleMenu.consoleMenuExplorationEntered(room);
        return explorationConsoleMenu.consoleMenuExplorationOptionChooser(room);
    }

    public Optional<Room> chooseNextRoom(Room currentRoom){
        return explorationConsoleMenu.consoleMenuDisplayAndChooseConnectedRooms(currentRoom.getConnectedRooms());
    }

    public void replayRoomDialog(List<String> roomDialog){
        explorationConsoleMenu.consoleMenuDisplayRoomDialog(roomDialog);
    }

    public void playInteractableDialog(Room room, GameState gameState){

        Optional<PointOfInterest> maybePOI = explorationConsoleMenu.consoleMenuDisplayAndChooseInteractables(room);
        if (maybePOI.isEmpty()) {
            return;
        }

        PointOfInterest pOI = maybePOI.get();
        Player player = gameState.getPlayer();
        explorationConsoleMenu.consoleMenuDisplayInteractableDialog(
                pOI.getDialogChunks()
        );

        if (pOI.getType() == PointOfInterestType.RIDDLE) {
            if (!pOI.isUsed()) {
                playRiddle(room, pOI, gameState);
            }
            return;
        }
        if (pOI.getType() == PointOfInterestType.SPELL_SEAL) {
            if (!pOI.isUsed()) {
                playSpellSeal(room, pOI, gameState);
            }
            return;
        }
        if (pOI.getType() == PointOfInterestType.REST){
            player.takeRest();
            explorationConsoleMenu.explorationMenuTakeRest(player);
        }
        rewardHandler.grantRewardsFromPOI(pOI, player);
        room.removeOrFlagInteractable(pOI);
    }

    private void playRiddle(
            Room room,
            PointOfInterest pointOfInterest,
            GameState gameState
    ) {
        RiddleDefinition riddle = pointOfInterest.getRiddle().orElseThrow(
                () -> new IllegalStateException("Riddle POI has no riddle definition")
        );
        Optional<String> maybeAnswer =
                explorationConsoleMenu.consoleMenuReadRiddleAnswer();
        RiddleAttemptResult result = maybeAnswer
                .map(answer -> riddleHandler.attempt(riddle, answer, gameState))
                .orElse(RiddleAttemptResult.CANCELLED);

        explorationConsoleMenu.consoleMenuDisplayRiddleResult(result);

        if (result == RiddleAttemptResult.SOLVED) {
            rewardHandler.grantRewardsFromPOI(
                    pointOfInterest,
                    gameState.getPlayer()
            );
            room.removeOrFlagInteractable(pointOfInterest);
        } else if (result == RiddleAttemptResult.ALREADY_SOLVED) {
            room.removeOrFlagInteractable(pointOfInterest);
        }
    }

    private void playSpellSeal(
            Room room,
            PointOfInterest pointOfInterest,
            GameState gameState
    ) {
        SpellSealDefinition seal = pointOfInterest.getSpellSeal().orElseThrow(
                () -> new IllegalStateException(
                        "Spell-seal POI has no spell-seal definition"
                )
        );
        SpellSealResult status = spellSealHandler.inspect(seal, gameState);
        if (status != SpellSealResult.READY) {
            explorationConsoleMenu.consoleMenuDisplaySpellSealResult(status);
            if (status == SpellSealResult.ALREADY_OPENED) {
                room.removeOrFlagInteractable(pointOfInterest);
            }
            return;
        }

        List<AvailableSpell> availableSpells =
                gameState.getPlayer().getAvailableSpells();
        if (availableSpells.isEmpty()) {
            explorationConsoleMenu.consoleMenuDisplaySpellSealResult(
                    SpellSealResult.NO_AVAILABLE_SPELLS
            );
            return;
        }

        Optional<AvailableSpell> maybeSpell =
                spellConsoleMenu.chooseSpell(availableSpells);
        if (maybeSpell.isEmpty()) {
            explorationConsoleMenu.consoleMenuDisplaySpellSealResult(
                    SpellSealResult.CANCELLED
            );
            return;
        }

        AvailableSpell selectedSpell = maybeSpell.get();
        SpellSealResult result = spellSealHandler.attempt(
                seal,
                selectedSpell,
                gameState
        );
        explorationConsoleMenu.consoleMenuDisplaySpellSealResult(result);

        if (result == SpellSealResult.WRONG_SPELL
                || result == SpellSealResult.OPENED) {
            explorationConsoleMenu.consoleMenuDisplayCastingState(
                    gameState.getPlayer(),
                    selectedSpell
            );
        }
        if (result == SpellSealResult.OPENED) {
            room.removeOrFlagInteractable(pointOfInterest);
        }
    }


}