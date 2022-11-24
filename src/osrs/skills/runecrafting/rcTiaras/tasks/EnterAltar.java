package osrs.skills.runecrafting.rcTiaras.tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import osrs.skills.runecrafting.rcTiaras.RcTiaras;


public class EnterAltar extends Task {

    private RcTiaras bot;

    public EnterAltar(RcTiaras bot) {
        this.bot = bot;
    }

    public boolean validate() {
        Player me = Players.getLocal();
        return Inventory.contains(RcTiaras.talisman) && RcTiaras.ALTAR_AREA.contains(me);
    }

    public void execute() {
        GameObject ruins = GameObjects.newQuery().names("Mysterious ruins").results().nearest();

        enterRuins(ruins);
    }

    private void enterRuins(GameObject ruins) {
        if (ruins != null && ruins.isValid()) {
            if (ruins.isVisible()) {
                ruins.interact("Enter");
                Execution.delayWhile(() -> ruins.isValid() && Players.getLocal().isMoving(), 1200, 1800);
            } else {
                Camera.concurrentlyTurnTo(ruins, Random.nextDouble(0.300, 0.600));
            }
        }
    }
}
