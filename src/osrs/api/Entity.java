package osrs.api;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.entities.definitions.NpcDefinition;
import com.runemate.game.api.hybrid.entities.details.Interactable;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.Menu;
import com.runemate.game.api.hybrid.local.hud.MenuItem;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.Execution;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by Ned on 03/07/2019.
 */
public interface Entity {

    static boolean interact(final SpriteItem obj, final List<String> actions) {
        MenuItem item;
        String validAction = getValidAction(obj, actions);
        if (Menu.isOpen()) {
            if (!Menu.getItems().isEmpty()) {
                item = Menu.getItem(validAction);
                return item != null && item.click();
            } else {
                Menu.close();
            }
        } else {
            return obj != null && obj.interact(validAction) && Mouse.wasClickSuccessful(obj);
        }
        return false;
    }

    static boolean interact(final GameObject obj, final List<String> actions) {
        MenuItem item;
        String validAction = getValidAction(obj, actions);
        if (Menu.isOpen()) {
            if (!Menu.getItems().isEmpty()) {
                item = Menu.getItem(validAction);
                return item != null && item.click();
            } else {
                Menu.close();
            }
        } else {
            return obj != null && obj.interact(validAction) && Mouse.wasClickSuccessful(obj);
        }
        return false;
    }

    static boolean interact(final Npc obj, final List<String> actions) {
        MenuItem item;
        String validAction = getValidAction(obj, actions);
        if (Menu.isOpen()) {
            if (!Menu.getItems().isEmpty()) {
                item = Menu.getItem(validAction);
                return item != null && item.click();
            } else {
                Menu.close();
            }
        } else {
            return obj != null && obj.interact(validAction) && Mouse.wasClickSuccessful(obj);
        }
        return false;
    }

    static boolean interact(final Interactable obj, final Pattern action) {
        MenuItem item;
        if (Menu.isOpen()) {
            if ((item = Menu.getItem(action)) != null) {
                return item.click();
            } else {
                Menu.close();
            }
        } else {
            return obj != null && obj.interact(action) && Mouse.wasClickSuccessful(obj);
        }
        return false;
    }

    static boolean interact(final String action, final GameObject object) {
        MenuItem item;
        if (Menu.isOpen()) {
            GameObjectDefinition def;
            if (object != null && (def = object.getDefinition()) != null) {
                String name = def.getName();
                if ((item = Menu.getItem(name, action)) != null) {
                    return item.click();
                } else {
                    Menu.close();
                }
            }
        } else {
            return object != null && object.interact(action) && Mouse.wasClickSuccessful(object);
        }
        return false;
    }

    static boolean interact(final String action, final Npc npc) {
        MenuItem item;
        if (Menu.isOpen()) {
            NpcDefinition def;
            if (npc != null && (def = npc.getDefinition()) != null) {
                String name = def.getName();
                if ((item = Menu.getItem(name, action)) != null) {
                    return item.click();
                } else {
                    Menu.close();
                }
            }
        } else {
            return npc != null && npc.interact(action) && Mouse.wasClickSuccessful(npc);
        }
        return false;
    }

    static boolean interact(final String action, final Player player) {
        MenuItem item;
        if (Menu.isOpen()) {
            String name;
            if (player != null && (name = player.getName()) != null) {
                if ((item = Menu.getItem(name, action)) != null) {
                    return item.click();
                } else {
                    Menu.close();
                }
            }
        } else {
            return player != null && player.interact(action) && Mouse.wasClickSuccessful(player);
        }
        return false;
    }

    static boolean interact(final Interactable obj, final String action) {
        MenuItem item;
        if (Menu.isOpen()) {
            if ((item = Menu.getItem(action)) != null) {
                return item.click();
            } else {
                Menu.close();
            }
        } else {
            return obj != null && obj.interact(action) && Mouse.wasClickSuccessful(obj);
        }
        return false;
    }

    static boolean isVisible(LocatableEntity entity) {
        return entity != null && (entity.isVisible() || Camera.turnTo(entity));
    }

    static boolean hover(Interactable interactable) {
        return interactable.contains(Mouse.getPosition()) || interactable.hover();
    }

    static boolean turnAndHover(Interactable interactable) {
        if (interactable != null && (interactable.getVisibility() <= 80)) {
            Camera.concurrentlyTurnTo((Locatable) interactable, Random.nextDouble(0.8, 1));
        }
        return hover(interactable);
    }

    static boolean hopAndClick(Interactable interactable) {
        if (interactable != null) {
            if (Mouse.PathGenerator.hop(interactable.getInteractionPoint())) {
                return fastClick(Mouse.Button.LEFT);
            } else {
                return click(interactable);
            }
        }
        return false;
    }

    static boolean fastClick(Mouse.Button button) {
        final long delay = (long) Random.nextGaussian(20, 140, 50);
        if (Mouse.press(button)) {
            Execution.delay(delay);
        }
        return Mouse.release(button);
    }

    static boolean click(Interactable interactable) {
        return interactable != null && hover(interactable) && fastClick(Mouse.Button.LEFT);
    }

    static boolean turnAndInteract(final LocatableEntity obj, final String action) {
        return turnAndInteract(obj, action, false);
    }

    static boolean turnAndInteract(final LocatableEntity obj, final String action, final boolean force) {
        if (obj != null && (force || obj.getVisibility() <= 60)) {
            Camera.concurrentlyTurnTo(obj, 0.6);
        }
        return interact(obj, action);
    }

    static boolean turnAndWalk(final LocatableEntity obj) {
        return turnAndWalk(obj, false);
    }

    static boolean turnAndWalk(final LocatableEntity obj, final boolean force) {
        if (obj != null && (force || obj.getVisibility() <= 60)) {
            Camera.concurrentlyTurnTo(obj, Random.nextDouble(0.8, 1));
        }
        return turnAndWalk(obj);
    }

    static boolean turnAndClick(final LocatableEntity obj, final boolean force) {
        if (obj != null && (force || obj.getVisibility() <= 60)) {
            Camera.concurrentlyTurnTo(obj, Random.nextDouble(0.8, 1));
        }
        return hover(obj) && fastClick(Mouse.Button.LEFT);
    }

    static boolean turnAndClick(final LocatableEntity obj) {
        return turnAndClick(obj, false);
    }

    static boolean hoverMenu(Interactable interactable, Pattern menuName) {
        if (!Menu.isOpen()) {
            if (Mouse.move(interactable)) {
                fastClick(Mouse.Button.RIGHT);
            }
            return false;
        }
        if (Menu.isOpen()) {
            MenuItem menuItem = getMenuItem(menuName);
            if (menuItem == null) {
                Menu.close();
                return false;
            }
            return hover(menuItem);
        }
        return false;
    }

    static MenuItem getMenuItem(Pattern pattern) {
        return Menu.getItems().stream().filter(mi -> pattern.matcher(mi.getAction()).matches()).findFirst().orElse(null);
    }

    static String getMenuAction(String[] action) {
        return Arrays.stream(action).filter(act -> Menu.getItems().stream().anyMatch(item -> Objects.equals(act, item.getAction()))).findFirst().orElse(null);
    }

    static String getValidAction(final GameObject obj, final List<String> actions) {
        if (obj != null) {
            GameObjectDefinition def = obj.getDefinition();
            if (def != null) {
                return def.getActions().stream().filter(defAct -> actions.stream().anyMatch(act -> Objects.equals(defAct, act))).findFirst().orElse(null);
            }
        }
        return null;
    }

    static String getValidAction(final Npc obj, final List<String> actions) {
        if (obj != null) {
            NpcDefinition def = obj.getDefinition();
            if (def != null) {
                return def.getActions().stream().filter(defAct -> actions.stream().anyMatch(act -> Objects.equals(defAct, act))).findFirst().orElse(null);
            }
        }
        return null;
    }

    static String getValidAction(final SpriteItem obj, final List<String> actions) {
        if (obj != null) {
            ItemDefinition def = obj.getDefinition();
            if (def != null) {
                return def.getInventoryActions().stream().filter(defAct -> actions.stream().anyMatch(act -> Objects.equals(defAct, act))).findFirst().orElse(null);
            }
        }
        return null;
    }
}
