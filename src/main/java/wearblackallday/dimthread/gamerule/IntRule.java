package wearblackallday.dimthread.gamerule;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;

import java.util.function.BiConsumer;

public class IntRule extends GameRule<GameRules.IntegerValue> {

    protected IntRule(String name, GameRules.Category category, GameRules.Type<GameRules.IntegerValue> rule) {
        super(name, category, rule);
    }

    public static Builder builder(String name, GameRules.Category category) {
        return new Builder(name, category);
    }

    public static class Builder {
        private final String name;
        private final GameRules.Category category;

        private int initialValue = 0;
        private int minimumValue = Integer.MIN_VALUE;
        private int maximumValue = Integer.MAX_VALUE;
        private BiConsumer<MinecraftServer, GameRules.IntegerValue> callback = (server, rule) -> {
        };

        private Builder(String name, GameRules.Category category) {
            this.name = name;
            this.category = category;
        }

        public Builder setInitial(int initial) {
            this.initialValue = initial;
            return this;
        }

        public Builder setMin(int min) {
            this.minimumValue = min;
            return this;
        }

        public Builder setMax(int max) {
            this.maximumValue = max;
            return this;
        }

        public Builder setBounds(int min, int max) {
            return this.setMin(min).setMax(max);
        }

        public Builder setCallback(BiConsumer<MinecraftServer, GameRules.IntegerValue> callback) {
            this.callback = callback;
            return this;
        }

        public IntRule build() {
            return new IntRule(this.name, this.category, create(
                this.initialValue, this.minimumValue, this.maximumValue, this.callback));
        }

        private GameRules.Type<GameRules.IntegerValue> create(int def, int min, int max, BiConsumer<MinecraftServer, GameRules.IntegerValue> changedCallback) {
            return new GameRules.Type<>(
                () -> IntegerArgumentType.integer(min, max),
                type -> new RestrictedIntegerValue(type, def, min, max),
                changedCallback,
                GameRules.GameRuleTypeVisitor::visitInteger
            );
        }

    }
}