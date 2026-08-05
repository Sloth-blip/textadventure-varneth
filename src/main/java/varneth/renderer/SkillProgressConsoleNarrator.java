package varneth.renderer;

import varneth.engine.events.EventBus;
import varneth.engine.events.SkillProgressed;

public class SkillProgressConsoleNarrator {

    public SkillProgressConsoleNarrator(EventBus bus) {
        bus.subscribe(SkillProgressed.class, this::onSkillProgressed);
    }

    private void onSkillProgressed(SkillProgressed event) {
        if (event.levelsGained() == 0) {
            System.out.println(
                    event.skillName() + " erhält " + event.experienceGained()
                            + " Zauber-EP (" + event.currentExperience()
                            + "/" + event.experienceThreshold() + ")."
            );
        } else if (event.levelsGained() == 1) {
            System.out.println(
                    event.skillName() + " erhält " + event.experienceGained()
                            + " Zauber-EP und steigt auf Zauberlevel "
                            + event.resultingLevel() + "! (" + event.currentExperience()
                            + "/" + event.experienceThreshold() + ")"
            );
        } else {
            System.out.println(
                    event.skillName() + " erhält " + event.experienceGained()
                            + " Zauber-EP und steigt um " + event.levelsGained()
                            + " Zauberlevel auf Level " + event.resultingLevel()
                            + "! (" + event.currentExperience()
                            + "/" + event.experienceThreshold() + ")"
            );
        }
    }
}
