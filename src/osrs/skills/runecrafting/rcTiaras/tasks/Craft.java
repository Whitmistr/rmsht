package osrs.skills.runecrafting.rcTiaras.tasks;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import javafx.application.Platform;
import misc.CustomPlayerSense;
import osrs.skills.runecrafting.rcTiaras.RcTiaras;


import java.util.Objects;

public class Craft extends Task {

    private RcTiaras bot;

    public Craft(RcTiaras bot) {
        this.bot = bot;
    }

    public boolean validate() {
        Player me = Players.getLocal();
        return RcTiaras.INSIDE_ALTAR.contains(me) && Inventory.contains(RcTiaras.talisman) && Inventory.contains("Tiara");
    }

    public void execute() {
        GameObject altar = GameObjects.newQuery().names("Altar").results().nearest();
        WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(altar);
        SpriteItem tally = Inventory.newQuery().names(RcTiaras.talisman).results().random();

        if (Objects.requireNonNull(Players.getLocal()).distanceTo(altar) > 8) {
            if (path != null) {
                path.step();
            }
        }

        useItemOnObject(tally, altar);
        Platform.runLater(() -> bot.updateInfo());
    }

    private static void useItemOnObject(SpriteItem item, GameObject object) {
        if (item == null || object == null || !item.isValid() || !object.isValid()) {
        } else {
            if (Inventory.getSelectedItem() == null) {
                if (item.interact("Use")) {
                    Execution.delayUntil(() -> Inventory.getSelectedItem() != null, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                    if (locatable(object,
                            "Use", item.getDefinition().getName() + " -> " + object.getDefinition().getName())) {
                    }
                }
            } else {
                locatable(object,
                        "Use", item.getDefinition().getName() + " -> " + object.getDefinition().getName());
            }
        }
    }

    private static <I extends LocatableEntity> boolean locatable(I target, String action, String name) {
        if (target == null || !target.isValid()) {
            return false;
        }
        if (target.getVisibility() >= 80d) {
            if (name.equals("")) {
                return target.interact(action);
            } else {
                return target.interact(action, name);
            }
        } else {
            if (target.distanceTo(Players.getLocal()) > 6d && target.getPosition().getPlane() == Players.getLocal().getPosition().getPlane()) {
                BresenhamPath.buildTo(target).step();
                return false;
            } else {
                Camera.concurrentlyTurnTo(target, Random.nextDouble(0.300, 0.600));
                return false;
            }
        }
    }


    public boolean IsContinueUp() {
        return Interfaces.getLoaded(i -> i.getText().contains("Click here to continue")) != null;
    }

}
