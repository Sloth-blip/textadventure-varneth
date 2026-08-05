package varneth.systems.riddles;

import java.util.Objects;

public class RiddleDefinition {

    private final String id;
    private final String expectedTextAnswer;
    private final String solvedStoryFlag;

    public RiddleDefinition(
            String id,
            String expectedTextAnswer,
            String solvedStoryFlag
    ) {
        this.id = requireValue(id, "Riddle id");
        this.expectedTextAnswer = requireValue(
                expectedTextAnswer,
                "Expected text answer"
        );
        this.solvedStoryFlag = requireValue(solvedStoryFlag, "Solved story flag");
    }

    public String getId() {return id;}
    public String getSolvedStoryFlag() {return solvedStoryFlag;}

    boolean accepts(String answer) {
        return expectedTextAnswer.equalsIgnoreCase(
                Objects.requireNonNull(answer).trim()
        );
    }

    private String requireValue(String value, String fieldName) {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
