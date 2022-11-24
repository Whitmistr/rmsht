package osrs.skills.crafting.aio.tasks;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.osrs.local.hud.interfaces.MakeAllInterface;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;
import misc.CustomPlayerSense;
import osrs.skills.crafting.aio.AioCrafting;

import java.util.Objects;

public class HardLeatherTask extends Task {

    private AioCrafting bot;
    private Player local;
    private SpriteItem needle;
    private SpriteItem thread;
    private SpriteItem hide;


    public HardLeatherTask(AioCrafting bot) {
        this.bot = bot;
    }

    @Override
    public boolean validate() {
        return AioCrafting.itemType.equalsIgnoreCase("Hard leather");
    }

    @Override
    public void execute() {

        needle = Inventory.getItems("Needle").first();
        thread = Inventory.getItems("Thread").first();
        hide = Inventory.getItems(AioCrafting.hide).random();

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
        if (shouldUseItems()) {
            itemHandler();
            return;
        }
        makeXHandler();
    }


    private void makeXHandler() {
        if (MakeAllInterface.isOpen()) {
            if (MakeAllInterface.getSelectedQuantity() != 0) {
                MakeAllInterface.setSelectedQuantity(0);
                return;
            }
            Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
            if (MakeAllInterface.selectItem(AioCrafting.itemName, true)) {
                Execution.delayUntil(() -> !MakeAllInterface.isOpen(), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                Execution.delayWhile(() -> Inventory.contains(AioCrafting.hide) && !ChatDialog.isOpen(), () -> Objects.requireNonNull(local).getAnimationId() != -1, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY_LONG.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY_LONG.getAsInteger());
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsLong());
            }
        }
    }

    private void itemHandler() {
        if (Inventory.getSelectedItem() == null) {
            if (needle.interact("Use")) {
                Execution.delayUntil(() -> Inventory.getSelectedItem() != null, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
            }
        } else if (hide.interact("Use")) {
            Execution.delayUntil(MakeAllInterface::isOpen, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
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
        if (Inventory.getQuantity(AioCrafting.hide) > 26) {
            Bank.depositInventory();
            return;
        }
        if (Inventory.containsAnyExcept("Needle", "Thread", AioCrafting.hide)) {
            Bank.depositAllExcept("Needle", "Thread", AioCrafting.hide);
            return;
        }
        withdraw();
    }

    private void withdraw() {
        if (!Inventory.contains("Needle")) {
            if (Bank.getQuantity("Needle") > 0) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw("Needle", 1)) {
                    Execution.delayUntil(() -> Inventory.contains("Needle"), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("No needle found.");
            }
            return;
        }
        if (Inventory.getQuantity() < 25) {
            if (Bank.getQuantity("Thread") > 50) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw("Thread", 50 - Inventory.getQuantity("Thread"))) {
                    Execution.delayUntil(() -> Inventory.contains("Thread"), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("No thread found.");
            }
            return;
        }
        if (Inventory.contains("Needle") && Inventory.contains("Thread") && !Inventory.contains(AioCrafting.hide)) {
            if (Bank.getQuantity(AioCrafting.hide) > 30) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw(AioCrafting.hide, 27 - Inventory.getQuantity(AioCrafting.hide))) {
                    Execution.delayUntil(() -> Inventory.contains(AioCrafting.hide), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("Out of: " + AioCrafting.hide);
            }
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
        return needle == null || thread == null || hide == null;
    }

    private boolean shouldCloseBank() {
        return Bank.isOpen();
    }

    private boolean shouldUseItems() {
        return !MakeAllInterface.isOpen() && Objects.requireNonNull(local).getAnimationId() == -1;
    }
}
