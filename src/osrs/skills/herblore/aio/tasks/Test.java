package osrs.skills.herblore.aio.tasks;

import com.runemate.game.api.script.framework.task.Task;
import osrs.skills.herblore.aio.AioHerblore;
import osrs.skills.herblore.aio.enums.Potion;

public class Test extends Task {

    private AioHerblore bot;

    public Test(AioHerblore bot) {
        this.bot = bot;
    }

    public boolean validate() {
        return !AioHerblore.scriptStarted;
    }

    public void execute() {

        System.out.println(AioHerblore.itemName + " " + AioHerblore.primaryIngredient + " " + AioHerblore.secondaryIngredient);


    }
}
