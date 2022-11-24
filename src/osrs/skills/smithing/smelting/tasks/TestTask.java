package osrs.skills.smithing.smelting.tasks;

import com.runemate.game.api.script.framework.task.Task;
import osrs.skills.smithing.smelting.AioSmelting;

public class TestTask extends Task {

    private AioSmelting bot;

    public TestTask(AioSmelting bot) {
        this.bot = bot;
    }

    public boolean validate() {
        return false;
    }

    public void execute() {

        System.out.print(AioSmelting.mode + " " + AioSmelting.item1 + " " + AioSmelting.item1qt + " " + AioSmelting.item2 + " " + AioSmelting.item2qt + " " + AioSmelting.mould + " ");

    }
}
