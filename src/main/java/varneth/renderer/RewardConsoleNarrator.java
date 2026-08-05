package varneth.renderer;

import varneth.engine.events.EventBus;
import varneth.engine.events.RewardGranted;

public class RewardConsoleNarrator {

    public RewardConsoleNarrator(EventBus bus) {
        bus.subscribe(RewardGranted.class, this::onRewardGranted);
    }

    private void onRewardGranted(RewardGranted event) {
        for (String skillName : event.learnedSkillNames()) {
            System.out.println(event.recipientName() + " hat " + skillName + " erlernt!");
        }
        for (String itemName : event.receivedItemNames()) {
            System.out.println(event.recipientName() + " erhält: " + itemName + ".");
        }
        if (event.experience() > 0) {
            System.out.println(
                    event.recipientName() + " hat " + event.experience() + " Erfahrung erhalten!"
            );
        }
        if (event.gold() > 0) {
            System.out.println(event.recipientName() + " hat " + event.gold() + " Gold erhalten!");
        }
        if (event.levelsGained() == 1) {
            System.out.println(
                    event.recipientName() + " ist ein Level aufgestiegen und ist nun Level "
                            + event.resultingLevel()
            );
        } else if (event.levelsGained() > 1) {
            System.out.println(
                    event.recipientName() + " ist " + event.levelsGained()
                            + " Level aufgestiegen und ist nun Level " + event.resultingLevel()
            );
        }
    }
}
