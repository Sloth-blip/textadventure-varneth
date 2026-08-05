package varneth.systems.riddles;

import java.util.Objects;

import varneth.engine.GameState;
import varneth.engine.events.CrystalShattered;
import varneth.engine.events.EventBus;
import varneth.systems.rooms.Room;
import varneth.systems.spells.AvailableSpell;

public class SpellSealHandler {

    private final EventBus bus;

    public SpellSealHandler(EventBus bus) {
        this.bus = Objects.requireNonNull(bus);
    }

    public SpellSealResult inspect(
            SpellSealDefinition seal,
            GameState gameState
    ) {
        Objects.requireNonNull(seal);
        Objects.requireNonNull(gameState);
        requireSourceRoom(seal, gameState);

        if (gameState.hasStoryFlag(seal.getOpenedStoryFlag())) {
            ensureConnection(seal, gameState);
            return SpellSealResult.ALREADY_OPENED;
        }
        if (!gameState.hasStoryFlag(seal.getRequiredKnowledgeFlag())) {
            return SpellSealResult.MISSING_KNOWLEDGE;
        }
        return SpellSealResult.READY;
    }

    public SpellSealResult attempt(
            SpellSealDefinition seal,
            AvailableSpell selectedSpell,
            GameState gameState
    ) {
        Objects.requireNonNull(selectedSpell);
        SpellSealResult status = inspect(seal, gameState);
        if (status != SpellSealResult.READY) {
            return status;
        }

        if (!gameState.getPlayer().tryPayCastingCost(selectedSpell)) {
            return SpellSealResult.CAST_FAILED;
        }
        if (selectedSpell.crystalBreaks()) {
            bus.publish(new CrystalShattered(
                    gameState.getPlayer().getName(),
                    selectedSpell.crystal().getName()
            ));
        }
        if (!selectedSpell.skill().getId().equals(seal.getRequiredSpellId())
                || selectedSpell.source() != seal.getRequiredSource()) {
            return SpellSealResult.WRONG_SPELL;
        }

        gameState.addStoryFlag(seal.getOpenedStoryFlag());
        ensureConnection(seal, gameState);
        return SpellSealResult.OPENED;
    }

    private void requireSourceRoom(
            SpellSealDefinition seal,
            GameState gameState
    ) {
        if (!gameState.getCurrentRoomId().equals(seal.getSourceRoomId())) {
            throw new IllegalStateException(
                    "Spell seal " + seal.getId() + " belongs to room "
                            + seal.getSourceRoomId()
            );
        }
    }

    private void ensureConnection(
            SpellSealDefinition seal,
            GameState gameState
    ) {
        Room source = gameState.getWorld().getRoomById(seal.getSourceRoomId());
        Room destination = gameState.getWorld().getRoomById(
                seal.getDestinationRoomId()
        );
        source.connectTo(destination);
        destination.connectTo(source);
    }
}
