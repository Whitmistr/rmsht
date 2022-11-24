package osrs.skills.crafting.aio.tasks;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.*;
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

public class GlassCraftTask extends Task {

    private AioCrafting bot;
    private Player local;
    private SpriteItem glasspipe;
    private SpriteItem glass;

    public GlassCraftTask(AioCrafting bot) {
        this.bot = bot;
    }

    @Override
    public boolean validate() {
        return AioCrafting.itemType.equalsIgnoreCase("Glass items");
    }

    @Override
    public void execute() {
        glasspipe = Inventory.getItems("Glassblowing pipe").first();
        glass = Inventory.getItems("Molten glass").random();

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
                Execution.delayWhile(MakeAllInterface::isOpen, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                Execution.delayWhile(() -> Inventory.contains("Molten glass") && !ChatDialog.isOpen(), () -> Objects.requireNonNull(local).getAnimationId() != -1, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY_LONG.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY_LONG.getAsInteger());
            }
        }
    }

    private void itemHandler() {
        if (Inventory.getSelectedItem() == null) {
            if (glasspipe.interact("Use")) {
                Execution.delayUntil(() -> Inventory.getSelectedItem() != null, CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
            }
        } else if (glass.interact("Use")) {
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
        if (Inventory.getQuantity("Molten glass") > 27 || Inventory.getQuantity("Glassblowing pipe") > 1) {
            Bank.depositInventory();
            return;
        }
        if (Inventory.containsAnyExcept("Glassblowing pipe", "Molten glass")) {
            Bank.depositAllExcept("Glassblowing pipe", "Molten glass");
            return;
        }
        if (Inventory.isFull() && (!Inventory.contains("Glassblowing pipe") || !Inventory.contains("Molten glass"))) {
            Bank.depositAllExcept("Glassblowing pipe", "Molten glass");
            return;
        }
        withdraw();
    }

    private void withdraw() {
        if (!Inventory.contains("Glassblowing pipe")) {
            if (Bank.getQuantity("Glassblowing pipe") > 0) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw("Glassblowing pipe", 1)) {
                    Execution.delayUntil(() -> Inventory.contains("Glassblowing pipe"), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("No Glassblowing pipe found.");
            }
            return;
        }
        if (Inventory.contains("Glassblowing pipe") && !Inventory.contains("Molten glass")) {
            if (Bank.getQuantity("Molten glass") > 30) {
                Execution.delay(CustomPlayerSense.Key.JOJO_MIN_IDLE_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_IDLE_INTERACT_DELAY.getAsInteger());
                if (Bank.withdraw("Molten glass", 27 - Inventory.getQuantity("Molten glass"))) {
                    Execution.delayUntil(() -> Inventory.contains("Molten glass"), CustomPlayerSense.Key.JOJO_MIN_INTERACT_DELAY.getAsInteger(), CustomPlayerSense.Key.JOJO_MAX_INTERACT_DELAY.getAsInteger());
                }
            } else {
                bot.stop("Out of: " + "Molten glass");
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
        return glasspipe == null || glass == null;
    }

    private boolean shouldCloseBank() {
        return Bank.isOpen();
    }

    private boolean shouldUseItems() {
        return !MakeAllInterface.isOpen() && Objects.requireNonNull(local).getAnimationId() == -1;
    }

}