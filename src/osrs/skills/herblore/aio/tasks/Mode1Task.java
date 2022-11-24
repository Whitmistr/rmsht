package osrs.skills.herblore.aio.tasks;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.queries.SpriteItemQueryBuilder;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.osrs.local.hud.interfaces.MakeAllInterface;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import misc.CustomPlayerSense;
import osrs.skills.herblore.aio.AioHerblore;

import java.util.Objects;

public class Mode1Task extends Task {

    /*
     * This mode withdraws only a single item with quantity of 28.
     */

    private AioHerblore bot;

    private Player local;
    private SpriteItem item1;

    public Mode1Task(AioHerblore bot) {
        this.bot = bot;
    }

    public boolean validate() {
        return AioHerblore.mode.equals("1");
    }

    public void execute() {
        item1 = Inventory.getItems(AioHerblore.itemName).random();

        if ((local = Players.getLocal()) == null) {
            return;
        }
        if (shouldBank()) {
            bank();
            return;
        }
        if (shouldCloseBank()) {
            bankCloser();
            return;
        }
        itemHandler();
    }

    private void itemHandler() {
        if (Inventory.getSelectedItem() == null) {
            item1.interact("Clean");
            Execution.delay(CustomPlayerSense.Key.JOJO_MIN_HERB.getAsInteger(),CustomPlayerSense.Key.JOJO_MAX_HERB.getAsInteger());
        } else {
            Mouse.click(Mouse.Button.LEFT);
        }
    }

    private void bank() {
        if (!Bank.isOpen()) {
            if (Inventory.getSelectedItem() != null) {
                if (Mouse.click(Mouse.Button.LEFT)) {
                    return;
                }
            }
            openBank();
            return;
        }
        if (Inventory.containsAnyExcept(AioHerblore.itemName)) {
            Bank.depositAllExcept(AioHerblore.itemName);
            return;
        }
        if (Inventory.isFull() && !Inventory.contains(AioHerblore.itemName)) {
            Bank.depositAllExcept(AioHerblore.itemName);
            return;
        }
        withdraw();
    }

    private void withdraw() {
        if (Bank.getQuantity(AioHerblore.itemName) > 30) {
            Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
            if (Bank.withdraw(AioHerblore.itemName, 28 - Inventory.getQuantity(AioHerblore.itemName))) {
                Execution.delayUntil(() -> Inventory.contains(AioHerblore.itemName), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
            }
        } else {
            bot.stop("Out of: " + AioHerblore.itemName);
        }
    }

    private void bankCloser() {
        Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
        if (Bank.close(CustomPlayerSense.Key.JOJO_USE_BANK_HOTKEYS.getAsBoolean())) {
            Execution.delayUntil(() -> !Bank.isOpen(), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
        }
    }

    private void openBank() {
        LocatableEntity bank = Npcs.newQuery().actions("Bank").results().nearest();
        if (bank == null) {
            bank = GameObjects.newQuery().actions("Bank").results().nearest();
        }
        if (bank != null) {
            if (bank.isVisible()) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (bank.interact("Bank")) {
                    Player local = Players.getLocal();
                    if (local == null) {
                        return;
                    }
                    Execution.delayUntil(Bank::isOpen, local::isMoving, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY_LONG.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY_LONG.getAsInteger());
                }
            } else {
                Camera.concurrentlyTurnTo(bank, Random.nextDouble(0.300, 0.600));
            }
        }
    }

    private boolean shouldBank() {
        return item1 == null;
    }

    private boolean shouldCloseBank() {
        return Bank.isOpen();
    }

}

