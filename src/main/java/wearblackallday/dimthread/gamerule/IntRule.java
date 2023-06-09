/*
 * This file is part of Dimensional Threading Reforged - https://github.com/CCr4ft3r/DimensionalThreading-Reforged
 * Copyright (C) WearBlackAllDay and contributors: https://github.com/WearBlackAllDay/DimensionalThreading
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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