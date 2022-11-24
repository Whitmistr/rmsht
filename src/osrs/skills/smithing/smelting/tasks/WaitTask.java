package osrs.skills.smithing.smelting.tasks;

import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import osrs.skills.smithing.smelting.AioSmelting;

public class WaitTask extends Task {

    private AioSmelting bot;

    public WaitTask(AioSmelting bot) {
        this.bot = bot;
    }

    public boolean validate() {
        return !AioSmelting.scriptStarted;
    }

    public void execute() {
        Execution.delay(200);
    }
}
