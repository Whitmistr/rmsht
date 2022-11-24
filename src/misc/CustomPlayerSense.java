package misc;

import com.runemate.game.api.hybrid.player_sense.PlayerSense;
import com.runemate.game.api.hybrid.util.calculations.Random;

import java.util.function.Supplier;

public class CustomPlayerSense {
    public static void initializeKeys() {
        for (Key key : Key.values()) {
            if (PlayerSense.get(key.name) == null) {
                PlayerSense.put(key.name, key.supplier.get());
            }
        }
    }

    public enum Key {
        JOJO_ACTIVENESS_FACTOR_WHILE_WAITING("jojo_activeness_factor", () -> Random.nextDouble(0.2, 0.7)),
        JOJO_REACTION_TIME("jojo_reaction_time", () -> Random.nextLong(50, 100)),
        JOJO_CAMERA_TOLERANCE_HIGH("jojo_camera_tolerance_high", () -> Random.nextGaussian(50, 80, 70)),
        JOJO_FAST_MOUSE_CLICK_DELAY_AVERAGE("jojo_mouse_click_delay_average", () -> Random.nextLong(50, 70)),
        JOJO_FAST_MOUSE_CLICK_DELAY_HIGH("jojo_fast_mouse_click_delay_high", () -> Random.nextLong(100, 120)),
        JOJO_MAX_INTERACT_DELAY("jojo_max_interact_delay", () -> Random.nextInt(1500, 1800)),
        JOJO_MIN_INTERACT_DELAY("jojo_min_interact_delay", () -> Random.nextInt(1000, 1200)),
        JOJO_MAX_INTERACT_DELAY_LONG("jojo_max_interact_delay_long", () -> Random.nextInt(2400, 3600)),
        JOJO_MIN_INTERACT_DELAY_LONG("jojo_min_interact_delay_long", () -> Random.nextInt(1200, 1600)),
        JOJO_MAX_IDLE_INTERACT_DELAY("jojo_max_idle_interact_delay", () -> Random.nextInt(70, 100)),
        JOJO_MIN_IDLE_INTERACT_DELAY("jojo_min_idle_interact_delay", () -> Random.nextInt(30, 50)),
        JOJO_MAX_HERB("jojo_max_herb", () -> Random.nextInt(70, 90)),
        JOJO_MIN_HERB("jojo_min_herb", () -> Random.nextInt(50, 60)),
        JOJO_MAX_IDLE_INTERACT_DELAY_HUGE("jojo_max_interact_delay_huge", () -> Random.nextInt(5600, 6400)),
        JOJO_MIN_IDLE_INTERACT_DELAY_HUGE("jojo_min_interact_delay_huge", () -> Random.nextInt(4000, 4600)),
        JOJO_MIN_VISIBLITY_TOLERANCE("jojo_min_visibility_tolerance", () -> Random.nextGaussian(50, 85, 65)),
        JOJO_MAX_DISTANCE_ALLOWANCE("jojo_max_distance_allowance", () -> Random.nextInt(10, 16)),
        JOJO_USE_BANK_HOTKEYS("jojo_use_bank_hotkeys", Random::nextBoolean),
        JOJO_MOUSE_SPEED_MULTIPLIER("jojo_mouse_speed_multiplier", () -> Random.nextGaussian(2, 5, 3)),
        JOJO_USE_NUM_KEYS("jojo_use_num_keys", Random::nextBoolean);

        private final String name;
        private final Supplier supplier;

        Key(String name, Supplier supplier) {
            this.name = name;
            this.supplier = supplier;
        }

        public String getKey() {
            return name;
        }

        public Integer getAsInteger() {
            return PlayerSense.getAsInteger(name);
        }

        public Double getAsDouble() {
            return PlayerSense.getAsDouble(name);
        }

        public Long getAsLong() {
            return PlayerSense.getAsLong(name);
        }

        public Boolean getAsBoolean() {
            return PlayerSense.getAsBoolean(name);
        }
    }
}