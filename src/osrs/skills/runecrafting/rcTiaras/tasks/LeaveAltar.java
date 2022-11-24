package osrs.skills.runecrafting.rcTiaras.tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import osrs.skills.runecrafting.rcTiaras.RcTiaras;

import java.util.Objects;


public class LeaveAltar extends Task {


    private RcTiaras bot;

    public LeaveAltar(RcTiaras bot) {
        this.bot = bot;
    }

    public boolean validate() {
        Player me = Players.getLocal();
        return !Inventory.contains(RcTiaras.talisman) && RcTiaras.INSIDE_ALTAR.contains(me);
    }

    public void execute() {
        GameObject portal = GameObjects.newQuery().names("Portal").results().nearest();

        if (IsContinueUp()) {
            Execution.delay(300, 500);
        }
        leaveRuins(portal);
    }

    private void leaveRuins(GameObject portal) {
        if (portal != null && portal.isValid()) {
            if (portal.isVisible()) {
                portal.interact("Use");
                Execution.delayWhile(() -> portal.isValid() && Players.getLocal().isMoving(), 1200, 1800);
            } else {
                Camera.concurrentlyTurnTo(portal, Random.nextDouble(0.300, 0.600));
            }
        }
    }


    private boolean IsContinueUp() {
        return Interfaces.getLoaded(i -> Objects.requireNonNull(i.getText()).contains("Click here to continue")) != null;
    }
}
