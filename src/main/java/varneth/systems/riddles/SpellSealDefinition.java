package varneth.systems.riddles;

import java.util.Objects;

import varneth.systems.spells.SpellSource;

public class SpellSealDefinition {

    private final String id;
    private final String sourceRoomId;
    private final String destinationRoomId;
    private final String requiredKnowledgeFlag;
    private final String requiredSpellId;
    private final SpellSource requiredSource;
    private final String openedStoryFlag;

    public SpellSealDefinition(
            String id,
            String sourceRoomId,
            String destinationRoomId,
            String requiredKnowledgeFlag,
            String requiredSpellId,
            SpellSource requiredSource,
            String openedStoryFlag
    ) {
        this.id = requireValue(id, "Spell seal id");
        this.sourceRoomId = requireValue(sourceRoomId, "Source room id");
        this.destinationRoomId = requireValue(
                destinationRoomId,
                "Destination room id"
        );
        this.requiredKnowledgeFlag = requireValue(
                requiredKnowledgeFlag,
                "Required knowledge flag"
        );
        this.requiredSpellId = requireValue(
                requiredSpellId,
                "Required spell id"
        );
        this.requiredSource = Objects.requireNonNull(requiredSource);
        this.openedStoryFlag = requireValue(
                openedStoryFlag,
                "Opened story flag"
        );
    }

    public String getId() {return id;}
    public String getSourceRoomId() {return sourceRoomId;}
    public String getDestinationRoomId() {return destinationRoomId;}
    public String getRequiredKnowledgeFlag() {return requiredKnowledgeFlag;}
    public String getRequiredSpellId() {return requiredSpellId;}
    public SpellSource getRequiredSource() {return requiredSource;}
    public String getOpenedStoryFlag() {return openedStoryFlag;}

    private String requireValue(String value, String fieldName) {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
