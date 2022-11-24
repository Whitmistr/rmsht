package osrs.api;

import com.runemate.game.api.hybrid.entities.Actor;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.status.CombatGauge;
import com.runemate.game.api.hybrid.entities.status.Hitsplat;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.rs3.local.hud.Powers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ned on 03/07/2019.
 */
public interface Local {
    static Boolean isMoving() {
        final Player local = Players.getLocal();
        return local != null && local.isMoving();
    }

    static Boolean isAnimating() {
        final Player local = Players.getLocal();
        return local != null && local.getAnimationId() != -1;
    }

    static Coordinate getPosition() {
        final Player local = Players.getLocal();
        return local != null ? local.getPosition() : null;
    }

    static List<Hitsplat> getHitsplats() {
        final Player local = Players.getLocal();
        return local != null ? local.getHitsplats() : null;
    }

    static Actor getTarget() {
        final Player local = Players.getLocal();
        return local != null ? local.getTarget() : null;
    }

    static boolean isTarget(String name) {
        final Actor target = getTarget();
        return target != null && Objects.equals(target.getName(), name);
    }

    static boolean isTargetAlive() {
        final  Actor target = getTarget();
        if (target != null) {
            CombatGauge gauge = target.getHealthGauge();
            if (gauge != null) {
                return gauge.getPercent() > 0;
            }
        }
        return false;
    }

    static int getPrayerRemaining() {
        return Math.round((Powers.Prayer.getPoints() / Powers.Prayer.getMaximumPoints()) * 100);
    }

    static boolean isIdle() {
        final Player me = Players.getLocal();
        List<Integer> anims = Arrays.asList(18022, 18038, 18048);
        return me != null && anims.contains(me.getStanceId());
    }

    static boolean isInside(Area area) {
        final Player me = Players.getLocal();
        return me != null && area.contains(me);
    }
}
