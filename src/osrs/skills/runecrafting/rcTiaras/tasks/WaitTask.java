package osrs.skills.runecrafting.rcTiaras.tasks;

import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import osrs.skills.runecrafting.rcTiaras.RcTiaras;

public class WaitTask extends Task {

    private RcTiaras bot;

    public WaitTask(RcTiaras bot) {
        this.bot = bot;
    }

    public boolean validate() {
    return !RcTiaras.scriptStarted;
    }

    public void execute() {
        Execution.delay(200);
    }
}
