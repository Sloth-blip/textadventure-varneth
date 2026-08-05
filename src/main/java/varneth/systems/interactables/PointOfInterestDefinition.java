package varneth.systems.interactables;

import java.util.List;
import java.util.Optional;

import varneth.systems.riddles.RiddleDefinition;
import varneth.systems.riddles.SpellSealDefinition;
import varneth.systems.reward.Reward;


public class PointOfInterestDefinition {

    private final String pOIID;
    private final String name;
    private final PointOfInterestType type;
    private final List<List<String>> dialogChunks;
    private final Reward rewards;
    private final RiddleDefinition riddle;
    private final SpellSealDefinition spellSeal;


    public PointOfInterestDefinition(
            String pOIID,
            String name,
            PointOfInterestType type,
            List<List<String>> dialogChunks,
            Reward rewards
    )
    {
        this(pOIID, name, type, dialogChunks, rewards, null, null);
    }

    public PointOfInterestDefinition(
            String pOIID,
            String name,
            PointOfInterestType type,
            List<List<String>> dialogChunks,
            Reward rewards,
            RiddleDefinition riddle
    )
    {
        this(pOIID, name, type, dialogChunks, rewards, riddle, null);
    }

    public PointOfInterestDefinition(
            String pOIID,
            String name,
            PointOfInterestType type,
            List<List<String>> dialogChunks,
            Reward rewards,
            SpellSealDefinition spellSeal
    )
    {
        this(pOIID, name, type, dialogChunks, rewards, null, spellSeal);
    }

    private PointOfInterestDefinition(
            String pOIID,
            String name,
            PointOfInterestType type,
            List<List<String>> dialogChunks,
            Reward rewards,
            RiddleDefinition riddle,
            SpellSealDefinition spellSeal
    )
    {
        boolean validTypeContent = switch (type) {
            case RIDDLE -> riddle != null && spellSeal == null;
            case SPELL_SEAL -> riddle == null && spellSeal != null;
            default -> riddle == null && spellSeal == null;
        };
        if (!validTypeContent) {
            throw new IllegalArgumentException(
                    "POI type and specialized definition must match"
            );
        }

        this.pOIID = pOIID;
        this.name = name;
        this.type = type;
        this.dialogChunks = dialogChunks;
        this.rewards = rewards;
        this.riddle = riddle;
        this.spellSeal = spellSeal;
    }

    protected String getpOIID() {return pOIID;}
    protected String getName() {return name;}
    protected PointOfInterestType getType() {return type;}
    protected List<List<String>> getDialogsChunks() {return dialogChunks;}
    protected List<String> getDialogChunk(boolean isUsed) {
        if (dialogChunks.isEmpty()) {
            return List.of();
        }

        int chunkIndex = isUsed ? dialogChunks.size() - 1 : 0;
        return dialogChunks.get(chunkIndex);
    }
    protected Reward getRewards() {return rewards;}
    protected Optional<RiddleDefinition> getRiddle() {
        return Optional.ofNullable(riddle);
    }
    protected Optional<SpellSealDefinition> getSpellSeal() {
        return Optional.ofNullable(spellSeal);
    }


}
