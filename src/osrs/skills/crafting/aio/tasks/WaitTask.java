package osrs.skills.crafting.aio.tasks;

import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import osrs.skills.crafting.aio.AioCrafting;

public class WaitTask extends Task {

    private AioCrafting bot;

    public WaitTask(AioCrafting bot) {
        this.bot = bot;
    }

    public boolean validate() {
    return !AioCrafting.scriptStarted;
    }

    public void execute() {
        Execution.delay(200);
    }
}
