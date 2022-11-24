package osrs.skills.runecrafting.rcTiaras.tasks;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import javafx.application.Platform;
import osrs.skills.runecrafting.rcTiaras.RcTiaras;

public class Walk extends Task {

    private RcTiaras bot;

    public Walk(RcTiaras bot) {
        this.bot = bot;
    }

    public boolean validate() {

    Player me = Players.getLocal();

        if (RcTiaras.INSIDE_ALTAR.contains(me) || (RcTiaras.ALTAR_AREA.contains(me) && Inventory.contains(RcTiaras.talisman))) {
            return false;
        } else {
            return walkToBank(RcTiaras.talisman, RcTiaras.BANK_AREA) || walkToAltar(RcTiaras.talisman, RcTiaras.ALTAR_AREA);
        }
    }

    public void execute() {
        WebPath webPath = Traversal.getDefaultWeb().getPathBuilder().buildTo(walkToBank(RcTiaras.talisman, RcTiaras.BANK_AREA) ? RcTiaras.BANK_AREA.getRandomCoordinate() : RcTiaras.ALTAR_AREA.getRandomCoordinate());

        if (Players.getLocal() != null) {
            if (webPath != null) {
                webPath.step();
            } else {
                final BresenhamPath bp = BresenhamPath.buildTo(walkToBank(RcTiaras.talisman, RcTiaras.BANK_AREA) ? RcTiaras.BANK_AREA.getRandomCoordinate() : RcTiaras.ALTAR_AREA.getRandomCoordinate());
                if (bp != null && bp.step()) {
                    Execution.delayWhile(Players.getLocal()::isMoving, 1000, 2000);
                }
            }
        }

        Platform.runLater(() -> bot.updateInfo());
    }

    private boolean walkToBank(String talisman, Area bankArea) {
        Player me = Players.getLocal();
        return !Inventory.contains(talisman) && !bankArea.contains(me);
    }

    private boolean walkToAltar(String talisman, Area altarArea) {
        Player me = Players.getLocal();
        return Inventory.contains(talisman) && !altarArea.contains(me);
    }
}

