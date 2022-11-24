package osrs.skills.herblore.aio.tasks;

import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import osrs.skills.herblore.aio.AioHerblore;

public class WaitTask extends Task {

    private AioHerblore bot;

    public WaitTask(AioHerblore bot) {
        this.bot = bot;
    }

    public boolean validate() {
        return !AioHerblore.scriptStarted;
    }

    public void execute() {
        Execution.delay(200);
    }
}
